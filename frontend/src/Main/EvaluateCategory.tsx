import {useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useState} from "react";
import {soldItem} from "../Models/model";


const EvaluateCategory = () => {

    const linkedId = useParams()
    const {t} = useTranslation()

    const [dateFrom, setDateFrom] = useState("")
    const [dateTo, setDateTo] = useState("")
    const [listOfItems, setListOfItems] = useState([] as soldItem[])

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
            .then(response => {return response.json()})
            .then((responseBody: soldItem[]) => setListOfItems(responseBody))

    }

    return (
        <div>

            {t("Kategorie")} mit id: {linkedId.categoryId}<br/><br/>
            von: <input type={"date"} value={dateFrom} onChange={e => setDateFrom(e.target.value)}/><br/>
            bis: <input type={"date"} value={dateTo} onChange={e => setDateTo(e.target.value)}/><br/><br/>
            <button onClick={sendDate}>senden</button>
            <div>
                {listOfItems.map(e => <div>{e.itemName}</div>)}
            </div>

        </div>
    )
}
export default EvaluateCategory