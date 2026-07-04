<script setup>
import Nav from "./components/Nav.vue";
import { useRoute } from "vue-router";
const route = useRoute();
</script>

<template>
  <div class="container-fluid vh-100 d-flex">
    <Suspense>
      <template #default>
        <Nav v-if="route.path !== '/'"/>
      </template>
      <template #fallback>
      </template>
    </Suspense>
    <router-view/>
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
