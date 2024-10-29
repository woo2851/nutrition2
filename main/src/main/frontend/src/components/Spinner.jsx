import React from 'react';
import './nav_footer.css'

const Spinner = () => {
  return (
    <div className='spinner_div'>
            <div className="spinner"></div>
            <h3 className='spinner_text'>로딩중입니다, 잠시만 기다려주세요</h3>
    </div>
  );
}

export default Spinner;