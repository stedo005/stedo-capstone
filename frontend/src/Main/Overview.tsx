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

    return (
        <div>
            <button onClick={refreshDatabase}>{t("Datenbank aktualisieren")}</button><br/><br/>
        </div>
    )

}

export default Overview

