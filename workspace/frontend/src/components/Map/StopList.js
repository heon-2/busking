import React, { useState, useEffect } from 'react';
import axios from 'axios';

export function StopList() {
    useEffect(() => {
        axios.get('/api/companies/1/stations')
        .then((response) => {
            console.log(response.data)
        })
        .catch((error) => {
            console.log(error)
        })
    }, [])
    return (
        <></>
    )
}