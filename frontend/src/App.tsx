import React from 'react';
import {Link, Outlet} from 'react-router-dom';
import {useTranslation} from "react-i18next";

function App() {

    const {t} = useTranslation()

    const logout = () => {
        localStorage.removeItem("token")
        localStorage.removeItem("username")
    }

    return (
        <div>
            <div>
                <Link to={"login"}><button>{t("zum Login")}</button></Link>
                <Link to={"logout"}><button onClick={logout}>{t("Logout")}</button></Link>
            </div><br/><br/>
            <Outlet />
        </div>
    );
}

export default App;
