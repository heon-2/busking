import React, { useState } from "react";
import axios from "axios";
import {
  Card,
  Typography,
  Button,
  Dialog,
  CardHeader,
  CardBody,
  CardFooter,
  Input,
  IconButton,
} from "@material-tailwind/react";

import { useEffect } from "react";


// 버스정보조회 페이지

const TABLE_HEAD = ["호차", "노선 이름", "Detail"];


export function BusList() {
  const [TABLE_ROWS, setTABLE_ROWS] = useState([]);
  const [busList, setBusList] = useState([]);
  const [ routeName, setRouteName ] = useState('');
  const [ busNumber, setBusNumber ] = useState('');


  console.log(busList)
  console.log(TABLE_ROWS)
  console.log(routeName)
  console.log(busNumber)

  useEffect(() => {
    getDefaultBusList();
}, []);



function getDefaultBusList() {
    
  axios
  .get("/api/companies/1/buses")
  .then((res) => {
      console.log(res.data);
      const busNumbers = res.data.map(bus => bus.id.no);
      setBusList(busNumbers);

      
  })
  .catch((err) => {
      console.log(err);
  });
}







  return (
    <div className="flex flex-col flex-grow p-4 pl-4 pr-8">
    <Card className="w-full h-full mt-4 overflow-y-auto">
      <table className="w-full text-left table-auto min-w-max">
        <thead>
          <tr>
            {TABLE_HEAD.map((head) => (
              <th key={head} className="p-4 border-b border-blue-gray-100 bg-blue-gray-50">
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
          {TABLE_ROWS.map(({ busNumber, routeName }, index) => (
            <tr key={routeName} className="even:bg-blue-gray-50/50">
              <td className="p-4">
                <Typography variant="small" color="blue-gray" className="font-normal">
                  {busNumber}
                </Typography>
              </td>
              <td className="p-4">
                <Typography variant="small" color="blue-gray" className="font-normal">
                  {routeName}
                </Typography>
              </td>
              <td className="p-4">
                <Typography as="a" href="#" variant="small" color="blue" className="font-medium">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 14.25v-2.625a3.375 3.375 0 00-3.375-3.375h-1.5A1.125 1.125 0 0113.5 7.125v-1.5a3.375 3.375 0 00-3.375-3.375H8.25m5.231 13.481L15 17.25m-4.5-15H5.625c-.621 0-1.125.504-1.125 1.125v16.5c0 .621.504 1.125 1.125 1.125h12.75c.621 0 1.125-.504 1.125-1.125V11.25a9 9 0 00-9-9zm3.75 11.625a2.625 2.625 0 11-5.25 0 2.625 2.625 0 015.25 0z" />
                </svg>
                </Typography>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </Card>
    </div>
  )
}