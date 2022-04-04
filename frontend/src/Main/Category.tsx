import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {savedCategories} from "../Models/model";

const Category = () => {

    const linkedId = useParams()
    const {t} = useTranslation()

    const [allItemNames, setAllItemNames] = useState([] as Array<string>)
    const [category, setCategory] = useState({} as savedCategories)
    const [checked, setChecked] = useState(true)
    const [lengthItemsInCategory, setLengthItemsInCategory] = useState(0)
    const itemsInCategory = category.itemsInCategory

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
                setLengthItemsInCategory(responseBody.itemsInCategory.length)
            })
    }, [linkedId.categoryId])

    useEffect(() => {
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
    }, [])

    const addItemsToCategory = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category`, {
            method: "PUT",
            body: JSON.stringify({
                "id": linkedId.categoryId,
                "categoryName": category.categoryName,
                "itemsInCategory": itemsInCategory
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
    }

    /*    useEffect(() => {

            for (let i = category.itemsInCategory.length; i > 0; i--) {

                const itemToSetChecked = allItemNames.indexOf(itemsInCategory[i])
                if (allItemNames[itemToSetChecked]) {
                    setChecked(true)
                }

            }
        }, [])*/


    const setItemsToCategory = (value: string, checked: boolean) => {

        const i = allItemNames.indexOf(value)
        checked
            ? itemsInCategory.push(allItemNames[i])
            : itemsInCategory.splice(itemsInCategory.indexOf(value), 1)

        setLengthItemsInCategory(category.itemsInCategory.length)
        console.log(itemsInCategory)
    }

    return (

        <div>
            {t("Kategorie")} {category.categoryName} mit id: {linkedId.categoryId}<br/><br/>
            <div>
                <button onClick={() => alert(category.itemsInCategory.length)}>check</button>
                <button onClick={addItemsToCategory}>{t("Speichern")}</button>
                <div>{t("Artikel in Kategorie: ")}{lengthItemsInCategory}</div>
                {
                    allItemNames.length > 0
                        ? allItemNames.map(n =>
                            <div key={n}>
                                <input id={n} type={"checkbox"} value={n} defaultChecked={checked}
                                       onChange={e => setItemsToCategory(e.target.value, e.target.checked)}/>
                                <label htmlFor={n}> {n}</label>
                            </div>)
                        : <p>{t("Artikel werden geladen!")}</p>
                }
                <button onClick={addItemsToCategory}>{t("Speichern")}</button>
            </div>
        </div>

    )

}

export default Category