<template>
    <div class="bargain">
        <img :src="imageSrc" alt="交易记录" width="240px" height="40px" style="align-items: center;" />
        <div style="margin-left: 910px;margin-bottom: 10px;">
            <el-button type="primary" icon="el-icon-caret-left" circle @click="selectleft"></el-button>
            <el-button  style="border-radius: 20px; width: 80px;">第{{ pageNum }}页</el-button>
            <el-button type="primary" icon="el-icon-caret-right" circle @click="selectRight"></el-button>
        </div>
        
        <el-table :data="successedOrders" stripe style="width: 100%"   :default-sort = "{ prop: 'id', order: 'descending' }">
            <el-table-column width="30"></el-table-column>
            <el-table-column type="index" width="50"></el-table-column>
            <el-table-column sortable prop="id" label="订单ID" width="120"></el-table-column>
            <el-table-column sortable prop="username" label="买家姓名" width="120"></el-table-column>
            <el-table-column prop="phone" label="买家电话" width="180"></el-table-column>
            <el-table-column prop="address" label="收货地址" width="180"></el-table-column>
            <el-table-column prop="name" label="商品名称" width="200"></el-table-column>
            <el-table-column type="index" width="50"></el-table-column>
            <el-table-column sortable prop="amount" label="总价" width="100"></el-table-column>
            <el-table-column sortable prop="creatTime" label="订单开始时间" width="200"></el-table-column>
            <el-table-column sortable prop="endTime" label="订单结束时间" width="200"></el-table-column>
            <el-table-column prop="commission" label="抽成金额" :formatter="formatterCommission" width="100"></el-table-column>
            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="deleteOrder(scope.row)">删除订单</el-button>
                </template>
            </el-table-column>

        </el-table>
    </div>
</template>

<script>
export default {
    name: 'Bargain',

    data() {
        return {
            successedOrders: [],
            imageSrc: require('@/assets/image/交易记录.png'),
            pageNum:1,
        }
    },

    created() {
        this.axios.get("/order", { params: { page: 1 } }).then(response => {
            if (response.data.code == 0) {
                this.successedOrders = response.data.data.records
            }
        })
    },
    methods: {
        selectleft() {
            if (this.pageNum === 1) {
                 this.$message({
                    message: "已经是第一页了",
                });
            } else {
                this.pageNum--;
                this.axios.get("/order", { params: { page: this.pageNum } }).then(response => {
                if (response.data.code == 0) {
                    this.successedOrders = response.data.data.records
                }
            })
            }
             
        },
        selectRight() {
           
                this.pageNum++;
                this.axios.get("/order", { params: { page: this.pageNum } }).then(response => {
                    if (response.data.code == 0) {
                        if (response.data.data.records.length>0) {
                            this.successedOrders = response.data.data.records     
                        } else {
                            this.pageNum--;
                            this.$message({
                            message: "已经到底了",
                        });
                        }
                        
                    }
                })
    
        },
        formatterCommission(row) {
            let commission = 0
            if (row.level == 1) {
                commission = row.amount * 0.001
            } else if (row.level == 2) {
                commission = row.amount * 0.002
            } else if (row.level == 3) {
                commission = row.amount * 0.005
            } else if (row.level == 4) {
                commission = row.amount * 0.0075
            } else
                commission = row.amount * 0.01
            return commission.toFixed(2)
        },
         deleteOrder(row) {
            this.$confirm("确认删除订单吗？").then(() => {
                this.axios.post('/order/delete',  { id: row.id },  // 发送 JSON 格式的数据
                    {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }).then(response => {
                    if (response.data.code == 0) {
                        this.$router.go(0);
                    }
                });
            }).catch(() => { });
        },
    }
}
</script>

<style>
.bargain {
    width: 1100px;
    margin: 0 auto;
    margin-top: 20px;
}

.bargain h3 {
    margin-bottom: 10px;
}
</style>