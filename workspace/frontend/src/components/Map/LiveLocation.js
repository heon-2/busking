import axios from "axios";
import { useState, useEffect, useRef } from "react";
import { Marker } from "react-leaflet";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import L from "leaflet";
export function LiveLocation() {
  // const [markerLocation, setMarkerLocation] = useState(null);
  const [markerLocations, setMarkerLocations] = useState([null, null, null, null]);
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
  function getLocation() {
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
        console.log(response.data.data);
        if (response.data.data == {}) {
          setMarkerLocations([null, null, null, null])
        }
        else {
          let copy = [...markerLocations]
          for (const k in response.data.data) {
            const [_, companyId, busNo] = k.split(":");
            const state = response.data.data[k];
            console.log(state);
            if (state.adj === null) {
              // console.log("진짜 내위치");
              copy[Number(busNo) - 1] = [state.raw.latlng.lat, state.raw.latlng.lng]
              // lat = state.raw.latlng.lat;
              // lng = state.raw.latlng.lng;
            } else {
              // console.log("보정된 내 위치");
              // lat = state.adj.latlng.lat;
              // lng = state.adj.latlng.lng;
              let copy = [...markerLocations]
              copy[Number(busNo) - 1] = [state.adj.latlng.lat, state.adj.latlng.lng]
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

      getLocation();
      // console.log(lat, lng);
    }, 2000);
    return () => {
      clearInterval(timer);
    }; // 1분을 밀리초로 표현한 값
  }, []);

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
    }, 2000);
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



  return (
    <>
      {
        markerLocations.map((loc, index) => (
          
          loc != null ? <Marker key={index} position={loc} icon={busIcon}></Marker> : null
          
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
