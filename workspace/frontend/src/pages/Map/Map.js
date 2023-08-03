import React, { useState, useEffect } from 'react';
import { useNavigate, Routes, Route, Link } from 'react-router-dom';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';

import { FindMe } from '../../common/FindMe.js';
import { BusInfo } from '../../components/Map/BusInfo';
import { BusNum } from '../../components/Map/BusNum';
import { Dial } from '../../components/Map/Dial';
import { MapLayer } from '../../components/Map/MapLayer';
import { TopBar } from '../../components/Map/TopBar';

export function Map() {
    
    

    return (
        <div>
            <MapLayer 
            FindMe={<FindMe></FindMe>}
            Dial={<Dial></Dial>}
            />
        </div>
    )
}