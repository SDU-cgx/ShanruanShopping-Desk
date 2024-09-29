<template>
  <div class="login">

    <el-form class="login-form">
      <h3 class="title">山软购物平台</h3>

      <el-form-item prop="username" v-if="identity === 'user'">
        <el-input v-model.trim="name" type="text" auto-complete="off" placeholder="用户名" class="input-class">
          <template slot="prefix">
            <i class="el-icon-user" style="color: rgb(29, 149, 240);"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="username" v-if="identity === 'sell'">
          <el-input v-model.trim="name" type="text" auto-complete="off" placeholder="店铺名称" class="input-class">
            <template slot="prefix">
              <i class="el-icon-shopping-cart-full" style="color: rgb(29, 149, 240);"></i>
            </template>
          </el-input>
        </el-form-item>

      <el-form-item prop="username" v-if="identity === 'admin'">
            <el-input v-model.trim="name" type="text" auto-complete="off" placeholder="管理员账户" class="input-class">
              <template slot="prefix">
                <i class="el-icon-setting" style="color: rgb(29, 149, 240);"></i>
              </template>
            </el-input>
          </el-form-item>

      <el-form-item prop="password">
        <el-input v-model.trim="password" type="password" auto-complete="off" placeholder="密码" @keyup.enter.native="login"
          class="input-class">
          <template slot="prefix">
              <i class="el-icon-lock" style="color: rgb(29, 149, 240);"></i>
            </template>
        </el-input>
      </el-form-item>

      <div class="identity">
        <input type="radio" id="user" name="identity" value="user" v-model="identity">
        <label for="user">用户</label>
        <input type="radio" id="merchant" name="identity" value="sell" v-model="identity">
        <label for="merchant">商家</label>
        <input type="radio" id="admin" name="identity" value="admin" v-model="identity">
        <label for="admin">管理员</label>
      </div>

      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="primary" style="width:100%;" @click.native.prevent="login">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>

        <div class="toRegister">
          <router-link to="/register">立即注册</router-link>
        </div>

      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
export default {
  name: "UserLogin",
  data() {
    return {
      name: '',
      password: '',
      identity: "user"
    };
  },
  computed: {
    ...mapState('user', {
      user: 'user',
      sell: 'sell',
      admin: 'admin'
    })
  },
  methods: {
    // login() {
    //   if (!this.checkForm())
    //     return;
    //   this.axios.post(this.identity + "/login", 
    //     { name: this.name, password: this.password })
    //     .then(response => {
    //       if (response.data.code == 0) {
    //         this.name = '';
    //         this.password = '';
    //         //普通用户登录
    //         if (this.identity == "user") {
    //           this.saveUser(response.data.data.user);
    //           sessionStorage.setItem("user", JSON.stringify(this.user));
    //           this.$message({
    //             message: "登录成功！",
    //             type: "success"
    //           });
    //           //如果存在查询参数
    //           if (this.$route.query.redirect) {
    //             let redirect = this.$route.query.redirect;
    //             //跳转至进入登录页前的路由
    //             this.$router.replace(redirect);   //跳转到指定url路径，但是history栈中不会有记录，点击返回会跳转到上上个页面
    //           } else {
    //             // 否则跳转至首页
    //             this.$router.replace('/home');
    //           }
    //         } else if (this.identity == "sell") {  //
    //           this.saveSell(response.data.data.sell);
    //           sessionStorage.setItem("sell", JSON.stringify(this.sell));
    //           this.$message({
    //             message: "登录成功！",
    //             type: "success"
    //           });
    //           //如果存在查询参数
    //           if (this.$route.query.redirect) {
    //             let redirect = this.$route.query.redirect;
    //             //跳转至进入登录页前的路由
    //             this.$router.replace(redirect);   //跳转到指定url路径，但是history栈中不会有记录，点击返回会跳转到上上个页面
    //           } else {
    //             // 否则跳转至首页
    //             this.$router.replace('/shop/shopId/' + this.sell.id);
    //           }
    //         } else {    //管理员登录
    //           this.saveAdmin(response.data.data.admin);
    //           sessionStorage.setItem("admin", JSON.stringify(this.admin));
    //           this.$message({
    //             message: "登录成功！",
    //             type: "success"
    //           });
    //           this.$router.replace('/admin');
    //         }
    //       } else {
    //         this.$message({
    //           message: response.data.msg,
    //           type: 'error'
    //         });
    //       }
    //     })
    //     .catch(error => {
    //       alert(error.message)
    //     })
    // }
    login() {
      if (!this.checkForm()) return;

      this.axios.post(this.identity + "/login", {
        name: this.name,
        password: this.password
      })
        .then(response => {
          if (response.data.code == 0) {
            const token = response.data.data.token; // 获取 token
            sessionStorage.setItem("token", token); // 保存 token

            this.name = '';
            this.password = '';

            if (this.identity == "user") {
              this.saveUser(response.data.data.user);
              sessionStorage.setItem("user", JSON.stringify(this.user));
              this.$message({
                message: "登录成功！",
                type: "success"
              });
              if (this.$route.query.redirect) {
                let redirect = this.$route.query.redirect;
                this.$router.replace(redirect);
              } else {
                this.$router.replace('/home');
              }
            } else if (this.identity == "sell") {
              this.saveSell(response.data.data.sell);
              sessionStorage.setItem("sell", JSON.stringify(this.sell));
              this.$message({
                message: "登录成功！",
                type: "success"
              });
              if (this.$route.query.redirect) {
                let redirect = this.$route.query.redirect;
                this.$router.replace(redirect);
              } else {
                this.$router.replace('/shop/shopId/' + this.sell.id);
              }
            } else {
              this.saveAdmin(response.data.data.admin);
              sessionStorage.setItem("admin", JSON.stringify(this.admin));
              this.$message({
                message: "登录成功！",
                type: "success"
              });
              this.$router.replace('/admin');
            }
          } else {
            this.$message({
              message: response.data.msg,
              type: 'error'
            });
          }
        })
        .catch(error => {
          alert(error.message);
        });
    },
    ...mapMutations('user', [
      'saveUser', 'saveSell', 'saveAdmin'
    ]),
    checkForm() {
      if (!this.name || !this.password) {
        this.$message("用户名和密码不能为空");
        return false;
      }
      return true;
    }
  }
};
</script>
<style scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-size: cover !important;
  background-image: url('~@/assets/login-background.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  min-height: 100vh;
  /* 还需要设置高度和宽度，除非你有其他方式来确定这个元素的大小 */
  height: 100vh;
  /* 视口高度的100%，使背景图片覆盖整个视口 */
  width: 100%;
  /* 元素宽度为父元素的100% */
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #f5f5f5;
}

.login-form {
  opacity: 0.9;
  border-radius: 6px;
  background-image: linear-gradient(to top, #e9eaea 0%, #5ba2cb 100%);
  width: 250px;
  padding: 25px 25px 5px 25px;
  margin-bottom: 50px;
  box-shadow: 0 0 10px rgba(0, 0, 0.5, 0.5);

  .el-input {
    height: 40px;

    input {
      height: 38px;
      border-radius: 20px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 38px;
}

.input-class::v-deep {
  .el-input-group__prepend {
    border-radius: 95px;
    border: 0;
    box-shadow: 0 0 0 0px;
  }

  .el-input__wrapper {
    border-radius: 95px;
    border: 0;
    box-shadow: 0 0 0 0px;
  }

  .el-input-group__append {
    border-radius: 95px;
    border: 0;
    box-shadow: 0 0 0 0px;
  }
}

/* .login .lable{
  position: relative;
}
.login .lable label{
  position: absolute;
  width: 40px;
  height: 40px;
  left: 0;
  top: 10px;
  border-right: solid 1px #ccc;
} */
/* i{
  margin-top: 12px;
}
.login .error{
  height: 30px;
  color: red;
  font-weight: bold;
  font-size: 0.5em;
} 
.identity label{
  margin-right: 10px;
}
.login .lable input{
  width: 300px;
  height: 20px;
  padding: 10px 0 10px 50px;
  font-size: 14px;
  margin: 10px 0px;
  outline: none;
  font-weight: 500;
  background: #eee;
  border: solid 1px #ccc;
}

.login .submit input{
    border: 1px solid #e85356;
    width: 352px;
    padding: 10px 0;
    margin: 15px 0px 30px 0px;
    background: #e4393c;
    color: #fff;
    cursor: pointer;
}

.login .toRegister{
  position: absolute;
  right: 0;
  bottom: 0;
}

a{
  text-decoration: none;
  color: #e4393c;
} */
</style>