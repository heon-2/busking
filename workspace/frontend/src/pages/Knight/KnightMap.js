import { MapLayer } from "./../../components/Map/MapLayer";
import { Button } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";

import RTC from "../../components/RTC/RTC";
export function KnightMap() {
  const navigate = useNavigate();
  return (
    <div>
      <div className="z-0">
        <MapLayer></MapLayer>
      </div>
      {/* 나중에 zindex 조정하기 */}
      <div className="flex fixed bottom-10 left-10" style={{ zIndex: 1000 }}>
        <RTC></RTC>
      </div>
      <div className="flex fixed bottom-10 right-10" style={{ zIndex: 1000 }}>
        <Button
          className="w-96 h-28 text-4xl"
          onClick={() => {
            navigate("/knightquit");
          }}
          color="red"
        >
          운행 종료
        </Button>
      </div>
    </div>
  );
}
