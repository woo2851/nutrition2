import React from 'react'
import "../css/main.css"
import { Link } from 'react-router-dom'

export default function Main() {

  return (
    <div className = "Main">
      <div className='main_description'>
        <h2 className='main_h2'>Smart Nutri에서 편하게 식단 관리 시작</h2>
        <h1 className='main_h1'>영양정보를 효율적으로 관리하세요!</h1>
        <div className='main_buttons'>
        <button className='main-button'><Link to = "/upload" style={{ textDecoration: "none", color: "white", fontWeight: "bold" }}>이미지 업로드</Link></button>
        <button className='main-button'><Link to = "/search" style={{ textDecoration: "none", color: "white", fontWeight: "bold"}}>상품명 검색</Link></button>
        </div>
      </div>
        <img src="image/main.jpg" alt="" />
    </div>
  );
}

