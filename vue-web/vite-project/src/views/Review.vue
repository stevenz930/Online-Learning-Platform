<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from 'axios';

const router = useRouter();
//const token = localStorage.getItem("token");
//axios.defaults.headers.common['Authorization'] = token;
axios.defaults.withCredentials = true;

const row_name_review = [' ',' ','ID','Rating','Comment','Created At','Course Name','User name'];
const review_data = ref([]);
const fetchAllReviewData  = async () => { 
  review_data.value = [];
  try {
    const response = await axios.get("http://localhost/api/get-some-review");
    if(response.data.status == "success"){
       //console.log("success", response.data.data);
       for( var i = 0; i < response.data.data.length; i++){
         review_data.value.push(response.data.data[i]);
       }
       //console.log("success", review_data);
    }else{
      console.log("fail");
    }
  } catch (error) {
    console.error("Logout fail", error);
  }
};
fetchAllReviewData();

const handleRemoveReview = async (idValue) => {
  const idobj = {"id": String(idValue)};
  const jsonId = JSON.stringify(idobj);
  console.log("remove", jsonId);
  try {
    const response = await axios.post(
      "http://localhost/api/delete-review", jsonId
    ).then(() => { 
      fetchAllReviewData();
    });
    console.log("remove", jsonId);
  } catch (error) {
    console.error("remove fail", error);
  }
};
</script>

<template>
  <table class="table table-sm align-middle caption-top position-absolute top-0 start-0 mt-5">
    <caption class="text-secondary font-monospace fs-7 mt-2 px-2">
      <div class="d-flex justify-content-between">
        <div class="mt-3">
          <p class="mb-0 d-inline">List of reviews. Total </p>
          <p class="mb-0 d-inline text-primary fw-bold"> {{ review_data.length }} </p>
          <p class="mb-0 d-inline"> reviews.</p>
        </div>
      </div>
    </caption>
    <thead class="table-light font-monospace text-center">
      <tr>
        <th scope="col" v-for="(item, index) in row_name_review">{{ item }}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item, index) in review_data" class="fw-light text-body">
        <td :class="{'bg-dark-subtle text-white' :item.is_active==0}" class="rounded-3 d-flex justify-content-evenly">
          <router-link class="btn btn-sm d-inline" :to="'/edit-review/' + item.id">
            <img src="../assets/edit_20dp_0D6EFD_FILL0_wght400_GRAD0_opsz20.svg" alt="">
          </router-link>
          <div class="d-inline">
            <button class="btn btn-sm" @click="handleRemoveReview(item.id)">
              <img src="../assets/delete_20dp_595C5F_FILL0_wght400_GRAD0_opsz20.svg" alt="">
            </button>
          </div>
        </td>
        <td class="bg-transparent"></td>
        <th :class="{'bg-dark-subtle text-white':item.is_active==0}" scope="row" class="fs-7 fw-light rounded-start-3 text-center">{{ item.id }}</th>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.rating }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.comment }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.created_at }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.course_name }}</td>
        <td :class="{'bg-dark-subtle text-white':item.is_active==0}" class="fs-7 text-center">{{ item.reviewer_name }}</td>
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
