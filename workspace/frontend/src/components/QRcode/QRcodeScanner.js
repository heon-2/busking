import Html5QrcodePlugin from "../../Html5QrcodePlugin";
import axios from "axios";

export const QRcodeScanner = (props) => {
  const onNewScanResult = (decodedText, decodedResult) => {
    console.log(`Scan result = ${decodedText}`, decodedResult);

    try {
      const decodedData = JSON.parse(decodedText);
      console.log("decodedData:", decodedData);
      alert(`Scan result = ${decodedText}`);


      // axios 로 qr 에 담긴 정보랑 cnt도 어떻게 보내야할지 ?
      // axios.post("/api/qr", {
      //   busId: decodedData.selectedBus,
      //   departure: decodedData.Departure,
      //   destination: decodedData.Destination,
      // })
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