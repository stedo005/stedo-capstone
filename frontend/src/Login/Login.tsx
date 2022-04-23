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
                    throw new Error(t("Passwort und / oder E-Mail sind falsch!"))
                }
                return response.text()
            })
            .then(responseBody => {
                localStorage.setItem("token", responseBody)
                localStorage.setItem("username", username)
                setUsername("")
                setPassword("")
                navigate("../")
            })
            .catch((e: Error) => setErrMsg(e.message))
    }

    return (

        <div className={"row justify-content-center"}>
            <div>
                <input type={"text"} placeholder={t("Nutzername")} style={{background: "#66a4ac"}} value={username}
                       onChange={event => setUsername(event.target.value)}/><br/>
                <input type={"password"} placeholder={t("Passwort")} style={{background: "#66a4ac"}} value={password}
                       onChange={event => setPassword(event.target.value)}/><br/><br/>
            </div>
            <div className={"clickable btn-nav"} onClick={login}>{t("Anmelden")}</div>
            <div style={{color:"red"}}>{errMsg}</div>
        </div>

    )

}

export default Login