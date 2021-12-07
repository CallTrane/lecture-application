<template>
  <div>
    <div class="login-box">
      <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="loginForm">
        <!-- 头像 -->
        <img src="~assets/img/login/avatar.svg" alt="" class="avatar" />

        <!-- 标题 -->
        <h2 class="title">学生选课系统</h2>

        <!-- 输入区域 -->
        <el-form-item prop="account" class="input-group">
          <div class="icon">
            <img src="~assets/img/login/user.svg" alt="" />
          </div>

          <div>
            <!-- <h5>用户名/学号</h5> -->
            <el-input v-model="loginForm.account" placeholder="用户名/学号" type="text" class="input" style="border: 0"></el-input>
          </div>
        </el-form-item>

        <el-form-item prop="password" class="input-group" prefix>
          <div class="icon">
            <img src="~assets/img/login/pwd.svg" alt="" />
          </div>

          <div class="pwd">
            <!-- <h5>密码</h5> -->
            <el-input :type="pwdType" v-model="loginForm.password" id="input" placeholder="密码"></el-input>

            <!-- 密码可见/不可见--眼睛icon -->
            <div class="eye" @click="eyeClick">
              <img src="~assets/img/login/visible.svg" alt="" v-if="eye" />
              <img src="~assets/img/login/invisible.svg" alt="" v-else />
            </div>
          </div>
        </el-form-item>

        <!-- 登录按钮 -->
        <div @click="handleLoginIn" class="btn">登录</div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { loginPost } from 'network/login'

import { mapMutations } from 'vuex'

export default {
  name: 'Entry',
  components: {},
  data() {
    return {
      eye: false,
      // 密码输入框，默认是password，密码以原点显示
      pwdType: 'password',
      // 表单对象
      loginForm: {
        account: 'lizhendong',
        password: 'lizhendong'
      },
      // 表单验证规则
      loginFormRules: {
        account: [
          { required: true, message: '请输入用户名', trigger: 'change' }
          // { min: 11, max: 11, message: "请输入正确的学号", trigger: "blur" }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
          // { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" }
        ]
      },
      // 个人信息
      myInfo: {}
    }
  },
  methods: {
    ...mapMutations(['setMyInfo']),

    // 设置密码是否可见
    eyeClick() {
      this.eye = !this.eye
      if (this.eye) {
        this.pwdType = 'text'
      } else {
        this.pwdType = 'password'
      }
    },

    handleLoginIn() {
      this.$refs.loginFormRef.validate(async valid => {
        if (!valid) {
          return this.$message.error('请输出入正确的用户名/密码')
        }

        const res = await loginPost(
          this.loginForm.account,
          this.loginForm.password
        )
        console.log('登录结果', res)

        // 保存个人信息
        window.sessionStorage.setItem('myInfo', JSON.stringify(res.data))
        this.$store.commit('setMyInfo')

        if (res.status !== 200) {
          return this.$message.error(res.data.message)
        }
        this.$message.success('登录成功，欢迎！')
        this.$router.push('/home')
      })
    }
  }
}
</script>

<style scoped>
.login-box {
  display: flex;
  align-items: center;
  text-align: center;
}

.loginForm {
  width: 360px;
}

/* 头像 */
.avatar {
  width: 100px;
  height: 100px;
}

.title {
  font-size: 2.9rem;
  margin: 35px 0;
  color: #999;
}

.input-group {
  position: relative;
  display: grid;
  grid-template-columns: 7% 93%;
  margin: 25px 0;
  padding: 0 10px;
  /* border-bottom: 2px solid #d9d9d9; */
}

.input-group:nth-child(1) {
  margin-bottom: 4px;
}

/* 用户名/密码 图标 */
.icon {
  position: absolute;
  top: 7px;
  left: -35px;
  z-index: 10;
}

.icon img {
  width: 25px;
  height: 25px;
}

.input-group > div {
  position: relative;
  height: 45px;
}

input {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 15px;
  border: 0;
  outline: none;
  background: none;
  padding: 0.5rem 0.7rem;
  font-size: 3.2rem;
  color: #555;
  font-family: 'Roboto', sans-serif;
}

/* 密码--眼睛可见/不可见 */

.pwd {
  position: relative;
}

.eye {
  position: absolute;
  top: 12px;
  right: 10px;
  cursor: pointer;
}

.eye img {
  width: 25px;
  height: 25px;
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

/* 登录按钮 */
.btn {
  width: 70%;
  height: 50px;
  line-height: 50px;
  border-radius: 25px;
  margin: 1rem auto;
  font-size: 1.2rem;
  background-size: 200%;
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