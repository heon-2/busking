import React from 'react';
import { FaBeer } from 'react-icons/fa';
import { BiBus } from 'react-icons/bi';
import { Button } from "@material-tailwind/react";
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
export function BusNum() {
    const navigate = useNavigate();
    const [num, setNum] = useState([false, false, false, false]);

    return (
        // 4개의 버스 번호를 표시하는 버튼. -> 열을 4개로 쪼개서 버튼 하나당 한 열에 배치. ( 이거 zIndex 400부터 표시됨.. )
        <div>
    {/* <div className="flex fixed justify-center bottom-0 w-screen h-12 grid-cols-4 gap-3" style={{ zIndex: 400}}> */}
    <div className="flex w-screen h-12 grid-cols-4 gap-3 " style={{ zIndex: 400}}>
        {
            num.map((item, index) => {
                if (item === false){
                    return <div className="w-1/4"><Button key={index} color="white" className="w-full h-full" onClick={() => toggleBus({index, num, setNum})}><BiBus className="inline-block align-middle mr-3"></BiBus>
                    <span className="inline-block align-middle">{index+1}</span></Button></div>
                }
                else {
                    // 각 너비를 1/4 씩 주고, 버튼과 글씨를 inline-block 설정 후, 수직 정렬을 위해 align-middle 설정.
                    return <div className="w-1/4"> 
                    <Button key={index} className="w-full h-full" onClick={() => toggleBus({index, num, setNum})}>
                        <BiBus className="inline-block align-middle mr-3 "></BiBus>
                        <span className="inline-block align-middle">{index+1}</span>
                        </Button> 
                        </div>
                }
            })
        }
    </div>

    <div className="bg-white h-1/5">
        
          {num[0] === true && '1호차'}
          {num[1] === true && '2호차'}
          {num[2] === true && '3호차'}
          {num[3] === true && '4호차'}
    </div>


    <div className="w-screen">
    <Button className="w-full h-12 etxt-lg" onClick={()=> navigate('/setQR')}>탑승하기</Button>

    </div>
    </div>
    
    ) 
  }

function toggleBus({index, num, setNum}) {
    let copy = [...num]
    copy.map((item, i) => {
        if (i === index) {
            copy[i] = !item
        }
        else {
            copy[i] = false
        }
    })
    console.log(copy)
    setNum(copy)
}