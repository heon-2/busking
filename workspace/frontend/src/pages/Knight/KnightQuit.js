import { Button } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";

export function KnightQuit() {
  const navigate = useNavigate();
  return (
    <div>
      <h1>Knight Quit</h1>
      <p> 진짜 종료할거임 ? </p>
      <Button
        color="red"
        onClick={() => {
          navigate("/knightselect");
        }}
      >
        진짜 종료할 거
      </Button>
      <Button
        onClick={() => {
          navigate(-1);
        }}
      >
        뒤로가기
      </Button>
    </div>
  );
}
