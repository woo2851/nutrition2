import React, { useState, useEffect } from 'react'
import "./main.css"
import { useLogin } from './context/LoginContext'
import { Link } from 'react-router-dom'

export default function Main() {

  return (
    <div className = "Main">
      <div className='main_description'>
        <h2>Smart Nutri에서 편하게 식단 관리 시작</h2>
        <h1>영양정보를 효율적으로 관리하세요!</h1>
        <button className='main_button'><Link to = "/upload">이미지 업로드</Link></button>
      </div>
        <img src="image/main.jpg" alt="" />
    </div>
  );
}

