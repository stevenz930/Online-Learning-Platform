<script setup>
import { defineAsyncComponent, reactive } from "vue";
import { ref } from "vue";
import Nav from "../components/Nav.vue";
import { useRoute } from "vue-router";
import axios from 'axios';

const route = useRoute();
const courseId = parseInt(route.params.courseId, 10);
const editCourseSuccess = ref(false);
const nextLessonOrder = ref(0);
const row_name_course = [
  'id', 'rating', 'comment', 'created_at',
  'course_name', 'user_name',
];
const all_course_data = ref([]);
const all_lesson_data_by_course = ref([]);
const fetchCourseData = async () => { 
  try {
    const response = await axios.get("http://localhost/api/get-course-for-edit-page?id=" + courseId);

    if(response.data.status == "success"){
      all_course_data.value = response.data.data[0];
      console.log("fetch Course Data", all_course_data.value);
    }else{
      console.log("fail");
    }
  } catch (error) {
   console.error("Logout fail", error);
  }
};
const fetchLessonDataByCourse = async () => { 
  try {
    const response = await axios.get("http://localhost/api/get-all-lesson-by-course?id=" + courseId);

    if(response.data.status == "success"){
      all_lesson_data_by_course.value = response.data.data;
      nextLessonOrder.value = all_lesson_data_by_course.value.length + 1;
      console.log("fetch Lesson Data by Course ", nextLessonOrder.value);
    }else{
      nextLessonOrder.value = 1;
      console.log("fetch Lesson Data by Course ", nextLessonOrder.value);
    }
  } catch (error) {
   console.error("Logout fail", error);
  }
};

const handleSave = async () => {
  console.log("handleSave", all_course_data.value);
  try {
    const response = await axios.post(
      "http://localhost/api/update-course-data", all_course_data.value
    ).then(()=>{editCourseSuccess.value = true});
    console.log("handleSave", response.data.status);
  } catch (error) {
    console.log("handleSave", error);
  }
};

fetchCourseData();
fetchLessonDataByCourse();
</script>

<template>
  <div class="container pt-5">
    <div class="alert alert-primary z-3 mt-4" role="alert" v-show="editCourseSuccess">
      Edit course info successfully!
    </div>
    
    <Suspense>
      <template #default>
        <div class="container pt-5">
          <form @submit.prevent="handleSave">
            <div class="d-flex justify-content-between align-items-center">
              <h1 class="fw-bold font-monospace">Course Info</h1>
              <div class="d-flex">
                <button type="button" class="btn btn-light me-3 shadow-sm" @click="fetchCourseData">Reset</button>
                <button type="submit" class="btn btn-primary shadow-sm">Save</button>
              </div>
            </div>

            <div class="card mb-5 shadow-sm">
              <ul class="list-group list-group-flush">
                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Course ID</p>
                  <p class="my-0 fw-light">{{ all_course_data['id'] }}</p>
                </li>

                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Create At</p>
                  <p class="my-0 fw-light">{{ all_course_data['created_at'] }}</p>
                </li>

                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Updated At</p>
                  <p class="my-0 fw-light">{{ all_course_data['updated_at'] }}</p>
                </li>

                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Average Rating</p>
                  <p class="my-0 fw-light">{{ all_course_data['avg_rating'] }}</p>
                </li>
                
                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Review Count</p>
                  <p class="my-0 fw-light">{{ all_course_data['review_count'] }}</p>
                </li>

                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Course Status</p>
                  <p class="my-0 text-success fw-light" v-if="all_course_data['is_published'] == 1">Published</p>
                  <p class="my-0 text-danger fw-light" v-else>Not Published</p>
                </li>

              </ul>
            </div>

            <div class="card mb-5 shadow-sm">
              <div class="card-body d-flex flex-column justify-content-around">
                <h3 class="fw-bold mb-3 font-monospace">Info</h3>

                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Title</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="title" 
                    aria-label="title" v-model="all_course_data['title']" required>
                  </div>
                </div>

                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Price</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="price" 
                    aria-label="price" v-model="all_course_data['price']" required>
                  </div>
                </div>

                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Level</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="level" 
                    aria-label="level" v-model="all_course_data['level']" required>
                  </div>
                </div>
                
                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Thumbnail</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="thumbnail" 
                    aria-label="thumbnail" v-model="all_course_data['thumbnail']" required>
                  </div>
                </div>

                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Instructor ID</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="Instructor ID" 
                    aria-label="Instructor ID" v-model="all_course_data['user_id']" required>
                  </div>
                </div>

                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Tags</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="tags" 
                    aria-label="tags" v-model="all_course_data['tags']" required>
                  </div>
                </div>

                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Subject</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="subject" 
                    aria-label="subject" v-model="all_course_data['subject']" required>
                  </div>
                </div>
                
                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Category</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="category" 
                    aria-label="category" v-model="all_course_data['category']" required>
                  </div>
                </div>

                <div class="row mb-4">
                  <div class="col">
                    <p class="my-0 mb-1">Description</p>
                    <textarea class="form-control fw-light" style="height: 200px" placeholder="Input here" v-model="all_course_data['description']"></textarea>
                  </div>
                </div>


              </div>

              <div class="card-body d-flex flex-column justify-content-around">
                <h3 class="fw-bold mb-3 font-monospace">Lesson</h3>

                <div v-for="(item, index) in all_lesson_data_by_course" :key="index" class="row mb-4">
                  <div class="col ">
                    <div class="d-flex border rounded ">
                        <div class="d-flex justify-content-center my-auto ms-1" style="height: 50px;">
                            <img :src="item['cover']" class="img-thumnail rounded" alt="...">
                        </div>
                        <p class="my-auto ms-2 display-5 text-primary-emphasis">{{ item['lesson_order'] }}</p>
                        <p class="my-auto ms-3">{{ item['title'] }}</p>
                    </div>
                  </div>

                </div>
                
                <router-link class="btn btn-primary mt-1" :to="'/add-lesson/' + courseId + '/' + nextLessonOrder">
                  <img class="my-auto" src="../assets/add_circle_20dp_FFFFFF_FILL0_wght400_GRAD0_opsz20.svg" alt="">
                  <p class="d-inline">Add Lesson {{ nextLessonOrder }}</p>
                </router-link>

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
