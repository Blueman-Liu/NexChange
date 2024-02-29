<template setup>
  <div class="login-container">
    <el-form :model="loginForm" :rules="loginRules" ref="loginForm" label-width="0px" class="demo-ruleForm login-page">
      <h3 class="title">系统登录</h3>
      <el-form-item prop="account">
        <el-input type="text" v-model="loginForm.account" auto-complete="off" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button type="primary" style="width:100%;" @click="login" :loading="logining">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';
import { useRouter } from 'vue-router';
//import router from "@/router/index.js";
//const router = useRouter();

export default {
  data() {
    return {
      router: useRouter(),
      logining: false, // 设置登录按钮状态
      loginForm: {
        account: '',
        password: ''
      },
      loginRules: {
        account: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    };
  },
  methods: {
    login() {
      console.log("loging1")
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.logining = true; // 开启加载状态
          axios.post('http://localhost:8080/api/login/withPasswd', {
            account: this.loginForm.account,
            password: this.loginForm.password
          })
              .then(response => {
                console.log("loging2")
                if (response.data.code === '200') {
                  // this.$message({
                  //   type: 'success',
                  //   message: response.data.msg,
                  // });
                  console.log(response.data.msg)
                  // 登录成功后的跳转逻辑
                  this.router.push({ path: '/index' }); // 假设登录成功后跳转到首页
                } else {
                  // this.$message({
                  //   type: 'error',
                  //   message: response.data.msg
                  // });
                  console.log(response.data.msg)
                }
                this.logining = false; // 关闭加载状态
              })
              .catch(error => {
                console.error('登录失败:', error);
                this.logining = false; // 关闭加载状态
              });
        }
      });
    }
  }
};
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
}
.login-page {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
</style>