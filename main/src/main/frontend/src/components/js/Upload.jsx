import React, { useState } from 'react';
import { useLogin } from '../context/LoginContext';
import { useNavigate } from 'react-router-dom';
import "../css/nav_footer.css";

export default function Upload() {
    const navigate = useNavigate();
    const { user, uploadFile } = useLogin();
    const [foodName, setFoodName] = useState("");
    const [image, SetImage] = useState("");
    const [formData, SetformData] = useState(null);
    let selectedFile = null;

    const handleFileChange = async (event) => {
        selectedFile = event.target.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(selectedFile);
        reader.onloadend = () => {
            SetImage(reader.result);
        };
        const formData = new FormData();
        formData.append('file', selectedFile);
        SetformData(formData);
        console.log(formData);
    };

    const handleUpload = (e) => {
        e.preventDefault();
        if(formData === null){
          alert("사진을 등록해주세요")
        }
        else{
          if(foodName === ""){
            alert("식품명을 입력해주세요")
          }
          else{
            navigate("/loding");
            uploadFile(user, formData, foodName);
          }
        }
    };

    const handleChange = (e) => {
      setFoodName(e.target.value)
    }

    return (
        <div>
            <h2 className='upload_h2'>File Upload</h2>
            <h3 className='upload_line'></h3>
            <div className='upload_container'>
                <div className='upload'>
                    <input
                        type="file"
                        onChange={handleFileChange}
                        id="avatar"
                        name="file"
                        accept="image/png, image/jpeg, image/jpg"
                    />
                    <div className='preview_description'>
                        {image && <h4>식품명 : </h4>}
                        {image && <input value={foodName} onChange={handleChange}/>}
                    </div>
                    <div className='file_preview'>
                        {image && <img className='image' src={image} alt="preview" />}
                    </div>
                    
                </div>
                <div className='upload_example'>
                    <h3>이미지 업로드 예시</h3>
                    <img className='image' src="image/cocoa2.jpg" alt="" />
                </div>
            </div>
            <div className='button-container'>
                <button type="button" onClick={handleUpload}>이미지 업로드</button>
            </div>
        </div>
    );
}
