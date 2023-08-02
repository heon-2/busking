import { useEffect } from 'react';
import { useGeolocated } from 'react-geolocated';
import { useMapStore } from '../store.js';

function useLocation() {
//   const { setLocation } = useMapStore();
//   const { coords, isGeolocationAvailable, isGeolocationEnabled } = useGeolocated({
//     positionOptions: {
//       enableHighAccuracy: false,
//     },
//     userDecisionTimeout: 5000,
//   });

//   useEffect(() => {
//     if (coords && coords.latitude && coords.longitude) {
//       setLocation([coords.latitude, coords.longitude]);
//     }
//   }, [coords]);
}

export default useLocation;
