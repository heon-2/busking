import React, { useState, useEffect, useRef } from "react";
import { useMap } from "react-leaflet";
import { BiCurrentLocation } from "react-icons/bi";
import { IconButton } from "@material-tailwind/react";
import { useMapStore } from "../store.js";

export function FindMe() {
  const map = useMap();
  const { location } = useMapStore();

  return location ? (
<div className="fixed top-20 left-4 " style={{ zIndex: 1000}}>
    <IconButton
      size="lg"
      className="rounded-full ml-3"
      onClick={() => {
        map.flyTo(location, 17, {
          duration: 2,
        });
      }}
      style={{ zIndex: 2000 }}
    >
      <BiCurrentLocation className="h-5 w-5 transition-transform group-hover:rotate-45" />
    </IconButton>
    </div>
  ) : null;
}
