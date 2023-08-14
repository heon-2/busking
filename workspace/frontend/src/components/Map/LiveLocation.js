import axios from "axios";
import { useState } from "react";
import { Marker } from "react-leaflet";

export function LiveLocation() {
  const [markerLocation, setMarkerLocation] = useState(null);
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
        // console.log("제발 보내져라 제발 제발 ");
        const rlt = response.data.data;
        setMarkerLocation([rlt.lat, rlt.lng]);
        console.log("위치수신함");
      })
      .catch((error) => {
        console.log("안 보내졌따 ㅅㅄㅄㅄㅄㅄㅄ");
        console.error(error);
      });
  }

  // setInterval(() => {
  //   getLocation();
  // }, 3000); // 1분을 밀리초로 표현한 값
  return (
    <>
      {markerLocation != null ? (
        <Marker position={markerLocation}></Marker>
      ) : null}
    </>
  );
}
