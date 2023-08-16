import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
// import { divIcon } from "leaflet";
import { MapLayer } from "./../../components/Map/MapLayer";
import { FindMe } from "../../common/FindMe.js";
import { Dial } from "../../components/Map/Dial";
import { useMap } from "react-leaflet";

import { BiCurrentLocation } from "react-icons/bi";
import { IconButton } from "@material-tailwind/react";
import {
  Button,
  Typography,
  Card,
  CardBody,
  CardFooter,
} from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
import { useUserStore } from "../../store";



export function ReportDetail() {
  const navigate = useNavigate();
  const { reportId } = useParams();
  const [lng, setLng] = useState("");
  const [lat, setLat] = useState("");
  const [description, setDescription] = useState("");
  const [time, setTime] = useState("");
  const [busNum, setBusNum] = useState("");
  const [reporter, setReporter] = useState("");
  //   const map = useMap();
  const { location } = { lng, lat };
  //   function FindMe() {
  //     const map = useMap();
  //     const { location } = { lng, lat };
  //   }
  const { user } = useUserStore();
  useEffect(() => { 
    console.log(user)
    if (user == null){
      navigate('/')
    }
    else if (user.role != 'COMPANY_ADMIN'){
      if (user.role == 'EMPLOYEE'){
        navigate('/usermap');
      }
      else if (user.role == 'DRIVER'){
        navigate('/knightselect');
      }
    }
  }, [])



  useEffect(() => {
    // console.log(props);
    const accessToken = localStorage.getItem("accessToken");
    axios
      .get("/api/reports/" + reportId, {
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((response) => {
        const 지금 = new Date()
        console.log(지금)
        // console.log(accessToken);
        console.log(response.data);
        
        console.log(response.data.localDateTime)


        setLng(response.data.lng);
        setLat(response.data.lat);
        setDescription(response.data.description);
        setTime(response.data.localDateTime);
        setBusNum(response.data.busNum);
        setReporter(response.data.reporter);
        // console.log(detail);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <>
      <div className="grid grid-cols-2 bg-white">
        <div className="col-span-1">
          <MapLayer />
        </div>
        <div className="col-span-1 text-start">
          <Card className="w-full mt-10">
            <CardBody>
              <Typography variant="h3" color="blue-gray" className="mb-2">
                상세 신고 내용
              </Typography>
              <div className="gap-10">
                <Typography variant="h5">신고자 : {reporter}</Typography>
                <Typography variant="h5">신고 호차 : {busNum}호차</Typography>
                <Typography variant="h5">신고 내용 : {description}</Typography>
                <Typography variant="h5">사고 지점</Typography>

                <Typography variant="h5">
                  위도 : {lng} / 경도 : {lat}
                </Typography>
                <Typography variant="h5">신고 일시 : {time.at(0)}년 {time.at(1)}월 {time.at(2)}일 {(time.at(3)+9)%12}시 {time.at(4)}분 {time.at(5)}초 </Typography>
              </div>
            </CardBody>
            <CardFooter className="pt-0">
              <div className="flex justify-start gap-8">
                <Button color="red" onClick={() => navigate(-1)}>
                  신고 위치
                </Button>
                <Button onClick={() => navigate(-1)}>뒤로 가기</Button>
              </div>
            </CardFooter>
          </Card>
          {/* <Typography className="flex justify-center" variant="h1">
            상세 신고 내용
          </Typography>
          <Typography variant="h4">신고자 : {reporter}</Typography>
          <Typography variant="h4">신고 호차 : {busNum}호차</Typography>
          <Typography variant="h4">신고 내용 : {description}</Typography>
          <Typography variant="h4">사고 지점</Typography>

          <Typography variant="h4">
            위도 : {lng} / 경도 : {lat}
          </Typography>
          <div className="flex justify-evenly">
            <Button color="red" onClick={() => navigate(-1)}>
              신고 위치
            </Button>
            <Button onClick={() => navigate(-1)}>뒤로 가기</Button>
          </div> */}
        </div>
      </div>
    </>
  );
}
