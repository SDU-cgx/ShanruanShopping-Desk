<template>
    <div class="commodityAudit">
        <img :src="imageSrc1" alt="待审核商品" width="240px" height="40px" style="align-items: center;" />
        <CommodityList :list="list" :isNeedMask="true" />
        <img :src="imageSrc2" alt="审核失败商品" width="265px" height="40px" style="align-items: center;margin-top: 20px;" />
        <CommodityList :list="list3" :isNeedMask="true" />
        <img :src="imageSrc3" alt="下架商品" width="240px" height="45px" style="align-items: center;margin-top: 20px;" />
        <CommodityList :list="list4" :isNeedMask="true" />
        <img :src="imageSrc4" alt="上线商品" width="260px" height="42px" style="align-items: center;margin-top: 20px;" />
        <CommodityList :list="list2" :isNeedMask="true" />
    </div>
</template>
<script>
import CommodityList from '../../components/CommodityList.vue';
export default {
    components: { CommodityList },
    name: 'CommodityAudit',
    data() {
        return {
            list: [],
            list2: [],
            list3: [],
            list4: [],
            imageSrc1: require('@/assets/image/待审核商品.png'),
            imageSrc2: require('@/assets/image/审核失败商品.png'),
            imageSrc3: require('@/assets/image/下架商品2.png'),
            imageSrc4: require('@/assets/image/上线商品2.png'),
        }
    },
    created() {
        this.axios.post("/commodity/audit", { "status": 0 }).then(response => {
            if (response.status == 200) {
                this.list = response.data.data;
            }
        }).catch(() => { });
        this.axios.post("/commodity/audit", { "status": 1 }).then(response => {
            if (response.status == 200) {
                this.list2 = response.data.data;
            }
        }).catch(() => { });
        this.axios.post("/commodity/audit", { "status": 2 }).then(response => {
            if (response.status == 200) {
                this.list3 = response.data.data;
            }
        }).catch(() => { });
        this.axios.post("/commodity/audit", { "status": 3 }).then(response => {
            if (response.status == 200) {
                this.list4 = response.data.data;
            }
        }).catch(() => { });
    }
}
</script>