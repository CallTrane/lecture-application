<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import Login from "views/login/Login";

export default {
  name: "APP",
  components: {
    Login,
  },
};
</script>


<style>
@import "./assets/css/base.css";
</style>
