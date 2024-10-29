import React, { useState } from 'react'
import './login_signup.css'
import { Link } from 'react-router-dom';
import { useLogin } from './context/LoginContext';

export default function SignUp() {

  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const [pwCheck, setPwCheck] = useState('');
  const [selectedOption, setSelectedOption] = useState('');
  const [weight, setWeight] = useState(0);
  const { isLoggedIn, changeLogin, signUp, login, logout, user} = useLogin()

  const saveUserId = event => {
    setId(event.target.value);
  };

  const saveUserPw = event => {
    setPw(event.target.value);
  };

  const saveUserPwCheck = event => {
    setPwCheck(event.target.value);
  };

  const saveUserWeight = event => {
    setWeight(event.target.value);
  }

  const handleClick = (e) => {
    e.preventDefault()
    const isBlank = checkBlank()
    if(isBlank == true) {
      alert("아이디와 비밀번호를 입력해주세요")
    }
    else if(pw != pwCheck) {
      alert("비밀번호를 다시한번 확인해주세요")
    }
    else{
      console.log(id, pw)
      signUp(id,pw)
    }
  }

  const handleChange = (event) => {
    setSelectedOption(event.target.value);
  };

  const checkBlank = () => {
    if (id == "" || pw == "" || pwCheck == ""){
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
    
      <form action="">
      <div class="first-input input__block first-input__block">
         <input placeholder="아이디" class="input" id="email" value={id} onChange={saveUserId}/>
      </div>
      <div class="input__block">
         <input type="password" placeholder="비밀번호" class="input" id="password" value={pw} onChange={saveUserPw}/>
         <input type="password" placeholder="비밀번호확인" class="input" id="password" value={pwCheck} onChange={saveUserPwCheck} onKeyDown={(e) => activeEnter(e)}/>
         <input placeholder="체중" class="input" value={weight} onChange={saveUserWeight}/>
         
      </div>
      <div className='radio-group-parent'>
      <h4>성별:</h4>
      <div className="radio-group">
        <label>
          <input
            type="radio"
            value="option1"
            checked={selectedOption === 'option1'}
            onChange={handleChange}
          />
          남자
        </label>
        <label>
          <input
            type="radio"
            value="option2"
            checked={selectedOption === 'option2'}
            onChange={handleChange}
          />
          여자
        </label>
      </div>
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
      구글로그인 
    </button>
  </div>
  )
}