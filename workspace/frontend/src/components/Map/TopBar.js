import React from "react";
import { Navbar, Typography, Button, Avatar } from "@material-tailwind/react";

import { useNavigate } from "react-router-dom";

// profile menu component

export function TopBar() {
  const navigate = useNavigate();
  return (
    <Navbar className="w-screen h-14">
      <div className="gap-4 flex justify-between">
        <div className="flex items-center gap-4">
          <div>
            <Avatar
              src="/img/face-2.jpg"
              alt="avatar"
              withBorder={true}
              color="blue-50"
            />
          </div>
          <div>
            <Typography variant="h6" color="gray">
              [사람이름]
            </Typography>
            <Typography variant="small" color="gray" className="font-normal">
              SSAFY Student
            </Typography>
          </div>
        </div>

        <div className="flex items-center">
          <div>
            <img
              onClick={() => navigate("/report")}
              src="../../../public/alarm.png"
              alt="avatar"
              withBorder={true}
              color="blue-50"
            />
          </div>
        </div>
      </div>
    </Navbar>
  );
}
