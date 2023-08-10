import React, { useState, useEffect, useRef, useMemo } from "react";
import { useAdminStore } from "../../store.js";

import L from "leaflet";
import polyline from "@mapbox/polyline";

import RBush from "rbush";
import {
  MapContainer,
  useMap,
  TileLayer,
  Marker,
  useMapEvents,
  Popup,
  Polyline,
} from "react-leaflet";

class Vec2 {
  constructor(x, y) {
    this.x = x;
    this.y = y;
  }

  dot(other) {
    return this.x * other.x + this.y * other.y;
  }

  add(other) {
    return new Vec2(this.x + other.x, this.y + other.y);
  }

  sub(other) {
    return new Vec2(this.x - other.x, this.y - other.y);
  }

  scale(scalar) {
    return new Vec2(this.x * scalar, this.y * scalar);
  }

  norm() {
    return Math.sqrt(this.dot(this));
  }

  static ZERO = new Vec2(0, 0);
}

function bounds(y1, x1, y2, x2) {
  return {
    minX: Math.min(x1, x2),
    minY: Math.min(y1, y2),
    maxX: Math.max(x1, x2),
    maxY: Math.max(y1, y2),
  };
}

// https://stackoverflow.com/questions/639695/how-to-convert-latitude-or-longitude-to-meters/11172685#11172685
function distOf(lat1, lng1, lat2, lng2) {
  var R = 6378.137; // Radius of earth in KM
  var dLat = (lat2 * Math.PI) / 180 - (lat1 * Math.PI) / 180;
  var dLon = (lng2 * Math.PI) / 180 - (lng1 * Math.PI) / 180;
  var a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos((lat1 * Math.PI) / 180) *
      Math.cos((lat2 * Math.PI) / 180) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2);
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  var d = R * c;
  return d * 1000; // meters
}

// v 벡터를 u에 투영한다.
function projectTo(v, u, clip) {
  const l_u = u.norm();
  let t = u.scale(1 / l_u).dot(v);
  t = clip ? Math.max(0, Math.min(l_u, t)) : t;
  return u.scale(t / l_u);
}

function pointMatch(lines, pt) {
  let optimalPoint = null;
  let optimalDist = Number.MAX_VALUE;

  for (let i = 0; i < lines.length; ++i) {
    const [a, b] = lines[i];
    const u = b.sub(a);
    const v = pt.sub(a);
    const p = projectTo(v, u, true).add(a);

    if (pt.sub(p).norm() < optimalDist) {
      optimalPoint = p;
      optimalDist = pt.sub(p).norm();
    }
  }

  return [optimalPoint.y, optimalPoint.x];
}

export function CreateStop() {
  const { newPath, mouseLocation } = useAdminStore();
  const [closestPoint, setClosestPoint] = useState(null);
  const [rtree] = useState(new RBush());

  useEffect(() => {
    rtree.clear();
    for (let i = 0; i < newPath.length - 1; ++i) {
      const boundingBox = bounds(...newPath[i], ...newPath[i + 1]);
      rtree.insert({ ...boundingBox, data: i });
    }
  }, [newPath]);

  useEffect(() => {
    const lat = mouseLocation[0]; // y
    const lng = mouseLocation[1]; // x

    let candidates = rtree.search({
      minX: lng - 0.003, // Adjust the search range as needed
      maxX: lng + 0.003,
      minY: lat - 0.003,
      maxY: lat + 0.003,
    });

    if (candidates && 0 < candidates.length) {
      candidates = candidates.map(({ data: i }) => {
        const [y1, x1] = newPath[i];
        const [y2, x2] = newPath[i + 1];
        return [new Vec2(x1, y1), new Vec2(x2, y2)];
      });

      const projectedPoint = pointMatch(candidates, new Vec2(lng, lat));
      setClosestPoint(projectedPoint);
    } else {
      setClosestPoint(null);
    }
  }, [mouseLocation]);

  return (
    <>
      {closestPoint !== null ? <Marker position={closestPoint}></Marker> : null}
    </>
  );
}
