import React, {useCallback, useEffect, useState} from 'react';
import {Outlet, useNavigate} from 'react-router-dom';
import {useTranslation} from "react-i18next";
import {checkLogin} from "./Models/checkLogin";
import {user} from "./Models/model";
import logo from "./images/logo.png";

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
        <div className={"container-fluid background"}>
            <div className={"row pb-3 my-header"}>
                <div className={"col-sm-3 col-12"}>
                    <img className={"logo mt-4"} src={logo}/>
                </div>
                <div className={"row col-sm-6 col-12 justify-content-center mx-auto my-auto pt-3"}>
                    <div className={"clickable btn-nav col-12 align-content-center"}
                         onClick={() => navigate("../")}>{t("Kategorien")}</div>
                    <div className={"clickable btn-nav col-12"} onClick={refreshDatabase}>{t("Daten")} <i
                        className="bi bi-arrow-repeat"/>
                    </div>
                    <div className={"clickable btn-nav col-12"} onClick={() => {
                        logout()
                        navigate("../logout")
                    }}>{t("Logout")}</div>
                </div>
                <div className={"mb-3 mt-3 col-sm-3"}
                     style={{
                         fontSize: 18,
                         color: "#c2dde4"
                     }}>
                    {t("Daten vom: ")}{new Date(lastUpdate).toLocaleDateString()}
                </div>
            </div>
            {/*localStorage.getItem("username") === null ? "" : "Du bist angemeldet als: " + localStorage.getItem("username")*/}
            {/*<button onClick={() => navigate("../chartItem")}>{t("LineChartItem Artikel")}</button>*/}
            <Outlet/>
        </div>
    )
}

export default App;
