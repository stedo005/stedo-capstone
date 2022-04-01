import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";

const Overview = () => {

    const {t} = useTranslation()

    const [itemNames, setItemNames] = useState([] as Array<string>)

    const refreshDatabase = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/getData/${localStorage.getItem("username")}`,{
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })

    }

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/soldItems`,{
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {return response.json()})
            .then((responseBody: Array<string>) => setItemNames(responseBody))
    }, [])

    return (
        <div>
            <button onClick={refreshDatabase}>{t("Datenbank aktualisieren")}</button><br/><br/>
            <div>
                {itemNames.map(n => <div key={n}><input id={n} type={"checkbox"} value={n}/><label htmlFor={n}> {n}</label></div>)}
            </div>
        </div>
    )

}

export default Overview

