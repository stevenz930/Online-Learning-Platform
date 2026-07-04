import { createApp } from 'vue'
import { createPinia } from 'pinia'

import './assets/styles.scss';

import 'bootstrap';
import '@popperjs/core';

import router from './router'
import App from './App.vue'
import { useAuthStore } from './stores/useAuthStore';

const app = createApp(App);

const pinia = createPinia();
app.use(pinia);

app.use(router);

app.mount('#app');

const authStore = useAuthStore();
const savedState = sessionStorage.getItem('auth');
if(savedState){
    authStore.$patch(JSON.parse(savedState));
}

