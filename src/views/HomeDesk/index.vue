<template>
  <div class="container">
    <el-container class="header">
      <el-header>Header</el-header>
    </el-container>
    <el-container class="middle">
      <el-aside>Aside</el-aside>
      <el-main>Main</el-main>
    </el-container>
    <el-container class="bottom">
      <el-footer>Footer</el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {};
  },
};
</script>

<style scoped>
.header {
  height: 70px;
  background-color: skyblue;
}
.bottom {
  position: relative;
  height: 66px;
  top: calc(100vh- (100vh - 136px));
  background-color: skyblue;
}
.el-aside,
.el-main {
  background-color: pink;
  border: 1px solid #000;
  height: calc(100vh - 136px);
}
.el-main {
  width: 100%;
}
.middle {
  display: flex;
}
</style>
