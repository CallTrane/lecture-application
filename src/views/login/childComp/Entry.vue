<template>
  <div>
    <div class="login-box">
        <form action="">
          <!-- 头像 -->
          <img src="~assets/img/login/avatar.svg" alt="" class="avatar">

          <!-- 标题 -->
          <h2 class="title">欢迎</h2>

          <!-- 输入区域 -->
          <div class="input-group">

            <div class="icon">
              <!-- <i class="fa fa-user"></i> -->
              <img src="~assets/img/login/user.svg" alt="">
            </div>

            <div>
              <!-- <h5>用户名/学号</h5> -->
              <input type="text" class="input" placeholder="用户名/学号">
            </div>
          </div>

          <div class="input-group">
            <div class="icon">
              <img src="~assets/img/login/pwd.svg" alt="">
            </div>

            <div class="pwd">
              <!-- <h5>密码</h5> -->
              <input :type="pwdType" class="input" placeholder="密码">
              <div class="eye" @click="eyeClick">
                <img src="~assets/img/login/visible.svg" alt="" v-if="eye">
                <img src="~assets/img/login/invisible.svg" alt="" v-else>
              </div>
              
            </div>
          </div>
          <a href="#">忘记密码?</a>
          <div class="btn">登录</div>
         
        </form>
      </div>
  </div>
</template>

<script>
import NavBar from "components/common/navbar/NavBar";
import { log } from "util";

export default {
  name: "Entry",
  components: {
    NavBar
  },
  data() {
    return {
      eye: true,
      // 密码输入框，默认是password，密码以原点显示
      pwdType: "password"
    };
  },
  methods: {
    eyeClick() {
      this.eye = !this.eye;
      if (this.eye) {
        this.pwdType = "text";
      } else {
        this.pwdType = "password";
      }
    }
  }
};
</script>

<style scoped>
.login-box {
  display: flex;
  align-items: center;
  text-align: center;
}

form {
  width: 360px;
}

/* 头像 */
.avatar {
  width: 100px;
  height: 100px;
}

.title {
  font-size: 2.9rem;
  margin: 15px 0;
  color: #999;
}

.input-group {
  position: relative;
  display: grid;
  grid-template-columns: 7% 93%;
  margin: 25px 0;
  padding: 5px 0;
  border-bottom: 2px solid #d9d9d9;
}

.input-group:nth-child(1) {
  margin-bottom: 4px;
}

.input-group:before,
.input-group:after {
  content: "";
  position: absolute;
  bottom: -2px;
  width: 0;
  height: 2px;
  background-color: #38d39f;
  transition: 0.5s;
}

.input-group:after {
  right: 50%;
}
.input-group:before {
  left: 50%;
}

.icon {
  display: flex;
  justify-content: center;
  align-items: center;
}
.icon img {
  width: 25px;
  height: 25px;
}

.input-group > div {
  position: relative;
  height: 45px;
}

.input-group > div > h5 {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: #aaa;
  font-size: 18px;
  transition: 0.3s;
}
.input-group.focus .icon i {
  color: #38d39f;
}
.input-group.focus div h5 {
  top: -5px;
  font-size: 15px;
}
.input-group.focus:after,
.input-group.focus:before {
  width: 50%;
}

.input {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  border: none;
  outline: none;
  background: none;
  padding: 0.5rem 0.7rem;
  font-size: 1.2rem;
  color: #555;
  font-family: "Roboto", sans-serif;
}

/* 密码--眼睛可见/不可见 */

.pwd {
  position: relative;
}
.eye {
  position: absolute;
  top: 5px;
  right: 0;
  cursor: pointer;
}

.eye img {
  width: 30px;
  height: 30px;
}

/* 忘记密码 */
a {
  display: block;
  text-align: right;
  text-decoration: none;
  color: #999;
  font-size: 0.9rem;
  transition: 0.3s;
}
a:hover {
  color: #38d39f;
}

.btn {
  display: block;
  width: 100%;
  height: 50px;
  line-height: 50px;
  border-radius: 25px;
  margin: 1rem 0;
  font-size: 1.2rem;
  background-image: linear-gradient(to right, #32be8f, #38d39f, #32be8f);
  cursor: pointer;
  color: #fff;
  transition: 0.5s;
}
.btn:hover {
  background-position: right;
}

/*媒体查询*/
@media screen and (max-width: 1080px) {
  .container {
    grid-gap: 9rem;
  }
}
@media screen and (max-width: 1024px) {
  form {
    width: 290px;
  }
  form h2 {
    font-size: 2.4rem;
    margin: 8px 0;
  }
  .img img {
    width: 360px;
  }
}
@media screen and (max-width: 768px) {
  .wave {
    display: none;
  }
  .img {
    display: none;
  }
  .container {
    grid-template-columns: 1fr;
  }
  .login-box {
    justify-content: center;
  }
}
</style>