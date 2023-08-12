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
        {/* <Navbar className="w-[90vw] h-[8vh] mt-3"> */}
        <div className="flex justify-between">
          <div className="flex gap-2">
            <div className="flex items-center justify-center h-8 w-10">
              {/* <img
                className=" h-8 w-8 rounded-none flex items-center justify-center object-cover relative !rounded-full border-2 border-blue-500"
                src="https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1480&q=80"
                alt="avatar"
                // withBorder={true}
                // color="blue-50"
              /> */}

              {/* <img
                className=" h-8 w-8 rounded-none flex items-center justify-center object-cover relative !rounded-full border-2 border-blue-500"
                src="bus_512.png"
                alt="avatar"
                // withBorder={true}
                // color="blue-50"
              /> */}

              <IconButton className="bg-concept1">
                <Bars3Icon className="w-6 h-6 text-concept3" />
              </IconButton>
            </div>
            {/* <div className="flex flex-col items-start">
            <Typography variant="small" color="black">
              [사람이름]
            </Typography>
            <Typography variant="small" color="gray" className="font-normal">
              SSAFY Student
            </Typography>
          </div> */}
          </div>

          <div>
            <Typography className="items-center justify-center flex text-gray-300 font-medium font-main text-lg whitespace-pre">
              {/* Added this Typography */}

              {props.page === "usermap" && props.content}
              {props.page === "report" && props.content}
              {/* {props.page === "setqr" && props.setqr} */}
            </Typography>
          </div>
          <div className="flex items-center justify-center h-8 w-8">
            {/* <FontAwesomeIcon icon="fa-solid fa-bell" /> */}
            <div>
              {/* <img
                className="h-6 w-6 mx-2 rounded-none flex items-center justify-center relative"
                onClick={() => navigate("/report")}
                src="/reportButton.png"
                alt="avatar"
              /> */}
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
