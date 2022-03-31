import {useTranslation} from "react-i18next";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

const Login = () => {

    const {t} = useTranslation()

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [errMsg, setErrMsg] = useState("")
    const navigate = useNavigate()

    const login = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/login`, {
            method: "POST",
            body: JSON.stringify({
                "username": username,
                "password": password
            }),
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                if (!response.ok) {
                    setErrMsg(t("Passwort und / oder E-Mail sind falsch!"))
                    throw new Error()
                }
                return response.text()
            })
            .then(responseBody => {
                localStorage.setItem("token", responseBody)
                localStorage.setItem("username", username)
                setUsername("")
                setPassword("")
                navigate("../overview")
            })

    }

    return (

        <div>
            <input type={"text"} placeholder={t("Nutzername")}  value={username} onChange={event => setUsername(event.target.value)}/><br/>
            <input type={"password"} placeholder={t("Passwort")}  value={password} onChange={event => setPassword(event.target.value)}/><br/>
            <button onClick={login}>{t("Einloggen")}</button>
            <div>{errMsg}</div>
        </div>

    )

}

export default Login