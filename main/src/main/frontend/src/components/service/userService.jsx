import axios from 'axios'

export default class UserService{
  constructor(){
    const id = ""
  }

  async login(loginId, password){
    try{
      return(
      await axios.post(`http://localhost:9001/login/${loginId}`, {
        loginId: loginId,
        password: password,
      })
      .then((res) => {return res}))
    } catch (e) {
      console.error(e)
    }
  }

  async signUp(loginId, password, gender, weight){
    try{
      return(
      await axios.post(`http://localhost:9001/signup/${loginId}`, {
        loginId: loginId,
        password: password,
        gender: gender,
        weight: weight
      })
      .then((res) => {return res}))
    } catch(e) {
      console.error(e)
    }
  }

  async uploadFile(id, formData){
    try {
      return(
      await axios.post(`http://localhost:9001/upload/${id}`, formData).then((res) => {return res}));
    } catch (error) {
      console.error('Error uploading photo:', error);
    }
  };

  async getNutrition(id, nutrition){
    try {
      return(
      await axios.get(`http://localhost:9001/getNutrition/${id}/${nutrition}`).then((res) => {return res}));
    } catch (error) {
      console.error('Error getting nutrition:', error);
    }
  };

  async getNutritionDaily(id){
    try {
      return(
      await axios.get(`http://localhost:9001/getNutrition/daily/${id}`).then((res) => {return res}));
    } catch (error) {
      console.error('Error getting nutrition:', error);
    }
  };

  async getNutritionAll(id){
    try {
      return(
      await axios.get(`http://localhost:9001/getNutrition/all/${id}`).then((res) => {return res}));
    } catch (error) {
      console.error('Error getting nutrition:', error);
    }
  };

  async getRecommend(id){
    try {
      return(
      await axios.get(`http://localhost:9001/getNutrition/recommend/${id}`).then((res) => {return res}));
    } catch (error) {
      console.error('Error getting nutrition:', error);
    }
  };

  async searchFood(food){
    try {
      return(
      await axios.get(`http://localhost:9001/getNutrition/search/${food}`).then((res) => {return res}));
    } catch (error) {
      console.error('Error getting nutrition:', error);
    }
  };
}

