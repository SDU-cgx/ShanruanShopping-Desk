<template>
    <div class="container">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <el-menu-item index="1"><router-link to="/home">首页</router-link></el-menu-item>
            <el-menu-item index="2"><router-link to="/order">我的订单</router-link></el-menu-item>
            <el-menu-item index="3"><router-link to="/account">个人信息</router-link></el-menu-item>
            <el-menu-item index="4"><router-link to="/cart">购物车<span v-if="cartItemsCount != 0">{{ cartItemsCount
            }}</span></router-link></el-menu-item>

            <el-submenu index="5">
                <template slot="title">商品分类</template>

                <li v-for="(category, index) in reversedCategories" :key="category.id" class="li">
                    <el-menu-item :index="'5-' + (index + 1)">
                        <router-link :to="'/kind/' + category.id">{{ category.name }}</router-link>
                    </el-menu-item>
                </li>

            </el-submenu>
  
            <el-submenu index="7">
                <template slot="title">状态操作</template>
                <el-menu-item index="7-1" v-if="!user"><router-link to="/login"
                        class="subitem">登录界面</router-link></el-menu-item>
                <el-menu-item index="7-2" v-if="!user"><router-link to="/register">注册界面</router-link></el-menu-item>
                <el-menu-item index="7-3" v-else @click="exit">退出登录</el-menu-item>
            </el-submenu>

            <div class="avatar-section">
                 <span v-if="this.user" class="huaying"><b>{{ userInfo.name }},欢迎您！</b></span>
                 <span v-else class="huaying"><b>请登录！</b></span>
                <router-link to="/account">
                <el-avatar class="avatar-img" :size="100" 
                    :src="userInfo.avatar ? require('@/assets/image/' + userInfo.avatar) : require('@/assets/image/common.png')">
                </el-avatar>
                </router-link>
            </div>
        </el-menu>


    </div>
</template>

<script>
import { mapMutations, mapState, mapGetters } from 'vuex'
export default {
    data() {
        return {
            activeIndex: '1',
            activeIndex2: '1', categories: [],
            userInfo: {
                avatar: "common.png", // 替换为实际头像地址
                name:null
            },

        };
    },
    created() {
        this.axios.get('/kind')
            .then(response => {
                if (response.status === 200) {
                    this.categories = response.data.data;
                }
            }).catch(error => alert(error));

        this.axios.get('/user/' + this.user.id)
            .then(response => {
                if (response.status === 200) {
                    this.userInfo.avatar = response.data.data.avatar;
                    this.userInfo.name = response.data.data.name;
                }
            })
    },
    name: 'Navigation',
    computed: {
        //user模块带有命名空间
        ...mapState('user', {
            //将this.user映射为this.$store.state.user.user  因为定义变量名称相同，可简写成 ['user']
            user: 'user'
        }),
        ...mapGetters('cart', {
            //将this.user映射为this.$store.state.user.user  因为定义变量名称相同，可简写成 ['user']
            cardItemsCount: 'itemCount'
        }),
        reversedCategories() {
            // 返回categories数组的逆序副本  
            return [...this.categories].reverse();
        },
         isName() {
            return this.userInfo.name != null;
        }
    },

    methods: {
        exit() {
            this.deleteUser();
            sessionStorage.removeItem("user");
            this.userInfo.avatar= "common.png"
        },
        ...mapMutations('user', ['deleteUser'])  //将this.deleteUser映射为this.$store.state.commit('user/deleteUser')
    }
}
</script>


<style scoped>
.el-menu {
    background-color: #89d1ce;

}

.el-menu-item {
    width: 100px;

}
a {
    text-decoration: none;
    /* 移除所有<a>标签的下划线 */
    color: inherit;
    /* 如果你想让链接颜色继承父元素的颜色 */
}
.avatar-section {
    margin-bottom: 20px;
    display: flex !important;
    justify-content: flex-end; /* 水平居右 */
    margin-top: 5px;
    margin-right: 20px !important;
    margin-bottom: 5px;

}

.avatar-img {
    width: 50px !important;
    /* 头像框的宽度 */
    height: 50px !important;
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
.huaying{
    margin-top: 20px !important;
}
</style>