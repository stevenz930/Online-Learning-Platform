<script setup>
import { defineAsyncComponent, reactive } from "vue";
import { ref } from "vue";
import Nav from "../components/Nav.vue";
import { useRoute } from "vue-router";
import axios from 'axios';

const route = useRoute();
const courseId = parseInt(route.params.courseId, 10);
const lessonOrder = parseInt(route.params.lessonOrder, 10);
const addLessonSuccess = ref(false);

//const row_name_course = [
//  'id', 'rating', 'comment', 'created_at',
//  'course_name', 'user_name',
//];
let all_lesson_data = ref({
    'title':'', 'lesson_order':lessonOrder, 'content':'',
    'course_id':courseId, 'duration':0,
    'video_url':'video1.mp4', 'cover':'http://picsum.photos/seed/1/240/120'
});
let all_lesson_data_by_course = ref([]);

const ClearInput = async () => { 
    all_lesson_data.value = {
    'title':'', 'lesson_order':lessonOrder, 'content':'',
    'course_id':courseId, 'duration':0,
    'video_url':'video1.mp4', 'cover':'http://picsum.photos/seed/1/240/120'
    };
    //console.log("XXX", all_lesson_data.value);
};
//ClearInput();
const handleSaveAdd = async () => {
    console.log("handleSaveAdd", all_lesson_data.value);
    try {
        const response = await axios.post(
          "http://localhost/api/add-lesson", all_lesson_data.value
        );
        addLessonSuccess.value = true;
        console.log("handleSaveAdd", response);
        console.log("handleSaveAdd", response.data);
    } catch (error) {
        console.log("handleSaveAdd", error);
    }
};
</script>

<template>
  <div class="container pt-5">
    <div class="alert alert-primary z-3 mt-4" role="alert" v-show="addLessonSuccess">
      Add new lesson successfully!
    </div>
    
    <Suspense>
      <template #default>
        <div class="container pt-5">
          <form @submit.prevent="handleSaveAdd">
            <div class="d-flex justify-content-between align-items-center">
              <h1 class="fw-bold font-monospace">Lesson Info</h1>
              <div class="d-flex">
                <button type="button" class="btn btn-light me-3 shadow-sm" @click="ClearInput">Reset</button>
                <button type="submit" class="btn btn-primary shadow-sm">Save</button>
              </div>
            </div>

            <div class="card mb-5 shadow-sm">
              <div class="card-body d-flex flex-column justify-content-around">
                <h3 class="fw-bold mb-3 font-monospace">
                    Info Add Lesson {{ lessonOrder }} in Course {{ courseId }}
                </h3>

                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Title</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="title" 
                    aria-label="title" v-model="all_lesson_data['title']" required>
                  </div>
                </div>

                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Duration</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="duration" 
                    aria-label="duration" v-model="all_lesson_data['duration']">
                  </div>
                </div>
                
                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Video URL</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="video_url" 
                    aria-label="video_url" v-model="all_lesson_data['video_url']">
                  </div>
                </div>

                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Cover</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="cover" 
                    aria-label="cover" v-model="all_lesson_data['cover']">
                  </div>
                </div>

                <div class="row mb-4">
                  <div class="col">
                    <p class="my-0 mb-1">Content</p>
                    <textarea class="form-control fw-light" style="height: 200px" placeholder="Input here" v-model="all_lesson_data['content']"></textarea>
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
