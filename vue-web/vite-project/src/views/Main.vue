<script setup>
import { defineAsyncComponent, computed } from "vue";
import { useCurrentPageInMain } from '../stores/currentPageinMain';

const CoursePage = defineAsyncComponent(() => import("./Course.vue"));
const UserPage = defineAsyncComponent(() => import("./User.vue"));
const ReviewPage = defineAsyncComponent(() => import("./Review.vue"));
const componentMap = {
  CoursePage,
  UserPage,
  ReviewPage,
};
const currentPageInMain = useCurrentPageInMain();

const resolvedComponent = computed(() => {
  const componentName = currentPageInMain.currentPage;

  if (componentMap[componentName]) {
    return componentMap[componentName];
  } else {
    console.warn(`Invalid component requested: ${componentName}`);
    return 'div';
  }
});

</script>

<template>
  <div class="container-fluid overflow-x-hidden pt-5">
    <Suspense>
      <template #default>
        <component :is="resolvedComponent"/>
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
