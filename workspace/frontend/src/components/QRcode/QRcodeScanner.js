import Html5QrcodePlugin from "../../Html5QrcodePlugin";
import axios from "axios";
import { useUserStore } from "../../store.js";

export const QRcodeScanner = (props) => {
  const onNewScanResult = (decodedText, decodedResult) => {
    console.log(`Scan result = ${decodedText}`, decodedResult);

    try {
      const decodedData = JSON.parse(decodedText);
      const accessToken = localStorage.getItem("accessToken");

      console.log("decodedData:", decodedData);
      alert(`Scan result = ${decodedText}`);


      // axios 로 qr코드 정보랑 헤더 담아서 accessToken 보내기
      // axios.post("/api/qr", {
      //   busId: decodedData.selectedBus,
      //   departure: decodedData.Departure,
      //   destination: decodedData.Destination,
      // },
      // {
      //   headers: {
      //     Accept: "application/json",
      //     Authorization: `Bearer ${accessToken}`,
      //     },
      // });
      // .then((res) => {
      //   console.log(res.data);
      // })
      // .catch((err) => {
      //   console.log(err);
      // });

    } catch (error) {
      console.error(error);
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