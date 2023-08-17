import React from "react";
import {
  Navbar,
  Typography,
  Button,
  Avatar,
  IconButton,
} from "@material-tailwind/react";
import { BellIcon, Bars3Icon } from "@heroicons/react/24/solid";
import { useNavigate } from "react-router-dom";
// profile menu component

export function TopBar(props) {
  const navigate = useNavigate();
  return (
    <div className="flex justify-center items-center">
      <Navbar className="w-screen h-12 pl-1 pr-2 py-[0.7vh] rounded-lg bg-white !bg-opacity-100">

        <div className="flex justify-between">
          <div className="flex gap-2">
            <div className="flex items-center justify-center h-8 w-10">

              <IconButton className="bg-white rounded-none shadow-none ">
                <Bars3Icon className="w-7 h-7 text-gray-600" />
              </IconButton>
            </div>
          </div>

          <div>
            <Typography className="items-center justify-center flex mt-1 text-gray-400 font-medium font-main text-lg whitespace-pre">
              {props.page === "usermap" && props.content}
              {props.page === "report" && props.content}
              {/* {props.page === "setqr" && props.setqr} */}
            </Typography>
          </div>
          <div className="flex items-center justify-center h-8 w-8">
            <div>
              <IconButton color="red" onClick={() => navigate("/report")}>
                <BellIcon className="w-6 h-6 text-white" />
              </IconButton>
            </div>
          </div>
        </div>
      </Navbar>
    </div>
  );
}
