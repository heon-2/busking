import axios from "axios";
import { useState, useEffect, useRef } from "react";
import { Marker, Polyline, Popup } from "react-leaflet";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import L from "leaflet";
import { useUserStore, useMapStore, useQrStore } from '../../store'


export function LiveLocation() {
  const { selectedBuss, selectedStations, selectedRoute, setSelectedBuss, setSelectedStations, setSelectedRoute } = useUserStore();
  const [markerLocations, setMarkerLocations] = useState([null, null, null, null]);
  const { busInfo, setBusInfo } = useMapStore();
  const { currentPeople,setCurrentPeople } = useQrStore();
  function getLocation(selectedBuss) {
    axios
      .post(
        "/api/realtime/driving/track",
        {
          bus: {
            companyId: 1,
            no: -1,
          },
        },
        {
          headers: {
            Accept: "application/json",
          },
        }
      )
      .then((response) => {

        if (response.data.data == {}) {
          setMarkerLocations([null, null, null, null])
          // setCurrentPeople([0,0,0,0])
        }
        else {
          let copy = [...markerLocations]
          for (const k in response.data.data) {
            const [_, companyId, busNo] = k.split(":");
            const state = response.data.data[k].loc;
            const passengers = response.data.data[k].passengers;
          // 각 버스 호차마다 탑승객 수를 구함. 
          const sumPassengers = passengers.reduce(function add(sum,currValue) {
            return sum + currValue;
          },0);
          
          let copy2 = [...currentPeople]
          copy2[Number(busNo)-1] = sumPassengers
          setCurrentPeople(copy2)
            if (state.adj == null) {

              copy[Number(busNo) - 1] = [state.raw.latlng.lat, state.raw.latlng.lng]

            } else {

              copy[Number(busNo) - 1] = [state.adj.latlng.lat, state.adj.latlng.lng]
              if (selectedBuss != null) {
                if (selectedBuss == Number(busNo)) {
                  let newcopy = [...selectedRoute]
                  let i = 0;
                  while(true) {
                    if (newcopy.length <= i) {
                      break;
                    }
                    if (Math.abs(copy[selectedBuss - 1][0] - newcopy[i][0]) < 1e-5 && Math.abs(copy[selectedBuss - 1][1] - newcopy[i][1]) < 1e-5){
                      break;
                    }
                    i++;
                  }
                  setSelectedRoute(newcopy.splice(i));
                }
              }
            }
          }
          setMarkerLocations(copy);
          
        }

      })
      .catch((error) => {
        console.error(error);
      });
  }


  useEffect(() => {
    const timer = setInterval(() => {
      getLocation(selectedBuss);
    }, 500);
    return () => {
      clearInterval(timer);
    };
  }, [selectedRoute]);

  const customIcon = L.icon({
    iconUrl: "bus_512.png",
    iconSize: [50, 50], // 아이콘 크기
    iconAnchor: [16, 32], // 아이콘 기준점 위치
    shadowSize: [50, 64],
  });

  // 버스 아이콘
  const busIcon = L.icon({
    iconUrl: "bus.png",
    iconSize: [45, 45], // 아이콘 크기
    iconAnchor: [23, 46], // 아이콘 기준점 위치
  });

  // 정류장 아이콘
  const stationIcon = L.icon({
    iconUrl: "station2.png",
    iconSize: [45, 45], // 아이콘 크기
    iconAnchor: [23, 46], // 아이콘 기준점 위치
  });

  // 사용자 아이콘

  const personIcon = L.icon({
    iconUrl: "person1.png",
    iconSize: [45, 45], // 아이콘 크기
    iconAnchor: [23, 46], // 아이콘 기준점 위치
  });



  /////////////////////////// 현재 내 위치 받아오기 /////////////////////////////////////////////////////
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


  const [myLocate, setMyLocate] = useState(null);
  function success(position) {
    const userLat = position.coords.latitude;
    const userLng = position.coords.longitude;
    setMyLocate([userLat, userLng]);
  }

  function error() {
    console.log("죄송합니다. 위치 정보를 사용할 수 없습니다.");
  }
/////////////////////////
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


  return (
    <>
      {
        selectedBuss == null || selectedRoute == null ?
        <></> : <Polyline positions={selectedRoute} {...polylineOptions}></Polyline>
      }
      {
        selectedBuss == null? markerLocations.map((loc, index) => (
            
          loc != null ? <Marker key={index} position={loc} icon={busIcon}></Marker> : null
          
        )) : markerLocations[selectedBuss-1] == null ?
          null
        : <Marker position={markerLocations[selectedBuss-1]} icon={busIcon}></Marker>
      }
      {
        selectedBuss == null ? null : selectedStations == [] ?
        <></> : 
        selectedStations.map((station, index) => ( 
          <Marker key={index} position={station[0]} icon={stationIcon}>
          <Popup {...stationPopup}>
            <div className="text-md font-bold text-concept3">
            {station[1]}
            </div>
            </Popup>
            
        </Marker>
        ))
      }
      { 
      myLocate != null ? (
        <Marker position={myLocate} icon={personIcon}></Marker>
      ) : null}
    </>
  );
}
