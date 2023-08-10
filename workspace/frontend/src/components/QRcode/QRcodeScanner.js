import Html5QrcodePlugin from "../../Html5QrcodePlugin";

export const QRcodeScanner = (props) => {
  const onNewScanResult = (decodedText, decodedResult) => {
    console.log(`Scan result = ${decodedText}`, decodedResult);
    alert(`Scan result = ${decodedText}`);
  };

  return (
    <div className="h-full w-full">
      <Html5QrcodePlugin
        fps={10}
        qrbox={400}
        disableFlip={false}
        qrCodeSuccessCallback={onNewScanResult}
        />
    </div>
  )
}