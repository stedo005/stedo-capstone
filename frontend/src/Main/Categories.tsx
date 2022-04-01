import {useTranslation} from "react-i18next";
import {useState} from "react";

const Categories = () => {

    const {t} = useTranslation()

    const [categoryName, setCategoryName] = useState("");

    const createCategory = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/category`, {
            method: "POST",
            body: JSON.stringify({
                "categoryName": categoryName
            }),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        })
    }

    return(

        <div>
            <input type={"text"} placeholder={t("Name der Kategorie")} value={categoryName} onChange={event => setCategoryName(event.target.value)}/>
            <button onClick={createCategory}>{t("neue Katergorie erstellen")}</button>
        </div>

    )
}

export default Categories