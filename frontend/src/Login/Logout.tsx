import {useTranslation} from "react-i18next";

const Logout = () => {

    const {t} = useTranslation()

    return (

        <div>
            {t("Abmelden erfolgreich. Bis zum nächsten mal.")}
        </div>

    )

}

export default Logout