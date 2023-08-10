import React, { useState, useEffect, useRef, useMemo } from 'react';
import { useAdminStore } from '../../store.js';
import { MapContainer, useMap, TileLayer, Marker, useMapEvents, Popup, Polyline } from 'react-leaflet'; 
import L from 'leaflet';
import polyline from '@mapbox/polyline'
import RBush from 'rbush'; 

export function CreateStop() {
    const { newPath, mouseLocation } = useAdminStore();
    const map = useMapEvents({
        mousemove: (e) => {

        }
    })
    useEffect(() => {
    const rtree = new RBush();
    newPath.forEach((point, idx) => {
        rtree.insert({
            minX: point[0],
            minY: point[1],
            maxX: point[0],
            maxY: point[1],
            idx: idx
        })
    })
    console.log(rtree)
    }, [newPath]) 
}