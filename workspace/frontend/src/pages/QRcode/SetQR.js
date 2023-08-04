import React from 'react';
import { SelectQR } from '../../components/QRcode/SelectQR';
import { Button } from '@material-tailwind/react';
import { useNavigate } from "react-router-dom";

export function SetQR() {
    let navigate = useNavigate();
    return (
        <div className='bg-blue-50 bg-opacity-50 h-screen'>
            <div className="font-bold text-2xl h-20 ">
                탑승할 버스와 하차지를 선택하세요
            </div>
            <div className="logo p-4 mt-10 mb-20 flex justify-center">
            <div className="h-96 w-96 rounded-full bg-white shadow-lg flex justify-center items-center">
            <img src="/ssabus_logo.png" alt="싸버지 로고" className='h-80'/>

            </div>
                
            </div>

            <div className="flex justify-center mb-10">
            <SelectQR />
            </div>

            <div className="flex flex-col gap-5 m-16 bg-blue-50">
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