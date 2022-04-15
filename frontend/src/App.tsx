import React, {useCallback, useEffect, useState} from 'react';
import {Outlet, useNavigate} from 'react-router-dom';
import {useTranslation} from "react-i18next";
import {checkLogin} from "./Models/checkLogin";
import {user} from "./Models/model";

function App() {

    const {t} = useTranslation()

    const navigate = useNavigate()
    const [lastUpdate, setLastUpdate] = useState("")

    const logout = () => {
        localStorage.removeItem("token")
        localStorage.removeItem("username")
    }

    useEffect(() => {
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
            .catch(() => {
                localStorage.removeItem("username")
                navigate("../login")
            })
    }, [navigate])

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

    const getLastUpdate = useCallback(() => {
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
    },[navigate])

    useEffect(() => {
        getLastUpdate()
    }, [getLastUpdate])

    return (
        <div>
            <span>
                <button onClick={() => navigate("../")}>{t("Home")}</button>
                <span> {t("letztes Update: ")}{new Date(lastUpdate).toLocaleDateString()} </span>
                <button onClick={refreshDatabase}>{t("Datenbank aktualisieren")}</button>
            </span>
            <span>
                <span> {localStorage.getItem("username") === null ? "" : "Du bist angemeldet als: " + localStorage.getItem("username")} </span>
                <button onClick={() => navigate("../login")}>{t("Login")}</button>
                <button onClick={() => {
                    logout()
                    navigate("../logout")
                }}>{t("Logout")}</button>
                <button onClick={() => navigate("../chartItem")}>{t("LineChart Artikel")}</button>
            </span>
            <br/><br/><br/>
            <Outlet/>
        </div>
    )
}

export default App;
