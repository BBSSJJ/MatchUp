<script setup>
const props = defineProps({ indexes: Object })
const indexes = props.indexes
</script>
<template>
  <div class="side-bar-container container rounded shadow">
    <p class="ps-2 pt-3">목차<a class="p-2" href="#">&#8679;</a></p>
    <ol>
      <template v-for="index in indexes" :key="index.id">
        <li class="p-2">
          <a :href="`#${index.id}`">{{ index.title }}</a>
        </li>
      </template>
    </ol>
  </div>
</template>
<style scoped>
.side-bar-container {
  position: fixed;
  width: 15%;
  top: 20%;
  left: 80%;
  padding: 1rem;
}
a {
  text-decoration: none;
}
</style>
