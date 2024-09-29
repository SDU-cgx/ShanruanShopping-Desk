<template>
    <div class="userAudit">

        <img :src="imageSrc2" alt="商户管理" width="280px" height="45px" style="align-items: center;margin-top: 20px;" />
        <el-table :data="sellList" style="width: 80%">
            <el-table-column prop="id" label="ID" width="180">
            </el-table-column>
            <el-table-column label="姓名" width="180">
                <template slot-scope="scope">
                    <el-popover trigger="hover" placement="top">
                        <p>{{ scope.row.name }}</p>
                        <p>电话: {{ scope.row.phone }}</p>
                        <div slot="reference" class="name-wrapper">
                            <el-tag size="medium">{{ scope.row.name }}</el-tag>
                        </div>
                    </el-popover>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="150"
                :filters="[{ text: '待审核', value: 0 }, { text: '正常运营中', value: 1 }, { text: '商铺停业中', value: 2 }]"
                :filter-method="filterTag" filter-placement="bottom-end">
                <template slot-scope="scope">
                    <el-tag :type="getTap(scope.row)" disable-transitions>{{ scope.row.status | statusValueSell }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="权限管理">
                <template slot-scope="scope">
                    <el-button size="mini" v-if="scope.row.status === 0" type="primary"
                        @click="handleEdit(scope.row, 'sell')">确认审核通过</el-button>
                    <el-button size="mini" v-if="scope.row.status === 3" type="primary"
                        @click="handleEdit(scope.row, 'sell')">恢复运营</el-button>
                    <el-button size="mini" v-if="scope.row.status === 1" type="danger"
                        @click="handleDelete(scope.row, 'sell')">停止运营</el-button>
                </template>
            </el-table-column>
        </el-table>

        <img :src="imageSrc1" alt="用户管理" width="280px" height="45px" style="align-items: center;" />
        <el-table :data="userList" style="width: 80%">
            <el-table-column prop="id" label="ID" width="180">
            </el-table-column>
            <el-table-column label="姓名" width="180">
                <template slot-scope="scope">
                    <el-popover trigger="hover" placement="top">
                        <p>{{ scope.row.name }}</p>
                        <p>电话: {{ scope.row.phone }}</p>
                        <div slot="reference" class="name-wrapper">
                            <el-tag size="medium">{{ scope.row.name }}</el-tag>
                        </div>
                    </el-popover>
                </template>
            </el-table-column>

            <!-- filters是筛选，绑定的方法是filterTag，会接受改行数据和一个值，判断该行
             数据是否和值相同，相同返回true，这样数据就会显示，相当于v-if
             后面的状态显示就是把row的status传入，会在filters里面进行匹配，返回对应的值-->
            <el-table-column prop="status" label="状态" width="150"
                :filters="[{ text: '正常使用', value: 1 }, { text: '暂停使用', value: 2 }]" :filter-method="filterTag"
                filter-placement="bottom-end">
                <template slot-scope="scope">
                    <el-tag :type="getTap(scope.row)" disable-transitions>{{ scope.row.status | statusValue }}</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="权限管理">
                <template slot-scope="scope">

                    <el-button v-if="scope.row.status === 2" size="mini" type="primary"
                        @click="handleEditUser(scope.row, 'user')">恢复服务</el-button>

                    <el-button v-if="scope.row.status === 1" size="mini" type="danger"
                        @click="handleDeleteUser(scope.row, 'user')">停止服务</el-button>

                </template>
            </el-table-column>
        </el-table>
        <div style="margin-left: 10px;margin-top: 10px;">
            <el-button type="primary" icon="el-icon-caret-left" circle @click="selectleft"></el-button>
            <el-button style="border-radius: 20px; width: 80px;">第{{ pageNum }}页</el-button>
            <el-button type="primary" icon="el-icon-caret-right" circle @click="selectRight"></el-button>
        </div>

    </div>
</template>

<script>
export default {
    name: 'UserAudit',
    data() {
        return {
            userList: [],
            sellList: [],
            imageSrc1: require('@/assets/image/用户管理.png'),
            imageSrc2: require('@/assets/image/商户管理.png'),
            pageNum:1,
        }
    },
    created() {
        this.axios.get("/user", { params: { page: 1 } }).then(response => {
            if (response.data.code == 0) {
                this.userList = response.data.data.records;
            }
        });
        this.axios.get("/sell", { params: { page: 1 } }).then(response => {
            if (response.data.code == 0) {
                this.sellList = response.data.data.records;
            }
        });
    },
    methods: {
        selectleft() {
            if (this.pageNum === 1) {
                this.$message({
                    message: "已经是第一页了",
                });
            } else {
                this.pageNum--;
                this.axios.get("/user/" , { params: { page: this.pageNum } }).then(response => {
                    if (response.status == 200) {
                        this.userList = response.data.data.records;
                    }
                })
            }

        },
        selectRight() {

            this.pageNum++;
            this.axios.get("/user/" , { params: { page: this.pageNum } }).then(response => {
                if (response.data.code == 0) {
                    if (response.data.data.records.length > 0) {
                        this.userList = response.data.data.records;
                    } else {
                        this.pageNum--;
                        this.$message({
                            message: "已经到底了",
                        });
                    }

                }
            })

        },
        handleEdit(row, identity) {
            this.$confirm("确认审核通过？").then(() => {
                row.status = 1;
                this.axios.post(identity + '/update', row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({
                            type: 'success',
                            message: "操作成功！"
                        })
                    } else {
                        this.$message("操作失败！")
                    }
                });
            }).catch(() => { });
        },

        handleDelete(row, identity) {
            this.$confirm("确认审核失败？").then(() => {
                row.status = 3;
                this.axios.post(identity + '/update', row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({
                            type: 'success',
                            message: "操作成功！"
                        })
                    } else {
                        this.$message("操作失败！")
                    }
                });
            }).catch(() => { });
        },

        handleEditUser(row, identity) {
            this.$confirm("确定恢复用户服务？").then(() => {
                row.status = 1;
                this.axios.post(identity + '/update', row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({
                            type: 'success',
                            message: "操作成功！"
                        })
                    } else {
                        this.$message("操作失败！")
                    }
                });
            }).catch(() => { });
        },

        handleDeleteUser(row, identity) {
            this.$confirm("确认停止对该用户服务？").then(() => {
                row.status = 2;
                this.axios.post(identity + '/update', row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({
                            type: 'success',
                            message: "操作成功！"
                        })
                    } else {
                        this.$message("操作失败！")
                    }
                });
            }).catch(() => { });
        },


        filterTag(value, row) {
            return row.status === value;
        },

        getTap(row) {
            if (row.status == '0') {
                return ""
            } else if (row.status == '1') {
                return "success"
            } else
                return "danger"
        }
    },
    filters: {
        statusValue: function (value) {
            if (value == '1') {
                return "正常使用"
            } else
                return "暂停使用"
        },
        statusValueSell: function (value) {
            if (value == '0') {
                return "待审核"
            } else if (value == '1') {
                return "正常营业中"
            } else
                return "商铺停业中"
        },


    }
}
</script>

<style scoped>
h3 {

    margin: 20px 0;
}

.el-table {
    margin: 0 auto;
}
</style>