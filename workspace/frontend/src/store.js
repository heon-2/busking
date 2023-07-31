import { create } from 'zustand';
import { devtools } from 'zustand/middleware';
import { persist } from 'zustand/middleware';



// export const userStore = create((set) => ({
//     user: {
//         name: null,
//         role: null,
//     },
//     accessToken: null,
//     refreshToken: null,
//     setUser: (payload) => set({ user: payload }),
//     logout: () => set({ user: null }),
// }));

// User 상태
// =======================================================

let userStore = (set) => ({
    user: {
        name: null,
        role: null,
    },
    accessToken: null,
    refreshToken: null,
    setUser: (payload) => set({ user: payload }),
    logout: () => set({ user: null }),
});

userStore = devtools(userStore);
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
    setCenter: (payload) => set({ center: payload }),
    setBusPath: (payload) => set({ busPath: payload }),
    setBusInfo: (payload) => set({ busInfo: payload }),
})

// mapStore = devtools(mapStore);
// mapStore = persist(mapStore, { name: 'map_settings' });

export const useMapStore = create(mapStore)