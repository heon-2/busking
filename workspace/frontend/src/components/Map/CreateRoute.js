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
        <Button variant="gradient" onClick={() => {props.handleCreate(); props.setPathName(''); RegisterRoute({items, newRoute, stationIds, geometry, bound, setStationIds, setNewRoute, pName})}} fullWidth>
              등록
        </Button>
    )
}

async function RegisterRoute({items, newRoute, stationIds, geometry, bound, setStationIds, setNewRoute, pName}) {
    try {
        const accessToken = localStorage.getItem('accessToken')
        const response = await axios.get('/api/companies/1/stations')
        console.log(response.data)
        const response1 = await axios.get('/api/companies/1/buses')
        console.log(response1.data.length+1)
        console.log(bound)
        console.log(geometry)
        console.log(typeof(pName))
        console.log(accessToken)
        const response2 = await axios.post('/api/companies/1/buses/register',{
            "busNum": response1.data.length + 1
        },
        // {
            //     headers: {
                //       Accept: "application/json",
                //       Authorization: `Bearer ${accessToken}`,
                //     },
                // }
                )
                console.log(response2.data)
                const response3 = await axios.post('/api/companies/1/routes', {
                    stations: ["1", "2"],
                    name: pName,
                    geometry: geometry,
                    direction : "inbound",
                },
                {
                    headers: {
                      Accept: "application/json",
                      Authorization: `Bearer ${accessToken}`,
                    },
                }
                )
        function fetchData() {
            items['routes'].forEach((item, index) => {
                if (item.stop === true) {
                    response.data.forEach((st, idx) => {
                        if (st.name === item.title){
                            console.log(1)
                            setStationIds([...stationIds, st.stationId])
                        }
                    })
                }
            })
        } 
        
        await fetchData();

        console.log(response3)
    }
    catch (error) {
        console.log(error)
    }
}