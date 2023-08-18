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
import { useQrStore } from '../../store.js';
import { useUserStore } from '../../store.js';
import { useEffect } from "react";

export function ScanQR() {
  let navigate = useNavigate();

  const { selectedBus, isInBound , inBoundDeparture, inBoundDestination, outBoundDeparture, outBoundDestination } = useQrStore();
  const { user } = useUserStore();
  useEffect(() => { 
    if (user == null){
      navigate('/')
    }
    else if (user.role == 'DRIVER'){
        navigate('/knightselect');
    }
  }, [])

  return (
    <div className="flex flex-col items-center justify-center h-screen overflow-y-auto bg-blue-50">
  <div className="flex items-center justify-center h-16 text-xl font-bold header md:h-20">
    <p>해당 QR 코드를 버스에 찍어주세요</p>
  </div>
  <Card className="w-full md:w-96">
    <CardHeader floated={false} className="h-80 md:h-96">
      <QR />
    </CardHeader>
    {isInBound ? (

    <CardBody className="text-center">
      <Typography variant="h4" color="blue-gray" className="mb-2">
        {selectedBus}호차 {inBoundDeparture} 탑승
      </Typography>
      <Typography color="blue" className="font-medium" textGradient>
        {inBoundDestination} 하차
      </Typography>
    </CardBody>

    ) : (
      
    <CardBody className="text-center">
      <Typography variant="h4" color="blue-gray" className="mb-2">
        {selectedBus}호차 {outBoundDeparture} 탑승
      </Typography>
      <Typography color="blue" className="font-medium" textGradient>
        {outBoundDestination} 하차
      </Typography>
    </CardBody>
    )}
    <CardFooter className="flex flex-col gap-4 pt-2">
      <Button onClick={() => navigate("/setQR")}>QR 코드 재발급</Button>
      <Button onClick={() => navigate("/usermap")}>홈 화면 가기</Button>
    </CardFooter>
  </Card>
</div>
  );
}
