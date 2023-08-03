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


export function MapLayer() {

    const { location, mapType, center, busPath, busInfo, setCenter, setBusPath, setBusInfo } = useMapStore()
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
            style={{ width: '100%', height: '1000px' }}
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
    <Marker position={location} />
    <Test location={location} ></Test>
    </MapContainer>
    <Dial />   
    <TopBar style={{ zIndex: 1000 }}/>
    <BusNum/>

    {count}
</div>
    )
}

function Test({ location}) {
  const map = useMap();

  return location ? (
    <IconButton size="lg" className="rounded-full" onClick={() => {map.flyTo(location, 17,{
      duration: 1,
    });}} style={{zIndex: 2000}}>
      <BiCurrentLocation className="h-5 w-5 transition-transform group-hover:rotate-45" />
    </IconButton>
  ) : null;
}
