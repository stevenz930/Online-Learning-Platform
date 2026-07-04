import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
    state: () => ({
        isLoggedIn: false,
        //username: null,
        id: null,
        token: null,
        role: null,
    }),
    actions: {
        login(id, token, role) {
            this.isLoggedIn = true;
            //this.username = username;
            this.id = id;
            this.token = token;
            this.role = role;
            sessionStorage.setItem('auth', JSON.stringify(this.$state));
        },
        logout() {
            this.isLoggedIn = false;
            //this.username = null;
            this.id = null;
            this.token = null;
            this.role = null;

            sessionStorage.removeItem('auth');
        },
    },
});
