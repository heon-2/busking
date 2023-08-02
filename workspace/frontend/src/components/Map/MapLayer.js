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
    const locationHook = useLocation();
    const updatedLocation = locationHook.getLocation();
  
    useEffect(() => {
      setLocation(updatedLocation); // 가져온 위치 정보를 상태로 저장
  
      const timer = setInterval(() => {
        const updatedLocation = locationHook.getLocation(); // 업데이트된 위치 정보를 가져옴
        setLocation(updatedLocation); // 업데이트된 위치 정보를 상태로 저장
      }, 1000);
  
      return () => clearInterval(timer);
    }, []);

    const [count, setCount] = useState(0);

    useEffect(() => {
      const timer = setInterval(() => {
        setCount((prev) => prev + 1);
      }, 1000);
  
      return () => {
        clearInterval(timer);
      };
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
    {count}
</div>
    )
}