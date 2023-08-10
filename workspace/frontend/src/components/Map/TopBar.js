import React from "react";
import { Navbar, Typography, Button, Avatar } from "@material-tailwind/react";

import { useNavigate } from "react-router-dom";
// profile menu component

export function TopBar(props) {
  const navigate = useNavigate();
  return (
    <div className="flex justify-center">
      <Navbar className="w-screen h-[7vh] px-3 py-[0.7vh]">
        {/* <Navbar className="w-[90vw] h-[8vh] mt-3"> */}
        <div className="flex justify-between">
          <div className="flex items-start gap-2">
            <div>
              <img
                className="h-10 w-10 rounded-none flex items-center justify-center object-cover relative !rounded-full border-2 border-blue-500"
                src="https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1480&q=80"
                alt="avatar"
                // withBorder={true}
                // color="blue-50"
              />
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

          <Typography className="items-center flex text-gray-600 font-medium font-main text-lg whitespace-pre">
            {/* Added this Typography */}

            {props.page === "usermap" && props.content}
            {props.page === "report" && props.content}
            {/* {props.page === "setqr" && props.setqr} */}
          </Typography>
          <div className="flex items-center">
            <div>
              <img
                className="h-8 w-8 rounded-none flex items-center justify-center relative"
                onClick={() => navigate("/report")}
                src="/reportButton.png"
                alt="avatar"
              />
            </div>
          </div>
        </div>
      </Navbar>
    </div>
  );
}
