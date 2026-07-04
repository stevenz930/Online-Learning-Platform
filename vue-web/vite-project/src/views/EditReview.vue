<script setup>
import { defineAsyncComponent, reactive } from "vue";
import { ref } from "vue";
import Nav from "../components/Nav.vue";
import { useRoute } from "vue-router";
import axios from 'axios';

const route = useRoute();
const reviewId = parseInt(route.params.reviewId, 10);
const editReviewSuccess = ref(false);
const row_name_review = [
  'id', 'rating', 'comment', 'created_at',
  'course_name', 'user_name',
];
const all_review_data = ref([]);
const fetchReviewData = async () => { 
  try {
    const response = await axios.get("http://localhost/api/get-review?id=" + reviewId);

    if(response.data.status == "success"){
      all_review_data.value = response.data.data[0];
      console.log("fetch Review Data", all_review_data.value);
    }else{
      console.log("fail");
    }
  } catch (error) {
   console.error("Logout fail", error);
  }
};

const handleSave = async () => {
  //console.log("handleSave", all_review_data.value);
  try {
    const response = await axios.post(
      "http://localhost/api/update-review-data", all_review_data.value
    ).then(()=>{editReviewSuccess.value = true});
    console.log("handleSave", response);
  } catch (error) {
    console.log("handleSave", error);
  }
};
fetchReviewData();
</script>

<template>
  <div class="container pt-5">
    <div class="alert alert-primary z-3 mt-4" role="alert" v-show="editReviewSuccess">
      Edit review info successfully!
    </div>
    
    <Suspense>
      <template #default>
        <div class="container pt-5">
          <form @submit.prevent="handleSave">
            <div class="d-flex justify-content-between align-items-center">
              <h1 class="fw-bold font-monospace">Review Info</h1>
              <div class="d-flex">
                <button type="button" class="btn btn-light me-3 shadow-sm" @click="fetchReviewData">Reset</button>
                <button type="submit" class="btn btn-primary shadow-sm">Save</button>
              </div>
            </div>

            <div class="card mb-5 shadow-sm">
              <ul class="list-group list-group-flush">
                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Review ID</p>
                  <p class="my-0 fw-light">{{ all_review_data['id'] }}</p>
                </li>
              </ul>
            </div>

            <div class="card mb-5 shadow-sm">
              <div class="card-body d-flex flex-column justify-content-around">
                <h3 class="fw-bold mb-3 font-monospace">Info</h3>
                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Rating</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="rating" 
                    aria-label="username" v-model="all_review_data['rating']" required>
                  </div>
                </div>
                <div class="row mb-4">
                  <div class="col">
                    <p class="my-0 mb-1">Comment</p>
                    <textarea class="form-control fw-light" style="height: 200px" placeholder="Input here" v-model="all_review_data['comment']"></textarea>
                  </div>
                </div>
              </div>
            </div>
          </form>

        </div>
      </template>

      <template class="position-relative" #fallback>
        <div class="position-absolute top-50 start-50 translate-middle">
          <div class="spinner-border text-dark" role="status">
            <span class="visually-hidden">Loading...</span>
          </div>
        </div>
      </template>
    </Suspense>


  </div>
</template>

<style scoped>

</style>
