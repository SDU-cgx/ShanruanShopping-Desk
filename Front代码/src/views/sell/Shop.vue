<template>
    <div class="shop">
        <h1 style="color: #FF6600;font-size: 50px;"> <i class="el-icon-shopping-bag-1" style="color:#FF6600;font-size: 75px;"></i>{{ sell.name }}<el-button class="addBt" icon="el-icon-plus" size="mini" @click="addCommodity">添加商品</el-button>
        </h1>
        <Dialog @update="update" @dialogHide="dialogHide" :dialogVisible="dialogVisible" :addFlag="addFlag" :form="form" />

        <div class="others" v-if="others != null">
            <img :src="imageSrc1" loading="lazy"  alt="商品状态1" width="350px" height="50px" style="margin-left: 420px;"/>
            <CommodityList @update="update" @updateCommodity="updateCommodity" :list="others" :isNeedMask="true" />
            <Dialog @update="update" @dialogHide="dialogHide" :dialogVisible="dialogVisible" :addFlag="addFlag"
                :form="form" />
        </div>
        <div class="soldOut" v-if="soldOut != null">
            <img :src="imageSrc3" loading="lazy" alt="商品状态3" width="350px" height="50px" style="margin-left: 410px;"/>
            <CommodityList @update="update" @updateCommodity="updateCommodity" :list="soldOut" />
        </div>
        <div class="waitCheck" v-if="waitCheck != null">
                <img :src="imageSrc2" loading="lazy"  alt="商品状态2" width="350px" height="48px" style="margin-left: 410px;"/>
                <CommodityList @update="update" @updateCommodity="updateCommodity" :list="waitCheck" :isNeedMask="true" />
            </div>

    </div>
</template>
<script>
import { mapMutations, mapState } from 'vuex'
import CommodityList from '../../components/CommodityList.vue'
import Dialog from '../../components/Dialog.vue'
export default {
    name: "Shop",
    computed: {
        ...mapState(['waitCheck', 'soldOut', 'others']),  //待审核商品
        ...mapState('user', ['sell'])

    },
    components: { CommodityList, Dialog },
    created() {
        this.getWaitCheckList(); //获得商家待审核商品列表
        this.getSoldOutList();   //获得商家已下架商品列表
        this.getOthersList();  //获得正在卖的商品

    },
    data() {
        return {
            form: {},
            dialogVisible: false,
            addFlag: true,   //添加商品还是修改商品
            item: {},
            imageSrc1: require('@/assets/image/上线商品.png'),// 使用 require 加载图片
            imageSrc2: require('@/assets/image/审核中商品.png'),
            imageSrc3: require('@/assets/image/下架商品.png'),// 使用 require 加载图片
  


        }
    },
    methods: {
        addCommodity() {
            this.form = {
                shopId: "",
                name: "",
                kindId: "",
                price: "",
                discount: "",//折扣
                instruction: "",
            }
            this.addFlag = true;
            this.dialogVisible = true;
        },
        getWaitCheckList() {     //获得商家待审核商品列表
            this.axios.post('/commodity/searchOrder', { status: [0], shop_id: [this.$route.params.id] }).then(response => {
                if (response.data.code == 0) {
                    this.getWaitCheck(response.data.data.records);
                }
            }).catch(error => alert(error));
        },
        getSoldOutList() {     //获得商家已下架商品列表
            this.axios.post('/commodity/searchOrder', { status: [3], shop_id: [this.$route.params.id] }).then(response => {
                if (response.data.code == 0) {
                    this.getSoldOut(response.data.data.records);
                }
            }).catch(error => alert(error));
        },
        getOthersList() {     //获得商家所有商品列表
            this.axios.post('/commodity/searchOrderNoPage', { status: [1], shop_id: [this.$route.params.id] }).then(response => {
                if (response.data.code == 0) {
                    this.getOthers(response.data.data.records);
                }
            }).catch(error => alert(error));
        },


        dialogHide() {
            this.dialogVisible = false;
        },
        ...mapMutations(['getWaitCheck', 'getSoldOut', 'getOthers']),

        //子组件传值 更新商品
        updateCommodity(item) {
            this.item = item;
            this.form = Object.assign({}, item)
            this.addFlag = false;
            this.dialogVisible = true;
        },
        update(item) {
            this.item = item;
        },


    }
}
</script>
<style scoped>

.commodityList{
  position: relative;
  margin-top: 20px;
  background-color: #F6F8FD;
  width: 100%;
  overflow: hidden;     /* 清除浮动*/
}
.shop {
    position: relative;
    color: #846e6e;
    width: 1200px;
    text-align: left;
    margin: 0 auto;
}

.shop h3 {
    text-align: center;
    margin: 25px 0;
}

.shop .addBt {
    position: absolute;
    margin-left: 20px;
    top: 10px;
    color: #FF6600;
    border-radius: 20px;
    width: 150px;
    height: 50px;
    font-size: 20px;
    background-color: antiquewhite;
}
.shop .addBt:hover {
    background-color: #FF6600; /* Change background on hover */
    color: white; /* Change text color on hover */
}
</style>