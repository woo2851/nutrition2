import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Login from './components/Login';
import SignUp from './components/SignUp';
import Main from './components/Main';
import MyPage from './components/MyPage';
import NotFound from './components/NotFound';
import Dashboard from './components/Dashboard';
import Goal from './components/Goal';
import Upload from './components/Upload';

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
      {path: 'mypage', element: <MyPage/>},
      {path: 'dashboard', element: <Dashboard/>},
      {path: 'goal', element: <Goal/>},
      {path: 'upload', element: <Upload/>},
    ]
  }
])


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <RouterProvider router={router} />
);
