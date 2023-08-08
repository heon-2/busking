import React from "react";
import {
  Navbar,
  MobileNav,
  Typography,
  Button,
  Menu,
  MenuHandler,
  MenuList,
  MenuItem,
  Avatar,
  Card,
  IconButton,
} from "@material-tailwind/react";
import {
  CubeTransparentIcon,
  UserCircleIcon,
  CodeBracketSquareIcon,
  Square3Stack3DIcon,
  ChevronDownIcon,
  Cog6ToothIcon,
  InboxArrowDownIcon,
  LifebuoyIcon,
  PowerIcon,
  RocketLaunchIcon,
  Bars2Icon,
} from "@heroicons/react/24/outline";
 
// profile menu component
 
export function TopBar() {
  return(
    <div>
      <Navbar>
      <div className="flex items-center gap-4">
        <Avatar src="/img/face-2.jpg" alt="avatar" withBorder={true} color="blue-50" />
        <div>
          <Typography variant="h6" color="gray">[사람이름]</Typography>
          <Typography variant="small" color="gray" className="font-normal">
            SSAFY Student
          </Typography>
        </div>
        <img src="../../../public/alarm.png" alt="avatar" withBorder={true} color="blue-50" />
      </div>
      </Navbar>
    </div>
  )}