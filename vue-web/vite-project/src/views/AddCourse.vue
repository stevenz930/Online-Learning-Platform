<script setup>
import { defineAsyncComponent, reactive } from "vue";
import { ref } from "vue";
import Nav from "../components/Nav.vue";
import { useRoute } from "vue-router";
import axios from 'axios';

const route = useRoute();
const courseId = parseInt(route.params.courseId, 10);
const addCourseSuccess = ref(false);

const row_name_course = [
  'id', 'rating', 'comment', 'created_at',
  'course_name', 'user_name',
];
let all_course_data = ref({
    'title':'', 'description':'', 'price':'',
    'level':'', 'thumbnail':'',
    'user_id':'', 'tags':'',
    'subject':'', 'category':'',
    'is_published':1
});
let all_lesson_data_by_course = ref([]);

const ClearInput = async () => { 
    all_course_data.value = {
        'title':'', 'description':'', 'price':'',
        'level':'', 'thumbnail':'',
        'user_id':'', 'tags':'',
        'subject':'', 'category':'',
        'is_published':1
    };
    //console.log("XXX", all_course_data.value);
};
//ClearInput();
const handleSaveAdd = async () => {
    console.log("handleSaveAdd", all_course_data.value);
    try {
        const response = await axios.post(
          "http://localhost/api/add-course", all_course_data.value
        );
        addCourseSuccess.value = true;
        console.log("handleSaveAdd", response);
        console.log("handleSaveAdd", response.data);
    } catch (error) {
        console.log("handleSaveAdd", error);
    }
};
</script>

<template>
  <div class="container pt-5">
    <div class="alert alert-primary z-3 mt-4" role="alert" v-show="addCourseSuccess">
      Add new course successfully!
    </div>
    
    <Suspense>
      <template #default>
        <div class="container pt-5">
          <form @submit.prevent="handleSaveAdd">
            <div class="d-flex justify-content-between align-items-center">
              <h1 class="fw-bold font-monospace">Course Info</h1>
              <div class="d-flex">
                <button type="button" class="btn btn-light me-3 shadow-sm" @click="ClearInput">Reset</button>
                <button type="submit" class="btn btn-primary shadow-sm">Save</button>
              </div>
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
