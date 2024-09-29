<template>
        <div class="personal-info">
            <div class="avatar-section">
                <el-avatar class="avatar-img" :size="100"
                    :src="sellInfo.avatar ? require('@/assets/image/' + sellInfo.avatar) : require('@/assets/image/common.png')">
                </el-avatar>


                <input type="file" accept="image/*" ref="avatarFile" autocomplete="off" v-if="isEditing" />


                <el-button type="danger" disabled
                    style="width: 100px; height: 80px; text-align: center; margin-left: 20px;">等级：<span style="font-size: 30px; color: gold;">{{ sellInfo.level }}</span></el-button>
                <el-button type="danger" disabled
                    style="width: 200px; height: 40px; text-align: center; margin-left: 20px;margin-top: 40px;"><i
                        class="el-icon-shopping-card1"></i>店铺总收入:{{ sellInfo.wallet }} </el-button>

            </div>

            <div style="text-align: left;">
                <!-- 信息展示 -->
                <el-descriptions title="个人信息" :column="4">
                    <el-descriptions-item label="店铺名称">
                        <div style="margin-bottom: 16px;">
                            <label style="width: 100px; display: inline-block;">店铺名称:</label>
                            <el-input v-model="sellInfo.name" :disabled="!isEditing" style="width: 400px;"></el-input>
                        </div>
                  
                    </el-descriptions-item>
                    <el-descriptions-item label="发货地址">
                        <div style="margin-bottom: 16px;">
                            <label style="width: 100px; display: inline-block;">发货地址:</label>
                            <el-input v-model="sellInfo.address" :disabled="!isEditing" style="width: 400px;"></el-input>
                        </div>
                    </el-descriptions-item>

                    <el-descriptions-item label="电话">
                        <div style="margin-bottom: 16px;">
                            <label style="width: 100px; display: inline-block;">电话:</label>
                            <el-input v-model="sellInfo.phone" :disabled="!isEditing" style="width: 400px;"></el-input>
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
</template>

<script>
import { mapState } from "vuex";
export default {
    name: "sellInfo",
    data() {
        return {
            isEditing: false,
            sellInfo: {
                avatar: "common.png", // 替换为实际头像地址
                name: "",
                phone: "",
                wallet: 0,
                level: 0,
                id: 0,
                address:'',
            },
        };
    },

    created() {
        this.axios.get('/sell/' + this.sell.id)
            .then(response => {
                if (response.status === 200) {
                    if (this.sellInfo.wallet != null) {
                       this.sellInfo.wallet = response.data.data.wallet.toFixed(2); 
                    }
                    this.sellInfo.name = response.data.data.name;
                    this.sellInfo.phone = response.data.data.phone;
                    this.sellInfo.level = response.data.data.level;
                    this.sellInfo.avatar = response.data.data.avatar;
                    this.sellInfo.id = response.data.data.id;
                    this.sellInfo.address = response.data.data.address;


                }
            })

            
            

    },     computed: {
            ...mapState('user', {
                sell: 'sell'
            })
     
    },

    methods: {
        editInfo() {
            this.isEditing = true;
        },


        submitInfo() {
            var param = new FormData();
            param.append("sellInfo", JSON.stringify(this.sellInfo));

            if (this.$refs.avatarFile.files.length > 0) {
                param.append("avatar", this.$refs.avatarFile.files[0]);
                this.axios.post("/sell/change", param, {
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
                this.axios.post("/sell/changeNo", param, {
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
    background: linear-gradient(to right, #FBFCFF, #F4F6FB);
    margin-top: 20px;
    margin-left: 20px;
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
