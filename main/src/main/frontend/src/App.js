import React from 'react';
import { Outlet } from 'react-router-dom'
import Nav from './components/js/Nav';
import Footer from './components/js/Footer';
import { LoginProvider } from './components/context/LoginContext';

function App() {

    return (
        
    <LoginProvider>
        <Nav/>
            <Outlet/>
        <Footer/>
    </LoginProvider>
    );
}

export default App;
