import {useCallback, useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {checkLogin} from "../Models/checkLogin";

const ChartItem = () => {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const [allItems, setAllItems] = useState([] as string[])

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
            .then((responseBody: Array<string>) => setAllItems(responseBody))
            .catch(() => navigate("../login"))
    }, [navigate])

    useEffect(() => {
        getAllItemNames()
    }, [getAllItemNames])

    return (
        <>
            {
                allItems.length > 0

                    ?
                    <select>
                        {
                            allItems.map(e => <option>{e}</option>)
                        }
                    </select>
                    :
                    t("Artikel werden geladen")
            }
        </>
    )
}

export default ChartItem