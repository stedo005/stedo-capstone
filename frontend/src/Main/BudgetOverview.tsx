import {useNavigate} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {savedCategories} from "../Models/model";
import {checkLogin} from "../Models/checkLogin";


const BudgetOverview = () => {

    const navigate = useNavigate()

    const [categories, setCategories] = useState([] as Array<savedCategories>)

    const fetchCategories = useCallback( () => {
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
    },[navigate])

    useEffect(() => {
        fetchCategories()
    }, [fetchCategories])

    return (
        <>
            <div>
                {categories.map(e => <div
                    key={e.id}
                    className={"clickable m-3"}
                    onClick={() => {
                        localStorage.setItem("currentCategory", e.categoryName)
                        navigate(`../budget/${e.id}`)
                    }}>{e.categoryName}</div>)}
            </div>
        </>
    )
}

export default BudgetOverview