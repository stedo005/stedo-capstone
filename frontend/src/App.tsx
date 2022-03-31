import React from 'react';
import {Link, Outlet} from 'react-router-dom';
import {useTranslation} from "react-i18next";

function App() {

    const {t} = useTranslation()

    return (
        <div>
            <div>
                <Link to={"login"}><button>{t("zum Login")}</button></Link>
            </div>
            <Outlet />
        </div>
    );
}

export default App;
