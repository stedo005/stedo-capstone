import {useTranslation} from "react-i18next";
import {useState} from "react";

const Overview = () => {

    const {t} = useTranslation()

    const [dateFrom, setDateFrom] = useState("")
    const [dateTo, setDateTo] = useState("")

    const refreshDatabase = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/getData/${localStorage.getItem("username")}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })

    }

    const sendDate = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/soldItems`, {
            method: "PUT",
            body: JSON.stringify({
                "dateFrom": dateFrom,
                "dateTo": dateTo
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })

    }

    return (
        <div>
            <button onClick={refreshDatabase}>{t("Datenbank aktualisieren")}</button>
            <br/><br/>
            von: <input type={"date"} value={dateFrom} onChange={e => setDateFrom(e.target.value)}/><br/>
            bis: <input type={"date"} value={dateTo} onChange={e => setDateTo(e.target.value)}/><br/><br/>
            <button onClick={sendDate}>senden</button>
        </div>
    )

}

export default Overview

