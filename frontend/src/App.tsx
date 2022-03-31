import React from 'react';
import { Outlet } from 'react-router-dom';

function App() {

    return (
        <div>
            <div>
                Hallo
            </div>
            <Outlet />
        </div>
    );
}

export default App;
