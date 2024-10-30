import React, { useEffect, useState } from 'react'
import '../css/login_signup.css'
import { Link } from 'react-router-dom';
import { useLogin } from '../context/LoginContext';

export default function SignUp() {

  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const [pwCheck, setPwCheck] = useState('');
  const [gender, setGender] = useState('');
  const [weight, setWeight] = useState('');
  const { signUp } = useLogin()

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
    if (isBlank === true) {
      alert("정보를 입력해주세요")
    }
    else if (pw !== pwCheck) {
      alert("비밀번호를 다시한번 확인해주세요")
    }
    else {
      console.log(gender)
      signUp(id, pw, gender, weight)
    }
  }

  const handleChange = event => {
    setGender(event.target.value)
  };

  const checkBlank = () => {
    if (id === "" || pw === "" || pwCheck === "" || weight === "") {
      return true
    }
    else {
      return false
    }
  }

  return (
    <div class="main-container">
        <div class="container">

<h1 className='login_h1'>회원가입</h1>

<ul class="links">
  <li>
    <Link to="/login"><a id="signin">로그인</a></Link>
  </li>
  <li>
    <Link to="/signup"><a id="signup">회원가입</a></Link>
  </li>
  <li>
    <a id="reset">초기화</a>
  </li>
</ul>

<form action="">
  <div class="first-input input__block first-input__block">
    <input placeholder="아이디" class="input" id="email" value={id} onChange={saveUserId} />
  </div>
  <div class="input__block">
    <input type="password" placeholder="비밀번호" class="input" id="password" value={pw} onChange={saveUserPw} />
  </div>
  <div class="input__block">
  <input type="password" placeholder="비밀번호확인" class="input" id="password" value={pwCheck} onChange={saveUserPwCheck} />
  </div>
  <div class="input__block">
  <input placeholder="체중" class="input" value={weight} onChange={saveUserWeight} />
  </div>

  <div className='radio-group-parent'>
    <h4>성별 :</h4>
    <div className="radio-group">
      <label>
        <input
          type="radio"
          value="male"
          checked={gender === 'male'}
          onChange={handleChange}
        />
        남성
      </label>
      <label>
        <input
          type="radio"
          value="female"
          checked={gender === 'female'}
          onChange={handleChange}
        />
        여성
      </label>
    </div>
  </div>
  <button class="signin__btn" onClick={handleClick}>
    회원가입
  </button>
</form>
<button class="google__btn">
  <i class="fa fa-google"></i>
  구글로그인
</button>
</div>
    </div>
    
  )
}