import React, { createContext, useState, useContext, useEffect } from 'react';
import { useLogin } from './context/LoginContext'
export default function Goal() {

  const { isLoggedIn, changeLogin, login, logout, user, getNutritionContext} = useLogin()

  return (
    <div>Goal</div>
  )
}
