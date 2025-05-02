<script setup>
import Header from './components/Header.vue'
import CardList from './components/CardList.vue'
import Drawer from './components/Drawer.vue'
</script>
<template>
  <Drawer />
  <div className="bg-white w-4/5 m-auto rounded-xl shadow-xl mt-14">
    <Header />

    <div className="p-10">
      <h2 className="text-3xl font-bold mb-8">Все велосипеды</h2>

      <CardList />
    </div>
  </div>
</template>

<style scoped></style>
