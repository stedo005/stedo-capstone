import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";

const Category = () => {

    const linkedId = useParams()
    const {t} = useTranslation()
    const [itemNames, setItemNames] = useState([] as Array<string>)
    const itemInCategory = [] as Array<string>

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
            .then((responseBody: Array<string>) => setItemNames(responseBody))
    }, [])

    const test = (value: string, checked: boolean) => {

        const i = itemNames.indexOf(value)
        checked
            ? itemInCategory.push(itemNames[i])
            : itemInCategory.splice(itemInCategory.indexOf(value),1)

        console.log(itemInCategory)
    }

    return(

        <div>
            Category mit id: {linkedId.categoryId}<br/><br/>
            <div>
                {itemNames.map(n =>
                    <div key={n}>
                    <input id={n} type={"checkbox"} value={n} onChange={e => test(e.target.value, e.target.checked)}/>
                    <label htmlFor={n}> {n}</label>
                </div>)}
            </div>
        </div>

    )

}

export default Category