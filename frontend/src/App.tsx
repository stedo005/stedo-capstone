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

        fetch(`${process.env.REACT_APP_BASE_URL}/api/getData/${localStorage.getItem("username")}`, {
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
    }, [navigate])

    useEffect(() => {
        getLastUpdate()
    }, [getLastUpdate])

    return (
        <div className={"background row justify-content-center"}>
            <div className={"my-header"}>
                <div className={"row justify-content-center my-3"}>
                    <div className={"clickable btn-nav col-1 align-content-center"}
                         onClick={() => navigate("../")}>{t("Home")}</div>
                    <div className={"clickable btn-nav col-1"} onClick={refreshDatabase}>{t("Daten")} <i className="bi bi-arrow-repeat"/>
                    </div>
                    <div className={"clickable btn-nav col-1"} onClick={() => {
                        logout()
                        navigate("../logout")
                    }}>{t("Logout")}</div>
                </div>
                <div className={"mb-3"} style={{fontSize: 18, color:"#c2dde4"}}>{t("letztes Update: ")}{new Date(lastUpdate).toLocaleDateString()}</div>
            </div>
            {/*localStorage.getItem("username") === null ? "" : "Du bist angemeldet als: " + localStorage.getItem("username")*/}
            {/*<button onClick={() => navigate("../chartItem")}>{t("LineChartItem Artikel")}</button>*/}
            <Outlet/>
        </div>
    )
}

export default App;
