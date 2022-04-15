import React, {useEffect} from 'react';
import {Outlet, useNavigate} from 'react-router-dom';
import {useTranslation} from "react-i18next";
import {checkLogin} from "./Models/checkLogin";

function App() {

    const {t} = useTranslation()

    const navigate = useNavigate()

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

    return (
        <div>
            <div>
                <button onClick={() => navigate("../overview")}>{t("Übersicht")}</button>
                <button onClick={() => navigate("../categories")}>{t("Kategorien")}</button>
                <button onClick={() => navigate("../chartItem")}>{t("LineChart Artikel")}</button>
                <span> {localStorage.getItem("username") === null ? "" : "Du bist angemeldet als: " + localStorage.getItem("username")} </span>
                <button onClick={() => navigate("../login")}>{t("Login")}</button>
                <button onClick={() => {
                    logout()
                    navigate("../logout")
                }}>{t("Logout")}</button>
            </div>
            <br/>
            <Outlet/>
        </div>
    )
}

export default App;
