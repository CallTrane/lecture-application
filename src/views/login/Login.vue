<template>
  <div class="main">
    <img src="~assets/img/login/wave.png" alt="" class="wave">
  
    <div class="container">
      <div class="img">
        <img src="~assets/img/login/login-bg.svg" alt="">
    </div>
      
    <entry class="input-box"/>
    </div>
  </div>
</template>

<script>
import Entry from "./childComp/Entry";

export default {
  name: "Login",
  components: {
    Entry
  }
};
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}
body {
  font-family: "Roboto", sans-serif;
}
.wave {
  position: fixed;
  height: 100%;
  left: 0;
  bottom: 0;
  z-index: -1;
}
.container {
  width: 100vw;
  height: 100vh;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 18rem;
  padding: 0 2rem;
}
.img {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.img img {
  width: 500px;
}

.input-box {
  margin-top: 20%;
}
</style>
