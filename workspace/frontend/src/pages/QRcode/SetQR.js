import React from 'react';
import { SelectQR } from '../../components/QRcode/SelectQR';
import { Button } from '@material-tailwind/react';
import { useNavigate } from "react-router-dom";

export function SetQR() {
    let navigate = useNavigate();
    return (
        <div className='h-full overflow-y-auto bg-opacity-50 bg-blue-50'>
            <div className="h-20 mt-10 text-2xl font-bold">
                탑승할 버스와 하차지를 선택하세요
            </div>
            <div className="flex justify-center p-4 mt-5 mb-14 logo">
            <div className="flex items-center justify-center bg-white rounded-full shadow-lg h-60 w-60">
            <img src="/ssabus_logo.png" alt="싸버지 로고" className='h-40'/>

            </div>
                
            </div>

            <div className="flex justify-center mb-10">
            <SelectQR />
            </div>

            <div className="flex flex-col gap-5 m-16 bg-opacity-50 bg-blue-50">
                <Button onClick={()=> {
                    navigate("/scanQR");
                }}>QR 생성</Button>
                <Button className='bg-gray-400' onClick={()=> {
                    navigate("/usermap");
                }}>홈 화면 가기</Button>
                
            </div>
        </div>
    )
}