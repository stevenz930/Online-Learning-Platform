<script setup>
defineProps(['visible']);
</script>
<template>
    <transition name="fade">
        <div v-if="visible" class="modal fade show d-block bg-dark d-flex justify-content-center align-items-center" @click="$emit('close')" tabindex="-1" style="--bs-bg-opacity: .5;">
          <div class="modal-dialog w-25">
            <div class="modal-content">
              <slot></slot>
              <div class="modal-footer">
                <button type="button" @click="$emit('close')" class="btn btn-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
    </transition>
</template>