import {useTranslation} from "react-i18next";
import {useState} from "react";


const Login = () => {

    const {t} = useTranslation()

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    return (

        <div>
            <input type={"text"} placeholder={t("Nutzername")}  value={username} onChange={event => setUsername(event.target.value)}/><br/>
            <input type={"password"} placeholder={t("Passwort")}  value={password} onChange={event => setPassword(event.target.value)}/><br/>
            <button>{t("Einloggen")}</button>
        </div>

    )

}

export default Login