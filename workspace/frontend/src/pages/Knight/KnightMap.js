import { MapLayer } from "./../../components/Map/MapLayer";
import { Button } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
import axios from  "axios"
import { useEffect, useState } from "react";
import RTC from "../../components/RTC/RTC";
import { useMapStore, useBusStore,useUserStore } from "../../store";

export function KnightMap() {
  const {location} = useMapStore();
  const {busNumber} = useBusStore();
  const navigate = useNavigate();
  const { user, accessToken} =
    useUserStore();

  

  function startDrive() {
    axios.post(
      "/api/realtime/driving/begin",
      {
        "bus": {
          "companyId": 1,
          "no": 3
        },
        "route": {
          "id": 1,
        }
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
    })
    .catch((error) => {
      console.log("안 보내졌따 ㅅㅄㅄㅄㅄㅄㅄ");
      console.error(error);
    });
  }
  ////////////////
  function sendLocation() {
    console.log(location);
    axios
      .put(
        "/api/realtime/driving/drive",
        {
          bus: {
            companyId: 1,
            no: 3,
          },
          gps: {
            timestamp: 1508367639600,
            accuracy:null,
            latlng: {
              lat: location[0],
              lng: location[1],
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
        console.log("제발 보내져라 제발 제발 ");
        console.log(response);
      })
      .catch((error) => {
        console.log("안 보내졌따 ㅅㅄㅄㅄㅄㅄㅄ");
        console.error(error);
      });
  }
  

  return (
    <div>
      <div className="z-0">
        <MapLayer></MapLayer>
      </div>
      {/* 나중에 zindex 조정하기 */}
      <div className="flex fixed bottom-10 left-10" style={{ zIndex: 1000 }}>
        <RTC></RTC>
      </div>
      <div className="flex fixed bottom-10 right-10" style={{ zIndex: 1000 }}>
        <Button
          className="w-96 h-28 text-4xl"
          onClick={() => {
            navigate("/knightquit");
            startDrive();
            // sendLocation();
          }}
          color="red"
        >
          운행 종료
          {busNumber}
        </Button>
      </div>
    </div>
  );
}
