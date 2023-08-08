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
import { Button, Typography } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
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
        // console.log(accessToken);
        console.log(response.data);
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
      <div className="grid grid-cols-2 w-screen bg-white">
        <div className="col-span-1">
          <MapLayer FindMe={<FindMe />} Dial={<Dial />} />
        </div>
        <div className="col-span-1 text-start">
          <Typography className="flex justify-center" variant="h1">
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
          </div>
        </div>
      </div>
    </>
  );
}
