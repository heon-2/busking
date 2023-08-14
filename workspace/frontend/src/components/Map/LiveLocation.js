import axios from "axios";
import { useState, useEffect, useRef } from "react";
import { Marker } from "react-leaflet";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import L from "leaflet";
export function LiveLocation() {
  // const [markerLocation, setMarkerLocation] = useState(null);
  const [markerLocation, setMarkerLocation] = useState(null);
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
        console.log("제발 보내져라 제발 제발 ");
        console.log(response.data.data);
        for (const k in response.data.data) {
          const [_, companyId, busNo] = k.split(":");
          const state = response.data.data[k];
          console.log(state);
          if (state.adj === null) {
            console.log("진짜 내위치");
            setMarkerLocation([state.raw.latlng.lat, state.raw.latlng.lng]);
            // lat = state.raw.latlng.lat;
            // lng = state.raw.latlng.lng;
          } else {
            console.log("보정된 내 위치");
            // lat = state.adj.latlng.lat;
            // lng = state.adj.latlng.lng;
            setMarkerLocation([state.adj.latlng.lat, state.adj.latlng.lng]);
          }
          // console.log(state.raw.latlng);
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
    }, 2500);
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

  return (
    <>
      {markerLocation != null ? (
        <Marker position={markerLocation}></Marker>
      ) : null}
      {/* {lat != null ? (
        <Marker position={[lat, lng]}></Marker>
      ) : null} */}
    </>
  );
}
