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