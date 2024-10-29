import React from 'react'
import { Link } from 'react-router-dom'
import "./nav_footer.css"
import { useLogin } from './context/LoginContext'

export default function Nav() {

  const { isLoggedIn, user} = useLogin()

  if (isLoggedIn === true) {
    console.log(user)
    return (
      <div className='nav'>
        <Link to = '/main' className="link">
          <img src="image/logo.png" alt = "" className='nav_image'/>
        </Link>
        
      <div className='signUp_Login'>
          <h3><Link to = '/main' className="link">메인페이지</Link></h3>
          <h3><Link to = '/dashboard' className="link">개인 대시보드</Link></h3>
          <h3><Link to = '/goal' className="link">관리목표</Link></h3>
      </div>
      <div className='nav_user'>
      <h4 className='nav_user_name'>{user.id}님</h4>
      {/* <button onClick={logout}>로그아웃</button> */}
      </div>
      </div>
    )
  }
  else {
    return (
        <div className='nav'>
          <Link to = '/main' className="link">
            <img src="image/logo.png" alt = "" className='nav_image'/>
          </Link>
          
        <div className='signUp_Login'>
            <h3><Link to = '/login' className="link">로그인</Link></h3>
            <h3><Link to = '/signup' className="link">회원가입</Link></h3>
        </div>
      </div>
    )
  }
  }
