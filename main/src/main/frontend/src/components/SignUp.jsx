import React, { useState } from 'react'
import './login_signup.css'
import { Link } from 'react-router-dom';
import { useLogin } from './context/LoginContext';

export default function SignUp() {

  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const { isLoggedIn, changeLogin, signUp, login, logout, user} = useLogin()

  const saveUserId = event => {
    setId(event.target.value);
  };

  const saveUserPw = event => {
    setPw(event.target.value);
  };

  const handleClick = (e) => {
    const isBlank = checkBlank()
    if(isBlank == true) {
      alert("please type your id or password")
    }
    else{
      signUp(id,pw)
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
    
    <h1 className='login_h1'>회원가입</h1>
  
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
    
    <form  action="" method="post">
      <div class="first-input input__block first-input__block">
         <input placeholder="Id" class="input" id="email" value={id} onChange={saveUserId}/>
      </div>
      <div class="input__block">
         <input type="password" placeholder="Password" class="input" id="password" value={pw} onChange={saveUserPw} onKeyDown={(e) => activeEnter(e)}/>
      </div>
      <button class="signin__btn" onClick={handleClick}>
        회원가입
      </button>
    </form>
    <div class="separator">
      <p>OR</p>
    </div>
    <button class="google__btn">
      <i class="fa fa-google"></i>
      Sign up with Google
    </button>
  </div>
  )
}