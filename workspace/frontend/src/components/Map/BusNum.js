import React from 'react';
import { FaBeer } from 'react-icons/fa';
import { BiBus } from 'react-icons/bi';
import { Button } from "@material-tailwind/react";
import { useState, useEffect } from 'react';

export function BusNum() {

    const [num, setNum] = useState([false, false, false, false]);

    return (
    <div className="flex fixed top-6 left-6" style={{ zIndex: 1000}}>
        {
            num.map((item, index) => {
                if (item === false){
                    return <Button key={index} color="white" onClick={() => toggleBus({index, num, setNum})}><BiBus></BiBus></Button>;
                }
                else {
                    
                    return <Button key={index} onClick={() => toggleBus({index, num, setNum})}><BiBus></BiBus></Button>;
                }
            })
        }
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