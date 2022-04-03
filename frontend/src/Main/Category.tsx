import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";

const Category = () => {

    const linkedId = useParams()
    const {t} = useTranslation()
    const [allItemNames, setAllItemNames] = useState([] as Array<string>)
    const itemsInCategory = [] as Array<string>

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
                "itemsInCategory": itemsInCategory
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
    }

    const test = (value: string, checked: boolean) => {

        const i = allItemNames.indexOf(value)
        checked
            ? itemsInCategory.push(allItemNames[i])
            : itemsInCategory.splice(itemsInCategory.indexOf(value),1)

        console.log(itemsInCategory)
    }

    return(

        <div>
            Category mit id: {linkedId.categoryId}<br/><br/>
            <div>
                <button onClick={addItemsToCategory}>{t("Speichern")}</button>
                {allItemNames.map(n =>
                    <div key={n}>
                    <input id={n} type={"checkbox"} value={n} onChange={e => test(e.target.value, e.target.checked)}/>
                    <label htmlFor={n}> {n}</label>
                </div>)}
                <button onClick={addItemsToCategory}>{t("Speichern")}</button>
            </div>
        </div>

    )

}

export default Category