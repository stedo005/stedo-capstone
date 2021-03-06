import {useTranslation} from "react-i18next";
import {useCallback, useEffect, useState} from "react";
import {savedCategories} from "../Models/model";
import {useNavigate} from "react-router-dom";
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

    const fetchCategories = useCallback(() => {
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
        fetchCategories()
    }, [fetchCategories])

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

    const confirmDelete = (id: string) => {

        if (window.confirm(t("Wirklich l??schen?"))) {
            deleteCategory(id)
        }

    }

    return (

        <>
            <div className={"justify-content-center row"}>
                <div className={"clickable btn-new-category row justify-content-center align-items-center"}>
                    <div className={"p-2 text-center"} onClick={() => setHideNewCategory(false)}>
                        {t("neue Kategorie anlegen")}
                        {errMsg}
                    </div>
                    <div className={"text-center"} hidden={hideNewCategory}>
                        <input className={"mt-2 mb-3"} style={{background: "#66a4ac"}} type={"text"}
                               placeholder={t("Name der Kategorie")} value={categoryName}
                               onChange={event => setCategoryName(event.target.value)}/>
                        <i className={"clickable bi bi-check-circle-fill m-3"} onClick={() => {
                            createCategory()
                            setHideNewCategory(true)
                        }}/>
                        <i className="clickable bi bi-x-circle-fill m-1" onClick={() => setHideNewCategory(true)}/>
                    </div>
                </div>
                <br/><br/>
                <div className={"maxWidth row justify-content-center mt-3"}>
                    {categories.map(e =>
                        <div className={"category m-3 p-1 row align-items-center"}
                            key={e.id}>
                            <i className="clickable btn-del bi bi-trash-fill col-4 py-4"
                               onClick={() => confirmDelete(e.id)}/>
                            <div className={"clickable category-text col row m-0 align-content-center justify-content-center"}
                                 onClick={() => navigate(`../${e.id}`)}>
                                {e.categoryName}
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </>

    )
}

export default Categories