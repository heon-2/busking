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
  const { user, selectedStations, selectedRoute, selectedBus, setSelectedStations, setSelectedRoute, setSelectedBus } = useUserStore();
  const { busInfo, setBusInfo } = useMapStore(); 
  const navigate = useNavigate();

  useEffect(() => {
    console.log(user)
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
    setSelectedBus(null)
    axios.get('/api/companies/1/buses')
    .then((response) => {
      console.log(response.data)
      setBusInfo(response.data)
      // console.log(response.data[0].routes[0].stations)
      // console.log(polyline.decode(response.data[0].routes[0].geometry))
    })
    .catch((error) => {
      console.log(error)
    })
  }, [])

  useEffect(() => {
    console.log(busInfo)
    if (selectedBus == null) {
      console.log(selectedStations)
      setSelectedStations([])
      setSelectedRoute(null)
    }
    else {
      if (busInfo.length < selectedBus) {
        setSelectedStations([])
        setSelectedRoute(null)
        return;
      }
      else if (busInfo.length > 0) {
        setSelectedRoute(polyline.decode(busInfo[selectedBus - 1].routes[0].geometry))
        let copy = []
        busInfo[selectedBus - 1].routes[0].stations.map((station, index) => {
          console.log(station)
          copy.push([[station.lat, station.lng], station.name])
        })
        setSelectedStations(copy)
      }
    }
  }, [selectedBus])
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


// function setCoords({ hintPath, newPath, setNewPath, setGeometry, stopCreate }) {
//   console.log(hintPath);
//   if (stopCreate === true) {
//     return;
//   }
//   if (hintPath.length < 2) {
//     setNewPath([]);
//     setGeometry("");
//   } else {
//     axios
//       .post("/api/routes/generate", {
//         hints: hintPath,
//       })
//       .then((res) => {
//         console.log(res.data.route.geometry);
//         setGeometry(res.data.route.geometry);
//         setNewPath(polyline.decode(res.data.route.geometry));
//       })
//       .catch((error) => {
//         console.log(error);
//       });
//   }
// }