import { MapLayer } from "./../../components/Map/MapLayer";
import { Button } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import RTC from "../../components/RTC/RTC";
import { useMapStore, useBusStore, useUserStore } from "../../store";
import {
  QueryClient,
  QueryClientProvider,
  useQuery,
} from "@tanstack/react-query";

export function KnightMap() {
  const [latlng, setLatlng] = useState(null);
  const { location } = useMapStore();
  const { busNumber } = useBusStore();
  const navigate = useNavigate();
  const { user, accessToken } = useUserStore();

  useEffect(() => { 
    console.log(user)
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
  
  
  
  function success(position) {
    // setLocation([position.coords.latitude, position.coords.longitude]);
    const la = position.coords.latitude;
    const ln = position.coords.longitude;
    console.log(busNumber)
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
            // 이거 lat,lng로 넣어야함.
            // lat: 35.17433,
            // lng: 126.81739,
            lat: la,
            lng: ln,
            // [35.20631, 126.81919]
            // lat: 35.20631,
            // lng: 126.81919,
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
        // console.log("제발 보내져라 제발 제발 ");
        console.log(response);
        console.log(la, ln);
      })
      .catch((error) => {
        // console.log("안 보내졌따 ㅅㅄㅄㅄㅄㅄㅄ");
        console.error(error);
      });
      
    }
    
    function error() {
      alert("죄송합니다. 위치 정보를 사용할 수 없습니다.");
    }
    
    
    
    
    return (
      <div>
      <div className="z-0">
        <MapLayer></MapLayer>
      </div>
      {/* 나중에 zindex 조정하기 */}
      <div className="fixed flex bottom-10 left-10" style={{ zIndex: 1000 }}>
        {/* <RTC></RTC> */}
      </div>
      <div className="fixed flex bottom-10 right-10" style={{ zIndex: 1000 }}>
        <Button
          className="text-4xl w-96 h-28"
          onClick={() => {
            navigate("/knightquit");
            // sendLocation();
            // startDrive();
            // sendLocation();
          }}
          color="red"
          >
          {/* && 연산자로도 사용 가능 */}
          {/* 운행 종료 데이터:{result.data ? result.data.name : "데이터없음"}
          에러:{result.error ? "에러발생" : "에러없음"}
        로딩:{result.isLoading ? "로딩중" : "로딩완료"} */}
          운행 종료
        </Button>
      </div>
    </div>
  );
}

// ReactQuery 연습
// 장점1. 성공/실패/로딩중 쉽게 파악 가능

// const gps = useQuery(
  //   ["sendGps"],
  //   () => {
    //     return axios
//       .put(
//         "/api/realtime/driving/drive",
//         {
  //           bus: {
//             companyId: 1,
//             no: 1,
//           },
//           gps: {
//             timestamp: new Date().getTime(),
//             accuracy: "", // 추가 예정
//             latlng: {
//               lat: location[0],
//               lng: location[1],
//             },
//           },
//         },
//         {
//           headers: {
//             Accept: "application/json",
//           },
//         }
//       )
//       .then((response) => {
  //         return response.data;
  //       })
  //       .catch((error) => {
    //         console.log("안 보내졌따 ㅅㅄㅄㅄㅄㅄㅄ");
//         return error;
//       });
//   },
//   { staleTime: 2000 }
// );
// function startDrive() {

  /*
  const practice = useQuery(
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
  */

 ////////////////
 
 ////////////////
 // function sendLocation(lat, lng) {
   //   axios
   //     .put(
     //       "/api/realtime/driving/drive",
     //       {
       //         bus: {
         //           companyId: 1,
         //           no: 1,
         //         },
         //         gps: {
           //           timestamp: new Date().getTime(),
           //           accuracy: "", // 추가 예정
           //           latlng: {
             //             // 이거 lat,lng로 넣어야함.
             //             // lat: 35.17433,
             //             // lng: 126.81739,
             //             lat: lat,
             //             lng: lng,
             //           },
             //         },
             //       },
             //       {
               //         headers: {
 //           Accept: "application/json",
 //         },
 //       }
 //     )
 //     .then((response) => {
 //       // console.log("제발 보내져라 제발 제발 ");
 //       console.log(response);
 //       console.log(lat, lng);
 //     })
 //     .catch((error) => {
   //       // console.log("안 보내졌따 ㅅㅄㅄㅄㅄㅄㅄ");
   //       console.error(error);
   //     });
   // }
   // useEffect(() => {
     //   watchID();
     // });
     // useEffect(() => {
       //   // const timer = setInterval(() => {
         //   //   // console.log(location);
         //   if (latlng == null) {
           //     return;
           //   }
           
           //   const [lat, lng] = latlng;
           
           //   sendLocation(lat, lng);
           //   console.log(lat, lng);
           //   //   // console.log(lat, lng);
           //   // }, 5000);
           //   // return () => {
             //   //   clearInterval(timer);
             //   // }; // 1분을 밀리초로 표현한 값
             // }, [latlng]);