import React, { useState} from 'react'
import { Link } from 'react-router-dom';
import axios from 'axios';
import { useLogin } from './context/LoginContext';
import './login_signup.css'

export default function Login() {

  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const { isLoggedIn, changeLogin, login, logout, user} = useLogin()

  const saveUserId = event => {
    setId(event.target.value);
  };

  const saveUserPw = event => {
    setPw(event.target.value);
  };

  const handleClick = (e) => {
    e.preventDefault()
    const isBlank = checkBlank()
    if(isBlank == true) {
      alert("아이디와 비밀번호를 입력해주세요")
    }
    else{
      login(id,pw)
    }
  }
 
  const checkBlank = () => {
    if (id == "" || pw == ""){
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
         <input placeholder="Id" class="input" id="email" value={id} onChange={saveUserId}/>
      </div>
      <div class="input__block">
         <input type="password" placeholder="Password" class="input" id="password" value={pw} onChange={saveUserPw} onKeyDown={(e) => activeEnter(e)}   />
      </div>
      <button class="signin__btn" onClick={handleClick}>
        로그인
      </button>
      </form>
    <div class="separator">
      <p>OR</p>
    </div>
    <button class="google__btn">
      <i class="fa fa-google"></i>
      Sign in with Google
    </button>
  </div>
   )
}