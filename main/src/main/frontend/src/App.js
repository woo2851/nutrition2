import React, {useEffect, useState} from 'react';
import { Outlet } from 'react-router-dom'
import axios from 'axios';
import Nav from './components/Nav';
import Footer from './components/Footer';
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
