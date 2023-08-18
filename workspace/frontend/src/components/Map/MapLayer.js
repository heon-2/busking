import React, { useState, useEffect, useRef, useMemo } from "react";
import { useNavigate, Routes, Route, Link } from "react-router-dom";
import { useGeolocated } from "react-geolocated";

import {
  MapContainer,
  useMap,
  TileLayer,
  Marker,
  useMapEvents,
  Popup,
  Polyline,
} from "react-leaflet";
import L from "leaflet";
import axios from "axios";
import { useMapStore } from "../../store.js";
import { Dial } from "./Dial.js";
import { BusNum } from "./BusNum.js";
import { TopBar } from "./TopBar.js";
import useLocation from "../../common/Mylocation.js";
import { BiCurrentLocation } from "react-icons/bi";
import { IconButton, Button } from "@material-tailwind/react";
import polyline from "@mapbox/polyline";
import rbush from "rbush";
import { useAdminStore } from "../../store.js";

export function MapLayer(props) {


  // 현재 내 위치, 지도 타입(일반, 위성), 지도 중심, 버스 경로, 버스 정보(혼잡도 등)
  const {
    location,
    mapType,
    center,
    busPath,
    busInfo,
    setLocation,
    setCenter,
    setBusPath,
    setBusInfo,
  } = useMapStore();


  function success(position) {
    setLocation([position.coords.latitude, position.coords.longitude]);
  }

  function error() {
    alert("죄송합니다. 위치 정보를 사용할 수 없습니다.");
  }

  const options = {
    enableHighAccuracy: true,
    // maximumAge: 30000,
    // timeout: 27000,
  };
  function watchID() {
    navigator.geolocation.watchPosition(success, error, options);
  }
  const locationHook = useLocation();
  const [count, setCount] = useState(0);
  const [test, setTest] = useState([0, 0]);
  const position = [35.18734, 126.81425];

  useEffect(() => {
    const timer = setInterval(() => {
      // fetchLocation();
      watchID();
      setCount((prev) => prev + 1);
    }, 5000);
    return () => {
      clearInterval(timer);
    };
  }, []);

  return (
    <div style={{ position: "relative", width: "100%", height: "100vh" }}>
      <MapContainer
        center={location}
        zoom={11}
        style={{ width: "100%", height: "100vh" }}
        zoomControl={false}
        // 오른쪽 하단 Leaflet 없애는 설정
        attributionControl={false} 
      >
        {/* 기본 맵 */}
        {mapType === false ? (
          <TileLayer
             url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
        ) : (
          <TileLayer
            url="https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
          />
        )}
        {/* 위성 맵 */}
        {props.Marker}
        {props.FindMe}
        {props.Dial}
        {props.TopBar}
        {props.BusNum}
        {props.CreateMarker}
        {props.DraggableMarker}
        {props.AdminPath}
        {props.MouseLocation}
        {props.CreateStop}
        {props.UserPath}
        {props.Bus}
      </MapContainer>
    </div>
  );
}

