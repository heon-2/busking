import React, { useEffect } from 'react';
import StationCard from './StationCard';
import { useState } from 'react';
import { useBusStore, useAdminStore } from '../../store';
import axios from 'axios';

const StationContainer = () => {
  const { hintPath, markers, setMarkers } = useAdminStore();
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

  const [items, setItems] = useState({
    stations: stations.map((_, i) => ({
      id: `${i}${i}`,
      title: `${_.name}`,
      status: 'false',
      lat: `${_.lat}`,
      lng: `${_.lng}`
    })),
    routes: hintPath.map((_, i) => ({
      id: `${_[0]}${_[1]}`,
      title: `${_.name}`,
      status: 'false',
      lat: hintPath[i][0],
      lng: hintPath[i][1],
    })),
  }, [markers]);

  // useEffect(() => {
  //   let copy = JSON.parse(JSON.stringify(items))
  //   copy['routes'] = hintPath.map((_, i) => ({
  //     id: `${_.name}`,
  //     title: `${_.name}`,
  //     status: 'false',
  //     lat: hintPath[i][0],
  //     lng: hintPath[i][1],
  //   }))
  //   setItems(copy)
  //   console.log('이거 먹힘?')
  // }, [markers, hintPath])

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
