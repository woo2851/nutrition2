import React, { useState }  from 'react'
import { useLogin } from './context/LoginContext'
import { useNavigate } from 'react-router-dom'
import "./nav_footer.css"

export default function Upload() {

    const navigate = useNavigate()
    const { isLoggedIn, logout, user, uploadFile, setUser} = useLogin()
    const [image, SetImage] = useState("")
    const [formData, SetformData] = useState(null)
    let selectedFile = null

    const handleFileChange = async (event) => {
        selectedFile = event.target.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(selectedFile);
        reader.onloadend = () => {
            SetImage(reader.result);
            user.image = reader.result
            setUser(user)
      };
      const formData = new FormData();
      formData.append('file', selectedFile);
      SetformData(formData) 
      console.log(formData)
    }

    const handleUpload = (e) => {
        e.preventDefault()
        uploadFile(user, formData)
      }

    return (
    <div>
    <input type="file" onChange={handleFileChange} id="avatar" name="file" accept="image/png, image/jpeg, image/jpg"/>
    <div className='file_preview'>
    {image && <img className='image' src={image} alt="preview" />}
        </div>
        <button type="button" onClick={handleUpload}>upload</button>
    </div>
  )
}
