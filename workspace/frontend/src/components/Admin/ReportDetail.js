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
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";
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
        handleOpen("xl")
        // console.log(detail);
      })
      .catch((error) => {
        console.error(error);
      });
      
  }, []);

  const [size, setSize] = React.useState(null);
 
  const handleOpen = (value) => setSize(value);

  return (
    <>
      {/* <div className="grid grid-cols-2 bg-white">
        <div className="col-span-1">
          <MapLayer />
        </div>
        <div className="col-span-1 text-start">
          <Card className="mt-10 w-full">
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
                <Typography variant="h5">신고 일시 : {time} </Typography>
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
        </div>
      </div> */}
      

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
        className="h-[85vh]"
      >
        <DialogHeader>상세 신고 내용</DialogHeader>
        <DialogBody divider className="grid grid-cols-2">
        <div className="col-span-1 ">
          <MapLayer className="h-[80vh]"></MapLayer>
        </div>
          <div className="col-span-1">
          <Card className="mt-10 w-full">
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
                <Typography variant="h5">신고 일시 : {time} </Typography>
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
          </div>
        </DialogBody>
        <DialogFooter>
          <Button
            variant="text"
            color="red"
            onClick={() => handleOpen(null)}
            className="mr-1"
          >
            <span>Cancel</span>
          </Button>
          <Button
            variant="gradient"
            color="green"
            onClick={() => handleOpen(null)}
          >
            <span>Confirm</span>
          </Button>
        </DialogFooter>
      </Dialog>
    
    </>
  );
}
