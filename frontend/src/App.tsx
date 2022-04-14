import React, {useEffect} from 'react';
import {Link, Outlet, useNavigate} from 'react-router-dom';
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
                <Link to={"login"}>
                    <button>{t("Login")}</button>
                </Link>
                <Link to={"logout"}>
                    <button onClick={logout}>{t("Logout")}</button>
                </Link>
                <Link to={"overview"}>
                    <button>{t("Übersicht")}</button>
                </Link>
                <button onClick={() => navigate("../categories")}>{t("Kategorien")}</button>
                <button onClick={() => navigate("../chartItem")}>{t("LineChart Artikel")}</button>
                <span> {localStorage.getItem("username") === null ? "" : "Du bist angemeldet als: " + localStorage.getItem("username")}</span>
            </div>
            <br/>
            <Outlet/>
        </div>
    )
}

export default App;
