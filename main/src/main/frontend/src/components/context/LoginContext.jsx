import React, { createContext, useState, useContext } from 'react';
import UserService from '../service/userService';
import { useNavigate } from 'react-router-dom';

const LoginContext = createContext();

export const useLogin = () => {
  return useContext(LoginContext);
}

export const LoginProvider = ({ children }) => {

  const navigate = useNavigate()
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(new UserService())

  const login = async (id,pw) => {
    const new_user = await user.login(id,pw)
    // localStorage.setItem('token', new_user);
    console.log(new_user)
    
    if(new_user.data !== "") {
      user.id = new_user.data
      setUser(user)
      navigate("/main")
      changeLogin()
      
    }
    else{
      alert("Invaild Id or Password")
    }
  }

  const signUp = async (id, pw, gender, weight) => {
    const new_user = await user.signUp(id, pw, gender, weight)
    console.log(new_user)
  
    if(new_user.data === "") {
      alert("user already exists")
    }
    else {
      const user_name = new_user.data
      user.id = user_name
      setUser(user)
      navigate("/main")
      changeLogin()
    }
  }

  const logout = () => {
    setUser(new UserService());
    changeLogin();
  }

  const changeLogin = () => {
    const new_isLoggedIn = !isLoggedIn
    return(setIsLoggedIn(new_isLoggedIn))
  }

  const uploadFile = async (user, formData, foodName) => {
    try{
      const result = await user.uploadFile(user.id, formData, foodName)
      if(result.data === true){
        navigate("/upload")
        alert("업로드 성공!")
      }
    }
    catch(e){
      navigate("/upload")
      alert("업로드 실패 식품명으로 추가해주세요")
      navigate("/search")
    }
  }

  const getNutritionContext = async (user, nutrition) => {
    if(nutrition === "kcal"){
      const result = await user.getNutrition(user.id, "kcal")
      return result.data
    }
    else if(nutrition === "탄수화물"){
      const result = await user.getNutrition(user.id, "carb")
      return result.data
    }
    else if(nutrition === "당류"){
      const result = await user.getNutrition(user.id, "sugar")
      return result.data
    }
    else if(nutrition === "지방"){
      const result = await user.getNutrition(user.id, "fat")
      return result.data
    }
    else if(nutrition === "단백질"){
      const result = await user.getNutrition(user.id, "protein")
      return result.data
    }
  }

  const getNutritionDailyContext = async (user, goal) => {
    if(goal === "다이어트"){
      const result = await user.getNutritionDaily(user.id, "diet")
      return result.data
    }
    else if(goal === "일반"){
      const result = await user.getNutritionDaily(user.id, "common")
      return result.data
    }
    else if(goal === "근성장"){
      const result = await user.getNutritionDaily(user.id, "muscle")
      return result.data
    }
  }

  const getNutritionAllContext = async (user) => {
    const result = await user.getNutritionAll(user.id)
    return result.data
  }

  const getNutritionAllIntake = async (user) => {
    const result = await user.getNutritionAllIntake(user.id)
    return result.data
  }

  const getRecommend = async (user) => {
    const result = await user.getRecommend(user.id)
    return result.data
  }

  const searchFood = async (food) => {
    const result = await user.searchFood(food)
    return result.data
  }

  const searchFoodByKcal = async (food, kcal) => {
    const result = await user.searchFoodByKcal(food, kcal)
    return result.data
  }

  const addFood = async (user, food) => {
    const result = await user.addFood(user.id, food)
    return result.data
  }

  return (
    <LoginContext.Provider value={{isLoggedIn, changeLogin, signUp, login, logout, user, uploadFile, getNutritionContext, getNutritionAllContext, getNutritionAllIntake, getNutritionDailyContext, setUser, getRecommend, searchFood, addFood, searchFoodByKcal}}>
      {children}
    </LoginContext.Provider>
  );
}