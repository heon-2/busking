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
  };
  return (
    <div className="grid grid-cols-2 grid-rows-5 h-screen bg-blue-50">
      <div className="col-span-2 text-8xl flex items-center justify-center text-[#258fff] font-bold">
        호차를 선택해주세요
      </div>
      {/* onClick = { ()=> { navigate("이동할주소");}} */}
      {choiceBus.map((bus, idx) => {
        return (
          <div className="row-span-2 flex items-center justify-center">
            <Button
              className="w-full h-80 m-10 text-9xl border rounded-2xl"
              // className="gap-8"
              onClick={() => {
                choiceButtonClick(idx);
                // navigate("/knightmap/"+(idx+1));
                navigate("/knightmap/");
                // useEffect 사용해서 바꿔야할듯. state값 변경하고, 그거에 맞는 경로 렌더링 하도록하기
                // 이후에 뒤로가기를 누르거나, 운행종료 버튼을 누르면 state값 모두 false로 초기화 시켜줘야할듯?
                // let copy = choiceBus;
                // copy[idx] = !copy[idx];
                // setChoiceBus(copy);
                // console.log(choiceBus);
              }}
            >
              {idx + 1}
            </Button>
          </div>
        );
      })}
    </div>
  );