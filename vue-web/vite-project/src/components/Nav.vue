<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { useAuthStore } from "../stores/useAuthStore";
import { useCurrentPageInMain } from '../stores/currentPageinMain';

const router = useRouter();
const authStore = useAuthStore();
const currentPageInMain = useCurrentPageInMain();

axios.defaults.withCredentials = true;
const token = authStore.token;
const role = authStore.role;
const isInstructor = ref(false);
console.log("role", role);
if (role == 'instructor'){
  isInstructor.value = true;
}

const avatar = ref();
const username = ref();
axios.defaults.headers.common['Authorization'] = token;

try { 
  const response = await axios.post("http://localhost/api/get-login-user-detail", authStore.id);
  //console.log(response.data.avatar);
  avatar.value = response.data.avatar;
  username.value = response.data.username;
} catch (error) {
  console.error("Logout fail", error);
}

const handleLogout = async () => {
  try {
    //console.log(token)
    const response = await axios.post("http://localhost/api/logout", token);
    
    console.log(response.data);
    if(response.data.status == "success"){
      authStore.logout();
      router.push("/");
    }else{
      router.push("/main");
    }
  } catch (error) {
    console.error("Logout fail", error);
  }
};

const switchPage = (page) => {
  currentPageInMain.setCurrentPage(page);
  router.push("/main")
}
</script>

<template>
  <div class="navbar fixed-top bg-body-tertiary shadow-sm z-2 py-0">
    <div class="w-100 d-flex justify-content-between align-items-center mx-3">

      <ul class="nav nav-underline d-flex align-items-end">
        <a class="navbar-brand font-monospace fw-bold fs-2 py-0" href="/main">Online Study</a>
        <li class="nav-item" v-if="!isInstructor">
          <button class="nav-link" @click="switchPage('UserPage')">User</button>
        </li>
        <li class="nav-item">
          <button class="nav-link" @click="switchPage('CoursePage')">Course</button>
        </li>
        <li class="nav-item" v-if="!isInstructor">
          <button class="nav-link" @click="switchPage('ReviewPage')">Review</button>
        </li>
      </ul>

      <form @submit.prevent="handleLogout" class="d-flex align-items-center">
        <div class="d-flex justify-content-center me-3" style="width: 36px;">
          <img :src="avatar" class="img-thumnail rounded-circle img-fluid" alt="...">
        </div>
        <p class="my-0 me-3 fs-4 text-primary font-monospace">{{ username }}</p>
        <button class="btn btn-danger" type="submit">Logout</button>
      </form>
      
    </div>
  </div>
</template>