import {useNavigate, useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {result, savedCategories, soldItem} from "../Models/model";
import {checkLogin} from "../Models/checkLogin";
import {PieChart} from "../Charts/PieChart";

const EvaluateCategory = () => {

    interface dataEvaluateCategory{
        sales: Array<sales>
        quantities: Array<quantities>
        sumOfAllItems: number
    }

    interface sales {
        date: string
        sales: number
    }

    interface quantities{
        item: string
        quantity: number
    }

    const linkedId = useParams()
    const {t} = useTranslation()
    const navigate = useNavigate()

    const [dateFrom, setDateFrom] = useState("2022-01-01")
    const [dateTo, setDateTo] = useState("2022-01-10")
    const [currentCategory, setCurrentCategory] = useState({} as savedCategories)
    const [result, setResult] = useState(0)
    const [soldItems, setSoldItems] = useState([] as soldItem[])
    const [hide, setHide] = useState(true)
    const [calculationFactor, setCalculationFactor] = useState(2.5)
    let budget = 1 / calculationFactor * result
    let profit = result - budget
    const [chartLabels, setChartLabels] = useState([] as string[])

    const [data, setData] = useState({} as dataEvaluateCategory)
    const [quantities, setQuantities] = useState([] as quantities[])
    const [sales, setSales] = useState([] as sales [])

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category/${linkedId.categoryId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: savedCategories) => setCurrentCategory(responseBody))
            .catch(() => navigate("../login"))
    }, [linkedId.categoryId, navigate])

    const sendDate = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/soldItems`, {
            method: "PUT",
            body: JSON.stringify({
                "searchTerm": linkedId.categoryId,
                "dateFrom": dateFrom,
                "dateTo": dateTo
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: result) => {
                setResult(responseBody.sumOfAllItems)
                setSoldItems(responseBody.soldItems)
                setChartLabels([...currentCategory.itemsInCategory])
            })
            .catch(() => navigate("../login"))
    }

    const send = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/soldItems/query?searchTerm=${linkedId.categoryId}&dateFrom=${dateFrom}&dateTo=${dateTo}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: dataEvaluateCategory) => {
                setData(responseBody)
                setQuantities(responseBody.quantities)
                setSales(responseBody.sales)
            })
            .catch(() => navigate("../login"))
    }

    const getSumOfItems = (termToSearch: string) => {
        return soldItems.filter(e => e.itemName === termToSearch).length
    }

    return (
        <div className={"justify-content-center"} style={{color: "#003a44"}}>
            <div className={"head-category mx-auto mb-5 pt-4 pb-4"}>{currentCategory.categoryName}</div>
            <div className={""}>
                von: <input className={"background"} type={"date"} value={dateFrom}
                            onChange={e => setDateFrom(e.target.value)}/> bis: <input className={"background"}
                                                                                      type={"date"} value={dateTo}
                                                                                      onChange={e => setDateTo(e.target.value)}/>
            </div>
            <div>
                <label className={"mt-5"} htmlFor={"faktor"}>{t("Kalkulationsfaktor: ")}</label>
                <> {calculationFactor}</>
                <br/>
                <input
                    className={"mb-5"}
                    id={"faktor"}
                    type={"range"}
                    min={2}
                    max={3}
                    step={0.1}
                    onChange={e => setCalculationFactor(parseFloat(e.target.value))}
                    defaultValue={calculationFactor}
                />
            </div>
            <div className={"clickable btn-nav mx-auto"} onClick={sendDate}>{t("Budget anzeigen")}</div>
            <div className={"row"}>
                <div className={"col row align-items-center"}>
                    <div className={"result mx-auto my-5 px-5 py-4"}>
                        <div>{t("Umsatz: ")}{result.toFixed(2)} €</div>
                        <div>
                            {t("Rohertrag: ")}{profit.toFixed(2)} €
                        </div>
                        <div>
                            {t("Budget: ")}{budget.toFixed(2)} €
                        </div>
                    </div>
                </div>
                <div className={`bar-chart col-m my-5 mx-auto my-auto`}>
                    {
                        soldItems.length > 0
                            ?
                            <div className={""}>
                                <PieChart chartLabel={chartLabels}
                                          chartQuantity={[...currentCategory.itemsInCategory.map(e => getSumOfItems(e))]}/>
                            </div>
                            : <div>{t("Noch keine Daten zum anzeigen.")}</div>
                    }
                </div>
                <div className={"col-12"}>
                    <div className={"clickable btn-nav mx-auto my-5"}
                         onClick={() => hide ? setHide(false) : setHide(true)}>{t("Artikelansicht")}
                    </div>
                    <div className={"mb-5 mx-auto"} hidden={hide}>
                        {
                            soldItems.length > 0
                                ?
                                <div>{currentCategory.itemsInCategory.map(e => <div
                                    key={e}>{e}: {getSumOfItems(e)}</div>)}</div>
                                :
                                <div>{t("Noch nichts zum anzeigen da.")}</div>
                        }
                    </div>
                </div>
            </div>
        <button onClick={send}>test</button>
        <div>{quantities.length}</div>
        <div>{data.sumOfAllItems}</div>
        <div>{sales.map(e => e.sales)+", "}</div>
        </div>
    )
}

export default EvaluateCategory