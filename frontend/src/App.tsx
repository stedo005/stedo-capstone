import React from 'react';
import {Link, Outlet, useNavigate} from 'react-router-dom';
import {useTranslation} from "react-i18next";

function App() {

    const {t} = useTranslation()

    const navigate = useNavigate()

    const logout = () => {
        localStorage.removeItem("token")
        localStorage.removeItem("username")
    }

/*    useEffect(() => {
        navigate("../overview")
    }, [])*/

    return (
        <div>
            <div>
                <Link to={"login"}><button>{t("zum Login")}</button></Link>
                <Link to={"logout"}><button onClick={logout}>{t("Logout")}</button></Link>
                <Link to={"overview"}><button>{t("Übersicht")}</button></Link>
                <button onClick={() => navigate("../categories")}>Kategorien</button>
            </div><br/>
            <Outlet />
        </div>
    );
}

export default App;
