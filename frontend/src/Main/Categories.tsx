import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {Category} from "../Models/model";
import {Link} from "react-router-dom";

const Categories = () => {

    const {t} = useTranslation()

    const [categoryName, setCategoryName] = useState("")
    const [categories, setCategories] = useState([] as Array<Category>)
    const itemsInCategory = [] as Array<string>

    const [errMsg, setErrMsg] = useState("")

    useEffect(() => {
        const timeoutId = setTimeout(() => setErrMsg(''), 10000);
        return () => clearTimeout(timeoutId);
    }, [errMsg]);

    const fetchCategories = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {return response.json()})
            .then((responseBody: Array<Category>) => setCategories(responseBody))
    }

    const createCategory = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/category`, {
            method: "POST",
            body: JSON.stringify({
                "categoryName": categoryName,
                "itemsInCategory": itemsInCategory
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
            .then(fetchCategories)
            .catch((e: Error) => {
                setErrMsg(e.message)
            })
    }

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {return response.json()})
            .then((responseBody: Array<Category>) => setCategories(responseBody))
    }, [])

    return (

        <div>
            <input type={"text"} placeholder={t("Name der Kategorie")} value={categoryName}
                   onChange={event => setCategoryName(event.target.value)}/>
            <button onClick={createCategory}>{t("neue Katergorie erstellen")}</button>
            {errMsg}<br/><br/>
            <div>{categories.map(e => <div key={e.id}><Link to={e.id}>{e.categoryName}</Link></div>)}</div>
        </div>

    )
}

export default Categories