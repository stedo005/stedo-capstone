import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";

const Categories = () => {

    const {t} = useTranslation()

    const [categoryName, setCategoryName] = useState("")
    const [errMsg, setErrMsg] = useState("")

    useEffect(() => {
        const timeoutId = setTimeout(() => setErrMsg(''), 10000);
        return () => clearTimeout(timeoutId);
    }, [errMsg]);

    const createCategory = () => {


        fetch(`${process.env.REACT_APP_BASE_URL}/api/category`, {
            method: "POST",
            body: JSON.stringify({
                "categoryName": categoryName
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                if (response.status === 409) {
                    throw new Error(t("Diese Kategorie gibt es schon!"))
                } else if (response.status ===405) {
                    throw new Error(t("Keine Eingabe erfolgt!"))
                }
            })
            .then(() => setCategoryName(""))
            .catch((e: Error) => {
                setErrMsg(e.message)
            })
    }

    return (

        <div>
            <input type={"text"} placeholder={t("Name der Kategorie")} value={categoryName}
                   onChange={event => setCategoryName(event.target.value)}/>
            <button onClick={createCategory}>{t("neue Katergorie erstellen")}</button>
            {errMsg}
        </div>

    )
}

export default Categories