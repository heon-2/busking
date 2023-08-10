import { OpenVidu } from "openvidu-browser";
import {
  Button,
  Modal,
  Spinner,
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
  Typography,

} from "@material-tailwind/react";

import axios from "axios";
import React, { useCallback, useEffect, useRef, useState } from "react";
// import "./App.css";
import UserVideoComponent from "./../../components/RTC/UserVideoComponent";
import { useUserStore } from "./../../store.js";
import { useNavigate } from "react-router-dom";

// const APPLICATION_SERVER_URL =
//   process.env.NODE_ENV === "production" ? "" : "https://demos.openvidu.io/";

const APPLICATION_SERVER_URL = "https://i9c108.p.ssafy.io/";
export function Emergency() {
    const navigate = useNavigate();

    useEffect(() => {
        joinSession(); // Automatically join the session when the component mounts
        handleOpen("xl"); // Open the modal dialog
      }, []);

  // 스토어에서 user를 불러와 user.fcmToken 사용하기
  const { user } = useUserStore();
  // 로컬 스토리지에서 accessToken 불러오기.
  const accessToken = localStorage.getItem("accessToken");

  const [mySessionId, setMySessionId] = useState("ssafy_1");
  const [myUserName, setMyUserName] = useState("SSAFY");
  const [session, setSession] = useState(undefined);
  const [mainStreamManager, setMainStreamManager] = useState(undefined);
  const [publisher, setPublisher] = useState(undefined);
  const [subscribers, setSubscribers] = useState([]);
  const [currentVideoDevice, setCurrentVideoDevice] = useState(null);

  const OV = useRef(new OpenVidu());

  // size 조절
  const [size, setSize] = React.useState(null);
  const handleOpen = (value) => setSize(value);

  const handleChangeSessionId = useCallback((e) => {
    setMySessionId(e.target.value);
  }, []);

  const handleChangeUserName = useCallback((e) => {
    setMyUserName(e.target.value);
  }, []);

  const handleMainVideoStream = useCallback(
    (stream) => {
      if (mainStreamManager !== stream) {
        setMainStreamManager(stream);
      }
    },
    [mainStreamManager]
  );

  const joinSession = useCallback(() => {
    const mySession = OV.current.initSession();

    mySession.on("streamCreated", (event) => {
      const subscriber = mySession.subscribe(event.stream, undefined);
      setSubscribers((subscribers) => [...subscribers, subscriber]);
    });

    mySession.on("streamDestroyed", (event) => {
      deleteSubscriber(event.stream.streamManager);
    });

    mySession.on("exception", (exception) => {
      console.warn(exception);
    });

    setSession(mySession);
  }, []);

  useEffect(() => {
    if (session) {
      // Get a token from the OpenVidu deployment
      getToken().then(async (token) => {
        try {
          await session.connect(token, { clientData: myUserName });

          let publisher = await OV.current.initPublisherAsync(undefined, {
            audioSource: undefined,
            videoSource: undefined,
            publishAudio: true,
            publishVideo: true,
            resolution: "640x480",
            frameRate: 30,
            insertMode: "APPEND",
            mirror: false,
          });

          session.publish(publisher);

          const devices = await OV.current.getDevices();
          const videoDevices = devices.filter(
            (device) => device.kind === "videoinput"
          );
          const currentVideoDeviceId = publisher.stream
            .getMediaStream()
            .getVideoTracks()[0]
            .getSettings().deviceId;
          const currentVideoDevice = videoDevices.find(
            (device) => device.deviceId === currentVideoDeviceId
          );

          setMainStreamManager(publisher);
          setPublisher(publisher);
          setCurrentVideoDevice(currentVideoDevice);
        } catch (error) {
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        }
      });
    }
  }, [session, myUserName]);

  const leaveSession = useCallback(() => {
    // Leave the session
    if (session) {
      session.disconnect();
    }

    // Reset all states and OpenVidu object
    OV.current = new OpenVidu();
    setSession(undefined);
    setSubscribers([]);
    setMySessionId("ssafy_1");
    setMyUserName("SSAFY");
    setMainStreamManager(undefined);
    setPublisher(undefined);
  }, [session]);

  const switchCamera = useCallback(async () => {
    try {
      const devices = await OV.current.getDevices();
      const videoDevices = devices.filter(
        (device) => device.kind === "videoinput"
      );

      if (videoDevices && videoDevices.length > 1) {
        const newVideoDevice = videoDevices.filter(
          (device) => device.deviceId !== currentVideoDevice.deviceId
        );

        if (newVideoDevice.length > 0) {
          const newPublisher = OV.current.initPublisher(undefined, {
            videoSource: newVideoDevice[0].deviceId,
            publishAudio: true,
            publishVideo: true,
            mirror: true,
          });

          if (session) {
            await session.unpublish(mainStreamManager);
            await session.publish(newPublisher);
            setCurrentVideoDevice(newVideoDevice[0]);
            setMainStreamManager(newPublisher);
            setPublisher(newPublisher);
          }
        }
      }
    } catch (e) {
      console.error(e);
    }
  }, [currentVideoDevice, session, mainStreamManager]);

  const deleteSubscriber = useCallback((streamManager) => {
    setSubscribers((prevSubscribers) => {
      const index = prevSubscribers.indexOf(streamManager);
      if (index > -1) {
        const newSubscribers = [...prevSubscribers];
        newSubscribers.splice(index, 1);
        return newSubscribers;
      } else {
        return prevSubscribers;
      }
    });
  }, []);

  useEffect(() => {
    const handleBeforeUnload = (event) => {
      leaveSession();
    };
    window.addEventListener("beforeunload", handleBeforeUnload);

    return () => {
      window.removeEventListener("beforeunload", handleBeforeUnload);
    };
  }, [leaveSession]);

  /**
   * --------------------------------------------
   * GETTING A TOKEN FROM YOUR APPLICATION SERVER
   * --------------------------------------------
   * The methods below request the creation of a Session and a Token to
   * your application server. This keeps your OpenVidu deployment secure.
   *
   * In this sample code, there is no user control at all. Anybody could
   * access your application server endpoints! In a real production
   * environment, your application server must identify the user to allow
   * access to the endpoints.
   *
   * Visit https://docs.openvidu.io/en/stable/application-server to learn
   * more about the integration of OpenVidu in your application server.
   */
  const getToken = useCallback(async () => {
    return createSession(mySessionId).then((sessionId) =>
      createToken(sessionId)
    );
  }, [mySessionId]);

  const createSession = async (sessionId) => {
    const response = await axios.post(
      APPLICATION_SERVER_URL + "api/rtc/sessions",
      { customSessionId: sessionId },
      {
        headers: { "Content-Type": "application/json" },
      }
    );
    return response.data; // The sessionId
  };

  const createToken = async (sessionId) => {
    const response = await axios.post(
      APPLICATION_SERVER_URL + "api/rtc/sessions/" + sessionId + "/connections",
      {},
      {
        headers: { "Content-Type": "application/json" },
      }
    );
    return response.data; // The token
  };

    const pushAlarm =() => axios.post(
      "/api/fcm/notification",
      // 푸시 알람을 위해 admin 으로 고정.
      // 그러면 반대에서 푸시 알람을 하려면 userId를 받아서 보내야 하는데?
      {
        userId : 'admin'
        // userId : user.username
      },
      {
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      }
    )
    .then((response) => {
      console.log(' 하.. 됐다');
    })
    .catch((error) => {
      console.log('왜 안 되냐 대체');
    });

    // 점 3개 왔다갔다 하기.
    const [dots, setDots] = useState("....");

    useEffect(() => {
      const interval = setInterval(() => {
        setDots((prevDots) => (prevDots === "...." ? "" : prevDots + "."));
      }, 500); // 0.5초마다 점 추가
  
      return () => clearInterval(interval);
    }, []);
    ///

  return (
    <div>
      <div className="h-screen w-screen bg-transparent">
        
      </div>
      {/* {session === undefined ? (
        <div>
          <Button className="w-80 h-20 text-3xl" onClick={() => {handleOpen("xl"); joinSession();}}>
            관리자와 화상채팅
          </Button>
        </div>
      ) : null} */}

      <Dialog
        open={
          size === "xs" ||
          size === "sm" ||
          size === "md" ||
          size === "lg" ||
          size === "xl" ||
          size === "xxl"
        }
        size={size || "md"}
        handler={handleOpen}
        className="bg-skyblue-50"
      >
        <DialogHeader className="justify-center text-white text-4xl">
          긴급 화상통화
        </DialogHeader>
        <DialogBody divider className="flex justify-center">
          <div className="grid grid-cols-2 gap-5">
            <div>
              {publisher !== undefined ? (
                <div>
                  <UserVideoComponent streamManager={publisher} />
                </div>
              ) : null}
            </div>
            <div className="flex items-center justify-center">
              {subscribers.length === 0 ? (
                        <div>
                        <div>
                        <Spinner className=" h-64 w-64 text-blue-500/10" />
                        </div>
      
                        <div className="fixed mt-5">
                        <Typography className="text-2xl font-semibold text-gray-100 mr-3"> 관리자와 연결 중입니다{dots} </Typography>
                        </div>
                      </div>
              ) : (
                <div>
                  {subscribers.map((sub, i) => (
                    <div
                      key={sub.id}
                      // className="stream-container col-md-6 col-xs-6"
                      // onClick={() => handleMainVideoStream(sub)}
                    >
                      <span>{sub.id}</span>
                      <UserVideoComponent streamManager={sub} />
                    </div>
                  ))}
                </div>
              )}
              {/* {subscribers.map((sub, i) => (
                <div
                  key={sub.id}
                  // className="stream-container col-md-6 col-xs-6"
                  // onClick={() => handleMainVideoStream(sub)}
                >
                  <span>{sub.id}</span>
                  <UserVideoComponent streamManager={sub} />
                </div>
              ))} */}
            </div>
          </div>
        </DialogBody>
        <DialogFooter className="justify-center gap-5">
          <Button onClick={switchCamera}>카메라 전환</Button>
          <Button
            color="red"
            onClick={() => {
              handleOpen(null);
              leaveSession();
              navigate("/admin")
              
            }}
          >
            화상 채팅 종료
          </Button>
        </DialogFooter>
      </Dialog>
    </div>
  );
}