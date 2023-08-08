// import React, { useState, useEffect, useRef, useMemo, useCallback } from 'react'
// import { Marker, Popup } from 'react-leaflet'
// import { useAdminStore } from '../../store.js'
// import L from 'leaflet'
// import axios from 'axios'
// import polyline from '@mapbox/polyline'
// import { useMapEvents } from 'react-leaflet'
// import { IconButton, Button } from "@material-tailwind/react";


// export function DragMarker(props) {
//   const { hintPath, newPath, markers, setHintPath, setNewPath, setMarkers } = useAdminStore() 

//   const deleteMarker = (markerId) => {
//     let copy = [...markers]
//     copy.splice(markerId, 1)
//     setMarkers(copy);
//     copy = [...hintPath]
//     copy.splice(markerId, 1)
//     setHintPath(copy)
//   };

//   useEffect(() => {
//     if (hintPath.length >= 2) {
//       setCoords({hintPath, newPath, setNewPath})
//     }
//   }, [hintPath])

//   return (
//     <>
//     {
//       markers.map((marker, index) => {
//         <Marker key={index} position={marker.getLatLng()} draggable={true} icon={marker.options.icon} eventHandlers={{
//           dragend: (e) => {
//             const { lat, lng } = e.target.getLatLng();
//             let copy = [...hintPath];
//             copy[index] = [lat, lng];
//             setHintPath(copy);
//             console.log(copy);
//           },
//         }}>
//           <Popup>Marker {index}<IconButton onClick={(e) => {e.stopPropagation(); deleteMarker(index);}}>x</IconButton></Popup>
//         </Marker>
//       })
//     }
//     </>
//   )
// }


// function setCoords({hintPath, newPath, setNewPath}) {
//   if (hintPath.length < 2) {
//     alert("경유지를 2개 이상 설정해주세요.")
//   }
//   else {
//     axios.post('/api/routes/generate', {
//       hints: hintPath,
//     })
//     .then((res) => {
//       console.log(res)
//       setNewPath(polyline.decode(res.data.route.geometry))
//     })
//   }

// }