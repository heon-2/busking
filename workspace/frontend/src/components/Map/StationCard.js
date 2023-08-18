import { useEffect, useState } from "react";
import { DragDropContext, Draggable, Droppable } from "react-beautiful-dnd";
import { useAdminStore, useBusStore } from "../../store";
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

export default function StationCard({ items, setItems }) {
  const {
    hintPath,
    markers,
    route,
    stationMarkers,
    newStation,
    setHintPath,
    setMarkers,
    setRoute,
    setStationMarkers,
    setNewStation,
  } = useAdminStore();
  const { stations, setStations } = useBusStore();

  const deleteMarker = (markerId, title) => {
    let copy = [...markers];
    copy.splice(markerId, 1);
    setMarkers(copy);
    copy = [...hintPath];
    copy.splice(markerId, 1);
    setHintPath(copy);
    copy = JSON.parse(JSON.stringify(items));
    let targetItem = null;
    let targetNum = 0;
    for (let i = 0; i < copy["노선"].length; i++) {
      if (copy["노선"][i].title === title) {
        targetItem = copy["노선"][i];
        targetNum = i;
      }
    }
    copy["노선"].splice(targetNum, 1);
    if (targetItem.isExist === true) {
      copy["정류장"].push(targetItem);
    }
    setItems(copy);
  };

  const onDragEnd = ({ source, destination }) => {
    if (!destination) return;

    const scourceKey = source.droppableId;
    const destinationKey = destination.droppableId;
    if (destinationKey === "정류장" && scourceKey !== destinationKey) {
      const t = items[scourceKey][source.index];
      if (t.status === false) {
        return;
      }
    }
    const _items = JSON.parse(JSON.stringify(items));
    const [targetItem] = _items[scourceKey].splice(source.index, 1);
    _items[destinationKey].splice(destination.index, 0, targetItem);
    setItems(_items); // 여기서 items가 업데이트가 안됨
    // 경로에 추가
    if (destinationKey === "노선" && destinationKey != scourceKey) {
      const _hintPath = [...hintPath];
      _hintPath.splice(destination.index, 0, [
        parseFloat(targetItem.lat),
        parseFloat(targetItem.lng),
      ]);
      setHintPath(_hintPath);
      const _markers = [...markers];
      _markers.splice(destination.index, 0, {
        marker: 1,
        title: `${targetItem.title}`,
        drag: false,
        lat: parseFloat(targetItem.lat),
        lng: parseFloat(targetItem.lng),
        stop: true,
      });
      setMarkers(_markers);
    }
    // 경로에서 삭제
    else if (destinationKey === "정류장" && destinationKey != scourceKey) {
      const _hintPath = [...hintPath];
      _hintPath.splice(source.index, 1);
      setHintPath(_hintPath);
      const _markers = [...markers];
      _markers.splice(source.index, 1);
      setMarkers(_markers);
    } else if (destinationKey === "노선" && destinationKey === scourceKey) {
      const _hintPath = [...hintPath];
      const targetPath = _hintPath.splice(source.index, 1);
      _hintPath.splice(destination.index, 0, ...targetPath);
      setHintPath(_hintPath);
      const _markers = [...markers];
      const targetMarker = _markers.splice(source.index, 1);
      _markers.splice(destination.index, 0, ...targetMarker);
      setMarkers(_markers);
    }
    // setHintPath()
  };

  // --- requestAnimationFrame 초기화
  const [enabled, setEnabled] = useState(false);

  useEffect(() => {
    const animation = requestAnimationFrame(() => setEnabled(true));

    return () => {
      cancelAnimationFrame(animation);
      setEnabled(false);
    };
  }, []);

  if (!enabled) {
    return null;
  }
  // --- requestAnimationFrame 초기화 END

  return (
    <div className="p-4">
      <div className="mt-4 flex">
        <DragDropContext onDragEnd={onDragEnd}>
          <div className="grid flex-1 select-none grid-cols-2 gap-4 rounded-lg">
            {Object.keys(items).map((key) => (
              <Droppable key={key} droppableId={key}>
                {(provided, snapshot) => (
                  <div
                    ref={provided.innerRef}
                    {...provided.droppableProps}
                    className={
                      "overflow-scroll h-96 flex flex-col gap-3 rounded-xl bg-gray-200 p-4 ring-1 ring-gray-300 transition-shadow dark:bg-[#000000]" +
                      (snapshot.isDraggingOver ? " shadow-lg" : " shadow")
                    }
                  >
                    <span className="text-xs font-semibold">
                      {key.toLocaleUpperCase()}
                    </span>
                    {items[key].map((item, index) => (
                      <Draggable
                        key={item.id}
                        draggableId={item.id}
                        index={index}
                      >
                        {(provided, snapshot) => (
                          <div
                            ref={provided.innerRef}
                            {...provided.draggableProps}
                            {...provided.dragHandleProps}
                            className={
                              "rounded-lg bg-white p-4 transition-shadow dark:bg-[#121212]" +
                              (snapshot.isDragging
                                ? " bg-opacity-90 shadow-2xl shadow-gray-400"
                                : " shadow")
                            }
                          >
                            <h5 className="font-semibold">
                              {item.title}
                              {key === "노선" ? (
                                <>
                                  ({index + 1}) &nbsp;&nbsp;&nbsp;
                                  <Button className=""
                                    onClick={() =>
                                      deleteMarker(index, item.title)
                                    }
                                  >
                                    x
                                  </Button>
                                </>
                              ) : null}
                            </h5>
                          </div>
                        )}
                      </Draggable>
                    ))}
                    {provided.placeholder}
                  </div>
                )}
              </Droppable>
            ))}
          </div>
        </DragDropContext>
      </div>
    </div>
  );
}
