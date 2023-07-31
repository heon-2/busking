/* eslint-disable */
import React from 'react';
import './App.css';
import { Button } from "@material-tailwind/react";
import { Routes, Route, Link, useNavigate } from "react-router-dom";
import { Login } from "./pages/Auth/Login";
import { Map } from "./pages/Map/Map";
import { ScanQR } from "./pages/QRcode/ScanQR";
import { SetQR } from "./pages/QRcode/SetQR";
import { Report } from "./pages/Report/Report";



function App() {
  const navigate = useNavigate();


  return (
    <div className="App">
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/map" element={<Map />} />
      <Route path="/scanQR" element={<ScanQR />} />
      <Route path="/setQR" element={<SetQR />} />
      <Route path="/report" element={<Report />} />
      
    </Routes>
    </div>
  );
}

export default App;
