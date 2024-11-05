import React, { useEffect, useState } from 'react';
import { useLogin } from '../context/LoginContext';
import '../css/login_signup.css';
import '@fortawesome/fontawesome-free/css/all.min.css';

export default function Goal() {
  const { user, isLoggedIn, getNutritionDailyContext } = useLogin();
  const [nutritionData, setNutritionData] = useState([
    { id: 1, nutrition: "kcal", current: "", recommend: 0 },
    { id: 2, nutrition: "탄수화물", current: "", recommend: 0 },
    { id: 3, nutrition: "당류", current: "", recommend: 0 },
    { id: 4, nutrition: "지방", current: "", recommend: 0 },
    { id: 5, nutrition: "단백질", current: "", recommend: 0 },
  ]);

  useEffect(() => {
    if (isLoggedIn) {
      const fetchData = async () => {
        const nutrition = await getNutritionDailyContext(user);
        setNutritionData([
          { id: 1, nutrition: "kcal", current: nutrition[0][0], recommend: nutrition[1][0] },
          { id: 2, nutrition: "탄수화물", current: nutrition[0][1], recommend: nutrition[1][1] },
          { id: 3, nutrition: "당류", current: nutrition[0][2], recommend: nutrition[1][2] },
          { id: 4, nutrition: "지방", current: nutrition[0][3], recommend: nutrition[1][3] },
          { id: 5, nutrition: "단백질", current: nutrition[0][4], recommend: nutrition[1][4] },
        ]);
      };
      fetchData();
    }
  }, [isLoggedIn, user, getNutritionDailyContext]);

  return (
    <div className="goal-container">
      <div className="profile-sidebar">
        <i className="fas fa-user profile-icon"></i>
        <h3>{user.id}님의 관리목표</h3>
        <div className="goal-selection">
          <label htmlFor="goalSelect">목표 선택: </label>
          <select id="goalSelect" className="goal-dropdown">
            <option value="normal">일반</option>
            <option value="diet">다이어트</option>
            <option value="muscle">근성장</option>
          </select>
        </div>
        <div className="button-group">
          <button className="day-button">일간</button>
          <button className="week-button">주간</button>
        </div>
        <div className="feedback">
          <h3>피드백 :</h3>
        </div>
      </div>
      {/* 표와 추천 식단 */}
      <div className="main-content">
      <h3>건강목표: 다이어트</h3>
        <div className="content-section">
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
          {/* 추천 식단 */}
          <div className="recommend">
            <h3>추천 식단</h3>
            <img src="image/recommend.jpg" alt="추천 식단" />
            <h3>닭가슴살 샐러드</h3>
            <h4>설명</h4>
          </div>
        </div>
      </div>
    </div>
  );
}
