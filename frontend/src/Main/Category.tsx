import {useNavigate, useParams} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {savedCategories} from "../Models/model";

const Category = () => {

    const linkedId = useParams()
    const {t} = useTranslation()
    const navigate = useNavigate()

    const [allItemNames, setAllItemNames] = useState([] as Array<string>)
    const [category, setCategory] = useState({} as savedCategories)
    const [itemsInCategory, setItemsInCategory] = useState([] as Array<string>)

    const fetchAll = useCallback(() => {
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
                setItemsInCategory(responseBody.itemsInCategory)
            })
            .then(getAllItemNames)
    }, [linkedId.categoryId])

    useEffect(() => {
        fetchAll()
    }, [fetchAll])


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
                "itemsInCategory": itemsInCategory
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(() => navigate("../categories"))
    }

    const saveAllItemsToCategory = () => {
        setItemsInCategory([...allItemNames])
    }

    const removeAllItemsFromCategory = () => {
        setItemsInCategory([])
    }

    const setCheckedDefault = (itemName: string) => {
        return itemsInCategory.includes(itemName)
    }

    const setItemsToCategory = (id: string, checked: boolean) => {

        const i = allItemNames.indexOf(id)
        checked
            ? setItemsInCategory([...itemsInCategory, allItemNames[i]])
            : setItemsInCategory([...itemsInCategory.slice(0, itemsInCategory.indexOf(id)), ...itemsInCategory.slice(itemsInCategory.indexOf(id) + 1)])

    }

    return (

        <div>
            {t("Kategorie: ")}{category.categoryName}<br/><br/>
            <div>{t("Artikel in Kategorie: ")}{itemsInCategory.length}</div>
            <br/>
            <div>
                <button onClick={saveItemsToCategory}>{t("Speichern")}</button>
                {
                    allItemNames.length > 0
                        ?
                        <>
                            <input type={"checkbox"}
                                   id={"allChecked"}
                                   onChange={() =>
                                       allItemNames.length === itemsInCategory.length
                                           ? removeAllItemsFromCategory()
                                           : saveAllItemsToCategory()
                                   }
                                   checked={allItemNames.length === itemsInCategory.length}
                            />
                            <label htmlFor={"allChecked"}>{t(" alle auswählen")}</label>
                        </>

                        : <></>
                }

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
                                    checked={setCheckedDefault(n)}
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