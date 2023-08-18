import React, { useState, useEffect } from "react";
import { useAdminStore, useBusStore } from "../../store";
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
  Checkbox,
} from "@material-tailwind/react";

export function CreateRoute(props) {
  const pName = props.pathName;
  const pNo = props.busNo;
  const [stationIds, setStationIds] = useState([]);
  const [newRoute, setNewRoute] = useState([]);
  const [bound, setBound] = useState("");
  const { items, geometry, BusNo, direction } = useAdminStore();
  useEffect(() => {
    if (direction === true) {
      setBound("inbound");
    } else {
      setBound("outbound");
    }
  }, []);

  return (
    <Button
      variant="gradient"
      onClick={() => {
        props.handleCreate();
        props.setPathName("");
        RegisterRoute({
          direction,
          items,
          newRoute,
          stationIds,
          geometry,
          bound,
          setStationIds,
          setNewRoute,
          pName,
          pNo,
        });
      }}
      fullWidth
    >
      등록
    </Button>
  );
}

async function RegisterRoute({
  direction,
  items,
  newRoute,
  stationIds,
  geometry,
  bound,
  setStationIds,
  setNewRoute,
  pName,
  pNo,
}) {
  const sIds = [];
  let flag = 0;
  let b = "inbound";
  if (direction == true) {
    b = "outbound";
  }
  try {
    const accessToken = localStorage.getItem("accessToken");
    const response = await axios.get("/api/companies/1/stations");
    const response1 = await axios.get("/api/companies/1/buses");
    function numCheck() {
      response1.data.map((bus) => {
        if (bus.id.no == pNo) {
          flag = 1;
        }
      });
    }
    await numCheck();
    if (flag === 0) {
      const response2 = await axios.post("/api/companies/1/buses/register", {
        busNum: pNo,
      });
    }
    function fetchData() {
      items["노선"].forEach((item, index) => {
        if (item.stop === true) {
          response.data.forEach((st, idx) => {
            if (st.name === item.title) {
              sIds.push(st.stationId);
            }
          });
        }
      });
    }

    await fetchData();
    const response3 = await axios.post(
      "/api/companies/1/routes",
      {
        stations: sIds,
        name: pName,
        geometry: geometry,
        direction: b,
      },
      {
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    const response4 = await axios.get("/api/companies/1/routes");
    let rId = 0;
    
    function fetchName() {
      let check = 0;
      for (let i =0; i<response4.data.length;i++){
        if (response4.data[i].name == pName){
          check = response4.data[i].id
        }
      }
      return check;
    }
    rId = await fetchName();
    const response5 = await axios.post(
      `/api/companies/1/buses/${pNo}/assign`,
      {
        routeId: rId,
      },
      {
        headers: {
          Accept: "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    const response6 = await axios.get(`/api/companies/1/buses/${pNo}`, {
      headers: {
        Accept: "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
    });
  } catch (error) {
    console.log(error);
  }
}
