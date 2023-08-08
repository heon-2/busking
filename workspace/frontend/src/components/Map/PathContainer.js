import update from 'immutability-helper'
import { useCallback, useState, useEffect } from 'react'
import { PathCard } from './PathCard.js'
import { useAdminStore } from '../../store.js'
import { IconButton, Button } from "@material-tailwind/react";

const style = {
  width: 400,
}
const deleteMarker = (markerId, hintPath, setMarkers, setHintPath, markers) => {
    let copy = [...markers]
    copy.splice(markerId, 1)
    setMarkers(copy);
    copy = [...hintPath]
    copy.splice(markerId, 1)
    setHintPath(copy)
  };
export const PathContainer = () => {
  const { hintPath, markers, setMarkers, setHintPath } = useAdminStore()
  const [cards, setCards] = useState([])

  useEffect(() => {
    setCards(markers.map((item, index) => ({
      id: index,
      text: `${index + 1}`
    })));
  }, [markers]);
  {
    const moveCard = useCallback((dragIndex, hoverIndex) => {
      setCards((prevCards) =>
        update(prevCards, {
          $splice: [
            [dragIndex, 1],
            [hoverIndex, 0, prevCards[dragIndex]],
          ],
        }),
      )
    }, [])
    const renderCard = (card, index) => {
      return (
        <PathCard
          key={card.id}
          index={index}
          id={card.id}
          text={<>{index+1}<Button onClick={(e) => {e.stopPropagation(); deleteMarker(index, hintPath, setMarkers, setHintPath, markers);}}>x</Button></>}
          moveCard={moveCard}
        />
      )
    }
    return (
      <>
        <div style={style}>{cards.map((card, i) => renderCard(card, i))}</div>
      </>
    )
  }
}
