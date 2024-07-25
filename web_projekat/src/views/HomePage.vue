<template>
  <div id="home">
    <h2>Welcome</h2>
    <img alt="Vue logo" src="../assets/logo.png">
    <LatestList></LatestList>
  </div>
</template>

<script>


import LatestList from "@/components/LatestList.vue";

export default {
  name: 'HomePage',
  components: {
    LatestList
  },
}
</script>

<style scoped>
#home {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
