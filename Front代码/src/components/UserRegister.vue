<template>
  <div class="register">
    <el-form ref="form" :model="form" :rules="rules" class="register-form">
      <h3 class="title">成为商城用户</h3>

      <el-form-item prop="name">
        <el-input v-model="form.name" type="text" autocomplete="off" placeholder="账号" >
          <template slot="prefix">
            <i class="el-icon-user-solid" style="color: rgb(29, 149, 240);"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input v-model="form.password" type="password" autocomplete="off" placeholder="密码">
          <template slot="prefix">
            <i class="el-icon-lock" style="color: rgb(29, 149, 240);"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="checkPass">
        <el-input v-model="form.checkPass" type="password" autocomplete="off" placeholder="确认密码">
          <template slot="prefix">
            <i class="el-icon-lock" style="color: rgb(29, 149, 240);"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="phone">
        <el-input v-model="form.phone" type="tel" autocomplete="off" placeholder="电话号码">
          <template slot="prefix">
            <i class="el-icon-mobile-phone" style="color: rgb(29, 149, 240);"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="email">
        <el-input v-model="form.email" type="email" autocomplete="off" placeholder="邮箱账户">
          <template slot="prefix">
            <i class="el-icon-message" style="color: rgb(29, 149, 240);"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="city">
        <el-input v-model="form.city" type="text" autocomplete="off" placeholder="收货地址">
          <template slot="prefix">
            <i class="el-icon-house" style="color: rgb(29, 149, 240);"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="card">
        <el-input v-model="form.card" type="tel" autocomplete="off" placeholder="银行卡号">
          <template slot="prefix">
            <i class="el-icon-bank-card" style="color: rgb(29, 149, 240);"></i>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="sex" text-align="left">
        <el-radio-group v-model="form.sex">
          <el-radio label="0">男</el-radio>
          <el-radio label="1">女</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="register('form')">提交</el-button>
        <el-button type="primary" @click="resetForm('form')">重置</el-button>
      </el-form-item>

    </el-form>

    <div class="el-login-footer">
      <span><router-link to="/sellRegister" style="color:#579DCA antiquewhite;">商户注册</router-link></span>
      <span>&nbsp;&nbsp;&nbsp;</span>
      <span><router-link to="/login" style="color:#579DCA antiquewhite;">登录界面</router-link></span>
    </div>

  </div>
</template>

<script>
export default {
  name: "UserRegister",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.form.checkPass !== '') {
          this.$refs.form.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    var validatePass3 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('验证码不能为空'));
      } else if (value !== this.num) {
        this.updateCanvas();
        callback(new Error('验证码输入有误'));
      } else {
        callback();
      }
    }
    return {
      form: {
        name: "",
        password: "",
        checkPass: "",
        phone: "",
        email: "",
        city: "",
        sex: "",
        card: "",
        imageValid: ""  //用户输入的验证码
      },
      num: "", //图片中的随机验证码
      rules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        phone: [
          { required: true, message: "请输入电话", trigger: 'blur' },
          { min: 11, max: 11, message: "电话号码格式不正确", trigger: 'blur' }
        ],
        imageValid: [
          { validator: validatePass3, trigger: 'blur' }
        ],
        email: [{ required: true, message: "请输入邮箱地址", trigger: 'blur' }],
        city: [{ required: true, message: "请输入城市地址", trigger: 'blur' }],
        card: [
          { required: true, message: "请输入银行卡号", trigger: 'blur' },
          { min: 16, max: 16, message: "银行卡号格式不正确", trigger: 'blur' }
        ],
      }
    };
  },

  methods: {
    //重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    //提交注册
    register(formName) {
      this.$refs[formName].validate((valid) => {   //表单验证
        if (!valid) {
          return;
        }
        else {
          this.axios.post("/user/register", this.form)
            .then(response => {
              if (response.data.code === 0) {
                this.$message({
                  message: "注册成功！",
                  type: "success"
                });
                this.resetForm('form');
                this.$router.replace("/login");
              } else {
                this.resetForm('form');
                this.$message("该用户名已被注册！");
              }
            })
            .catch(error => {
              alert(error.message)
            })
        }
      });
    },


    // 随机颜色函数
    getColor() {
      var r = Math.floor(Math.random() * 256);
      var g = Math.floor(Math.random() * 256);
      var b = Math.floor(Math.random() * 256);
      return "rgb(" + r + "," + g + "," + b + ")";
    },
    draw() {
      var context = this.$refs.canvas.getContext("2d");//舞台，getContext() 方法可返回一个对象，该对象提供了用于在画布上绘图的方法和属性。
      context.strokeRect(0, 0, 120, 40);//绘制矩形（无填充）
      var aCode = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"];
      // 绘制字母
      var arr = [] //定义一个数组用来接收产生的随机数
      for (var i = 0; i < 4; i++) {
        let x = 20 + i * 20;//每个字母之间间隔20
        let y = 20 + 10 * Math.random();//y轴方向位置为20-30随机
        var index = Math.floor(Math.random() * aCode.length);//随机索引值
        var txt = aCode[index];
        context.font = "bold 20px 微软雅黑";//设置或返回文本内容的当前字体属性
        context.fillStyle = this.getColor;//设置或返回用于填充绘画的颜色、渐变或模式，随机
        context.translate(x, y);//重新映射画布上的 (0,0) 位置，字母不可以旋转移动，所以移动容器
        var deg = 90 * Math.random() * Math.PI / 180;//0-90度随机旋转
        context.rotate(deg);//  旋转当前绘图
        context.fillText(txt, 0, 0);//在画布上绘制“被填充的”文本
        context.rotate(-deg);//将画布旋转回初始状态
        context.translate(-x, -y);//将画布移动回初始状态
        arr[i] = txt //接收产生的随机数
      }
      this.num = arr[0] + arr[1] + arr[2] + arr[3] //将产生的验证码放入num
      // 绘制干扰线条
      for (let i = 0; i < 8; i++) {
        context.beginPath();//起始一条路径，或重置当前路径
        context.moveTo(Math.random() * 120, Math.random() * 40);//把路径移动到画布中的随机点，不创建线条
        context.lineTo(Math.random() * 120, Math.random() * 40);//添加一个新点，然后在画布中创建从该点到最后指定点的线条
        context.strokeStyle = this.getColor();//随机线条颜色
        context.stroke();//  绘制已定义的路径
      }
      // 绘制干扰点，和上述步骤一样，此处用长度为1的线代替点
      for (let i = 0; i < 20; i++) {
        context.beginPath();
        let x = Math.random() * 120;
        let y = Math.random() * 40;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.strokeStyle = this.getColor();
        context.stroke();
      }
    },
    //更新验证码
    updateCanvas() {
      var context = this.$refs.canvas.getContext("2d");
      context.clearRect(0, 0, 120, 40);//在给定的矩形内清除指定的像素
      this.draw();
      console.log(this.num);
    }
  },
  mounted() {
    this.draw()
  }
};
</script>

<style>
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('~@/assets/login-background.png');
  background-size: cover !important;
  background-position: center;
  background-repeat: no-repeat;
  min-height: 100vh;

  height: 100%;

  width: 100%;

}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #fffcfc;
}

.register-form {
  opacity: 0.9;
  border-radius: 6px;
  background-image: linear-gradient(to top, #e9eaea 0%, #5ba2cb 100%);
  width: 400px;
  /* 减小表单宽度 */
  padding: 13px;
  /* 减少内边距 */
  box-shadow: 0 0 10px rgba(0, 0, 0.5, 0.5);

  .el-input {
    height: 30px;
    /* 减小输入框外部容器的高度 */
    margin-bottom: 0px;
    /* 减小输入框之间的间隔 */

    input {
      height: 28px;
      /* 减小输入框内部的高度 */
      border-radius: 20px;
      padding-left: 20px;
      /* 减小输入框内部的填充，以匹配新的高度 */
      width: 400px;
    }
  }

}

.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #a91e1e;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.imageVaild {
  position: relative;
}

canvas {
  position: absolute;
  top: 0;
  right: 0;
}

.toMerchantRegister {
  position: relative;
}

.toMerchantRegister a {
  position: absolute;
  top: -50px;
  right: 0;
}

a {
  text-decoration: none;
}

/* 定制button样式 */
.linkButton {
  display: inline-block;
  /* 使链接可以接受宽度和高度 */
  padding: 5px 10px;
  /* 内边距 */
  text-decoration: none;
  /* 移除下划线 */
  color: #fff;
  cursor: pointer;
  /* 鼠标悬停时显示手指形状 */
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #ffffff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>