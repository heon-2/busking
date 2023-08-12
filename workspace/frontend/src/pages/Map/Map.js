import React, { useState, useEffect } from 'react';
import { useNavigate, Routes, Route, Link } from 'react-router-dom';
import { MapContainer, TileLayer, Marker, Popup, Polyline } from 'react-leaflet';
import L from 'leaflet';
import { FindMe } from '../../common/FindMe.js';
import { BusInfo } from '../../components/Map/BusInfo';
import { BusNum } from '../../components/Map/BusNum';
import { Dial } from '../../components/Map/Dial';
import { MapLayer } from '../../components/Map/MapLayer';
import { TopBar } from '../../components/Map/TopBar';
import { DragMarker } from '../../components/Map/DragMarker';
import { AdminPath } from '../../components/Map/AdminPath';
import { CreateMarker } from '../../components/Map/CreateMarker';
import { useAdminStore } from '../../store.js';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend'
import { PathContainer } from '../../components/Map/PathContainer.js'
import { MouseLocation } from '../../components/Map/MouseLocation';
import { CreateStop } from '../../components/Map/CreateStop.js'
// import { StopList } from '../../components/Map/StopList.js'
import StationContainer from '../../components/Map/StationContainer';
import { CreateRoute } from '../../components/Map/CreateRoute';

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

export function Map() {
    const [open, setOpen] = useState(false)
    const [pathName, setPathName] = useState('')
    const [createOpen, setCreateOpen] = useState(false)
    const handleCreate = () => {setCreateOpen(!createOpen)}
    const handleOpen = () => { setOpen(!open); };
    const { hintPath, stopCreate, busNo, setStopCreate, setDirection, setBusNo } = useAdminStore()
    return (
        <div >
            <StationContainer></StationContainer>
            {/* <DndProvider backend={HTML5Backend} >
                <PathContainer className=""></PathContainer>
                <PathContainer className=""></PathContainer>
            </DndProvider> */}

            
            {
                stopCreate ? null : <Button onClick={() => {
                    if (hintPath.length < 2){
                        alert('아직 경로 생성이 안됐습니다.')
                    }
                    else {
                        setStopCreate(true)
                    }
                }}>정류장 선택하기</Button>
            }
            {
                stopCreate ? <Button onClick={() => setStopCreate()}>뒤로 가기</Button> : null
            }
            {
                stopCreate ? <Button onClick={() => handleCreate()}>노선 생성</Button> : null
            }
      <Dialog
        size="xs"
        open={createOpen}
        handler={handleCreate}
        className="bg-transparent shadow-none"
      >
        <Card className="mx-auto w-full max-w-[24rem]">
          <CardHeader
            variant="gradient"
            color="blue"
            className="mb-4 grid h-28 place-items-center"
          >
            <Typography variant="h3" color="white">
              노선 등록
            </Typography>
          </CardHeader>
          <CardBody className="flex flex-col gap-4">
            <Input type="number" label="버스 번호" value={busNo} size="lg" onChange={(e) => setBusNo(e.target.value)} />
            <Input label="노선 이름" value={pathName} size="lg" onChange={(e) => setPathName(e.target.value)} />
          </CardBody>
          <CardFooter className="pt-0">
            {/* <Button variant="gradient" onClick={() => {handleCreate(); CreateRoute(); setPathName('')}} fullWidth>
              등록
            </Button> */}
            <CreateRoute handleCreate={handleCreate} setPathName={setPathName} pathName={pathName}></CreateRoute>
          </CardFooter>
            <Checkbox label={'퇴근'} onClick={() => setDirection()}></Checkbox>
        </Card>
      </Dialog>
            <MapLayer 
            // FindMe={<FindMe></FindMe>}
            Dial={<Dial></Dial>}
            // BusNum={<BusNum></BusNum>}
            CreateMarker={<CreateMarker></CreateMarker>}
            // DragMarker={<DragMarker></DragMarker>}
            AdminPath={<AdminPath></AdminPath>}
            MouseLocation={<MouseLocation></MouseLocation>}
            CreateStop={
                stopCreate ? <CreateStop></CreateStop> : null
            }
            />
        </div>
    )
}

