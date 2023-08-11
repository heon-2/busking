import React, { useEffect } from 'react';
import StationCard from './StationCard';
import { useState } from 'react';
import { useBusStore, useAdminStore } from '../../store';
import axios from 'axios';

const StationContainer = () => {
  const { hintPath, markers, setMarkers, items, setItems } = useAdminStore();
  const {stations, setStations} = useBusStore();
  // const stations = [{name: "SSAFY 교육장", lat: 35.20434915762915, lng: 126.80986958640538}, 
  //                   {name: "국민은행 사거리", lat: 35.19019048804865, lng: 126.82341592439369}]
  useEffect(() => {
    axios.get('api/companies/1/stations')
    .then((response) => {
      console.log(response)
      setStations(response.data)
    })    
  }, [])
  useEffect(() => {
    console.log('왜안돼')
    setItems({
      stations: stations.map((_, i) => ({
        id: `${_.name}`,
        title: `${_.name}`,
        status: true,
        lat: `${_.lat}`,
        lng: `${_.lng}`
      })),
      routes: [],
    });
    console.log(items)
  }, [stations])

  // useEffect(() => {
  //   // console.log(items)
  //   let copy = JSON.parse(JSON.stringify(items))
  //   console.log(markers)
  //   copy['routes'] = hintPath.map((_, i) => ({
  //     id: `${markers[i].title}`,
  //     title: `${markers[i].title}`,
  //     status: 'false',
  //     lat: markers[i].lat,
  //     lng: markers[i].lng,
  //   }))
  //   setItems(copy)
  //   console.log('이거 먹힘?')
  // }, [markers])

  // useEffect(() => {
  //   stations
  // }, [])

  return (
    <>
      <StationCard items={items} setItems={setItems} />
    </>
  );
};

export default StationContainer;
