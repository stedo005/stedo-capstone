import {useNavigate, useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useState} from "react";
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
    const [hide, setHide] = useState(true)
    const [calculationFactor, setCalculationFactor] = useState(2.5)

    const [itemQuantities, setItemQuantities] = useState([] as quantities[])
    const [sumAll, setSumAll] = useState(0)

    let budget = 1 / calculationFactor * sumAll
    let profit = sumAll - budget

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
                setItemQuantities(responseBody.quantities)
                setSumAll(responseBody.sumOfAllItems)
            })
            .catch(() => navigate("../login"))
    }

    return (
        <div className={"justify-content-center"} style={{color: "#003a44"}}>
            <div className={"head-category mx-auto mb-5 pt-4 pb-4"}>{localStorage.getItem("currentCategory")}</div>
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
            <div className={"clickable btn-nav mx-auto"} onClick={send}>{t("Budget anzeigen")}</div>
            <div className={"row"}>
                <div className={"col row align-items-center"}>
                    <div className={"result mx-auto my-5 px-5 py-4"}>
                        <div>{t("Umsatz: ")}{sumAll.toFixed(2)} €</div>
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
                        itemQuantities.length > 0
                            ?
                            <div className={""}>
                                <PieChart chartLabel={[...itemQuantities.map(e => e.item)]}
                                          chartQuantity={[...itemQuantities.map(e => e.quantity)]}/>
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
                            itemQuantities.length > 0
                                ?
                                <div>
                                    {itemQuantities.map(e => <div
                                    key={e.item}>{e.item} : {e.quantity}</div>)}
                                </div>
                                :
                                <div>{t("Noch nichts zum anzeigen da.")}</div>
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EvaluateCategory