import {useTranslation} from "react-i18next";


const Login = () => {

    const {t} = useTranslation()

    return (

        <div>
            <input type={"text"} placeholder={t("Nutzername")}/>
        </div>

    )

}

export default Login