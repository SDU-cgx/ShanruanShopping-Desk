<template>
    <div class="sellHome" >
        <div class="container" >
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                <el-menu-item index="1"><router-link :to="'/shop/shopId/' + sell.id">
                        <i class="el-icon-location-outline"></i>
                        <span>店铺首页</span>
                    </router-link></el-menu-item>
                <el-menu-item index="2"><router-link to="/shop/order">店铺订单</router-link></el-menu-item>
                <el-menu-item index="3"><router-link to="/shop/info">店铺信息</router-link></el-menu-item>
                <el-submenu index="4">
                    <template slot="title">状态操作</template>
                    <el-menu-item index="4-1" v-if="!sell"><router-link to="/login">账号登录</router-link></el-menu-item>
                    <el-menu-item index="4-2" v-if="!sell"><router-link to="/sellRegister">注册店铺</router-link></el-menu-item>
                    <el-menu-item index="4-3" v-else @click="exit">退出登录</el-menu-item>
                </el-submenu>
            </el-menu>
        </div>

       <img :src="imageSrc" alt="商家广告"  v-if="$route.path.startsWith('/shop/shopId')" width="1380" height="400" style="align-items: center;"/> 
       <router-view />
       <img :src="imageSrc3" alt="商家广告3"  v-if="$route.path.startsWith('/shop/info')" width="1380" height="500" style="align-items: center;"/> 
        <img :src="imageSrc2" alt="商家广告2"  v-if="$route.path.startsWith('/shop/shopId')" width="1330" height="60" style="align-items: center;"/> 
    </div>
    
</template>
<script>
import { mapMutations, mapState } from 'vuex'
export default {
    name: "SellHome",
    computed: {
        ...mapState('user', {
            sell: 'sell'
        })
    },
    watch: {
        sell(val) {
            if (val == null) {
                this.$router.push("/login")
            }
        }
    },
    methods: {
        exit() {
            this.deleteSell();
            sessionStorage.removeItem("sell");
        },
        ...mapMutations('user', ['deleteSell'])  //将this.deleteUser映射为this.$store.state.commit('user/deleteUser')
    },
    data() {
        return {
            imageSrc: require('@/assets/image/商家广告.png'),
            imageSrc2: require('@/assets/image/商家广告2.png'),
            imageSrc3: require('@/assets/image/商家信息.png'),// 使用 require 加载图片// 使用 require 加载图片
        }
    }
}
</script>

<style scoped>

.sellHome{
    margin: 0 auto;
    text-align: center;
    min-height: 100vh;
   background: linear-gradient(to right, #FBFCFF, #F4F6FB);
    color: #360909;
    overflow: hidden;
}

.el-menu {
    background-color: #1f4e5f;

}
.el-menu-item {
    width: 130px;

}
a {
    text-decoration: none;
    /* 移除所有<a>标签的下划线 */
    color: inherit;
    /* 如果你想让链接颜色继承父元素的颜色 */
}
</style>