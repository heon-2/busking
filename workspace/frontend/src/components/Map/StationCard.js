import { useEffect, useState } from 'react';
import { DragDropContext, Draggable, Droppable } from 'react-beautiful-dnd';
import { useAdminStore, useBusStore } from '../../store';

export default function StationCard({ items, setItems }) {
  const { hintPath, markers, route, stationMarkers, newStation, setHintPath, setMarkers, setRoute, setStationMarkers, setNewStation } = useAdminStore();
  const { stations, setStations } = useBusStore();

  const onDragEnd = ({ source, destination }) => {
    if (!destination) return;

    const scourceKey = source.droppableId;
    const destinationKey = destination.droppableId;

    const _items = JSON.parse(JSON.stringify(items));
    const [targetItem] = _items[scourceKey].splice(source.index, 1);
    _items[destinationKey].splice(destination.index, 0, targetItem);
    console.log(targetItem)
    // 경로에 추가
    if (destinationKey === 'routes' && destinationKey != scourceKey) {
      console.log('맞아?')
      const _hintPath = [...hintPath]
      _hintPath.splice(destination.index, 0, [parseFloat(targetItem.lat), parseFloat(targetItem.lng)])
      setHintPath(_hintPath)
      const _markers = [...markers]
      console.log(targetItem.lat)
      _markers.splice(destination.index, 0, {marker: 1, name: `${targetItem.lat}${targetItem.lng}`, drag: false})
      setMarkers(_markers)
    }
    // 경로에서 삭제
    else if (destinationKey === 'stations' && destinationKey != scourceKey) {
      const _hintPath = [...hintPath]
      _hintPath.splice(source.index, 1)
      setHintPath(_hintPath)
      const _markers = [...markers]
      _markers.splice(source.index, 1)
      setMarkers(_markers)
      console.log(source.index)
    }
    else if (destinationKey === 'routes' && destinationKey === scourceKey) {
      const _hintPath = [...hintPath]
      const targetPath = _hintPath.splice(source.index, 1)
      console.log(...targetPath)
      _hintPath.splice(destination.index, 0, ...targetPath)
      console.log(_hintPath)
      setHintPath(_hintPath)
      const _markers = [...markers]
      const targetMarker = _markers.splice(source.index, 1)
      _markers.splice(destination.index, 0, targetMarker)
      setMarkers(_markers)
    }
    setItems(_items);
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
                      'flex flex-col gap-3 rounded-xl bg-gray-200 p-4 ring-1 ring-gray-300 transition-shadow dark:bg-[#000000]' +
                      (snapshot.isDraggingOver ? ' shadow-lg' : ' shadow')
                    }
                  >
                    <span className="text-xs font-semibold">{key.toLocaleUpperCase()}</span>
                    {items[key].map((item, index) => (
                      <Draggable key={item.id} draggableId={item.id} index={index}>
                        {(provided, snapshot) => (
                          <div
                            ref={provided.innerRef}
                            {...provided.draggableProps}
                            {...provided.dragHandleProps}
                            className={
                              'rounded-lg bg-white p-4 transition-shadow dark:bg-[#121212]' +
                              (snapshot.isDragging
                                ? ' bg-opacity-90 shadow-2xl shadow-gray-400'
                                : ' shadow')
                            }
                          >
                            <h5 className="font-semibold">{item.title}</h5>
                            <span className="text-sm text-gray-500">Make the world beautiful</span>
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
