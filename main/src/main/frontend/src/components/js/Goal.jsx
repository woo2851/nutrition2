import React, { useEffect, useState } from 'react';
import { useLogin } from '../context/LoginContext'
import '../css/login_signup.css'

export default function Goal() {

  const { user, isLoggedIn, getNutritionDailyContext } = useLogin()

  const [nutritionData, setNutritionData] = useState([
    { id: 1, nutrition:"kcal", current: "", recommend: 0},
    { id: 2, nutrition:"탄수화물", current: "", recommend: 0 },
    { id: 3, nutrition:"당류", current: "", recommend: 0 },
    { id: 4, nutrition:"지방", current: "", recommend: 0 },
    { id: 5, nutrition:"단백질", current: "", recommend: 0},
])

  useEffect(() => {
    if(isLoggedIn === true) {
      const data = async () => {
        const nutrition = await getNutritionDailyContext(user)
        setNutritionData([
          { id: 1, nutrition:"kcal", current: nutrition[0], recommend: 0},
          { id: 2, nutrition:"탄수화물", current: nutrition[1], recommend: 0 },
          { id: 3, nutrition:"당류", current: nutrition[2], recommend: 0 },
          { id: 4, nutrition:"지방", current: nutrition[3], recommend: 0 },
          { id: 5, nutrition:"단백질", current: nutrition[4], recommend: 0},
      ])
      }
      data()
    }
  }, [])

  return (
    <div className='goal'>
      <div className='description'>
      <h3>{user.id}님의 관리목표</h3>
      <div><h3>건강목표: 다이어트</h3></div>
      <div className="table-container">
            <table className="styled-table">
                <thead>
                    <tr>
                        <th>영양소</th>
                        <th>섭취량</th>
                        <th>권장 섭취량</th>
                    </tr>
                </thead>
                <tbody>
                    {nutritionData.map((item) => (
                        <tr key={item.id}>
                            <td>{item.nutrition}</td>
                            <td>{item.current}</td>
                            <td>{item.recommend}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
      <div className='feedback'>
        <h3>피드백 : </h3>
      </div>
      </div>
      <div className='recommend'>
        <h3>추천 식단</h3>
        <img src="image/recommend.jpg"alt="" />
        <h3>닭가슴살 샐러드</h3>
        <h4>설명</h4>
      </div>
    </div>
  )
}
