import axios from "axios";
import { useState, useEffect, useRef } from "react";
import { Marker, Polyline, Popup } from "react-leaflet";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import L from "leaflet";
import { useUserStore, useMapStore } from '../../store'


export function LiveLocation() {
  // const [markerLocation, setMarkerLocation] = useState(null);
  const { selectedBuss, selectedStations, selectedRoute, setSelectedBuss, setSelectedStations, setSelectedRoute } = useUserStore();
  const [markerLocations, setMarkerLocations] = useState([null, null, null, null]);
  const { busInfo, setBusInfo } = useMapStore();
  // let [lat, lng] = [null, null];
  // r-query 공부해야할부분
  // const { data, error, isLoading } = useQuery("busLocation", getLocation);
  // const queryClient = useQueryClient();

  // useInterval(() => {
  //   queryClient.invalidateQueries("busLocation"); // Invalidate the query cache to refetch data
  // }, 3000); // Update every 3 seconds

  // function useInterval(callback, delay) {
  //   const savedCallback = useRef();

  //   useEffect(() => {
  //     savedCallback.current = callback;
  //   }, [callback]);

  //   useEffect(() => {
  //     function tick() {
  //       savedCallback.current();
  //     }
  //     if (delay !== null) {
  //       const id = setInterval(tick, delay);
  //       return () => clearInterval(id);
  //     }
  //   }, [delay]);
  // }
  //////////////////////
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
        // console.log("제발 보내져라 제발 제발 ");
        console.log(selectedBuss)
        if (response.data.data == {}) {
          setMarkerLocations([null, null, null, null])
        }
        else {
          let copy = [...markerLocations]
          for (const k in response.data.data) {
            const [_, companyId, busNo] = k.split(":");
            const state = response.data.data[k].loc;
            // console.log(state);
            // console.log(state.adj)
            // console.log(selectedRoute)
            if (state.adj == null) {
              // console.log("check1")
              // console.log("진짜 내위치");
              copy[Number(busNo) - 1] = [state.raw.latlng.lat, state.raw.latlng.lng]
              // lat = state.raw.latlng.lat;
              // lng = state.raw.latlng.lng;
            } else {
              // console.log("check2")
              // console.log("보정된 내 위치");
              // lat = state.adj.latlng.lat;
              // lng = state.adj.latlng.lng;
              copy[Number(busNo) - 1] = [state.adj.latlng.lat, state.adj.latlng.lng]
              console.log(selectedBuss)
              // console.log(busNo)
              if (selectedBuss != null) {
                if (selectedBuss == Number(busNo)) {
                  let newcopy = [...selectedRoute]
                  let i = 0;
                  while(true) {
                    if (newcopy.length <= i) {
                      break;
                    }
                    console.log('제발...')
                    if (Math.abs(copy[selectedBuss - 1][0] - newcopy[i][0]) < 1e-5 && Math.abs(copy[selectedBuss - 1][1] - newcopy[i][1]) < 1e-5){
                      break;
                    }
                    i++;
                  }
                  setSelectedRoute(newcopy.splice(i));
                }
              }
              // setMarkerLocations([state.adj.latlng.lat, state.adj.latlng.lng]);
            }
            // console.log(state.raw.latlng);
          }
          setMarkerLocations(copy);
          
        }

        // const rlt = response.data.data;
        // setMarkerLocation([rlt.lat, rlt.lng]);
        // console.log("위치수신함", rlt.lat, rlt.lng);
      })
      .catch((error) => {
        // console.log("안 보내졌따 ㅅㅄㅄㅄㅄㅄㅄ");
        console.error(error);
      });
  }

  // setInterval(() => {
  //   getLocation();
  // }, 3000); // 1분을 밀리초로 표현한 값

  useEffect(() => {
    const timer = setInterval(() => {
      // console.log(location);
      getLocation(selectedBuss);
      // console.log(lat, lng);
    }, 500);
    return () => {
      clearInterval(timer);
    }; // 1분을 밀리초로 표현한 값
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
  // function success(position) {
  //   // setLocation([position.coords.latitude, position.coords.longitude]);
  //   setLatlng([position.coords.latitude, position.coords.longitude]);
  //   console.log(position);
  // }
  // navigator.geolocation.watchPosition(success, error, options);

  useEffect(() => {
    const timer = setInterval(() => {
      // console.log(location);

      navigator.geolocation.getCurrentPosition(success, error, options)
      // console.log(lat, lng);
    }, 500);
    return () => {
      clearInterval(timer);
    }; // 1분을 밀리초로 표현한 값
  },[]);


  const [myLocate, setMyLocate] = useState(null);
  function success(position) {
    // setLocation([position.coords.latitude, position.coords.longitude]);
    const userLat = position.coords.latitude;
    const userLng = position.coords.longitude;
    console.log('내위치 :' + [userLat, userLng])
    setMyLocate([userLat, userLng]);
  }

  function error() {
    alert("죄송합니다. 위치 정보를 사용할 수 없습니다.");
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
      weight: 6,
      opacity: 0.9,
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
        selectedBuss == null || markerLocations[selectedBuss-1] == null? 
          markerLocations.map((loc, index) => (
            
            loc != null ? <Marker key={index} position={loc} icon={busIcon}></Marker> : null
            
          ))
        : <Marker position={markerLocations[selectedBuss-1]} icon={busIcon}></Marker>
      }
      {
        selectedBuss == null || selectedStations == [] ?
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
      {/* {lat != null ? (
        <Marker position={[lat, lng]}></Marker>
      ) : null} */}
    </>
  );
}
