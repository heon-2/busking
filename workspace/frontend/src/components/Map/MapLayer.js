import React, { useState, useEffect } from 'react';
import { useNavigate, Routes, Route, Link } from 'react-router-dom';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';
import axios from "axios";
import { useMapStore } from '../../store.js';
import { Dial } from './Dial.js';
import { BusNum } from './BusNum.js'
import { TopBar } from './TopBar.js'
import useLocation from '../../common/Mylocation.js';

export function MapLayer() {

    const { mapType, center, busPath, busInfo, setCenter, setBusPath, setBusInfo } = useMapStore()
    const [location, setLocation] = useState();
    const locationData = useLocation(); // useLocation Hook을 함수 컴포넌트 내에서 호출하여 위치 정보 가져옴
  
    useEffect(() => {
      // 1초마다 위치 정보 갱신
      const intervalId = setInterval(() => {
        const newLocationData = locationData;
        setLocation(newLocationData);
      }, 1000);
  
      return () => clearInterval(intervalId);
    }, []);


    return (
        <div style={{ position: 'relative', width: '100%', height: '100vh' }}>
            <MapContainer
            center={center}
            zoom={11}
            style={{ width: '100%', height: '1000px' }}>
{/* 기본 맵 */}
{
    mapType === false ?
    <TileLayer
    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    />
    :
    <TileLayer
    url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
    attribution='Tiles &copy; Esri'
    />
}
{/* 위성 맵 */}

    </MapContainer>
    <Dial />   
    <TopBar style={{ zIndex: 1000 }}/>
    <BusNum/>  
</div>
    )
}