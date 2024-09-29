<template>
    <!-- Vue开发中，建议把与父组件紧密耦合的子组件用父组件为前缀命名，这里search搜索框的父组件是header -->
    <div class="headerSearch">
        <el-input type="search" v-model.trim="keyword" placeholder="山软购物，搜索你想要的商品" prefix-icon="el-icon-search" clearable>
        </el-input>
        <el-button type="primary" @click="search">搜索</el-button>
        <!--<button @click="search">搜索</button>-->
    </div>
</template>

<script>
export default {
    name: 'HeaderSearch',
    data() {
        return {
            keyword: ''
        };
    },
    methods: {
        //当查询关键字与当前路由对象中的查询参数wd值不同时，才调用$router.push方法
        search() {

            if (this.keyword != this.$route.query.wd) {
                // 页面通过path和query传递参数
                this.$router.push({
                    path: '/searchOrder',
                    query: {
                        keyword: this.keyword
                    }
                })
            }
        }
    }
}
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
}

.headerSearch /deep/ {
    float: left;
    position: relative;

}

.headerSearch /deep/ .el-input__inner {
    width: 520px;
    height: 45px;
    border-radius: 20px;
    clear: both;
}

.headerSearch button {
    position: absolute;
    right: 0px;
    top: 0;
    width: 90px;
    height: 45px;
    margin: 0;
    border: none;
    color: white;
    background-color: red;
    cursor: pointer;
    border-radius: 20px;

}
</style>