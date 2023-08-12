import React from 'react'
import { useQrStore } from '../../store.js';


export function QR() {

    const { selectedBus, onStation, offStation ,setSelectedBus, setOnStation, setOffStation } = useQrStore();

    return (
        <div>
            <div>
            <img 
            className="object-cover ml-4 h-80"
            src="https://chart.apis.google.com/chart?cht=qr&chs=250x250&chl=https://i9c108.p.ssafy.io/" alt=""></img>
            </div>
            {/* <img 
            className="object-cover ml-4 h-80"
            src="https://chart.apis.google.com/chart?cht=qr&chs=250x250&chl={selectBus, onStation, offStation}" alt=""></img> */}
            
        </div>

        
    )
}