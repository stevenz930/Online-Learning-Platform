<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from 'axios';

const router = useRouter();
//const token = localStorage.getItem("token");
//axios.defaults.headers.common['Authorization'] = token;
axios.defaults.withCredentials = true;

const row_name_course = [' ',' ','ID','Title','Price','Thumbnail','Created At','Updated At','Instructor name'];
const course_data = ref([]);
const fetchAllCourseData  = async () => { 
  course_data.value = [];
  try {
    const response = await axios.get("http://localhost/api/get-some-course");
    if(response.data.status == "success"){
       //console.log("success", response.data.data);
       for( var i = 0; i < response.data.data.length; i++){
         course_data.value.push(response.data.data[i]);
       }
       //console.log("success", course_data);
    }else{
      console.log("fail");
    }
  } catch (error) {
    console.error("Logout fail", error);
  }
};
fetchAllCourseData();


</script>

<template>
  <table class="table table-sm align-middle caption-top position-absolute top-0 start-0 mt-5">
    <caption class="text-secondary font-monospace fs-7 mt-2 px-2">
      <div class="d-flex justify-content-between">
        <div class="mt-3">
          <p class="mb-0 d-inline">List of courses. Total </p>
          <p class="mb-0 d-inline text-primary fw-bold"> {{ course_data.length }} </p>
          <p class="mb-0 d-inline"> courses.</p>
        </div>
        <router-link class="btn btn-primary mt-1" to="/add-course">
          <img class="my-auto" src="../assets/add_circle_20dp_FFFFFF_FILL0_wght400_GRAD0_opsz20.svg" alt="">
          <p class="d-inline">Add Course</p>
        </router-link>
      </div>
    </caption>
    <thead class="table-light font-monospace text-center">
      <tr>
        <th scope="col" v-for="(item, index) in row_name_course">{{ item }}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item, index) in course_data" class="fw-light text-body">
        <td :class="{'bg-dark-subtle text-white' :item.is_active==0}" class="rounded-3 d-flex justify-content-evenly">
          <router-link class="btn btn-sm d-inline" :to="'/edit-course/' + item.id">
            <img src="../assets/edit_20dp_0D6EFD_FILL0_wght400_GRAD0_opsz20.svg" alt="">
          </router-link>
        </td>
        <td class="bg-transparent"></td>
        <th :class="{'bg-dark-subtle text-white':item.is_active==0}" scope="row" class="fs-7 fw-light rounded-start-3 text-center">{{ item.id }}</th>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.title }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.price }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.thumbnail }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.created_at }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.updated_at }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.instructor_name }}</td>
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
