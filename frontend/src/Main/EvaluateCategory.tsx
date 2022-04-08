import {useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {result, savedCategories, soldItem} from "../Models/model";


const EvaluateCategory = () => {

    const linkedId = useParams()
    const {t} = useTranslation()

    const [dateFrom, setDateFrom] = useState("2022-01-01")
    const [dateTo, setDateTo] = useState("2022-01-10")
    const [currentCategory, setCurrentCategory] = useState({} as savedCategories)
    const [result, setResult] = useState(0)
    const [soldItems, setSoldItems] = useState([] as soldItem[])
    const [hide, setHide] = useState(true)
    const [calculationFactor, setCalculationFactor] = useState(2.5)
    let budget = 1 / calculationFactor * result
    let profit = result - budget

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category/${linkedId.categoryId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {return response.json()})
            .then((responseBody: savedCategories) => setCurrentCategory(responseBody))
    }, [linkedId.categoryId])

    const sendDate = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/soldItems`, {
            method: "PUT",
            body: JSON.stringify({
                "categoryId": linkedId.categoryId,
                "dateFrom": dateFrom,
                "dateTo": dateTo
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                return response.json()
            })
            .then((responseBody: result) => {
                setResult(responseBody.sumOfAllItems)
                setSoldItems(responseBody.soldItems)
            })
    }

    const getSumOfItems = (termToSearch: string) => {
        return soldItems.filter(e => e.itemName === termToSearch).length
    }

    return (
        <div>

            {t("Kategorie: ")}{currentCategory.categoryName}<br/><br/>
            von: <input type={"date"} value={dateFrom} onChange={e => setDateFrom(e.target.value)}/> bis: <input
            type={"date"} value={dateTo} onChange={e => setDateTo(e.target.value)}/><br/><br/>
            <button onClick={sendDate}>{t("Budget anzeigen")}</button>
            <br/><br/>
            <div>
                {t("Umsatz: ")}{result.toFixed(2)} €
            </div>
            <div>
                <label htmlFor={"faktor"}>{t("Kalkulationsfaktor: ")}</label>
                <input
                    id={"faktor"}
                    type={"number"}
                    value={calculationFactor}
                    onChange={e => setCalculationFactor(parseFloat(e.target.value))}
                />
            </div>
            <div>
                {t("Budget: ")}{budget.toFixed(2)} €
            </div>
            <div>
                {t("Rohertrag: ")}{profit.toFixed(2)} €
            </div>
            <br/>
            <button onClick={() => hide ? setHide(false) : setHide(true)}>{t("Artikelansicht an/aus")}</button>
            <br/><br/>
            <div hidden={hide}>
                {
                    soldItems.length > 0
                        ?
                        <div>{currentCategory.itemsInCategory.map(e => <div>{e} {getSumOfItems(e)}</div>)}</div>
                        :
                        <div>{t("Noch nichts zum anzeigen da.")}</div>
                }
            </div>
        </div>
    )
}
export default EvaluateCategory