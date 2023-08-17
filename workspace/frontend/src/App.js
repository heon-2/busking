/* eslint-disable */
import React from "react";
import "./App.css";
import { Routes, Route, Link, useNavigate } from "react-router-dom";
import { Login } from "./pages/Auth/Login";
import { Map } from "./pages/Map/Map";
import { ScanQR } from "./pages/QRcode/ScanQR";
import { SetQR } from "./pages/QRcode/SetQR";
import { Report } from "./pages/Report/Report";
import { Page404 } from "./pages/Page404";
import { Admin } from "./pages/Admin/Admin";
import { UserMap } from "./pages/Map/UserMap";
import { QRreader } from "./pages/QRcode/QRreader";
import { Emergency } from "./pages/Admin/Emergency";
// import "./firebase-messaging-sw.js";

import { KnightMap } from "./pages/Knight/KnightMap";
import { KnightQuit, knightQuit } from "./pages/Knight/KnightQuit";
import { KnightSelect } from "./pages/Knight/KnightSelect";
import { useUserStore } from "./store.js";

function App() {
  const navigate = useNavigate();
  const { user } = useUserStore();

  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/map" element={<Map />} />
        <Route path="/scanQR" element={<ScanQR />} />
        <Route path="/setQR" element={<SetQR />} />
        <Route path="/QRreader" element={<QRreader />} />
        <Route path="/report" element={<Report />} />
        <Route path="/knightselect" element={<KnightSelect />} />
        <Route path="/knightmap" element={<KnightMap />} />
        <Route path="/knightquit" element={<KnightQuit />} />
        <Route path="/admin" element={<Admin />} />
        <Route path="*" element={<Page404 />} />
        <Route path="/emergency" element={<Emergency />} />
        <Route path="/usermap" element={<UserMap />} />
      </Routes>

    </div>
  );
}

export default App;
