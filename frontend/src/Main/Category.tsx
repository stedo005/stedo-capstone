import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {savedCategories} from "../Models/model";

const Category = () => {

    const linkedId = useParams()
    const {t} = useTranslation()

    const [allItemNames, setAllItemNames] = useState([] as Array<string>)
    const [category, setCategory] = useState({} as savedCategories)
    const [checked, setChecked] = useState(false)
    const itemsInCategory = category.itemsInCategory

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
            .then((responseBody: savedCategories) => setCategory(responseBody))
    }, [linkedId.categoryId])

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
            : itemsInCategory.splice(itemsInCategory.indexOf(value),1)

        console.log(itemsInCategory)
    }

    return(

        <div>
            {t("Kategorie")} {category.categoryName} mit id: {linkedId.categoryId}<br/><br/>
            <div>
                <button onClick={addItemsToCategory}>{t("Speichern")}</button>
                {allItemNames.map(n =>
                <div key={n}>
                    <input id={n} type={"checkbox"} value={n} defaultChecked={checked} onChange={e => setItemsToCategory(e.target.value, e.target.checked)}/>
                    <label htmlFor={n}> {n}</label>
                </div>)}
                <button onClick={addItemsToCategory}>{t("Speichern")}</button>
            </div>
        </div>

    )

}

export default Category