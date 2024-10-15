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

  async signUp(loginId, password){
    try{
      return(
      await axios.post(`http://localhost:9001/signup/${loginId}`, {
        loginId: loginId,
        password: password,
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
}

