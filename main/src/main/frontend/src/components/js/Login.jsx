import React, { useState} from 'react'
import { Link } from 'react-router-dom';
import { useLogin } from '../context/LoginContext';
import '../css/login_signup.css'
import '@fortawesome/fontawesome-free/css/all.min.css';
// npm install @fortawesome/react-fontawesome @fortawesome/free-brands-svg-icons 아이콘 설치했음

export default function Login() {

  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const { login } = useLogin()

  const saveUserId = event => {
    setId(event.target.value);
  };

  const saveUserPw = event => {
    setPw(event.target.value);
  };

  const handleClick = (e) => {
    e.preventDefault()
    const isBlank = checkBlank()
    if(isBlank === true) {
      alert("아이디와 비밀번호를 입력해주세요")
    }
    else{
      login(id,pw)
    }
  }
 
  const checkBlank = () => {
    if (id === "" || pw === ""){
      return true
    }
    else{
      return false
    }
  } 

  const activeEnter = (e) => {
    if(e.key === "Enter") {
      handleClick()
    }
  }

   return (
    <div class="main-container">
      <div class="container">
    
    <h1 className='login_h1'>로그인</h1>
    
  
    <ul class="links">
      <li>
        <Link to = "/login"><a id="signin">로그인</a></Link>
      </li>
      <li>
        <Link to = "/signup"><a id="signup">회원가입</a></Link>
      </li>
      <li>
        <a id="reset">초기화</a>
      </li>
    </ul>
    <form action="">
      <div class="first-input input__block first-input__block">
         <input placeholder="아이디" class="input" id="email" value={id} onChange={saveUserId}/>
      </div>
      <div class="input__block"> 
         <input type="password" placeholder="비밀번호" class="input" id="password" value={pw} onChange={saveUserPw} onKeyDown={(e) => activeEnter(e)}   />
      </div>
      <button class="signin__btn" onClick={handleClick}>
        로그인
      </button>
      </form>
    
    <button class="google__btn">
      <i class="fab fa-google"></i>
      구글로그인
    </button> 
  </div>
    </div>
    
   )
}