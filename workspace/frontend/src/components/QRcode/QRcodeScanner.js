import Html5QrcodePlugin from "../../Html5QrcodePlugin";
import axios from "axios";
import { useUserStore } from "../../store.js";
import { useEffect } from "react";

export const QRcodeScanner = (props) => {
  let isThrottled = false;


  const onNewScanResult = (decodedText, decodedResult) => {
    if (!isThrottled) {
      isThrottled = true;
      
    console.log(`Scan result = ${decodedText}`, decodedResult);

    try {
      const decodedData = JSON.parse(decodedText);
      const accessToken = localStorage.getItem("accessToken");

      console.log("decodedData:", decodedData);
      console.log(decodedData.bus, decodedData.destination);

      axios.post("/api/realtime/driving/join", {
        bus: 
        {companyId: decodedData.bus.companyId, no: decodedData.bus.no},
        destination: decodedData.destination,
      });
      console.log(decodedData.bus);
      console.log(decodedData.destination);
      console.log("QR코드 스캔 완료");
      
    } catch (error) {
      console.error(error);
    }

    setTimeout(() => {
      isThrottled = false;
    }, 2000);
  }
  };


  return (
    <div className="w-full h-full">
      <Html5QrcodePlugin
        fps={10}
        qrbox={400}
        disableFlip={false}
        qrCodeSuccessCallback={onNewScanResult}
        />
    </div>
  )
}