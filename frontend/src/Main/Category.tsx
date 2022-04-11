import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {savedCategories} from "../Models/model";

const Category = () => {

    const linkedId = useParams()
    const {t} = useTranslation()
    const navigate = useNavigate()

    const [allItemNames, setAllItemNames] = useState([] as Array<string>)
    const [category, setCategory] = useState({} as savedCategories)
    const [lengthItemsInCategory, setLengthItemsInCategory] = useState(0)
    const [arrItemsInCategory, setArrItemsInCategory] = useState([] as Array<string>)

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category/${linkedId.categoryId}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                return response.json()
            })
            .then((responseBody: savedCategories) => {
                setCategory(responseBody)
                setArrItemsInCategory(responseBody.itemsInCategory)
                setLengthItemsInCategory(responseBody.itemsInCategory.length)
            })
            .then(getAllItemNames)
    }, [linkedId.categoryId])

    const getAllItemNames = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/soldItems`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                return response.json()
            })
            .then((responseBody: Array<string>) => setAllItemNames(responseBody))
    }

    const saveItemsToCategory = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category`, {
            method: "PUT",
            body: JSON.stringify({
                "id": linkedId.categoryId,
                "categoryName": category.categoryName,
                "itemsInCategory": arrItemsInCategory
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(() => navigate("../categories"))
    }

    const saveAllItemsToCategory = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category`, {
            method: "PUT",
            body: JSON.stringify({
                "id": linkedId.categoryId,
                "categoryName": category.categoryName,
                "itemsInCategory": allItemNames
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(() => window.location.reload())
    }

    const removeAllItemsFromCategory = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category`, {
            method: "PUT",
            body: JSON.stringify({
                "id": linkedId.categoryId,
                "categoryName": category.categoryName,
                "itemsInCategory": []
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(() => window.location.reload())
    }

    const setCheckedDefault = (itemName: string) => {
        for (let i = lengthItemsInCategory; i >= 0; i--) {
            if (itemName === arrItemsInCategory[i]) {
                return true
            }
        }
        return false
    }

    const setItemsToCategory = (id: string, checked: boolean) => {

        const i = allItemNames.indexOf(id)
        checked
            ? arrItemsInCategory.push(allItemNames[i])
            : arrItemsInCategory.splice(arrItemsInCategory.indexOf(id), 1)

        console.log("a: " + arrItemsInCategory.length)

    }


    return (

        <div>
            {t("Kategorie: ")}{category.categoryName}<br/><br/>
            <div>{t("Artikel in Kategorie: ")}{lengthItemsInCategory}</div>
            <br/>
            <div>
                <button onClick={saveItemsToCategory}>{t("Speichern")}</button>
                <button onClick={saveAllItemsToCategory}>{t("alle Artikel")}</button>
                <button onClick={removeAllItemsFromCategory}>{t("keinen Artikel")}</button>
                {
                    allItemNames.length > 0
                        ?
                        allItemNames.map(n =>
                            <div key={n}>
                                <input
                                    className={"checkbox-item"}
                                    id={n}
                                    type={"checkbox"}
                                    value={n}
                                    defaultChecked={setCheckedDefault(n)}
                                    onChange={e => {
                                        setItemsToCategory(e.target.id, e.target.checked)
                                    }}
                                />
                                <label htmlFor={n}> {n}</label>
                            </div>)
                        : <p>{t("Artikel werden geladen!")}</p>
                }
                <button onClick={saveItemsToCategory}>{t("Speichern")}</button>
            </div>
        </div>

    )

}

export default Category