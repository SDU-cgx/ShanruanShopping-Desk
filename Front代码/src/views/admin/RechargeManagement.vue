<template>
    <div class="adminDashboard">
        <!-- 商铺抽成管理 -->
        <div class="sellRateManagement">
            <img :src="imageSrc1" alt="商铺抽成" width="320px" height="48px" style="align-items: center;" />
            <el-table :data="sellList" stripe style="width: 100%">
                <el-table-column width="30"></el-table-column>
                <el-table-column type="index" width="90"></el-table-column>
                <el-table-column prop="id" label="ID" width="110"></el-table-column>
                <el-table-column prop="name" label="商家名称" width="155"></el-table-column>
                <el-table-column prop="level" label="商家等级" width="130"></el-table-column>
                <el-table-column prop="wallet" label="总销售额" width="130"></el-table-column>
                <el-table-column label="操作" width="200">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="handleChangeLevel(scope.row)">修改</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog title="商家等级修改" :visible.sync="dialogVisible" width="400px" :before-close="handleClose">
                <el-select v-model="level" style="width: 100%;">
                    <el-option label="1" value="1"></el-option>
                    <el-option label="2" value="2"></el-option>
                    <el-option label="3" value="3"></el-option>
                    <el-option label="4" value="4"></el-option>
                    <el-option label="5" value="5"></el-option>
                </el-select>
                <span slot="footer" class="dialog-footer">
                    <el-button size="mini" @click="dialogVisible = false">取消</el-button>
                    <el-button size="mini" type="primary" @click="changeLevel">确定</el-button>
                </span>
            </el-dialog>
        </div>

        <!-- 用户钱包充值 -->
        <div class="recharge">
            <img :src="imageSrc2" alt="用户充值" width="280px" height="40px" style="align-items: left;" />
            <el-form ref="form" :inline="true" :model="form">
                <el-form-item prop="name">
                    <el-input v-model="form.name" placeholder="输入用户名"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="searchUser">查询</el-button>
                </el-form-item>
            </el-form>

            <div v-if="userInfo" class="info">
                <p><span>用户名：</span>{{ userInfo.name }}</p>
                <p><span>账户余额：</span>{{ userInfo.wallet }}</p>
                <el-form :model="infoForm" ref="info" :rules="rules" :inline="true">
                    <el-form-item prop="rechargeAmount">
                        <el-input type="number" v-model="rechargeAmount" placeholder="输入充值金额"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="recharge">充值</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "recharge",
    data() {
        var checkRechargeAmount = (rule, value, callback) => {
            if (this.rechargeAmount == "") {
                return callback(new Error('充值金额不能为空'));
            } else {
                callback();
            }
        };
        return {
            form: {  //要查询的用户
                name: "",
                identity: "user"
            },
            infoForm: {},  //无用 为了使用表单的验证
            currentIdentity: "",
            userInfo: null, //查询得到的用户信息
            rechargeAmount: "",   //充值金额
            rules: {
                rechargeAmount: [{ validator: checkRechargeAmount, trigger: 'blur' }]
            },
            sellList: [],
            dialogVisible: false,
            level: 1,
            sell: null,
            imageSrc1: require('@/assets/image/店铺抽成管理.png'),
            imageSrc2: require('@/assets/image/用户钱包充值.png'),
        }
    },
    created() {
        this.axios.get("sell", { params: { page: 1, status: 1 } }).then(response => {
            if (response.status == 200) {
                this.sellList = response.data.data.records;
            }
        })
    },
    methods: {
        searchUser() {
            this.axios.post("/" + this.form.identity, { "name": this.form.name }).then(response => {
                if (response.data.code == 0) {
                    console.log(response.data.data.length)
                    if (response.data.data.length == 1) {
                        this.userInfo = response.data.data[0];
                        this.currentIdentity = this.form.identity;
                        this.$refs["form"].resetFields();
                    } else {
                        this.$message("对不起，没有此用户！")
                    }
                } else {
                    this.$message("查询失败！")
                }
            });
        },

        //充值
        recharge() {
            this.$refs['info'].validate((valid) => {
                if (valid) {
                    if ((Number)(this.rechargeAmount) < 0) {
                        this.$message({
                            type: 'fail',
                            message: "充值金额为负数，不合法"
                        })
                    } else {
                        this.userInfo.wallet = (Number)(this.userInfo.wallet) + (Number)(this.rechargeAmount);
                        this.axios.post("/" + this.currentIdentity + "/update", this.userInfo).then(response => {
                            if (response.data.code == 0) {
                                this.rechargeAmount = "";
                                this.$message({
                                    type: 'success',
                                    message: "充值成功"
                                })
                            }
                        }).catch(error => { alert(error.message) })

                    }



                } else {
                    return false;
                }
            });
        },
        handleChangeLevel(row) {
            this.sell = row;
            this.dialogVisible = true;
            this.level = row.level;
        },
        handleClose() {
            this.$confirm('确认关闭？')
                .then(() => {
                    this.dialogVisible = false;
                })
                .catch(() => { });
        },
        changeLevel() {
            this.$confirm("确认修改？").then(() => {
                this.sell.level = this.level;
                this.axios.post("/sell/update", this.sell).then(response => {
                    if (response.data.code == 0) {
                        this.dialogVisible = false;
                        this.$message({ type: 'success', message: "修改成功！" });
                    }
                })
            })
        }
    },
    filters: {
        formatIdentity: function (value) {
            if (value == 'user') return "普通用户"
            else if (value == 'sell') return "商家"
        }
    }
}
</script>

<style scoped>
.adminDashboard {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
}

.sellRateManagement,
.recharge {
    width: 80%;
    margin-bottom: 40px;
}

.sellRateManagement h3,
.recharge h3 {
    margin-bottom: 20px;
    text-align: center;
}
.recharge {
    width: 80%;
    margin-bottom: 40px;
    text-align: left; /* 内容左对齐 */
}


.el-table {
    margin: 0 auto;
}

.info {
    margin-left: 0;
    margin-top: 20px;
}

.dialog-footer {
    text-align: right;
}
</style>
