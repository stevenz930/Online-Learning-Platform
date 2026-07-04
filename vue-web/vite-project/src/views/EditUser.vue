<script setup>
import { defineAsyncComponent, reactive } from "vue";
import { ref } from "vue";
import Nav from "../components/Nav.vue";
import { useRoute } from "vue-router";
import axios from 'axios';

const route = useRoute();
const userId = parseInt(route.params.userId, 10);
const editUserSuccess = ref(false);
const row_name_user = [
  'id', 'last_login', 'date_joined', 
  'username', 'password', 'email', 
  'first_name', 'last_name', 
  'avatar', 
  'bio', 
  'facebook_url', 'instagram_url', 'linkedin_url', 'twitter_url', 
  'is_superuser', 'is_instructor', 'is_active'
];

const all_user_data = ref([]);
const fetchUserData = async () => { 
  try {
    const response = await axios.get("http://localhost/api/get-user?id=" + userId);

    if(response.data.status == "success"){
      all_user_data.value = response.data.data[0];
      console.log("fetch User Data", all_user_data.value);
    }else{
      console.log("fail");
    }
  } catch (error) {
   console.error("Logout fail", error);
  }
};

const handleSave = async () => {
  //console.log("handleSave", all_user_data.value);
  try {
    const response = await axios.post(
      "http://localhost/api/update-user-data", all_user_data.value
    ).then(()=>{editUserSuccess.value = true});
    console.log("handleSave", response.data);
  } catch (error) {
    console.log("handleSave", error);
  }
};
fetchUserData();
</script>

<template>
  <div class="container pt-5">
    <div class="alert alert-primary z-3 mt-4" role="alert" v-show="editUserSuccess">
      Edit user info successfully!
    </div>
    
    <Suspense>
      <template #default>
        <div class="container pt-5">
          <form @submit.prevent="handleSave">
            <div class="d-flex justify-content-between align-items-center">
              <h1 class="fw-bold font-monospace">User Info</h1>
              <div class="d-flex">
                <button type="button" class="btn btn-light me-3 shadow-sm" @click="fetchUserData">Reset</button>
                <button type="submit" class="btn btn-primary shadow-sm">Save</button>
              </div>
            </div>

            <div class="card mb-5 shadow-sm">
              <div class="d-flex justify-content-center mt-3">
                <img :src="all_user_data['avatar']" class="img-thumnail rounded-circle" alt="...">
              </div>
              <div class="card-body text-center">
                <h5 class="card-title fw-bold">{{ all_user_data['username'] }}</h5>
                <div class="card-text">
                  <div class="d-flex justify-content-center column-gap-1 align-items-center">
                    <span v-if="all_user_data['is_superuser'] == 1" class="fs-8 badge text-bg-danger font-monospace rounded-pill">Superuser</span>
                    <span v-if="all_user_data['is_instructor'] == 1" class="fs-8 badge text-bg-success font-monospace rounded-pill">Instructor</span>
                    <span class="fs-8 badge text-bg-secondary font-monospace rounded-pill">User</span>
                  </div>
                </div>
              </div>
              <ul class="list-group list-group-flush">
                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">User ID</p>
                  <p class="my-0 fw-light">{{ all_user_data['id'] }}</p>
                </li>
                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Last Login</p>
                  <p class="my-0 fw-light" v-if="all_user_data['last_login']">{{ all_user_data['last_login'] }}</p>
                  <p class="my-0 fw-light" v-else>Null</p>
                </li>
                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Date Joined</p>
                  <p class="my-0 fw-light">{{ all_user_data['date_joined'] }}</p>
                </li>
                <li class="list-group-item d-flex align-items-center justify-content-between">
                  <p class="my-0 me-3">Account Status</p>
                  <p class="my-0 text-success fw-light" v-if="all_user_data['is_active']">Active</p>
                  <p class="my-0 text-danger fw-light" v-else>Inactive</p>
                </li>
              </ul>
            </div>

            <div class="card mb-5 shadow-sm">
              <div class="card-body d-flex flex-column justify-content-around">
                <h3 class="fw-bold mb-3 font-monospace">Info</h3>
                <div class="row mb-4">
                  <div class="col">
                    <div class="d-inline-flex align-items-center">
                      <p class="my-0 mb-1">Username</p><p class="my-0 text-danger">*</p>
                    </div>
                    <input type="text" class="form-control col fw-light" placeholder="username" 
                    aria-label="username" v-model="all_user_data['username']" required>
                  </div>
                  <div class="col">
                    <p class="my-0 mb-1">Email</p>
                    <input type="text" class="form-control fw-light" placeholder="email" 
                    aria-label="email" v-model="all_user_data['email']">
                  </div>
                </div>
                <div class="row mb-4">
                  <div class="col">
                    <p class="my-0 mb-1">First Name</p>
                    <input type="text" class="form-control col fw-light" placeholder="first_name" 
                    aria-label="first_name" v-model="all_user_data['first_name']">
                  </div>
                  <div class="col">
                    <p class="my-0 mb-1">Last Name</p>
                    <input type="text" class="form-control fw-light" placeholder="last_name" 
                    aria-label="last_name" v-model="all_user_data['last_name']">
                  </div>
                </div>
                <div class="row mb-4">
                  <div class="col">
                    <p class="my-0 mb-1">Password</p>
                    <input type="text" class="form-control col fw-light" placeholder="password" 
                    aria-label="password" v-model="all_user_data['password']" required>
                  </div>
                  <div class="col">
                    <p class="my-0 mb-1">Avatar</p>
                    <input type="text" class="form-control col fw-light" placeholder="avatar" 
                    aria-label="avatar" v-model="all_user_data['avatar']">
                  </div>
                </div>
                <div class="row mb-4">
                  <div class="col">
                    <p class="my-0 mb-1">Bio</p>
                    <textarea class="form-control fw-light" style="height: 200px" placeholder="Input here" v-model="all_user_data['bio']"></textarea>
                  </div>
                </div>
                <div class="row mb-4">
                  <div class="col">
                    <div class="input-group mb-3">
                      <span class="input-group-text">@</span>
                      <div class="form-floating">
                        <input type="text" class="form-control fw-light" id="floatingInputGroupFacebook" placeholder="Facebook" v-model="all_user_data['facebook_url']">
                        <label for="floatingInputGroupFacebook">Facebook</label>
                      </div>
                    </div>
                    <div class="input-group mb-3">
                      <span class="input-group-text">@</span>
                      <div class="form-floating">
                        <input type="text" class="form-control fw-light" id="floatingInputGroupInstagram" placeholder="Instagram" v-model="all_user_data['instagram_url']">
                        <label for="floatingInputGroupInstagram">Instagram</label>
                      </div>
                    </div>
                    <div class="input-group mb-3">
                      <span class="input-group-text">@</span>
                      <div class="form-floating">
                        <input type="text" class="form-control fw-light" id="floatingInputGroupLinkedin" placeholder="Linkedin" v-model="all_user_data['linkedin_url']">
                        <label for="floatingInputGroupLinkedin">Linkedin</label>
                      </div>
                    </div>
                    <div class="input-group mb-3">
                      <span class="input-group-text">@</span>
                      <div class="form-floating">
                        <input type="text" class="form-control fw-light" id="floatingInputGroupX" placeholder="X" v-model="all_user_data['twitter_url']">
                        <label for="floatingInputGroupX">X</label>
                      </div>
                    </div>
                  </div>
                </div>
                <h3 class="fw-bold mb-3 font-monospace">Auth</h3>
                <div class="row mb-4">
                  <div class="col">
                    <div class="form-check">
                      <input class="form-check-input" type="checkbox" value="" id="checkSuperuser" v-model="all_user_data['is_superuser']" :true-value="1" :false-value="0">
                      <label class="form-check-label fw-light" for="checkSuperuser">
                        Superuser
                      </label>
                    </div>
                  </div>
                  <div class="col">
                    <div class="form-check">
                      <input class="form-check-input" type="checkbox" value="" id="checkInstructor" v-model="all_user_data['is_instructor']" :true-value="1" :false-value="0">
                      <label class="form-check-label fw-light" for="checkInstructor">
                        Instructor
                      </label>
                    </div>
                  </div>
                  <div class="col">
                    <div class="form-check">
                      <input class="form-check-input" type="checkbox" value="" id="checkActive" v-model="all_user_data['is_active']" :true-value="1" :false-value="0">
                      <label class="form-check-label fw-light" for="checkActive">
                        Active
                      </label>
                    </div>
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
