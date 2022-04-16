import {useCallback, useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {checkLogin} from "../Models/checkLogin";
import {dataForItemChart} from "../Models/model";
import {LineChart} from "../Charts/LineChart";

const ChartItem = () => {

    const {t} = useTranslation()
    const navigate = useNavigate()

    const [dataForChart, setDataForChart] = useState([] as dataForItemChart[])
    const [dateFrom, setDateFrom] = useState("2022-01-01")
    const [dateTo, setDateTo] = useState("2022-01-10")
    const [allItems, setAllItems] = useState([] as string[])
    const [currentItem, setCurrentItem] = useState("")
    const [quantity, setQuantity] = useState([] as number[])
    const [sales, setSales] = useState([] as number[])
    const [labels, setLabels] = useState([] as string[])

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

    const sendDate = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/soldItems/itemChart`, {
            method: "PUT",
            body: JSON.stringify({
                "searchTerm": currentItem,
                "dateFrom": dateFrom,
                "dateTo": dateTo
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
            .then(response => {
                checkLogin(response)
                return response.json()
            })
            .then((responseBody: dataForItemChart[]) => {
                setDataForChart(responseBody)
            })
            .catch(() => navigate("../login"))
    }

    useEffect(() => {
        setQuantity([...dataForChart.map(e => e.quantity)])
        setSales([...dataForChart.map(e => e.sales)])
        setLabels([...dataForChart.map(e => e.date)])
    },[dataForChart])

    return (
        <>
            von: <input type={"date"} defaultValue={dateFrom} onChange={e => setDateFrom(e.target.value)}/> bis: <input
            type={"date"} value={dateTo} onChange={e => setDateTo(e.target.value)}/><> </>
            <button onClick={sendDate}>{t("Zeige LineChart")}</button><br/><br/>
            <br/><br/>
            {
                allItems.length > 0

                    ?
                    <select onChange={e => setCurrentItem(e.target.value)}>
                        <option value={currentItem}>Bitte wählen</option>
                        {
                            allItems.map(e => <option key={e} value={e}>{e}</option>)
                        }
                    </select>
                    :
                    t("Artikel werden geladen")
            }
            {
                quantity.length > 0

                    ? <LineChart chartQuantity={quantity} chartSales={sales} chartLabels={labels} />
                    : ""
            }
        </>
    )
}

export default ChartItem