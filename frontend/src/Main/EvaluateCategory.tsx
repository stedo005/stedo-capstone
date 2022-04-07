import {useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useState} from "react";
import {result, soldItem} from "../Models/model";


const EvaluateCategory = () => {

    const linkedId = useParams()
    const {t} = useTranslation()

    const [dateFrom, setDateFrom] = useState("2022-02-01")
    const [dateTo, setDateTo] = useState("2022-02-05")
    const [result, setResult] = useState(0)
    const [soldItems, setSoldItems] = useState([] as soldItem[])

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

    return (
        <div>

            {t("Kategorie")} mit id: {linkedId.categoryId}<br/><br/>
            von: <input type={"date"} value={dateFrom} onChange={e => setDateFrom(e.target.value)}/> bis: <input type={"date"} value={dateTo} onChange={e => setDateTo(e.target.value)}/><br/><br/>
            <button onClick={sendDate}>senden</button><br/><br/>
            <div>{t("Umsatz: ")}{result} €</div>
            {
                soldItems.length > 0
                ?
                    <div>{soldItems.map(e => <div>{e.itemName}</div>)}</div>
                :
                    <div></div>
            }

        </div>
    )
}
export default EvaluateCategory