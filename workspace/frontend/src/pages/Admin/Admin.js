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


export function Admin() {


  return (
    <div className="admin">
      <div className="flex">
    <div className="LeftsideBar">
    <Card className="h-[calc(100vh-2rem)] w-full max-w-[20rem] p-4 shadow-xl shadow-blue-gray-900/5">
      <div className="mb-2 p-4 bg-blue-100 rounded-xl">
        <Typography variant="h5" color="blue-gray">
          관리자 메뉴
        </Typography>
      </div>
      <List>
        <ListItem>
          <ListItemPrefix>
            <PresentationChartBarIcon className="h-5 w-5" />
          </ListItemPrefix>
          교육생 전체 조회
        </ListItem>
        <ListItem>
          <ListItemPrefix>
            <InboxIcon className="h-5 w-5" />
          </ListItemPrefix>
          버스 정보 조회 ???
        </ListItem>
        <ListItem>
          <ListItemPrefix>
            <UserCircleIcon className="h-5 w-5" />
          </ListItemPrefix>
          신고 내역 조회
          <ListItemSuffix>
            <Chip value="3" size="sm" variant="ghost" color="blue-gray" className="rounded-full" />
          </ListItemSuffix>
        </ListItem>
        <ListItem>
          <ListItemPrefix>
            <UserCircleIcon className="h-5 w-5" />
          </ListItemPrefix>
          화상통화 메뉴 ??
          <ListItemSuffix>
            <Chip value="3" size="sm" variant="ghost" color="blue-gray" className="rounded-full" />
          </ListItemSuffix>
        </ListItem>
      </List>
    </Card>
    </div>

    <div className="flex flex-col flex-grow pl-4 pr-8 mt-2 p-4">
    <UserList />

    </div>
    </div>
    </div>
  );
}


