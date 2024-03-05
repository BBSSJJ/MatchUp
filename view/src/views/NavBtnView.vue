<script setup>
import { RouterLink } from 'vue-router'
</script>
<template>
  <ul class="nav nav-tabs" id="myTab" role="tablist">
    <span class="navbar-brand ps-4 pe-4">
      <!-- <img src="../assets/img/logo.png" alt="MatchUP" width="50" height="40" /> -->
    </span>
    <li class="nav-item" role="presentation">
      <RouterLink to="/">
        <button
          class="nav-link active"
          id="home-tab"
          data-bs-toggle="tab"
          data-bs-target="#home-tab-pane"
          type="button"
          role="tab"
          aria-controls="home-tab-pane"
          aria-selected="false"
        >
          Home
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/upload">
        <button
          class="nav-link"
          id="proposal-tab"
          data-bs-toggle="tab"
          data-bs-target="#proposal-tab-pane"
          type="button"
          role="tab"
          aria-controls="proposal-tab-pane"
          aria-selected="false"
        >
          업로드
        </button>
      </RouterLink>
    </li>
    <li class="nav-item" role="presentation">
      <RouterLink to="/files">
        <button
          class="nav-link"
          id="proposal-tab"
          data-bs-toggle="tab"
          data-bs-target="#proposal-tab-pane"
          type="button"
          role="tab"
          aria-controls="proposal-tab-pane"
          aria-selected="false"
        >
          목록
        </button>
      </RouterLink>
    </li>
  </ul>
</template>
<style scoped>
a {
  text-decoration-line: none;
  color: black;
}
</style>
