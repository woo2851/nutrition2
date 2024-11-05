import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Login from './components/js/Login';
import SignUp from './components/js/SignUp';
import Main from './components/js/Main';
import NotFound from './components/js/NotFound';
import Dashboard from './components/js/Dashboard';
import Goal from './components/js/Goal';
import Upload from './components/js/Upload';
import Spinner from './components/js/Spinner';
import Search from './components/js/Search';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App/>,
    errorElement: <NotFound/>,
    children: [
      {index: true, element: <Main/>},
      {path: 'login', element: <Login/>},
      {path: 'signup', element: <SignUp/>},
      {path: 'main', element: <Main/>},
      {path: 'dashboard', element: <Dashboard/>},
      {path: 'goal', element: <Goal/>},
      {path: 'search', element: <Search/>},
      {path: 'upload', element: <Upload/>},
      {path: 'loding', element: <Spinner/>},
    ]
  }
])


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <RouterProvider router={router} />
);
