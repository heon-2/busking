import { useEffect } from 'react';
import { useGeolocated } from 'react-geolocated';
import { useMapStore } from '../store.js';

function useLocation() {
  const { setLocation } = useMapStore();
  const { coords, isGeolocationAvailable, isGeolocationEnabled } = useGeolocated({
    positionOptions: {
      enableHighAccuracy: true,
    },
    userDecisionTimeout: 5000,
  });
  console.log('뭐지')
  console.log(coords)
  useEffect(() => {
    if (coords && coords.latitude && coords.longitude) {
      setLocation([coords.latitude, coords.longitude]);
    }
  }, [coords]);

  // 외부로 위치 정보를 반환하는 함수를 추가
  const getLocation = () => {
    return coords;
  };

  return { getLocation };
}

export default useLocation;