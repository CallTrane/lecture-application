<template>
  <div class="container">
    <div class="content">
      <el-container class="header">
        <el-header>学生选课系统</el-header>
      </el-container>
      <el-container class="middle">
        <el-aside width="200px">Aside</el-aside>
        <el-main>Main</el-main>
      </el-container>
      <el-container class="bottom">
        <el-footer>Footer</el-footer>
      </el-container>
    </div>
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
.container {
  /* background-image: url("../../assets/img/background.jpg");
  background-size: cover;
  background-attachment: fixed; */
  position: relative;
  /* filter: blur(5px); */
}
.container::after {
  content: "";
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background-image: url("../../assets/img/background2.jpg");
  background-size: cover;
  filter: blur(5px);
}
.content {
  position: relative;
  z-index: 1;
}
.header {
  height: 68px;
  line-height: 68px;
  font-size: 32px;
  letter-spacing: 1px;
  font-family: Arial;
  color: #333;
  text-align: center;
  font-weight: 600;
}
.bottom {
  position: relative;
  height: 61px;
  top: calc(100vh- (100vh - 130px));
  text-align: center;
}
.el-aside,
.el-main {
  border: 2px solid #000;
  height: calc(100vh - 130px);
  text-align: center;
}
.el-main {
  width: 100%;
}
.middle {
  display: flex;
}
</style>
