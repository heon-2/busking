import React from 'react';
import { FaBeer } from 'react-icons/fa';
import { BiBus } from 'react-icons/bi';
import { Button } from "@material-tailwind/react";
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {BusInfo} from './BusInfo';
import { FindMe } from "../../common/FindMe.js";
import { MapContainer } from 'react-leaflet';
import { StationDetail } from './StationDetail.js';
import { useUserStore } from '../../store'

export function BusNum() {
    const { selectedBuss, setSelectedBuss } = useUserStore();
    const navigate = useNavigate();
    const [num, setNum] = useState([false, false, false, false]);

    useEffect(() => {
        let tmp = null
        num.map((item, index) => {
            if (item == true) {
                tmp = index + 1
            }
        })

        setSelectedBuss(tmp)
    }, [num])

    return (
        // 4개의 버스 번호를 표시하는 버튼. -> 열을 4개로 쪼개서 버튼 하나당 한 열에 배치. ( 이거 zIndex 400부터 표시됨.. )
        <div>
<div className="flex w-screen h-10 grid-cols-4 gap-3 mb-3" style={{ zIndex: 400}}>

    <div className="pl-px">
        {/* 좌측 여백 1픽셀 */}
    </div>

    {
        num.map((item, index) => {
            if (item === false) {
                return <div className="w-1/4"><Button key={index} color="white" className="w-full h-full shadow-lg shadow-black/40" onClick={() => toggleBus({index, num, selectedBuss, setNum, setSelectedBuss})}><BiBus className="inline-block align-middle mr-2"></BiBus>
                    <span className="inline-block align-middle">{index+1}</span></Button></div>
            } else {
                return <div className="w-1/4"> 
                    <Button key={index} className="w-full h-full shadow-lg shadow-black/50" onClick={() => toggleBus({index, num, selectedBuss, setNum, setSelectedBuss})}>
                        <BiBus className="inline-block align-middle mr-2"></BiBus>
                        <span className="inline-block align-middle">{index+1}</span>
                    </Button>
                </div>
            }
        })
    }
    <div className="pr-px">
        {/* 우측 여백 1픽셀 */}
    </div>
</div>


<div className="bg-white h-1/5">
    {num.map((value, index) => {
        const busNumber = index + 1; // 호차 번호는 인덱스 + 1
        return value && <BusInfo index={index+1}></BusInfo>;
    })}
</div>

<div className=""></div>

    <div className="w-screen">
    <Button className="w-full h-14 text-lg" onClick={()=> navigate('/setQR')}>탑승하기</Button>

    </div>
    </div>
    
    ) 
  }

function toggleBus({index, num, selectedBuss, setNum, setSelectedBuss}) {
    let copy = [...num]
    let tmp = null
    copy.map((item, i) => {
        if (i == index) {
            if (selectedBuss != i+1){
                tmp = i + 1
            }
            else {
                tmp = null
            }
            copy[i] = !item
        }
        else {
            copy[i] = false
        }
    })
    setNum(copy)
}