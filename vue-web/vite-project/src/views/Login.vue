<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../stores/useAuthStore";

import axios from "axios";
import Modal from "../components/Modal.vue";

const form = ref({ username: "", password: "" });
const router = useRouter();

const isSuperuser = ref(true);
const isInstructor = ref(true);
const isError = ref(true);

const authStore = useAuthStore();

axios.defaults.withCredentials = true;

const handleLogin = async () => {
    try {
        const response = await axios.post(
            "http://localhost/api/login", form.value);
        console.log(response.data);

        if(response.data.role == "superuser"){
            const id = form.value.id;
            const token = response.data.token;

            authStore.login(id, token, "superuser");

            router.push("/main");
        }else if(response.data.role == "instructor"){
            const id = form.value.id;
            const token = response.data.token;

            authStore.login(id, token, "instructor");

            router.push("/main");
        }else if(response.data.status == "error"){
            router.push("/");
            isError.value = false;
            //console.log("su",isSuperuser.value);
        }
    } catch (error) {
        console.error(error);
    }
};
</script>

<template>
    <div class="container mx-auto my-auto">
        <div class="card mx-auto shadow-lg" style="max-width: 1080px;">
            <div class="row g-0">
                <div class="col-md-8" style="height: 80vh;">
                    <img src="http://picsum.photos/seed/seed/1280/860" class="img-fluid rounded-start" alt="..." style="object-fit: cover; height: 100%; width: 100%;">
                </div>
                <div class="col-md-4 px-3 pt-2 pb-5 my-auto">
                    <div class="card-body">
                        <p class="card-title font-monospace fw-bold fs-1 mb-3 text-center">User Login</p>
                        <form @submit.prevent="handleLogin">
                            <div class="mb-3">
                                <label for="loginID" class="form-label">ID</label>
                                <input type="text" v-model="form.id" class="form-control" id="loginID" placeholder="ID" required>
                            </div>
                            <div class="mb-5">
                                <label for="loginPassword" class="form-label">Password</label>
                                <input type="password" v-model="form.password" class="form-control" id="loginPassword" placeholder="Password" required>
                            </div>
                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-primary shadow-sm text-center w-50">LOGIN</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <Modal :visible="!isError" @close="isError = true">
        <div class="modal-header">
            <h5 class="modal-title text-danger fw-bold">ERROR</h5>
            <button type="button" class="btn-close" @click="$emit('close')"></button>
        </div>
        <div class="modal-body">
            <p>You Are Not Superuser or Instructor</p>
        </div>
    </Modal>

</template>

<style scoped>

</style>
