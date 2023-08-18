import React, { useEffect } from "react";
import StationCard from "./StationCard";
import { useState } from "react";
import { useBusStore, useAdminStore } from "../../store";
import axios from "axios";

const StationContainer = () => {
  const { hintPath, markers, setMarkers, items, setItems } = useAdminStore();
  const { stations, setStations } = useBusStore();
  // const stations = [{name: "SSAFY 교육장", lat: 35.20434915762915, lng: 126.80986958640538},
  //                   {name: "국민은행 사거리", lat: 35.19019048804865, lng: 126.82341592439369}]
  useEffect(() => {
    axios.get("api/companies/1/stations").then((response) => {
      setStations(response.data);
    });
  }, []);
  useEffect(() => {
    setItems({
      정류장: stations.map((_, i) => ({
        id: `${_.name}`,
        title: `${_.name}`,
        status: true,
        lat: `${_.lat}`,
        lng: `${_.lng}`,
        stop: true,
        isExist: true,
      })),
      노선: [],
    });
  }, [stations]);

  return (
    <>
      <StationCard items={items} setItems={setItems} />
    </>
  );
};

export default StationContainer;

