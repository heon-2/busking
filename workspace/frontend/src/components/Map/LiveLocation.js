import axios from "axios";
import { useState, useEffect, useRef } from "react";
import { Marker } from "react-leaflet";
import { useQuery, useQueryClient } from "@tanstack/react-query";
export function LiveLocation() {
  const [markerLocation, setMarkerLocation] = useState(null);

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
            no: 1,
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
        console.log(response);
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
    }, 3000);
    return () => {
      clearInterval(timer);
    }; // 1분을 밀리초로 표현한 값
  }, []);

  return (
    <>
      {markerLocation != null ? (
        <Marker position={markerLocation}></Marker>
      ) : null}
    </>
  );
}
