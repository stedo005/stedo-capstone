import {useNavigate, useParams} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {savedCategories} from "../Models/model";
import {checkLogin} from "../Models/checkLogin";

const Category = () => {

    const linkedId = useParams()
    const {t} = useTranslation()
    const navigate = useNavigate()

    const [allItemNames, setAllItemNames] = useState([] as Array<string>)
    const [category, setCategory] = useState({} as savedCategories)
    const [itemsInCategory, setItemsInCategory] = useState([] as Array<string>)
    const [searchTherm, setSearchTherm] = useState("")

    const getAllItemNames = useCallback(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/soldItems`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: Array<string>) => setAllItemNames(responseBody))
            .catch(() => navigate("../login"))
    }, [navigate])

    useEffect(() => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/category/${linkedId.categoryId}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: savedCategories) => {
                setCategory(responseBody)
                setItemsInCategory(responseBody.itemsInCategory)
            })
            .then(getAllItemNames)
            .catch(() => navigate("../login"))
    }, [linkedId.categoryId, getAllItemNames, navigate])

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
            .then(response => {
                checkLogin(response)
            })
            .then(() => navigate("../"))
            .catch(() => navigate("../login"))
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

        <div className={"justify-content-center"} style={{color: "#06565b"}}>
            <div className={"head-category mx-auto mb-5 p-3"}>
                <div>{category.categoryName}</div>
                <div>{itemsInCategory.length}{t(" Artikel")}</div>
            </div>
            <div>
                <input
                    className={"background mb-4"}
                    type={"text"}
                    placeholder={t("Artikelsuche")}
                    value={searchTherm}
                    onChange={e => setSearchTherm(e.target.value)}
                />
                <div className={""}>
                    <div className={"clickable btn-nav mx-auto mb-4"} onClick={saveItemsToCategory}>{t("Speichern")}</div>
                    {
                        allItemNames.length > 0
                            ?
                            <>
                                <input className={"mb-4"} type={"checkbox"}
                                       id={"allChecked"}
                                       onChange={() =>
                                           allItemNames.length === itemsInCategory.length
                                               ? removeAllItemsFromCategory()
                                               : saveAllItemsToCategory()
                                       }
                                       checked={allItemNames.length === itemsInCategory.length}
                                /> <label htmlFor={"allChecked"}>{t(" alle auswählen")}</label>
                            </>

                            : <></>
                    }

                    {
                        allItemNames.length > 0
                            ?
                            allItemNames.filter(e => e.toLowerCase().includes(searchTherm.toLowerCase()))
                                .map(n =>
                                    <div className={""} key={n}>
                                        <input
                                            className={"col-1 checkbox-item"}
                                            id={n}
                                            type={"checkbox"}
                                            value={n}
                                            checked={setCheckedDefault(n)}
                                            onChange={e => {
                                                setItemsToCategory(e.target.id, e.target.checked)
                                            }}
                                        />
                                        <label className={"col-11"} style={{textAlign: "left"}} htmlFor={n}>{n}</label>
                                    </div>)
                            : <p>{t("Artikel werden geladen!")}</p>
                    }
                    <div className={"clickable btn-nav mx-auto mb-4 mt-4"} onClick={saveItemsToCategory}>{t("Speichern")}</div>
                </div>
            </div>
        </div>

    )

}

export default Category