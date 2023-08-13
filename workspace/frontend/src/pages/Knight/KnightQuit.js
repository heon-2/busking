import { Button } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
export function KnightQuit() {
  const navigate = useNavigate();
  function endDrive() {
    axios
      .post(
        "/api/realtime/driving/end",
        {
          bus: {
            companyId: 1,
            no: 1,
          },
        },
        {
          headers: {
            Accept: "application/json",
          },
        }
      )
      .then((response) => {
        console.log("보내졌다");
        console.log(response);
      })
      .catch((error) => {
        console.error(error);
      });
  }
  return (
    <div className="grid grid-rows-2 h-screen p-20 gap-14 bg-[#F0F4F9]">
      <Button
        color="red"
        onClick={() => {
          navigate("/knightselect");
          endDrive();
        }}
        className="border rounded-3xl text-8xl"
      >
        운행 종료
      </Button>
      <Button
        onClick={() => {
          navigate(-1);
        }}
        className="border rounded-3xl text-8xl"
      >
        뒤로 가기
      </Button>
    </div>
  );
}
