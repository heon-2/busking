import React, { useState, useEffect } from 'react';
import { useAdminStore, useBusStore } from '../../store';
import axios from 'axios';
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
    const pName = props.pathName
    const pNo = props.busNo
    const [stationIds, setStationIds] = useState([])
    const [newRoute, setNewRoute] = useState([])
    const [bound, setBound] = useState('')
    const { items, geometry, BusNo, direction } = useAdminStore();
    useEffect(() => {
        if (direction === true) {
            setBound('inbound')
        }
        else {
            setBound('outbound')
        }
    }, [])
    

    return (
        <Button variant="gradient" onClick={() => {props.handleCreate(); props.setPathName(''); RegisterRoute({direction, items, newRoute, stationIds, geometry, bound, setStationIds, setNewRoute, pName, pNo})}} fullWidth>
              등록
        </Button>
    )
}

async function RegisterRoute({direction, items, newRoute, stationIds, geometry, bound, setStationIds, setNewRoute, pName, pNo}) {
    const sIds = []
    let flag = 0;
    try {
        const accessToken = localStorage.getItem('accessToken')
        const response = await axios.get('/api/companies/1/stations')
        console.log(response.data)
        const response1 = await axios.get('/api/companies/1/buses')
        console.log(response1)
        function numCheck() {
            response1.data.map((bus) => {
                console.log(pNo)
                console.log(bus.id.no)
                if (bus.id.no == pNo){
                    flag = 1;
                }
            })
        }
        await numCheck();
        console.log(flag)
        if (flag === 0){
            const response2 = await axios.post('/api/companies/1/buses/register',{
                busNum: pNo
            })
        }
        console.log(bound)
        console.log(geometry)
        console.log(typeof(pName))
        console.log(accessToken)
        function fetchData() {
            items['routes'].forEach((item, index) => {
                if (item.stop === true) {
                    response.data.forEach((st, idx) => {
                        if (st.name === item.title){
                            console.log(1)
                            sIds.push(st.stationId)
                        }
                    })
                }
            })
        } 
        
        await fetchData();
        console.log(sIds)
        const response3 = await axios.post('/api/companies/1/routes', {
            stations: sIds,
            name: pName,
            geometry: geometry,
            direction : direction,
        },
        {
            headers: {
                Accept: "application/json",
                Authorization: `Bearer ${accessToken}`,
            },
        }
        )
        console.log(response3)
        const response4 = await axios.get('/api/companies/1/routes')
        console.log(response4.data)
        const rId = response4.data[response4.data.length - 1].id
        const response5 = await axios.post(`/api/companies/1/buses/${pNo}/assign`, {
            routeId: rId
        },
        {
            headers: {
                Accept: "application/json",
                Authorization: `Bearer ${accessToken}`,
            },
        })
        console.log(response5);
        const response6 = await axios.get(`/api/companies/1/buses/${pNo}`,
        {
            headers: {
                Accept: "application/json",
                Authorization: `Bearer ${accessToken}`,
            },
        })
        console.log(response6)
    }
    catch (error) {
        console.log(error)
    }
}