import React, { useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { FindMe } from "../../common/FindMe.js";
import { BusInfo } from "../../components/Map/BusInfo";
import { BusNum } from "../../components/Map/BusNum";
import { Dial } from "../../components/Map/Dial";
import { MapLayer } from "../../components/Map/MapLayer";
import { TopBar } from "../../components/Map/TopBar";
import { DragMarker } from "../../components/Map/DragMarker";
import { AdminPath } from "../../components/Map/AdminPath";
import { CreateMarker } from "../../components/Map/CreateMarker";
import { useAdminStore, useMapStore, useUserStore } from "../../store.js";
import { IconButton, Button } from "@material-tailwind/react";
import { LiveLocation } from "../../components/Map/LiveLocation.js";
import axios from "axios";
import polyline from "@mapbox/polyline";

export function UserMap() {
  const { user, selectedStations, selectedRoute, selectedBuss, setSelectedStations, setSelectedRoute, setSelectedBuss } = useUserStore();
  const { busInfo, setBusInfo } = useMapStore(); 
  const navigate = useNavigate();

  useEffect(() => {
    if (user == null){
      navigate('/')
    }
    else if (user.role != 'EMPLOYEE'){
      if (user.role == 'DRIVER'){
        navigate('/knightselect')
      }
    }
  }, [])

  useEffect(() => {
    setSelectedBuss(null)
    axios.get('/api/companies/1/buses')
    .then((response) => {
      setBusInfo(response.data)
    })
    .catch((error) => {
      console.log(error)
    })
  }, [])

  useEffect(() => {
    if (selectedBuss == null) {
      setSelectedStations([])
      setSelectedRoute(null)
    }
    else {
      if (busInfo.length < selectedBuss) {
        setSelectedStations([])
        setSelectedRoute(null)
        return;
      }
      else if (busInfo.length > 0) {
        let nPath = []
        let tmp = [...polyline.decode(busInfo[selectedBuss - 1].routes[1].geometry)]
        for (let i = 0; i < tmp.length - 1; i++) {
          nPath.push(tmp[i])
          for (let j =1; j < 300; j++) {
            nPath.push([tmp[i][0] + ((tmp[i+1][0] - tmp[i][0])/300)*j, tmp[i][1] + ((tmp[i+1][1] - tmp[i][1])/300)*j])
          }
        }
        setSelectedRoute(nPath)
        let copy = []
        busInfo[selectedBuss - 1].routes[1].stations.map((station, index) => {
          copy.push([[station.lat, station.lng], station.name])
        })
        setSelectedStations(copy)
      }
    }
  }, [selectedBuss])
  return (
    <div className="relative">
      <MapLayer
        FindMe={<FindMe />}
        Dial={<Dial></Dial>}
        Marker={<LiveLocation></LiveLocation>}
        UserPath={
          <>
          {
            // selectedBus == null ? null : <Polyline positions={}></Polyline>
          }
          </>
        }
      ></MapLayer>
      <div
        className="absoulte fixed top-2 left-2 right-2"
        style={{ zIndex: 400 }}
      >
        <TopBar content={"실시간 버스 위치"} page={"usermap"}></TopBar>
      </div>

      <div className="absoulute fixed bottom-0" style={{ zIndex: 400 }}>
        <BusNum></BusNum>
      </div>
    </div>
  );
}

