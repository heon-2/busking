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
        console.log(accessToken);

        console.log(response.data);
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
      {/* <Button onClick={() => getReport()}>정보조회</Button> */}

      {/* <Route
          path="report/:reportId"
          element={<ReportDetail reportId={reportId} />}
        /> */}

      <Card className="h-full w-full overflow-scroll mt-4">
        <table className="w-full min-w-max table-auto text-left">
          <thead>
            <tr>
              {TABLE_HEAD.map((head) => (
                // const reportId = row.reportId;
                <th
                  // onClick={() => console.log(123)}
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
              // var datetime = row.localDateTime.toString().substring(0, 12);
              // var year = datetime.substring(0, 4);
              // var month = datetime.substring(5, 6);
              // var day = datetime.substring(6, 7);
              // var hour = datetime.substring(7, 8)-3;
              // var minute = datetime.substring(8, 10);
              // var second = datetime.substring(10, 12);
              // Object.values(row.reportId))
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

                    {/* {row.createdAt} */}
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
                    {/* <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      strokeWidth={1.5}
                      stroke="currentColor"
                      className="w-5 h-5"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10"
                      />
                    </svg> */}
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
