import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {savedCategories} from "../Models/model";
import {checkLogin} from "../Models/checkLogin";


const CompareOverview = () => {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const [categories, setCategories] = useState([] as Array<savedCategories>)

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

    useEffect(() => {
        fetchCategories()
    }, [fetchCategories])

    return (
        <>
            <div className={"head-category mx-auto mb-5 pt-4 pb-4"}>
                {t("Umsätze vergleichen")}
            </div>
            <div className={"maxWidth row justify-content-center"}>
                {categories.map(e => <div
                    key={e.id}
                    className={"clickable budget-category row align-items-center justify-content-center m-3 "}
                    onClick={() => {
                        localStorage.setItem("currentCategory", e.categoryName)
                        navigate(`../budget/${e.id}`)
                    }}>{e.categoryName}</div>)}
            </div>
        </>
    )
}

export default CompareOverview