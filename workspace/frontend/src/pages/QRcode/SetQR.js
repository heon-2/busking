import React from 'react';
import { SelectQR } from '../../components/QRcode/SelectQR';
import { Button } from '@material-tailwind/react';
import { useNavigate } from "react-router-dom";

export function SetQR() {
    let navigate = useNavigate();
    return (
        <div className='bg-blue-50 bg-opacity-50'>
            <h1>QR 생성하려는 정류장 선택 페이지</h1>

            <div className="logo p-4 mt-10 mb-20">
                <img src="/ssabus_logo.png" alt="싸버지 로고" className='h-80'/>
            </div>

            <SelectQR />

            <div className="btngroup">
                <Button onClick={()=> {
                    navigate("/scanQR");
                }}>QR 생성</Button>
                <Button className='bg-gray-400' onClick={()=> {
                    navigate("/map");
                }}>홈 화면 가기</Button>
            </div>
        </div>
    )
}