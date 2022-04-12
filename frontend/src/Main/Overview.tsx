import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {user} from "../Models/model";
import {checkLogin} from "../Models/checkLogin";
import {useNavigate} from "react-router-dom";

const Overview = () => {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const [lastUpdate, setLastUpdate] = useState("")

    const refreshDatabase = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/getData/${localStorage.getItem("username")}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response =>
                checkLogin(response)
            )
            .then(getLastUpdate)
            .catch(() => navigate("../login"))
    }

    const getLastUpdate = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/users/${localStorage.getItem("username")}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: user) => {
                setLastUpdate(responseBody.lastUpdate)
            })
            .catch(() => navigate("../login"))
    }

    useEffect(() => {
        getLastUpdate()
    }, [])


    return (
        <div>
            <p>{t("letztes Update: ")}{new Date(lastUpdate).toLocaleDateString()}</p>
            <button onClick={refreshDatabase}>{t("Datenbank aktualisieren")}</button>
            <br/><br/>
        </div>
    )

}

export default Overview

