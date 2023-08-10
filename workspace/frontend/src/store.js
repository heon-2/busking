import { create } from 'zustand';
import { devtools } from 'zustand/middleware';
import { persist } from 'zustand/middleware';

// User 상태
// =======================================================

let userStore = (set) => ({
    user: {
        email: '',
        phoneNumber: '',
        realName: '',
        role: '',
        username: '',
    },// JIRA..... 
    accessToken: '',
    refreshToken: '',
    fcmToken : '',

    setUser: (payload) => set({ user: payload }),
    setAccessToken: (payload) => set({accessToken: payload}),
    setRefreshToken: (payload) => set({refreshToken: payload}),
    setFcmToken: (payload) => set({fcmToken: payload}),
    logout: () => set({ user: '' }),
});

// userStore = devtools(userStore);
userStore = persist(userStore, { name: 'user_settings' });

export const useUserStore = create(userStore)

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
})

// mapStore = devtools(mapStore);
mapStore = persist(mapStore, { name: 'map_settings' });

export const useMapStore = create(mapStore)

let adminStore = (set) => ({
    mouseLocation: [],
    stationMarkers: [],
    hintPath: [],
    markers: [],
    newPath: [],
    newStop: [],
    stopCreate: false,
    newStop: [],
    setHintPath: (payload) => set({ hintPath: payload }),
    setMarkers: (payload) => set({ markers: payload }),
    setNewPath: (payload) => set({ newPath: payload }),
    setNewStop: (payload) => set({ newStop: payload }),
    setStopCreate: () => set((state) => ({ stopCreate: !state.stopCreate})),
    setMouseLocation: (payload) => set({ mouseLocation: payload }),
    setNewStop: (payload) => set({ newStop: payload }),
    setStationMarkers: (payload) => set({ stationMarkers: payload }),
})

adminStore = persist(adminStore, { name: 'admin_settings' });

export const useAdminStore = create(adminStore)


