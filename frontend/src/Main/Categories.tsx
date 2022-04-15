import {useTranslation} from "react-i18next";
import {useEffect, useState} from "react";
import {savedCategories} from "../Models/model";
import {Link, useNavigate} from "react-router-dom";
import {checkLogin} from "../Models/checkLogin";

const Categories = () => {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const [categoryName, setCategoryName] = useState("")
    const [categories, setCategories] = useState([] as Array<savedCategories>)
    const [hideNewCategory, setHideNewCategory] = useState(true)
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
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: Array<savedCategories>) => setCategories(responseBody))
            .catch(() => navigate("../login"))
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
                checkLogin(response)
                if (response.status === 409) {
                    throw new Error(t("Diese Kategorie gibt es schon!"))
                } else if (response.status === 400) {
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
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: Array<savedCategories>) => setCategories(responseBody))
            .catch(() => navigate("../login"))
    }, [navigate])

    const deleteCategory = (id: string) => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category/${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                checkLogin(response)
            })
            .then(fetchCategories)
            .catch(() => navigate("../login"))
    }

    return (

        <div className={"container"}>
            <button onClick={() => setHideNewCategory(false)}>{t("Katergorie anlegen")}</button>
            {errMsg}
            <div hidden={hideNewCategory}>
                <input className={""} type={"text"} placeholder={t("Name der Kategorie")} value={categoryName}
                       onChange={event => setCategoryName(event.target.value)}/>
                <i className={"clickable bi bi-check-circle-fill m-1"} onClick={() => {
                    createCategory()
                    setHideNewCategory(true)
                }}/>
                <i className="clickable bi bi-x-circle-fill m-1" onClick={() => setHideNewCategory(true)}/>
            </div>
            <br/><br/>
            <div className={"categories"}>{categories.map(
                e => <div className={"clickable category m-1"} onClick={() => navigate(`evaluate/${e.id}`)} key={e.id}>
                    <div>{e.categoryName}</div>
                    <i className="clickable bi bi-pencil-square m-1" onClick={() => navigate(`${e.id}`)}/>
                    <i className="clickable bi bi-trash-fill m-1" onClick={() => deleteCategory(e.id)}/>
                </div>)}
            </div>
        </div>

    )
}

export default Categories