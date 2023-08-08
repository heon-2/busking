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
import { DragMarker } from '../../components/Map/DragMarker';
import { AdminPath } from '../../components/Map/AdminPath';
import { CreateMarker } from '../../components/Map/CreateMarker';
import { useAdminStore } from '../../store.js';
import { IconButton, Button } from "@material-tailwind/react";



export function Map() {

    return (
        <div>
            <MapLayer 
            // FindMe={<FindMe></FindMe>}
            // Dial={<Dial></Dial>}
            // BusNum={<BusNum></BusNum>}
            CreateMarker={<CreateMarker></CreateMarker>}
            // DragMarker={<DragMarker></DragMarker>}
            AdminPath={<AdminPath></AdminPath>}
            />
        </div>
    )
}

