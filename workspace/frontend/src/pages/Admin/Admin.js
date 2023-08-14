import React, { useState } from "react";
import {
  Card,
  Typography,
  List,
  ListItem,
  ListItemPrefix,
  ListItemSuffix,
  Chip,
} from "@material-tailwind/react";
import {
  PresentationChartBarIcon,
  UserCircleIcon,
  InboxIcon,
} from "@heroicons/react/24/solid";

import { UserList } from "../../components/Admin/UserList";
// import { UserCreate } from "../../components/Admin/UserCreate";
import RTC from "./../../components/RTC/RTC";
import { ReportList } from "./../../components/Admin/ReportList";
// import { BusInfo } from "./../../components/Map/BusInfo";
import { Map } from "./../../pages/Map/Map";
export function Admin() {
  const [display, setDisplay] = useState("UserList");

  return (
    <div className="admin">
      <div className="flex">
        <div className="LeftsideBar">
          <Card className="h-full min-h-[45rem] w-full max-w-[20rem] p-4 shadow-xl shadow-blue-gray-900/5">
            <div className="mb-2 p-4 bg-blue-400 rounded-lg">
              <Typography variant="h5" color="white">
                관리자 메뉴
              </Typography>
            </div>
            <List>
              <ListItem onClick={() => setDisplay("UserList")}>
                <ListItemPrefix>
                  <PresentationChartBarIcon className="h-5 w-5" />
                </ListItemPrefix>
                교육생 전체 조회
              </ListItem>
              <ListItem onClick={() => setDisplay("BusList")}>
                <ListItemPrefix>
                  <InboxIcon className="h-5 w-5" />
                </ListItemPrefix>
                버스 정보 조회
              </ListItem>
              <ListItem onClick={() => setDisplay("ReportList")}>
                <ListItemPrefix>
                  <UserCircleIcon className="h-5 w-5" />
                </ListItemPrefix>
                신고 내역 조회
                <ListItemSuffix>
                  <Chip
                    value="9"
                    size="sm"
                    variant="ghost"
                    color="blue-gray"
                    className="rounded-full"
                  />
                </ListItemSuffix>
              </ListItem>
              <ListItem onClick={() => setDisplay("RTC")}>
                <ListItemPrefix>
                  <UserCircleIcon className="h-5 w-5" />
                </ListItemPrefix>
                긴급 화상 통화
                <ListItemSuffix>
                  <Chip
                    value="3"
                    size="sm"
                    variant="ghost"
                    color="blue-gray"
                    className="rounded-full"
                  />
                </ListItemSuffix>
              </ListItem>
            </List>
          </Card>
        </div>

        <div className="flex flex-col flex-grow pl-4 mt-1 pr-8 p-4">
          {display === "UserList" && <UserList />}
          {/* {display === "BusList" && <BusInfo />} */}
          {display === "ReportList" && <ReportList />}
          <div className="flex items-center h-full justify-center">
            {display === "RTC" && <RTC />}
          </div>
        </div>
      </div>
    </div>
  );
}
