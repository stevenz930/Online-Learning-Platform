<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from 'axios';

const router = useRouter();
//const token = localStorage.getItem("token");
//axios.defaults.headers.common['Authorization'] = token;
axios.defaults.withCredentials = true;

const row_name_user = [' ',' ','ID','Username','Password','Email','Last login','Date Joined','Role'];
const user_data = ref([]);

const fetchAllUserData  = async () => { 
  user_data.value = [];
  try {
    const response = await axios.get("http://localhost/api/get-some-user");
    if(response.data.status == "success"){
       //console.log("success", response.data.data);
       for( var i = 0; i < response.data.data.length; i++){
         user_data.value.push(response.data.data[i]);
       }
       //console.log("success", user_data);
    }else{
      console.log("fail");
    }
  } catch (error) {
    console.error("Logout fail", error);
  }
};
fetchAllUserData();
//const idValue = 164;
//const idobj = {"id": String(idValue)};
//const jsonId = JSON.stringify(idobj);
//console.log("jsonId", jsonId);

const handleSetInactive = async (idValue) => {
  const idobj = {"id": String(idValue)};
  const jsonId = JSON.stringify(idobj);
  console.log("set inactive", jsonId);
  try {
    const response = await axios.post(
      "http://localhost/api/set-user-inactive", jsonId
    ).then(() => { 
      fetchAllUserData();
    });
    console.log("set inactive", jsonId);
  } catch (error) {
    console.error("set inactive fail", error);
  }
};
const handleSetActive = async (idValue) => {
  const idobj = {"id": String(idValue)};
  const jsonId = JSON.stringify(idobj);
  console.log("set active", jsonId);
  try {
    const response = await axios.post(
      "http://localhost/api/set-user-active", jsonId
    ).then(() => { 
      fetchAllUserData();
    });
    console.log("set active", jsonId);
  } catch (error) {
    console.error("set active fail", error);
  }
};
</script>

<template class="position-relative">
  <table class="table table-sm align-middle caption-top position-absolute top-0 start-0 mt-5">
    <caption class="text-secondary font-monospace fs-7 mt-2 px-2">
      <div class="d-flex justify-content-between">
        <div class="mt-3">
          <p class="mb-0 d-inline">List of users. Total </p>
          <p class="mb-0 d-inline text-primary fw-bold"> {{ user_data.length }} </p>
          <p class="mb-0 d-inline"> users.</p>
        </div>
        <router-link class="btn btn-primary mt-1" to="/add-user">
          <img class="my-auto" src="../assets/add_circle_20dp_FFFFFF_FILL0_wght400_GRAD0_opsz20.svg" alt="">
          <p class="d-inline">Add User</p>
        </router-link>
      </div>
    </caption>
    <thead class="table-light font-monospace text-center">
      <tr>
        <th scope="col" v-for="(item, index) in row_name_user">{{ item }}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item, index) in user_data" class="fw-light text-body">
        <td :class="{'bg-dark-subtle text-white' :item.is_active==0}" class="rounded-3 d-flex justify-content-evenly">
          <router-link class="btn btn-sm d-inline" :to="'/edit-user/' + item.id">
            <img src="../assets/edit_20dp_0D6EFD_FILL0_wght400_GRAD0_opsz20.svg" alt="">
          </router-link>
          <div class="d-inline">
            <button class="btn btn-sm" v-if="item.is_active == 0" @click="handleSetActive(item.id)">
              <img src="../assets/visibility_20dp_FFFFFF_FILL0_wght400_GRAD0_opsz20.svg" alt="">
            </button>
            <button class="btn btn-sm" v-else @click="handleSetInactive(item.id)">
              <img src="../assets/visibility_off_20dp_595C5F_FILL0_wght400_GRAD0_opsz20.svg" alt="">
            </button>
          </div>
        </td>
        <td class="bg-transparent"></td>
        <th :class="{'bg-dark-subtle text-white':item.is_active==0}" scope="row" class="fs-7 fw-light rounded-start-3 text-center">{{ item.id }}</th>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.username }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.password }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.email }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.last_login }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.date_joined }}</td>

        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="rounded-end-3">
          <div class="d-flex justify-content-center column-gap-1">
            <span v-if="item.is_superuser == 1" class="fs-8 badge text-bg-danger font-monospace rounded-pill">Superuser</span>
            <span v-if="item.is_instructor == 1" class="fs-8 badge text-bg-success font-monospace rounded-pill">Instructor</span>
            <span class="fs-8 badge text-bg-secondary font-monospace rounded-pill">User</span>
          </div>
        </td>
        
      </tr>
    </tbody>
  </table>
  <div class="position-absolute top-50 start-50 translate-middle z-n1">
    <div class="spinner-border text-dark" role="status">
      <span class="visually-hidden">Loading...</span>
    </div>
  </div>
</template>

<style scoped>
.fs-7{
  font-size: 12px; 
}
.fs-8{
  font-size: 10px; 
}
</style>
