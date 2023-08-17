import React from "react";
import OpenViduVideoComponent from "./OvVideo";
// import "./UserVideo.css";

export default function UserVideoComponent({ streamManager }) {
  const getNicknameTag = () => {
    return JSON.parse(streamManager.stream.connection.data).clientData;
  };

  return (
    <div>
      {streamManager !== undefined ? (
        <div className="streamcomponent">
          <OpenViduVideoComponent streamManager={streamManager} />
        </div>
      ) : null}
    </div>
  );
}
