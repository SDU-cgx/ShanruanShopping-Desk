<template>
    <el-card style="background-color:#E4F9F5;border: none;box-shadow: none;">
        <div class="personal-info">
            <!-- 头像展示 -->
            <div class="avatar-section">
                <el-avatar class="avatar-img" :size="100"
                    :src="userInfo.avatar ? require('@/assets/image/' + userInfo.avatar) : require('@/assets/image/common.png')">
                </el-avatar>
                
                <input type="file" accept="image/*" ref="avatarFile" autocomplete="off" v-if="isEditing" />


                <el-button type="primary" disabled style="width: 80px; height: 80px; text-align: center; margin-left: 20px;" ><i :class=genderLabel style="font-size: 40px;"></i></el-button>
                <el-button type="primary" disabled style="width: 200px; height: 40px; text-align: center; margin-left: 20px;margin-top: 40px;"><i class="el-icon-bank-card"></i>:{{ userInfo.card }} </el-button>

            </div>

            <div style="text-align: left;">
                <!-- 信息展示 -->
                <el-descriptions title="个人信息" :column="4">
                    <el-descriptions-item label="姓名">
                        <div style="margin-bottom: 16px;">
                            <label style="width: 100px; display: inline-block;">姓名:</label>
                            <el-input v-model="userInfo.name" :disabled="!isEditing" style="width: 400px;"></el-input>
                        </div>
                    </el-descriptions-item>
                    <el-descriptions-item label="邮箱">
                        <div style="margin-bottom: 16px;">
                            <label style="width: 100px; display: inline-block;">邮箱:</label>
                            <el-input v-model="userInfo.email" :disabled="!isEditing" style="width: 400px;"></el-input>
                        </div>
                    </el-descriptions-item>
                    <el-descriptions-item label="收货地址">
                        <div style="margin-bottom: 16px;">
                            <label style="width: 100px; display: inline-block;">收货地址:</label>
                            <el-input v-model="userInfo.address" :disabled="!isEditing" style="width: 400px;"></el-input>
                        </div>
                    </el-descriptions-item>
                    <el-descriptions-item label="电话">
                        <div style="margin-bottom: 16px;">
                            <label style="width: 100px; display: inline-block;">电话:</label>
                            <el-input v-model="userInfo.phone" :disabled="!isEditing" style="width: 400px;"></el-input>
                        </div>
                    </el-descriptions-item>
                    <el-descriptions-item label="账户余额">
                        <div style="margin-bottom: 16px;">
                            <label style="width: 100px; display: inline-block;">账户余额:</label>
                            <el-input v-model="userInfo.wallet" disabled style="width: 200px;"></el-input>
                        </div>
                    </el-descriptions-item>
                    <el-descriptions-item label="消费总金额">
                        <div style="margin-bottom: 16px;">
                            <label style="width: 100px; display: inline-block;">消费总金额:</label>
                            <el-input v-model="userInfo.points" disabled style="width: 200px;"></el-input>
                        </div>
                    </el-descriptions-item>

                </el-descriptions>

                <!-- 按钮操作 -->
                <div class="actions">
                    <el-button type="primary" @click="editInfo" v-if="!isEditing">修改</el-button>
                    <el-button type="success" @click="submitInfo" v-if="isEditing">提交</el-button>
                    <el-button @click="cancelEdit" v-if="isEditing">取消</el-button>
                </div>
            </div>
        </div>
    </el-card>
</template>

<script>
import { mapState } from "vuex";
export default {
    data() {
        return {
            isEditing: false,
            userInfo: {
                avatar: "common.png", // 替换为实际头像地址
                name: "",
                email: "",
                address: "",
                phone: "",
                wallet: 0,
                points: 0,

                id: 0,
                sex: 0,
                card: 0,
                status: 0,
                password: "",


            },
        };
    },

    created() {
        this.axios.get('/user/' + this.user.id)
            .then(response => {
                if (response.status === 200) {
                    this.userInfo.wallet = response.data.data.wallet.toFixed(2);
                    this.userInfo.name = response.data.data.name;
                    this.userInfo.email = response.data.data.email;
                    this.userInfo.address = response.data.data.address;
                    this.userInfo.phone = response.data.data.phone;
                    this.userInfo.points = response.data.data.points.toFixed(2);

                    this.userInfo.avatar = response.data.data.avatar;
                    this.userInfo.id = response.data.data.id;
                    this.userInfo.sex = response.data.data.sex;
                    this.userInfo.card = response.data.data.card;
                    this.userInfo.status = response.data.data.status;
                    this.userInfo.password = response.data.data.password;
                }
            })
    }, computed: {
        ...mapState('user', {
            user: 'user'
        }),
         genderLabel() {
            return this.userInfo.sex === 1 ? 'el-icon-male' : 'el-icon-female';
        }
    },

    methods: {
        editInfo() {
            this.isEditing = true;
        },


        submitInfo() {
            var param = new FormData();
            param.append("userInfo", JSON.stringify(this.userInfo));

            if (this.$refs.avatarFile.files.length > 0) {
                param.append("avatar", this.$refs.avatarFile.files[0]);
                this.axios.post("/user/change", param, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                    .then(response => {
                        if (response.data.code === 0) {
                            this.isEditing = false;
                            this.$message({
                                message: "信息已修改",
                                type: "success",
                            });
                        } else {
                            this.isEditing = false;
                            this.$message("修改失败！");
                        }
                    })
                    .catch(error => {
                        alert(error.message);
                    });
            } else {
                this.axios.post("/user/changeNo", param, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                    .then(response => {
                        if (response.data.code === 0) {
                            this.isEditing = false;
                            this.$message({
                                message: "信息已修改",
                                type: "success",
                            });
                        } else {
                            this.isEditing = false;
                            this.$message("修改失败！");
                        }
                    })
                    .catch(error => {
                        alert(error.message);
                    });
            }
        },

        cancelEdit() {
            this.isEditing = false;
        },
    },
};
</script>

<style scoped>
.personal-info {
    display: flex;
    flex-direction: column;
    background-color: #E4F9F5;
}

.avatar-section {
    margin-bottom: 20px;
    display: flex !important;
    ;
}

.actions {
    margin-top: 20px;
}

.avatar-img {
    width: 100px;
    /* 头像框的宽度 */
    height: 100px;
    /* 头像框的高度 */
    border-radius: 50%;
    /* 使头像框变成圆形 */
    overflow: hidden;
    /* 隐藏超出头像框的部分 */
    display: flex;
    /* 使用 flex 布局来居中图片 */
    align-items: center;
    /* 垂直居中图片 */
    justify-content: center;
    /* 水平居中图片 */
}

.avatar-img img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    /* 保持图片的纵横比，填充整个头像框 */
    object-position: center;
    /* 将图片的中心对齐到头像框的中心 */
}
</style>

<!-- <template>
    <div class="account">
        <div class="wallet">
            <h3>我的钱包</h3>
            <h5>余额：</h5><span>{{ wallet.toFixed(2) }}</span>
        </div>
        <div class="integral">
            <h3>我的积分</h3>
            <h5>积分：</h5><span>{{ points }}</span>
        </div>
    </div>
</template>

<script>
import {mapState} from "vuex";
export default {
    name: "Account",
    data(){
        return{
            wallet:0,//余额
            points: 0,  //积分
        }
    },
    created(){
        this.axios.get('/user/'+this.user.id)
        .then(response => {
             if(response.status === 200){
                this.wallet = response.data.data.wallet;
                this.points = response.data.data.points;
            }
        })
    },
    computed:{
        ...mapState('user',{
            user: 'user'
        })
    }
}
</script>
<style scoped>
.account {
    width: 1200px;
    margin: 0 auto;
    text-align: left;
    height: 75vh;
    line-height: 30px;
}

.account .integral {
    margin-top: 30px;
}
</style> -->