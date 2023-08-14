import React, {
  useState,
  useEffect,
  useRef,
  useMemo,
  useCallback,
} from "react";
import { Marker, Popup, Tooltip } from "react-leaflet";
import { useAdminStore, useBusStore } from "../../store.js";
import L from "leaflet";
import axios from "axios";
import polyline from "@mapbox/polyline";
import { useMapEvents } from "react-leaflet";
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
} from "@material-tailwind/react";

export function CreateMarker() {
  const {
    items,
    hintPath,
    newPath,
    markers,
    newStaion,
    stationPath,
    stopCreate,
    setStationPath,
    setGeometry,
    setItems,
    setNewStation,
    setHintPath,
    setNewPath,
    setMarkers,
  } = useAdminStore();
  const [open, setOpen] = useState(false);
  const handleOpen = () => {
    setOpen(!open);
  };
  const [markerName, setMarkerName] = useState("");
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
        newMarker.on("dragend", (event) => {
          // const { lat, lng } = event.target.getLatLng();
          // console.log('새로운 좌표:', lat, lng);
          const { lat, lng } = event.target.getLatLng();
          let copy = [...hintPath];
          copy.push([lat, lng]);
          setHintPath(copy);
          console.log(copy);
        });

        let copy = [...hintPath];
        copy.push([lat, lng]);
        setHintPath(copy);
        copy = [...stationPath];
        copy.push([lat, lng]);
        setStationPath(copy);
        // 생성한 마커와 ID를 상태에 추가
        setMarkers([
          ...markers,
          {
            marker: 1,
            title: `경로${markers.length + 1}`,
            drag: true,
            lat: lat,
            lng: lng,
            stop: false,
          },
        ]);
        console.log("클릭 좌표:", lat, lng);
        console.log(newMarker);
        console.log(markers);
        copy = JSON.parse(JSON.stringify(items));
        copy["노선"].push({
          id: `경로${markers.length}`,
          title: `경로${markers.length + 1}`,
          status: false,
          lat: lat,
          lng: lng,
          stop: false,
          isExist: false,
        });
        setItems(copy);
        handleOpen();
      },
    });
  }

  const deleteMarker = (markerId, title) => {
    console.log(markerId);
    let copy = [...markers];
    copy.splice(markerId, 1);
    setMarkers(copy);
    copy = [...hintPath];
    copy.splice(markerId, 1);
    setHintPath(copy);
    copy = [...stationPath];
    copy.splice(markerId, 1);
    setStationPath(copy);
    copy = JSON.parse(JSON.stringify(items));
    let targetItem = null;
    let targetNum = 0;
    console.log(copy);
    for (let i = 0; i < copy["노선"].length; i++) {
      if (copy["노선"][i].title === title) {
        targetItem = copy["노선"][i];
        targetNum = i;
      }
    }
    console.log(targetItem);
    copy["노선"].splice(targetNum, 1);
    if (targetItem.isExist === true) {
      copy["정류장"].push(targetItem);
    }
    setItems(copy);
  };
  useEffect(() => {
    setCoords({ hintPath, newPath, setNewPath, setGeometry, stopCreate });
  }, [hintPath]);

  return (
    <>
      <Dialog
        size="xs"
        open={open}
        handler={handleOpen}
        className="bg-transparent shadow-none"
      >
        <Card className="mx-auto w-full max-w-[24rem]">
          <CardHeader
            variant="gradient"
            color="blue"
            className="mb-4 grid h-28 place-items-center"
          >
            <Typography variant="h3" color="white">
              경유지 등록
            </Typography>
          </CardHeader>
          <CardBody className="flex flex-col gap-4">
            <Input
              label="마커이름"
              value={markerName}
              size="lg"
              onChange={(e) => setMarkerName(e.target.value)}
            />
          </CardBody>
          <CardFooter className="pt-0">
            <Button
              variant="gradient"
              onClick={() => {
                const _markers = [...markers];
                _markers[_markers.length - 1].title = markerName;
                _markers[_markers.length - 1].id = markerName;
                let copy = JSON.parse(JSON.stringify(items));
                copy["노선"][copy["노선"].length - 1].title = markerName;
                copy["노선"][copy["노선"].length - 1].id = markerName;
                let flag = 0;
                console.log(copy);
                // copy['stations'].map((item, index) => {
                //   if (item.title === markerName) {
                //     flag = 1;
                //   }
                // })
                // copy['routes'].map((item, index) => {
                //   if (item.title === markerName && index != copy['routes'].length - 1) {
                //     flag = 1;
                //   }
                // })
                if (markerName === "") {
                  alert("경유지 이름을 지정해 주세요.");
                }
                // else if (flag == 1){
                //   alert('이미 있는 이름입니다. 다시 입력해 주세요.')
                // }
                else {
                  setMarkers(_markers);
                  setItems(copy);
                  handleOpen();
                  setMarkerName("");
                }
              }}
              fullWidth
            >
              등록
            </Button>
          </CardFooter>
        </Card>
      </Dialog>
      {stopCreate ? null : <MyComponent />}
      {/* <MyComponent></MyComponent> */}
      {stopCreate
        ? markers.map(
            ({ marker, title, drag, stop }, index) =>
              stop && (
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
                      copy = [...stationPath];
                      copy[index] = [lat, lng];
                      setStationPath(copy);
                    },
                  }}
                >
                  <Tooltip permanent>{title}</Tooltip>
                  <Popup>
                    Marker {index}
                    <IconButton
                      onClick={(e) => {
                        e.stopPropagation();
                        deleteMarker(index, title);
                      }}
                    >
                      x
                    </IconButton>
                  </Popup>
                </Marker>
              )
          )
        : markers.map(({ marker, title, drag }, index) => (
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
                  copy = [...stationPath];
                  copy[index] = [lat, lng];
                  setStationPath(copy);
                },
              }}
            >
              <Tooltip permanent>{title}</Tooltip>
              <Popup>
                Marker {index}
                <IconButton
                  onClick={(e) => {
                    e.stopPropagation();
                    deleteMarker(index, title);
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

function setCoords({ hintPath, newPath, setNewPath, setGeometry, stopCreate }) {
  console.log(hintPath);
  if (stopCreate === true) {
    return;
  }
  if (hintPath.length < 2) {
    setNewPath([]);
    setGeometry("");
  } else {
    axios
      .post("/api/routes/generate", {
        hints: hintPath,
      })
      .then((res) => {
        console.log(res.data.route.geometry);
        setGeometry(res.data.route.geometry);
        setNewPath(polyline.decode(res.data.route.geometry));
      })
      .catch((error) => {
        console.log(error);
      });
  }
}
