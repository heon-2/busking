import { Card, Input, Button, Typography } from "@material-tailwind/react";
import { useState } from "react";
import axios from "axios";
import { useUserStore } from "../../store.js";
import { queries } from "@testing-library/react";
import { useNavigate } from "react-router-dom";
import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

export function LoginForm() {
  const [username, setName] = useState("");
  const [password, setPassword] = useState("");

  const { user, accessToken, setUser, setAccessToken, setRefreshToken, setFcmToken } =
    useUserStore();
  const navigate = useNavigate();

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <Card color="transparent" shadow={true} className="p-8 bg-white">
        <Typography variant="h4" color="blue-gray">
          로그인
        </Typography>
        <Typography color="gray" className="mt-1 font-normal">
          교육생의 정보를 입력해주세요.
        </Typography>
        <form className="max-w-screen-lg mt-8 mb-2 w-80 sm:w-96">
          <div className="flex flex-col gap-6 mb-4">
            <Input
              size="lg"
              label="아이디"
              value={username}
              onChange={(e) => setName(e.target.value)}
            />
            <Input
              type="password"
              size="lg"
              label="비밀번호"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <Button
            className="mt-6"
            onClick={() =>
              onLogin({
                username,
                password,
                setUser,
                setAccessToken,
                setRefreshToken,
                navigate,
                setFcmToken,
              })
            }
          >
            Login
          </Button>
        </form>
      </Card>
    </div>
  );
}

async function onLogin({
  username,
  password,
  setUser,
  setAccessToken,
  setRefreshToken,
  navigate,
  setFcmToken,
}) {
  try {
    const response = await axios.post(
      "/api/auth/login",
      {
        username: username,
        password: password,
      },
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/x-www-form-urlencoded",
        },
      }
    );
    console.log(response);
    setAccessToken(response.data.accessToken);
    setRefreshToken(response.data.refreshToken);
    localStorage.setItem("accessToken", response.data.accessToken);
    localStorage.setItem("refreshToken", response.data.refreshToken);

    const accessToken = localStorage.getItem("accessToken");

    const response2 = await axios.get("/api/users", {
      headers: {
        Accept: "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
    });
    console.log(response2.data);

    //// FCM 부분 ////
    const firebaseConfig = {
      apiKey: "AIzaSyDEoTpr6NUf9YZ-QcZX8wFsvV_VY0nuGYw",
      authDomain: "test-pwa-7983a.firebaseapp.com",
      projectId: "test-pwa-7983a",
      storageBucket: "test-pwa-7983a.appspot.com",
      messagingSenderId: "898692671567",
      appId: "1:898692671567:web:be40c0b2878aeabe86623a",
      measurementId: "G-CQ6VCFFMFQ",
    };

    const app = initializeApp(firebaseConfig);
    const messaging = getMessaging(app);

    async function requestPermission() {
      console.log("권한 요청 중...");

      const permission = await Notification.requestPermission();
      if (permission === "denied") {
        console.log("알림 권한 허용 안됨");
        return;
      }

      console.log("알림 권한이 허용됨");

      const fcmToken = await getToken(messaging, {
        vapidKey:
          "BLfRUhCf9PsDUckaFewDvWbYSzm23QhIBGuRFu4Z1GXBNwNEWKqmvyelHHQfbGM2r3pZUeXuqT3UTjZXYLFvm0c",
      });

      setFcmToken(fcmToken);
      localStorage.setItem("setFcmToken", fcmToken);

      if (fcmToken) console.log("token: ", fcmToken);
      else console.log("Can not get Token");
      // console.log(accessToken)
      // FCM token을 서버에 전송
      const response3 = await axios.post(
        "/api/fcm",
        {
          token: fcmToken,
        },
        {
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );

      console.log(response3);

      onMessage(messaging, (payload) => {
        console.log("메시지가 도착했습니다.", payload);
        // ...
      });
    }

    requestPermission();
    ///////////////////////////////////////////////////////////////
    await setUser(response2.data);
    if (response2.data.role === "EMPLOYEE") {
      navigate("/map");
    } else if (response2.data.role === "COMPANY_ADMIN") {
      navigate("/admin");
    } else {
      navigate("/knightselect");
    }
  } catch (error) {
    console.log(error);
  }
}
///////////////////////firebase//////////////////////////

// function onUser(){
//    const accessToken = localStorage.getItem('accessToken')
//    console.log(accessToken)
//    axios.get('/api/users',
// //    {
// //     description: "adsdadasaffasfwf111sd",
// //     lng: 1,
// //     lat: 1,
// //     busNum: 3,
// //     companyId: 1
// //    },
//    {headers: {
//     'Accept': 'application/json',
//     'Authorization' : `Bearer ${accessToken}`,
//    }}
// //    {queries:{
// //     'Authorization': `Bearer ${accessToken}`
// //    }}
//    )
//    .then((response) => {
//     console.log(response)
//    })
//    .catch((error) => {
//     console.log(error)
//    })
// }