import { MapLayer } from "./../../components/Map/MapLayer";
import { Button } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import RTC from "../../components/RTC/RTC";
import { useMapStore, useBusStore, useUserStore } from "../../store";
import { useQuery } from "react-query";

export function KnightMap() {
  const { location } = useMapStore();
  const { busNumber } = useBusStore();
  const navigate = useNavigate();
  const { user, accessToken } = useUserStore();

  // ReactQuery 연습
  // 장점1. 성공/실패/로딩중 쉽게 파악 가능

  const rlt = useQuery("ㅇㅇㅇ", () => {
    return axios
      .post(
        "/api/realtime/driving/begin",
        {
          bus: {
            companyId: 1,
            no: 3,
          },
          route: {
            id: 1,
          },
        },
        {
          headers: {
            Accept: "application/json",
          },
        }
      )
      .then((response) => {
        return console.log(response.data);
      })
      .catch((error) => {
        console.log("안 보내졌따 ㅅㅄㅄㅄㅄㅄㅄ");
        console.error(error);
      });
  });
  // function startDrive() {

  const result = useQuery(
    "todos",
    () => {
      return axios
        .get("https://codingapple1.github.io/userdata.json")
        .then((response) => {
          console.log("요청하는중");
          return response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 틈만 나면 자동으로 refetch 해줌
    // 장점3. 실패시 자동으로 retry 해줌
    // 장점4. state 공유 안해도 됨.
    // 부모 컴포넌트에서 a를 요청하고 자식 컴포넌트에서 a를 요청하면 2번 요청하니깐 비효율적인것처럼 느껴지겠지만, 실제로는 1번만 요청함.
    // 캐싱 기능떄문에 앞에서 요청한 결과를 일단 가지고 있음. 5분 내에 다시 요청하면 이전에 저장한 결과를 출력함. 이후에 ajax 요청 수행.

    { staleTime: 2000 }
  );

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
            accuracy: null,
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
            // startDrive();
            // sendLocation();
          }}
          color="red"
        >
          {/* && 연산자로도 사용 가능 */}
          운행 종료 데이터:{result.data ? result.data.name : "데이터없음"}
          에러:{result.error ? "에러발생" : "에러없음"}
          로딩:{result.isLoading ? "로딩중" : "로딩완료"}
          {busNumber}
        </Button>
      </div>
    </div>
  );
}
