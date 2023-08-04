import React, { useState, useEffect, useRef } from 'react';
import { useNavigate, Routes, Route, Link } from 'react-router-dom';
import { MapContainer, useMap, TileLayer, Marker, useMapEvents, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';
import axios from "axios";
import { useMapStore } from '../../store.js';
import { Dial } from './Dial.js';
import { BusNum } from './BusNum.js'
import { TopBar } from './TopBar.js'
import useLocation from '../../common/Mylocation.js';
import { BiCurrentLocation } from 'react-icons/bi';
import { IconButton } from "@material-tailwind/react";


export function MapLayer(props) {
    // 현재 내 위치, 지도 타입(일반, 위성), 지도 중심, 버스 경로, 버스 정보(혼잡도 등)
    const { location, mapType, center, busPath, busInfo, setCenter, setBusPath, setBusInfo } = useMapStore()
    // 내 위치 받아올 함수
    const locationHook = useLocation();
    const updatedLocation = locationHook.getLocation();
    const [count, setCount] = useState(0);
    const [test, setTest] = useState([0, 0])

  
  
    const position = [51.505, -0.09];
    useEffect(() => {
      const timer = setInterval(() => {
        setCount((prev) => prev + 1);
        const updatedLocation = locationHook.getLocation(); // 업데이트된 위치 정보를 가져옴
        console.log(location)
      }, 3000);
      return () => {
        clearInterval(timer);
      };
    }, []);


    return (
        <div style={{ position: 'relative', width: '100%', height: '100vh' }}>
            <MapContainer
            center={location}
            zoom={11}
            style={{ width: '100%', height: '100vh' }}
            >
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

    { props.Marker }
    { props.FindMe }
    {/* <Marker position={location} /> */}
    {/* <Test location={location} ></Test> */}
    </MapContainer>
    { props.Dial }
    { props.TopBar }
    { props.BusNum }
    {/* <Dial />    */}
    {/* <TopBar style={{ zIndex: 1000 }}/> */}
    {/* <BusNum/> */}
</div>
    )
}