import React, { useState, useEffect } from 'react';
import Chart from 'chart.js/auto';
import { Line } from 'react-chartjs-2';
import { useLogin } from './context/LoginContext'
import './nav_footer.css'

export default function Dashboard() {

  const { isLoggedIn, user, getNutritionContext, getNutritionAllContext} = useLogin()

  const nutrition_list = ["kcal", "탄수화물", "당류", "지방", "단백질"]

  const [selectedOption, setSelectedOption] = useState('kcal');
  const [chartData, setChartData] = useState(
      {
        labels: [],
        datasets: [
          {
            type: 'bar',
            label: '섭취한 영양소',
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'red',
            borderWidth: 2,
          },
        ],
    }
  )

  const [chartOptions, setChartOptions] = useState({
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
    },
  });

  useEffect(() => {
    if (isLoggedIn === true){
      const data = async () => {
        try {
          const nutrition = await getNutritionAllContext(user)
          console.log(nutrition)
          setChartData({
            labels: nutrition[0],
            datasets: [
              {
                ...chartData.datasets[0],
                label: nutrition_list[0],
                data: nutrition[1],
                backgroundColor: 'rgb(255, 99, 132, 0.7)',
                borderColor: 'rgb(255, 99, 132, 0.7)'
              },
              {
                ...chartData.datasets[0],
                label: nutrition_list[1],
                data: nutrition[2],
                backgroundColor: 'rgb(54, 162, 235, 0.7)',
                borderColor: 'rgb(54, 162, 235, 0.7)'
              },
              {
                ...chartData.datasets[0],
                label: nutrition_list[2],
                data: nutrition[3],
                backgroundColor:  'rgb(255, 206, 86, 0.7)',
                borderColor: 'rgb(255, 206, 86, 0.7)'
              },
              {
                ...chartData.datasets[0],
                label: nutrition_list[3],
                data: nutrition[4],
                backgroundColor: 'rgb(75, 192, 192, 0.7)',
                borderColor: 'rgb(75, 192, 192, 0.7)'
              },
              {
                ...chartData.datasets[0],
                label: nutrition_list[4],
                data: nutrition[5],
                backgroundColor: 'rgb(153, 102, 255, 0.7)',
                borderColor: 'rgb(153, 102, 255, 0.7)'
              }
            ]
          })
        } catch (error) {
          console.error('Error fetching data:', error);
        }
    }
    data()
    };
  }, [])
  const handleSelectChange = (event) => {
    setSelectedOption(event.target.value);  
  };

  const getNutrition = async (e) => {
    e.preventDefault()
    const nutrition = await getNutritionContext(user, selectedOption)
    console.log(nutrition)

    setChartData({
      labels: nutrition[0],
      datasets: [
        {
          ...chartData.datasets[0],
          label: selectedOption,
          data: nutrition[1]
        }
      ]
    })
  }
  
return (
  <div className='dashboard'>
    <div className='dashboard_search'>
    <label for="fruits">영양소를 선택하세요:</label>
  <select value={selectedOption} onChange={handleSelectChange}>
      <option value="kcal">kcal</option>
      <option value="탄수화물">탄수화물</option>
      <option value="당류">당류</option>
      <option value="지방">지방</option>
      <option value="단백질">단백질</option>
  </select>
  <button onClick={getNutrition}>검색</button>
    </div>
    <div className='chart'>
        <Line type="line" data={chartData} options={chartOptions}/>
      </div>
  </div>
  );
}
