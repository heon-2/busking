import { MapLayer } from "./../../components/Map/MapLayer";
import { Polyline, Marker } from 'react-leaflet'
import { Button } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import RTC from "../../components/RTC/RTC";
import { useMapStore, useBusStore, useUserStore } from "../../store";
import L from "leaflet";
import {
  QueryClient,
  QueryClientProvider,
  useQuery,
} from "@tanstack/react-query";

export function KnightMap() {
  const [myPosition, setMyPosition] = useState(null)
  const [latlng, setLatlng] = useState(null);
  const { location } = useMapStore();
  const { busNumber, busPath, busStations } = useBusStore();
  const navigate = useNavigate();
  const { user, accessToken } = useUserStore();

  useEffect(() => { 
    if (user == null){
      navigate('/')
    }
    else if (user.role == 'EMPLOYEE'){
        navigate('/usermap');
    }
  }, [])

  
  
  const options = {
    enableHighAccuracy: true,
    maximumAge: 0,
    // timeout: 27000,
  };

  
  useEffect(() => {
    const timer = setInterval(() => {      
      navigator.geolocation.getCurrentPosition(success, error, options)
    }, 500);
    return () => {
      clearInterval(timer);
    }; // 1분을 밀리초로 표현한 값
  },[]);
  
  
  
  function success(position) {
    const la = position.coords.latitude;
    const ln = position.coords.longitude;
    setMyPosition([la, ln])
    axios
    .put(
      "/api/realtime/driving/drive",
      {
        bus: {
          companyId: 1,
          no: busNumber,
        },
        gps: {
          timestamp: new Date().getTime(),
          accuracy: "", // 추가 예정
          latlng: {
            lat: la,
            lng: ln,
          },
        },
      },
      {
        headers: {
          Accept: "application/json",
        },
      }
      )
      .then((response) => {
        console.log('성공');
      })
      .catch((error) => {
        console.error(error);
      });
      
    }
    
    function error() {
      setMyPosition(null)
      alert("죄송합니다. 위치 정보를 사용할 수 없습니다.");
    }
    const stationPopup = {
      minWidth: 5, // 최소 너비
      closeButton: false, // 닫기 버튼 숨김
      className: 'custom-popup',
      offset: [0,-25],
        };
    
        const polylineOptions = {
          color: '#344A82',
          weight: 9,
          // opacity: 1,
          // dashArray: '5,6,7,8, 10', // 점선 스타일
          lineJoin: 'miter', // 선의 연결 부분 스타일 ( miter, bevel, round,miter-clip )
        };
      // 버스 아이콘
  const busIcon = L.icon({
    iconUrl: "/bus.png",
    iconSize: [45, 45], // 아이콘 크기
    iconAnchor: [23, 46], // 아이콘 기준점 위치
  });

  // 정류장 아이콘
  const stationIcon = L.icon({
    iconUrl: "/station2.png",
    iconSize: [45, 45], // 아이콘 크기
    iconAnchor: [23, 46], // 아이콘 기준점 위치
  });
    
    
    return (
      <div>
      <div className="z-0">
        <MapLayer
        UserPath={<Polyline positions={busPath} {...polylineOptions}></Polyline>}
        Marker={
          busStations.map((station, index) => (
            <Marker key={index} position={station} icon={stationIcon}></Marker>
          ))
        }
        Bus={
          myPosition == null ? null : <Marker position={myPosition} icon={busIcon}></Marker>
        }
        ></MapLayer>
      </div>
      {/* 나중에 zindex 조정하기 */}
      <div className="fixed flex bottom-10 left-10" style={{ zIndex: 1000 }}>
        <RTC></RTC>
      </div>
      <div className="fixed flex bottom-10 right-10" style={{ zIndex: 1000 }}>
        <Button
          className="text-4xl w-96 h-28"
          onClick={() => {
            navigate("/knightquit");

          }}
          color="red"
          >

          운행 종료
        </Button>
      </div>
    </div>
  );
}

