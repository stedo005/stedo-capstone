import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {user} from "../Models/model";

const Overview = () => {

    const {t} = useTranslation()

    const [lastUpdate, setLastUpdate] = useState("")

    const refreshDatabase = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/getData/${localStorage.getItem("username")}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
    }

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/users/${localStorage.getItem("username")}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {return response.json()})
            .then((responseBody: user) => {setLastUpdate(responseBody.lastUpdate)})
    }, [])


    return (
        <div>
            <p>letzte: {lastUpdate}</p>
            <button onClick={refreshDatabase}>{t("Datenbank aktualisieren")}</button>
            <br/><br/>
        </div>
    )

}

export default Overview

