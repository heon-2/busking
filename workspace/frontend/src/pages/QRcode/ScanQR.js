import { Button } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
export function ScanQR() {
  let navigate = useNavigate();
  return (
    <div>
      <div className="header mb-40 text-lg">
        <h1>해당 QR 코드를 버스에 찍어주세요.</h1>
      </div>
      <div className="qrcode flex justify-center">
        <img
          className=""
          src="https://www.qrcode.com/en/img/model12/model1Image.png"
          alt=""
        />
      </div>

      <div>
        <p className="">[탑승하고 있는 버스호차 정보]</p>
        <h2>[버스 하차지 정보]</h2>
      </div>

      <div className="bottom">
        <Button
          onClick={() => {
            navigate("/setQR");
          }}
        >
          QR 코드 재발급
        </Button>
        <Button
          onClick={() => {
            navigate("/map");
          }}
        >
          홈 화면 가기
        </Button>
      </div>
    </div>
  );
}
