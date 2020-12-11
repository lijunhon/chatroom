<template>
  <div class="login-wrap">
    <div class="ms-title">奥特曼聊天系统</div>
    <div class="ms-login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
        <div v-if="errorInfo">
          <span>{{errInfo}}</span>
        </div>
        <el-form-item prop="accountNumber">
          <el-input v-model="ruleForm.accountNumber" placeholder="账号" ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="密码" v-model="ruleForm.password" @keyup.enter.native="submitForm('ruleForm')"></el-input>
        </el-form-item>
        <el-form-item  prop="validate">
          <el-input v-model="ruleForm.validate" class="validate-code" placeholder="验证码" ></el-input>
          <div class="code" @click="refreshCode">
            <s-identify :identifyCode="identifyCode"></s-identify>
          </div>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        </div>
        <p style="font-size:14px;line-height:30px;color:#999;cursor: pointer;float:right;" @click="handleCommand()">注册</p>
      </el-form>
    </div>
  </div>
</template>

<script>
const axios = require('axios');
export default {
  name: 'Login',
  data() {
    return {
      identifyCodes: "1234567890",
      identifyCode: "",
      errorInfo : false,
      ruleForm: {
        accountNumber: '',
        password: '',
        validate: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        validate: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.identifyCode = "";
    this.makeCode(this.identifyCodes, 4);
  },
  methods: {
    submitForm(formName) {
      const self = this;
      self.$refs[formName].validate((valid) => {
        if (valid) {
          localStorage.setItem('ms_username',self.ruleForm.accountNumber);
          localStorage.setItem('ms_user',JSON.stringify(self.ruleForm));
          console.log(JSON.stringify(self.ruleForm));
          //axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
          axios({
            method:"post",
            url:"http://localhost:9090/api/user/login",
            data:{
              accountNumber: this.ruleForm.accountNumber,
              password: this.ruleForm.password
            },
          }).then((res)=>{
            if (res.code == 301) {
              self.errorInfo = true;
              self.errInfo = '该用户不存在';
            } else if (res.code == 300) {
              self.errorInfo = true;
              self.errInfo = '用户名或密码错误';
            } else if (res.code == 200) {
              self.$router.push('/home');
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleCommand() {
      this.$router.push('/register');
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[
          this.randomNum(0, this.identifyCodes.length)
          ];
      }
      console.log(this.identifyCode);
    }
  }
}
</script>

<style scoped>
.login-wrap{
  position: absolute;
  background-image: url("../assets/images/login.jpg");
  width:100%;
  height:100%;
}
.ms-title{
  position: absolute;
  top:50%;
  width:100%;
  margin-top: -230px;
  text-align: center;
  font-size:30px;
  color: #fff;
}
.ms-login{
  position: absolute;
  left:50%;
  top:50%;
  width:300px;
  height:240px;
  margin:-150px 0 0 -190px;
  padding:40px;
  border-radius: 5px;
  background: #fff;
}
.ms-login span {
  color: red;
}
.login-btn{
  text-align: center;
}
.login-btn button{
  width:100%;
  height:36px;
}
.code {
  width: 112px;
  height: 35px;
  border: 1px solid #ccc;
  float: right;
  border-radius: 2px;
}
.validate-code {
  width: 136px;
  float: left;
}
</style>
