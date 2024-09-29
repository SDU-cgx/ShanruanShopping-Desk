<template>
    <div class="order">
        <div style="margin-left: 910px;margin-bottom: 10px;">
                <el-button type="primary" icon="el-icon-caret-left" circle @click="selectleft"></el-button>
                <el-button  style="border-radius: 20px; width: 80px;">第{{ pageNum }}页</el-button>
                <el-button type="primary" icon="el-icon-caret-right" circle @click="selectRight"></el-button>
            </div>
        <el-table :data="orders" stripe style="width: 100%" >
            <el-table-column width="30"></el-table-column>
            <el-table-column prop="id" label="订单Id" width="80"></el-table-column>
            <el-table-column prop="commodityId" label="商品Id" width="80"></el-table-column>
            <el-table-column prop="commodityname" label="商品名称" width="220"></el-table-column>
            <el-table-column prop="username" label="收货人" width="80"></el-table-column>
            <el-table-column prop="address" label="收货地址" width="150"></el-table-column>
            <el-table-column prop="phone" label="联系电话" width="150"></el-table-column>
            <el-table-column prop="status" label="标签" width="120" :filters="[{ text: '待支付', value: 0 }, { text: '取消订单', value: 1 },
            { text: '待发货', value: 2 }, { text: '已收货', value: 4 },
            { text: '退货审核中', value: 5 }, { text: '已退货', value: 6 },
            { text: '拒绝退货', value: 7 }, { text: '等待评价', value: 9 },
            { text: '已完成', value: 8 }]" :filter-method="filterTag" filter-placement="bottom-end">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status === 8 ? 'success' : 'primary'" disable-transitions>{{ scope.row.status
                        |
                        formatStatus }}</el-tag>
                </template>
            </el-table-column>
             <el-table-column label="操作" width="150">
                            <template slot-scope="scope">
                                <el-button v-if="determineType(scope.row.status, '付款')" size="mini" type="danger"
                                    @click="handlePay(scope.row)">付款</el-button>
                                <el-button v-if="determineType(scope.row.status, '取消订单')" size="mini"
                                    @click="handleCancelPay(scope.row)">取消订单</el-button>
                                <el-button v-if="determineType(scope.row.status, '退货')" size="mini" type="danger"
                                    @click="handleReturnGoods(scope.row)">退货</el-button>
                                <el-button v-if="determineType(scope.row.status, '确认订单')" size="mini"
                                    @click="handleConfirm(scope.row)">确认订单</el-button>
                                <el-button v-if="determineType(scope.row.status, '取消退货')" size="mini"
                                    @click="handleCancelReturn(scope.row)">取消退货</el-button>
                                <el-button v-if="determineType(scope.row.status, '进行评价')" size="mini"
                                    @click="writeComment(scope.row)">进行评价</el-button>
                                <el-dialog title="写评论" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
                                    <span>好评度：</span><el-rate v-model="score" :colors="colors"></el-rate>
                                    <el-input type="textarea" :autosize="{ minRows: 3, maxRows: 5 }" placeholder="请输入内容"
                                        v-model="evaluation1">
                                    </el-input>
                                    <span slot="footer" class="dialog-footer">
                                        <el-button @click="dialogVisible = false">取 消</el-button>
                                        <el-button type="primary" @click="addSubmit()">确 定</el-button>
                                    </span>
                                </el-dialog>
                            </template>
                        </el-table-column>
            <el-table-column prop="creatTime" sortable label="订单开始时间" width="200"></el-table-column>
            <el-table-column prop="endTime" sortable label="订单结束时间" width="200"></el-table-column>
        </el-table>
    </div>
</template>
<script>
import { mapState } from 'vuex'
import { mapMutations } from 'vuex';
export default {
    name: "order",
    data() {
        return {
            orders: [],
            dialogVisible: false,
            evaluation1: "",
            score: null,
            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
            pageNum:1,//星级评定不同分数的颜色
        }
    },
    created() {
        this.axios.get("/order/user/" + this.user.id, { params: { page: 1 } }).then(response => {
            if (response.status == 200) {
                this.orders = response.data.data.records;
            }
        })
    },
    computed: {
        ...mapState('user', ['user']),
        ...mapState({ comments: 'evaluation' }),
    },
    methods: {
         selectleft() {
            if (this.pageNum === 1) {
                this.$message({
                    message: "已经是第一页了",
                });
            } else {
                this.pageNum--;
                this.axios.get("/order/user/" + this.user.id, { params: { page: this.pageNum } }).then(response => {
                    if (response.status == 200) {
                        this.orders = response.data.data.records;
                    }
                })
            }

        },
        selectRight() {

            this.pageNum++;
             this.axios.get("/order/user/" + this.user.id, { params: { page: this.pageNum } }).then(response => {
                if (response.data.code == 0) {
                    if (response.data.data.records.length > 0) {
                        this.orders = response.data.data.records;
                    } else {
                        this.pageNum--;
                        this.$message({
                            message: "已经到底了",
                        });
                    }

                }
            })

        },
        filterTag(value, row) {
            return row.status === value;
        },

        //付款
        handlePay(row) {
            this.$confirm("确认支付?").then(() => {
                this.axios.post("/order/pay", [row]).then(response => {
                    if (response.data.code == 0) {
                        this.$message({ type: 'success', message: "支付成功！" })
                        row.status = 2

                    } else {
                        this.$message(response.data.msg)
                    }
                }).catch(() => { error => alert(error) });
            }).catch(() => { })
        },

        //取消订单
        handleCancelPay(row) {
            this.$confirm("确认取消清单?").then(() => {
                let old = row.status;
                row.status = 1
                let ordEndTime = row.endTime
                row.endTime = this.formatDate(new Date(), 'YY-MM-DD hh:mm:ss')
                this.axios.post("/order/update", row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({ type: 'success', message: "取消订单成功！" })
                    } else {
                        this.$message(response.data.msg)
                        row.status = old
                        row.endTime = ordEndTime
                    }
                }).catch(() => { error => alert(error) });
            }).catch(() => { })
        },

        //退货
        handleReturnGoods(row) {
            this.$confirm("确认提交退货申请?").then(() => {
                let old = row.status;
                row.status = 5
                row.endTime = this.formatDate(new Date(), 'YY-MM-DD hh:mm:ss')
                this.axios.post("/order/update", row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({ type: 'success', message: "申请退货成功，请等待商家回应！" })
                    } else {
                        this.$message(response.data.msg)
                        row.status = old
                    }
                }).catch(() => { error => alert(error) });
            }).catch(() => { })
        },

        //取消退货
        handleCancelReturn(row) {
            this.$confirm("确认取消退货? 确认后订单不能再次退货！").then(() => {
                let oldStatus = row.status;
                row.status = 9
                let ordEndTime = row.endTime
                row.endTime = this.formatDate(new Date(), 'YY-MM-DD hh:mm:ss')
                this.axios.post("/order/update", row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({ type: 'success', message: "取消退货成功，该订单已完成！" })
                    } else {
                        this.$message(response.data.msg)
                        row.status = oldStatus
                        row.endTime = ordEndTime
                    }
                }).catch(() => { error => alert(error) });
            }).catch(() => { })
        },
        //确认订单
        handleConfirm(row) {
            this.$confirm("确认订单已完成？").then(() => {
                let old = row.status;
                row.status = 9
                let ordEndTime = row.endTime
                row.endTime = this.formatDate(new Date(), 'YY-MM-DD hh:mm:ss')
                this.axios.post("/order/update", row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({ type: 'success', message: "确认成功，该订单已完成！" })
                    } else {
                        this.$message(response.data.msg)
                        row.status = old
                        row.endTime = ordEndTime
                    }
                }).catch(() => { error => alert(error) });
            }).catch(() => { })
        },

        writeComment(row) {
            this.$confirm("是否对商品进行评价？").then(() => {
                let oldStatus = row.status;
                row.status = 8
                this.evaluation1 = '';
                this.score = 0;
                this.commodityId = row.commodityId
                this.dialogVisible = true
                let ordEndTime = row.endTime
                row.endTime = this.formatDate(new Date(), 'YY-MM-DD hh:mm:ss')
                this.axios.post("/order/update", row).then(response => {
                    if (response.data.code == 0) {
                        this.$message({ type: 'success', message: "订单完成！" })
                    } else {
                        this.$message(response.data.msg)
                        row.status = oldStatus
                        row.endTime = ordEndTime
                    }
                }).catch(() => { error => alert(error) });
            }).catch(() => { })
        },

        addSubmit() {
            var data = {};
            data.commodityId = this.commodityId;
            data.score = this.score;
            data.evaluation = this.evaluation1;
            data.userId = this.user.id;
            this.axios.post("/commodityEvaluation", data).then(response => {
                if (response.data.code == 0) {
                    this.addEvaluation(data);
                    this.dialogVisible = false;
                    this.$message({
                        type: 'success',
                        message: "评论成功！"
                    })
                }
            })
        },

        ...mapMutations(['addEvaluation', 'getEvaluation']),

        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(() => {
                    done();
                })
                .catch(() => { });
        },

        //判断订单处于哪一阶段，显示该阶段的操作
        determineType(status, operation) {
            if (operation == "付款") {
                if (status == 0) return true
            } else if (operation == "取消订单") {
                if (status == 0) return true
            } else if (operation == "退货") {
                if (status == 4 || status == 3) return true
            } else if (operation == "取消退货") {
                if (status == 5) return true
            } else if (operation == "进行评价") {
                if (status == 9 || status == 7) return true
            } else {
                if (status == 4 || status == 3) return true
            }
            return false
        },

        //格式化时间
        //      formatDate(new Date().getTime());//2017-05-12 10:05:44
        //      formatDate(new Date().getTime(),'YY年MM月DD日');//2017年05月12日
        //      formatDate(new Date().getTime(),'今天是YY/MM/DD hh:mm:ss');//今天是2017/05/12 10:07:45
        formatDate(time, format = 'YY-MM-DD hh:mm:ss') {
            var date = new Date(time);

            var year = date.getFullYear(),
                month = date.getMonth() + 1,//月份是从0开始的
                day = date.getDate(),
                hour = date.getHours(),
                min = date.getMinutes(),
                sec = date.getSeconds();
            var preArr = Array.apply(null, Array(10)).map(function (elem, index) {
                return '0' + index;
            });////开个长度为10的数组 格式为 00 01 02 03       
            var newTime = format.replace(/YY/g, year)
                .replace(/MM/g, preArr[month] || month)
                .replace(/DD/g, preArr[day] || day)
                .replace(/hh/g, preArr[hour] || hour)
                .replace(/mm/g, preArr[min] || min)
                .replace(/ss/g, preArr[sec] || sec);
            return newTime;
        }
    },
    filters: {
        formatStatus(val) {
            let value = "";
            switch (val) {
                case 0:
                    value = "待支付";
                    break;
                case 1:
                    value = "已取消";
                    break;
                case 2:
                    value = "待发货";
                    break;
                case 3:
                    value = "运输中";
                    break;
                case 4:
                    value = "已收货";
                    break;
                case 5:
                    value = "退货审核中";
                    break;
                case 6:
                    value = "已退货";
                    break;
                case 7:
                    value = "拒绝退货";
                    break;
                case 9:
                    value = "等待评价";
                    break;
                case 8:
                    value = "已完成";
                    break;
            }
            return value;
        }
    }
}
</script>

<style scoped>
.order {
    width: 1200px;
    margin: 0 auto;
    margin-top: 50px;
}
</style>