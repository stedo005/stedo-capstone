import React, { Suspense } from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from './Login/Login';
import Logout from './Login/Logout';
import Categories from "./Main/Categories";
import Category from './Main/Category';
import EvaluateCategory from "./Main/EvaluateCategory";
import ChartItem from "./Main/ChartItem";
import "./index.css";
import "./i18n";

ReactDOM.render(
    <React.StrictMode>
        <Suspense fallback={"Loading..."}>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<App />}>
                        <Route path={"/login"} element={<Login />}/>
                        <Route path={"/logout"} element={<Logout />}/>
                        <Route path={"/:categoryId"} element={<Category />} />
                        <Route path={"/evaluate/:categoryId"} element={<EvaluateCategory />} />
                        <Route path={"/chartItem"} element={<ChartItem />} />
                        <Route path={"/"} element={<Categories />} />
                    </Route>
                </Routes>
            </BrowserRouter>
        </Suspense>
    </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
