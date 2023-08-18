import React from "react";
import { Card, Typography, Button } from "@material-tailwind/react";
import axios from "axios";
import { useState, useEffect } from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
export function ReportList() {
  // 토큰 불러와서
  const accessToken = localStorage.getItem("accessToken");
  const navigate = useNavigate();

  // report 정보를 담을 state
  const [report, setReport] = useState([]);

  useEffect(() => {
    getReport();
  }, []);
  // 정보 조회하는 함수
  function getReport() {
    axios
      .get("/api/reports", {
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((response) => {
        setReport(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  const TABLE_HEAD = [
    "신고 번호",
    "신고자",
    "버스 호차",
    "신고 내용",
    "신고 시각",
  ];
  return (
    <div>
      <Card className="h-full w-full overflow-scroll mt-4">
        <table className="w-full min-w-max table-auto text-left">
          <thead>
            <tr>
              {TABLE_HEAD.map((head) => (
                <th
                  key={head}
                  className="border-b border-blue-gray-100 bg-blue-gray-50 p-4"
                >
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal leading-none opacity-70"
                  >
                    {head}
                  </Typography>
                </th>
              ))}
            </tr>
          </thead>
          <tbody>
            {report.map((row, index) => (
              <tr
                onClick={() => {
                  navigate("/report/" + Object.values(row.reportId));
                }}
                key={index}
                className="even:bg-blue-gray-50/50 hover:bg-blue-50"
              >
                <td className="p-4">
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                  >
                    {index + 1}

                  </Typography>
                </td>
                <td className="p-4">
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                  >
                    {row.reporter}
                  </Typography>
                </td>
                <td className="p-4">
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                  >
                    {row.busNum}호차
                  </Typography>
                </td>
                <td className="p-4">
                  <Typography
                    variant="small"
                    color="blue-gray"
                    className="font-normal"
                  >
                    {row.description}
                  </Typography>
                </td>
                <td className="p-4">
                  <Typography
                    as="a"
                    href="#"
                    variant="small"
                    color="blue"
                    className="font-medium"
                  >
                    {row.localDateTime.at(0)}년 {row.localDateTime.at(1)}월{" "} {row.localDateTime.at(2)}일{" "} {row.localDateTime.at(3)}시{" "} {row.localDateTime.at(4)}분{" "} {row.localDateTime.at(5)}초{" "}
                  </Typography>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </Card>
    </div>
  );
}
