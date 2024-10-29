import React from 'react';
import { useLogin } from './context/LoginContext'

export default function Goal() {

  const { user } = useLogin()


  return (
    <div>{user.id}님의 관리목표</div>
  )
}
