import { create } from "zustand";
import { devtools } from "zustand/middleware";
import { persist } from "zustand/middleware";

// User 상태
// =======================================================

let userStore = (set) => ({
  user: {
    email: "",
    phoneNumber: "",
    realName: "",
    role: "",
    username: "",
  }, // JIRA.....
  accessToken: "",
  refreshToken: "",
  fcmToken: "",
  useBus: null,
  setUseBus: (payload) => set({ useBus: payload }),
  setUser: (payload) => set({ user: payload }),
  setAccessToken: (payload) => set({ accessToken: payload }),
  setRefreshToken: (payload) => set({ refreshToken: payload }),
  setFcmToken: (payload) => set({ fcmToken: payload }),
  logout: () => set({ user: "" }),
});

// userStore = devtools(userStore);
userStore = persist(userStore, { name: "user_settings" });

export const useUserStore = create(userStore);

// userStore = devtools(userStore);
// userStore = persist(userStore, { name: 'user_settings' });

// Map 상태
// =======================================================

let mapStore = (set) => ({
  center: [26, 25],
  busPath: [1],
  busInfo: [1],
  mapType: false,
  location: [20, 20],
  setCenter: (payload) => set({ center: payload }),
  setBusPath: (payload) => set({ busPath: payload }),
  setBusInfo: (payload) => set({ busInfo: payload }),
  toggleMapType: () => set((state) => ({ mapType: !state.mapType })),
  setLocation: (payload) => set({ location: payload }),
});

// mapStore = devtools(mapStore);
mapStore = persist(mapStore, { name: "map_settings" });

export const useMapStore = create(mapStore);

let adminStore = (set) => ({
  busNo: "",
  direction: false,
  stationPath: [],
  geometry: "",
  route: [],
  mouseLocation: [],
  stationMarkers: [],
  hintPath: [],
  markers: [],
  newPath: [],
  newStation: [],
  items: {},
  stopCreate: false,

  busInformation: [],

  setBusInformation: (payload) => set({ busInformation: payload }),

  setBusNo: (payload) => set({ busNo: payload }),
  setDirection: () => set((state) => ({ direction: !state.direction })),
  setStationPath: (payload) => set({ stationPath: payload }),
  setGeometry: (payload) => set({ geometry: payload }),
  setItems: (payload) => set({ items: payload }),
  setRoute: (payload) => set({ route: payload }),
  setHintPath: (payload) => set({ hintPath: payload }),
  setMarkers: (payload) => set({ markers: payload }),
  setNewPath: (payload) => set({ newPath: payload }),
  setNewStation: (payload) => set({ newStop: payload }),
  setStopCreate: () => set((state) => ({ stopCreate: !state.stopCreate })),
  setMouseLocation: (payload) => set({ mouseLocation: payload }),
  setStationMarkers: (payload) => set({ stationMarkers: payload }),
});

// adminStore = persist(adminStore, { name: 'admin_settings' });

export const useAdminStore = create(adminStore);

// ====================================================

let busStore = (set) => ({
  busNumber: [],
  stations: [],
  busPath: [],
  passengers: [],
  setBusNumber: (payload) => set({ busNumber: payload }),
  setStations: (payload) => set({ stations: payload }),
  setBusPath: (payload) => set({ busPath: payload }),
  setPassengers: (payload) => set({ passengers: payload }),
});

busStore = persist(busStore, { name: "bus_settings" });

export const useBusStore = create(busStore);

// ======================================================

function isTimeInBound() {
  const now = new Date();
  const hours = now.getHours();
  return hours >= 19 || hours < 10;
}

let qrStore = (set) => ({
  // busNumList:[],
  selectedBus: [],
  isInBound: true,
  inBoundDeparture: [],
  inBoundDestination: "SSAFY",
  outBoundDeparture: "SSAFY",
  outBoundDestination: [],
  // setBusNumList: (payload) => set({ busNumList: payload }),
  setSelectedBus: (payload) => set({ selectedBus: payload }),
  setIsInBound: () =>
    set((state) => ({
      isInBound: isTimeInBound(),
    })),
  setInBoundDeparture: (payload) => set({ inBoundDeparture: payload }),
  setInBoundDestination: (payload) => set({ inBoundDestination: payload }),
  setOutBoundDeparture: (payload) => set({ outBoundDeparture: payload }),
  setOutBoundDestination: (payload) => set({ outBoundDestination: payload }),
});

qrStore = persist(qrStore, { name: "qr_settings" });

export const useQrStore = create(qrStore);
