import { Button } from "@material-tailwind/react";
import { Routes, Route, Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { useBusStore } from "../../store";
import axios from "axios";
import Swal from "sweetalert2";

export function KnightSelect() {
  const navigate = useNavigate();
  let [choiceBus, setChoiceBus] = useState([false, false, false, false]);
  const { busNumber, setBusNumber } = useBusStore();

  const choiceButtonClick = (idx) => {
    const copy = [...choiceBus];
    copy[idx] = !copy[idx];
    setChoiceBus(copy);
  };

  // 버스 기사님 드라이빙 시작
async function startDrive(num) {
    console.log(num);
    const response = await axios.get('/api/companies/1/buses')
    console.log(response.data.length)
    if (response.data.length < num) {
      Swal.fire("배차되지 않은 차량입니다.", "다시 시도해 주세요.", "error");
      return;
    }
    else {
      const rId = Number(response.data[num-1].routes[0].id)
      console.log(rId)
      axios
        .post(
          "/api/realtime/driving/begin",
          {
            bus: {
              companyId: 1,
              no: num,
            },
            route: {
              id: rId,
            },
          },
          {
            headers: {
              Accept: "application/json",
            },
          }
        )
        .then((response) => {
          console.log(response.data);
        })
        .then(() => {
          navigate("/knightmap/");
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }

  return (
    <div className="grid grid-cols-2 grid-rows-5 h-screen bg-concept1">
      <div className="col-span-2 text-8xl flex items-center justify-center text-concept3 font-bold">
        호차를 선택해주세요
      </div>
      {/* onClick = { ()=> { navigate("이동할주소");}} */}
      {choiceBus.map((bus, idx) => {
        return (
          <div className="row-span-2 flex items-center justify-center">
            <Button
              className="w-full h-[35vh] m-10 text-9xl border rounded-2xl "
              // className="gap-8"
              onClick={() => {
                choiceButtonClick(idx);
                setBusNumber(idx + 1);
                // navigate("/knightmap/"+(idx+1));
                startDrive(idx + 1);
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
}
