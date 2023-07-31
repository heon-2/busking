/* eslint-disable */
import React from 'react';
import './App.css';
import { Button } from "@material-tailwind/react";
import { Routes, Route, Link, useNavigate } from "react-router-dom";
import { Login } from "./pages/Auth/Login";


function App() {
  const navigate = useNavigate();


  return (
    <div className="App">
    <Routes>
      <Route path="/" element={<Login />} />
    </Routes>
    </div>
  );
}

export default App;
