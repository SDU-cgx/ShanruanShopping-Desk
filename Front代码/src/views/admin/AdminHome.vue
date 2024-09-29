<template>
    <div class="adminHome">
        <div class="sidebar">
              <div class="sidebar-header">
                    <img src="@/assets/adminLogo.png" alt="Logo" class="header-logo" />
                    <span class="loginSpan">
                        <h5>山软购物管理员：{{ admin.name }}</h5>
                    </span>
                </div>
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="vertical" background-color="#132743"
                text-color="#fff" active-text-color="#ffd04b">
                <el-menu-item index="1"><router-link to="/admin/audit/user">
                    <i class="el-icon-user" style="color: #DBE2EF;"></i>
                    用户商户管理</router-link></el-menu-item>
                <el-menu-item index="2"><router-link to="/admin/audit/commodity">
                    <i class="el-icon-goods" style="color: #DBE2EF;"></i>
                    商品审核管理</router-link></el-menu-item>
                <el-menu-item index="3"><router-link to="/admin/recharge">
                    <i class="el-icon-s-shop" style="color: #DBE2EF;"></i>
                    充值抽成管理</router-link></el-menu-item>
                <el-menu-item index="4"><router-link to="/admin/bargain">
                    <i class="el-icon-folder-opened" style="color: #DBE2EF;"></i>
                    交易订单记录</router-link></el-menu-item>
               <el-menu-item index="5"><router-link to="/admin/dataAnalysis">
                <i class="el-icon-pie-chart" style="color: #DBE2EF;"></i>
                平台数据分析</router-link></el-menu-item>
                <el-menu-item index="6"><a href="javascript:;" @click="exit">
                <i class="el-icon-setting" style="color: #DBE2EF;"></i>
                    退出登录</a></el-menu-item>
            </el-menu>
        </div>

        <div class="main-content">
            <router-view />
        </div>
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
export default {
    name: "AdminHome",
    data() {
        return {
            activeIndex: '1',
        };
    },
    computed: {
        ...mapState('user', ['admin'])
    },
    watch: {
        admin(val) {
            if (val == null) {
                this.$router.push("/login");
            }
        }
    },
    methods: {
        exit() {
            sessionStorage.removeItem("admin");
            this.deleteAdmin();
        },
        ...mapMutations('user', ['deleteAdmin'])
    }
}
</script>

<style scoped>
.adminHome {
    display: flex;
    background-color: #dbe2ef;
    color: #112d4e;
    min-height: 100vh; /* 确保容器至少覆盖整个视口高度 */
    height: auto; /* 自动适应内容的高度 */
}

.sidebar {
    display: flex;
    flex-direction: column;
    width: 200px;
    min-height: 100vh; /* 确保菜单栏至少覆盖整个视口高度 */
    height: auto; /* 自动适应内容的高度 */
    border-right: 1px solid #dcdfe6;
}

.main-content {
    flex: 1;
    padding: 20px;
    min-height: 100vh; /* 确保内容区域至少覆盖整个视口高度 */
}

.sidebar-header {
    display: flex;
    align-items: center;
    background-color: #132743;
    color: #fff;
    padding: 10px;
    border-bottom: 1px solid #dcdfe6;
}

.el-menu-demo {
    flex: 1;
    /* 菜单栏填满剩余空间 */
}

a {
    text-decoration: none;
    color: inherit;
}

.loginSpan {
    margin-top: 5px;

}

.header-logo {
    width: 30px; /* 设置图片的宽度 */
    height: auto; /* 自动调整高度 */
    margin-right: 10px; /* 图片和标题之间的间距 */
}
</style>
