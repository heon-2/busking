import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

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

  const token = await getToken(messaging, {
    vapidKey:
      "BLfRUhCf9PsDUckaFewDvWbYSzm23QhIBGuRFu4Z1GXBNwNEWKqmvyelHHQfbGM2r3pZUeXuqT3UTjZXYLFvm0c",
  });

  if (token) console.log("token: ", token);
  else console.log("Can not get Token");

  onMessage(messaging, (payload) => {
    console.log("메시지가 도착했습니다.", payload);
    // ...
  });
}

requestPermission();
