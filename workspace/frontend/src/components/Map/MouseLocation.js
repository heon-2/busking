import React, { useState, useEffect, useRef, useMemo } from 'react';
import { useNavigate, Routes, Route, Link } from 'react-router-dom';
import { MapContainer, useMap, TileLayer, Marker, useMapEvents, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';
import axios from "axios";
import { useMapStore, useAdminStore } from '../../store.js';
import { Dial } from './Dial.js';
import { BusNum } from './BusNum.js'
import { TopBar } from './TopBar.js'
import useLocation from '../../common/Mylocation.js';
import { BiCurrentLocation } from 'react-icons/bi';
import { IconButton, Button } from "@material-tailwind/react";
import polyline from '@mapbox/polyline'

export function MouseLocation() {
    const { mouseLocation, setMouseLocation } = useAdminStore();
    const map = useMapEvents({
        mousemove: (e) => {
            // console.log(mouseLocation)
            setMouseLocation([e.latlng.lat, e.latlng.lng])
            console.log(mouseLocation)
        }
    })
}