import {useCallback, useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {checkLogin} from "../Models/checkLogin";

const ChartItem = () => {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const [dateFrom, setDateFrom] = useState("2022-01-01")
    const [dateTo, setDateTo] = useState("2022-01-10")
    const [allItems, setAllItems] = useState([] as string[])
    const [currentItem, setCurrentItem] = useState("")

    const getAllItemNames = useCallback(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/soldItems`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: Array<string>) => setAllItems(responseBody))
            .catch(() => navigate("../login"))
    }, [navigate])

    useEffect(() => {
        getAllItemNames()
    }, [getAllItemNames])

    const sendDate = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/`, {
            method: "PUT",
            body: JSON.stringify({
                "currentItem": currentItem,
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
            .catch(() => navigate("../login"))
    }

    console.log(currentItem)

    return (
        <>
            von: <input type={"date"} value={dateFrom} onChange={e => setDateFrom(e.target.value)}/> bis: <input
            type={"date"} value={dateTo} onChange={e => setDateTo(e.target.value)}/><> </>
            <button onClick={sendDate}>{t("Zeige Chart")}</button>
            <br/><br/>
            {
                allItems.length > 0

                    ?
                    <select value={currentItem} onChange={e => setCurrentItem(e.target.value)}>
                        <option selected={true}>Bitte wählen</option>
                        {
                            allItems.map(e => <option key={e} value={e}>{e}</option>)
                        }
                    </select>
                    :
                    t("Artikel werden geladen")
            }
        </>
    )
}

export default ChartItem