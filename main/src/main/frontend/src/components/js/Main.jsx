import React from 'react'
import "../css/main.css"
import { Link } from 'react-router-dom'

export default function Main() {

  return (
    <div className = "Main">
      <div className='main_description'>
        <h2>Smart Nutri에서 편하게 식단 관리 시작</h2>
        <h1>영양정보를 효율적으로 관리하세요!</h1>
        <button className='main_button'><Link to = "/upload" style={{ textDecoration: "none", color: "white", fontWeight: "bold" }}>이미지 업로드</Link></button>
      </div>
        <img src="image/main.jpg" alt="" />
    </div>
  );
}

