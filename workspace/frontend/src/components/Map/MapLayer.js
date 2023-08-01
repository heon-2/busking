import React, { useState, useEffect } from 'react';
import { useNavigate, Routes, Route, Link } from 'react-router-dom';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';
import axios from "axios";
import { useMapStore } from '../../store.js';
import { Dial } from './Dial.js';
import { BusNum } from './BusNum.js'

export function MapLayer() {

    const { center, busPath, busInfo, setCenter, setBusPath, setBusInfo } = useMapStore()

    console.log(center)
    // //mount 될 때 데이터 불러오는 과정
    useEffect(() => {

    }, [])


    return (
        <div style={{ position: 'relative', width: '100%', height: '100vh' }}>
            <MapContainer
            center={center}
            zoom={11}
            style={{ width: '100%', height: '1000px' }}>

무료로 사용 가능한 다양한 TileLayer 코드 목록을 아래에 제공합니다. 각 코드는 URL과 해당 타일 레이어에 대한 저작권 정보로 구성되어 있습니다.

OpenStreetMap (OSM):
jsx
Copy code
<TileLayer
  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
  attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
/>
Mapbox Streets:
jsx
Copy code
<TileLayer
  url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
  attribution='Tiles &copy; Esri'
  
/>

    </MapContainer>
    <Dial />   
    <BusNum/>  
</div>
    )
}