import React from 'react'
import { useQrStore } from '../../store.js';


export function QR() {

    const { busbus, destinationIndex ,selectedBus, isInBound ,inBoundDeparture, inBoundDestination, outBoundDeparture, outBoundDestination } = useQrStore();

    const data = {
        bus: busbus,
        Departure: isInBound ? inBoundDeparture : outBoundDeparture,
        nameDestination: isInBound ? inBoundDestination : outBoundDestination,
        destination: destinationIndex,
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