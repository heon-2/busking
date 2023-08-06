import { Button } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";

export function KnightQuit() {
  const navigate = useNavigate();
  return (
    <div className="grid grid-rows-2 h-screen p-20 gap-14 bg-[#F0F4F9]">
      <Button
        color="red"
        onClick={() => {
          navigate("/knightselect");
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
