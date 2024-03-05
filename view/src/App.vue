<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
  <header>
    <RouterView name="nav" />
  </header>
  <section class="container">
    <RouterView />
  </section>
</template>

<style scoped></style>
