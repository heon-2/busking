import { 
  Button,
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Typography,
  } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
import { QR }  from "../../components/QRcode/QR";

export function ScanQR() {
  let navigate = useNavigate();
  return (
    <div className="h-screen overflow-y-auto bg-blue-50 flex flex-col items-center justify-center">
  <div className="flex header text-xl font-bold h-16 md:h-20 items-center justify-center">
    <p>해당 QR 코드를 버스에 찍어주세요</p>
  </div>
  <Card className="w-full md:w-96">
    <CardHeader floated={false} className="h-80 md:h-96">
      <QR />
    </CardHeader>
    <CardBody className="text-center">
      <Typography variant="h4" color="blue-gray" className="mb-2">
        4-3 흑석사거리 (탑승 정류장)
      </Typography>
      <Typography color="blue" className="font-medium" textGradient>
        SSAFY 하차 (하차 정류장)
      </Typography>
    </CardBody>
    <CardFooter className="flex flex-col gap-4 pt-2">
      <Button onClick={() => navigate("/setQR")}>QR 코드 재발급</Button>
      <Button onClick={() => navigate("/map")}>홈 화면 가기</Button>
    </CardFooter>
  </Card>
</div>
  );
}
