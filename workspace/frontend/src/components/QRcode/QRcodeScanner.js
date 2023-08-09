import Html5QrcodePlugin from "../../Html5QrcodePlugin";

export const QRcodeScanner = (props) => {
  const onNewScanResult = (decodedText, decodedResult) => {
    console.log(`Scan result = ${decodedText}`, decodedResult);
    alert(`Scan result = ${decodedText}`);
  };

  return (
    <div>
      <Html5QrcodePlugin
        fps={10}
        qrbox={250}
        disableFlip={false}
        qrCodeSuccessCallback={onNewScanResult}
        />
    </div>
  )
}