import React from 'react'
import { useQrStore } from '../../store.js';


export function QR() {

    const { selectedBus, isInBound ,inBoundDeparture, inBoundDestination, outBoundDeparture, outBoundDestination } = useQrStore();

    const data = {
        selectedBus: selectedBus,
        Departure: isInBound ? inBoundDeparture : outBoundDeparture,
        Destination: isInBound ? inBoundDestination : outBoundDestination
    };

    const qrCodeUrl = `https://chart.apis.google.com/chart?cht=qr&chs=250x250&chl=${encodeURIComponent(JSON.stringify(data))}`;

    return (
        <div>
            <div>
            <img 
            className="object-cover ml-4 h-80"
            src={qrCodeUrl} alt="QR Code"></img>
            </div>
        </div>
    )
}