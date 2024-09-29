<template>
  <el-carousel :interval="3000" type="card" style="width:1200px; height: 350px;">
    <el-carousel-item v-for="item in imgList" :key="item.id" >
      <router-link :to="/commodity/ + item.id"><img :src="item.img" /></router-link>
    </el-carousel-item>
  </el-carousel>
</template>

<script>
export default {
  name: 'HomeScrollPic',
  data() {
    return {
      imgList: [
        { img: require("../assets/封面/封面1.png"), id: 99 },
        { img: require("../assets/封面/封面2.png"), id: 50 },
        { img: require("../assets/封面/封面3.png"), id: 63 },
        { img: require("../assets/封面/封面4.png"), id: 72 },
        { img: require("../assets/封面/封面5.png"), id: 73 },
        
      ]
    }
  }
}
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #94a8c4;

}

.el-carousel__item:nth-child(2n+1) {
  background-color: #e1e5ea;

}

img {
  width: 100%;
  height: 100%;
}

</style>