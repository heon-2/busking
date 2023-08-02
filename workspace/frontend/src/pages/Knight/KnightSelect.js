import { Button } from "@material-tailwind/react";
import { Routes, Route, Link, useNavigate } from "react-router-dom";
import { useState } from "react";

export function KnightSelect() {
  const navigate = useNavigate();
  let [choiceBus, setChoiceBus] = useState([false, false, false, false]);

  const choiceButtonClick = (idx) => {
    const copy = [...choiceBus];
    copy[idx] = !copy[idx];
    setChoiceBus(copy);
  }
  return (
    <div>
      <h1>오늘 운행할 버스를 선택하세요.</h1>
      {/* onClick = { ()=> { navigate("이동할주소");}} */}
      {choiceBus.map((bus, idx) => {
        return (
          <Button
            onClick={() => {
              choiceButtonClick(idx);
              navigate("/knightmap/"+(idx+1));
              // useEffect 사용해서 바꿔야할듯. state값 변경하고, 그거에 맞는 경로 렌더링 하도록하기
              // 이후에 뒤로가기를 누르거나, 운행종료 버튼을 누르면 state값 모두 false로 초기화 시켜줘야할듯?
              // let copy = choiceBus;
              // copy[idx] = !copy[idx];
              // setChoiceBus(copy);
            }}
          >
            {idx + 1}호차
          </Button>

          
        );
      })}
    </div>
  );
}
