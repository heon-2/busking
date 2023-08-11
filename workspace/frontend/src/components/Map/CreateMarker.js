import React, { useState, useEffect, useRef, useMemo, useCallback } from 'react'
import { Marker, Popup, Tooltip } from 'react-leaflet'
import { useAdminStore, useBusStore } from '../../store.js'
import L from 'leaflet'
import axios from 'axios'
import polyline from '@mapbox/polyline'
import { useMapEvents } from 'react-leaflet'
import { IconButton, Button } from "@material-tailwind/react";

export function CreateMarker() {
  const { items, hintPath, newPath, markers, newStaion, setItems, setNewStation, setHintPath, setNewPath, setMarkers } = useAdminStore() 
  // const { stations, setStations } = useBusStore();
    function MyComponent() {
        const map = useMapEvents({
            click: (e) => {
            const { lat, lng } = e.latlng;
        
            // 마커 아이콘 스타일 설정

        
            // 마커에 적용될 스타일 설정
            const markerOptions = {
                draggable: true, // 마커를 드래그할 수 있도록 설정
            };
        
            // 마커 생성 및 스타일 적용
            const newMarker = L.marker([lat, lng], markerOptions);
            const markerId = markers.length; // 마커에 부여할 고유한 ID 또는 인덱스
        
            // 드래그 이벤트 리스너 등록
            newMarker.on('dragend', (event) => {
                // const { lat, lng } = event.target.getLatLng();
                // console.log('새로운 좌표:', lat, lng);
                const { lat, lng } = event.target.getLatLng();
                let copy = [...hintPath]
                copy.push([lat, lng])
                setHintPath(copy)
                console.log(copy)
            });
            
            let copy = [...hintPath]
            copy.push([lat, lng])
            setHintPath(copy)
            console.log(copy)
            // 생성한 마커와 ID를 상태에 추가
            setMarkers([...markers, { marker: 1, title: `경로${markers.length+1}`, drag: true, lat: lat, lng: lng }]);
            console.log('클릭 좌표:', lat, lng);
            console.log(newMarker)
            console.log(markers)
            copy = JSON.parse(JSON.stringify(items))
            copy['routes'].push({id: `경로${markers.length}`, title: `경로${markers.length+1}`, status: false, lat: lat, lng: lng})
            setItems(copy)
            }
        });
    }

    const deleteMarker = (markerId) => {
        let copy = [...markers]
        copy.splice(markerId, 1)
        setMarkers(copy);
        copy = [...hintPath]
        copy.splice(markerId, 1)
        setHintPath(copy)
      };
    useEffect(() => {
          setCoords({hintPath, newPath, setNewPath})
      }, [hintPath])

      return (
        <>
          <MyComponent />
          {markers.map(({ marker, title, drag }, index) => (
            <Marker
              key={index}
              position={hintPath[index]} // 이 부분도 수정이 필요할 수 있습니다.
              draggable={drag}
              eventHandlers={{
                dragend: (e) => {
                  const { lat, lng } = e.target.getLatLng();
                  let copy = [...hintPath];
                  copy[index] = [lat, lng];
                  setHintPath(copy);
                  console.log(copy);
                },
              }}
              
            >
              <Tooltip permanent>
                {title}
              </Tooltip>
              <Popup>
                Marker {index}
                <IconButton
                  onClick={(e) => {
                    e.stopPropagation();
                    deleteMarker(index);
                  }}
                >
                  x
                </IconButton>
              </Popup>
            </Marker>
          ))}
        </>
      );
}


function setCoords({hintPath, newPath, setNewPath}) {
  console.log(hintPath)
    if (hintPath.length < 2) {
      setNewPath([])
    }
    else {
      axios.post('/api/routes/generate', {
        hints: hintPath,
      })
      .then((res) => {
        console.log(res)
        setNewPath(polyline.decode(res.data.route.geometry))
      })
      .catch((error) => {
        console.log(error)
      })
    }
  }