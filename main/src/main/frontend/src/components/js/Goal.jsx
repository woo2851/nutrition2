import React, { useEffect, useState } from 'react';
import { useLogin } from '../context/LoginContext';
import '../css/login_signup.css';

export default function Goal() {
  const { user, isLoggedIn, getNutritionDailyContext, getRecommend } = useLogin();

  const [recommend, setRecommend] = useState("")
  const [description, setDescription] = useState("")
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
          { id: 1, nutrition: "kcal", current: nutrition[0][0], recommend: nutrition[1][0], difference: nutrition[1][0] - nutrition[0][0] >= 0 ? "부족" : "초과" },
          { id: 2, nutrition: "탄수화물", current: nutrition[0][1], recommend: nutrition[1][1], difference: nutrition[1][1] - nutrition[0][1] >= 0 ? "부족" : "초과"},
          { id: 3, nutrition: "당류", current: nutrition[0][2], recommend: nutrition[1][2], difference: nutrition[1][2] - nutrition[0][2] >= 0 ? "부족" : "초과"},
          { id: 4, nutrition: "지방", current: nutrition[0][3], recommend: nutrition[1][3], difference: nutrition[1][3] - nutrition[0][3] >= 0 ? "부족" : "초과"},
          { id: 5, nutrition: "단백질", current: nutrition[0][4], recommend: nutrition[1][4], difference: nutrition[1][4] - nutrition[0][4] >= 0 ? "부족" : "초과"},
        ]);
      };
      fetchData();
      const fetchRecommend = async () => {
        const recommend = await getRecommend(user)
        setRecommend(recommend[0])
        setDescription(recommend[1])
      }
      fetchRecommend()
    }
  }, [isLoggedIn, user, getNutritionDailyContext]);

  const onClick = async () => {
    const recommend = await getRecommend(user)
    setRecommend(recommend[0])
    setDescription(recommend[1])
  }

  return (
    <div className="goal">
      <div className="description">
        <h3>{user.id}님의 관리목표</h3>
        <div><h3>건강목표: 다이어트</h3></div>
        <div className="table-container">
          <table className="styled-table">
            <thead>
              <tr>
                <th>영양소</th>
                <th>섭취량</th>
                <th>권장 섭취량</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {nutritionData.map((item) => (
                <tr key={item.id}>
                  <td>{item.nutrition}</td>
                  <td>{item.current}</td>
                  <td>{item.recommend}</td>
                  <td>{item.difference}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className="feedback">
          <h3>피드백 :</h3>
        </div>
      </div>
      <div className="recommend">
        <h3>추천 식단</h3>
        <img src="image/recommend.jpg" alt="추천 식단" />
        <h3>{recommend}</h3>
        <h4>{description}</h4>
        <button className="small-button" onClick={onClick}>다시 추천</button>
      </div>
    </div>
  );
}
