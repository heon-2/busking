import {
  Checkbox,
  Card,
  List,
  ListItem,
  ListItemPrefix,
  Typography,
  Button,
} from "@material-tailwind/react";

import { TopBar } from "./../../components/Map/TopBar";
import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useUserStore, useMapStore } from "../../store";

export function Report() {
  const navigate = useNavigate();
  const [reportContent, setReportContent] = useState("");
  const { busNum } = useUserStore();
  const { location } = useMapStore();
  const { user } = useUserStore();
  const [reportCheck, setReportCheck] = useState(false);
  // 경도
  const lat = location[0];
  // 위도
  const lng = location[1];
  // console.log(location);
  const accessToken = localStorage.getItem("accessToken");

  // 신고 항목들 변수로 담기
  const [reportList, setReportList] = useState([
    "난폭 운전",
    "예정 시간보다 빨리 출발",
    "운전 중 위험한 행동 ( 휴대폰 사용 등 )",
    "응급 상황 발생 ( 교통사고, 환자 발생 등 )",
    "무정차 출발",
  ]);
// busNum랑 companyId는 NULL값이 됨.. 
  function sendReport(reportContent) {
    console.log(reportContent);

    axios
      .post(
        "/api/reports",
        {
          description: reportContent,
          lng: lng,
          lat: lat,
          busNum: 3,
          companyId: 1,
        },

        {
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      )
      .then((response) => {
        console.log("성공");
        // console.log(response)
        // console.log(222)
        console.log(response);
        console.log(response.data);
        // console.log(response.data.reportContent)
        // localStorage.setItem('accessToken', response.data.accessToken)
        // setUser(response.data.user)
      })
      .catch((error) => {
        console.log("되겠냐");
        console.log(error);
      });
  }

  return (
    <div className="bg-gray-100 flex justify-center">
      <div>
        <TopBar content={"불편 사항 신고"} page={"report"}></TopBar>

        <div className="flex justify-center">
        <img src="/ssabus_logo.png" alt="싸버지 logo" className="flex justify-center items-center mt-5" />
        </div>
        <div>
        <p className="text-3xl my-10 text-blue-400 font-bold">신고하기</p>
        </div>
        <Card>
          <List className="flex justify-center">
            {reportList.map((item, idx) => {
              return (
                <ListItem
                  key={idx}
                  className="p-0"
                  onClick={() => {
                    setReportContent(item);
                  }}
                >
                  <label
                    htmlFor={`vertical-list-${idx}`}
                    className="flex w-full cursor-pointer items-center px-3 py-2"
                  >
                    <ListItemPrefix className="mr-3">
                      <Checkbox
                        id={`vertical-list-${idx}`}
                        ripple={false}
                        className="hover:before:opacity-0"
                        containerProps={{
                          className: "p-0",
                        }}
                      />
                    </ListItemPrefix>
                    <Typography color="blue-gray" className="font-medium">
                      {/* 신고 내용 각각을 list라는 이름으로 출력함 */}
                      {item}
                    </Typography>
                  </label>
                </ListItem>
              );
            })}
          </List>
        </Card>
        <div className="mt-10">
          <Button
            onClick={() => {
              sendReport(reportContent);
            }}
            className="bg-red-400"
          >
            신고하기
          </Button>

          <Button
            onClick={() => {
              navigate(-1);
            }}
          >
            뒤로가기
          </Button>
        </div>
      </div>
    </div>
  );
}
