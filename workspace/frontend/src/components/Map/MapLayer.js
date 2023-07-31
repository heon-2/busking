import React, { useState, useEffect } from 'react';
import { useNavigate, Routes, Route, Link } from 'react-router-dom';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';
import axios from "axios";
import { useMapStore } from '../../store.js';

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
                <TileLayer 
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                />

            </MapContainer>
        </div>
    )
}