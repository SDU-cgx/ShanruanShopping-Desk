<template>
    <div class="homePage">
        <Header />
        <home-scroll-pic />

        <img :src="imageSrc" alt="活动图片" width="300" height="200" />

        <Commodities />
        <img :src="imageSrc2" alt="活动图片" width="300" height="200" />
    </div>
</template>

<script>
import Header from '../components/Header'
import HomeScrollPic from '../components/HomeScrollPic.vue'
import Commodities from './Commodities.vue'

export default {
    name: 'homePage',
    components: {
        Header,
        Commodities,
        HomeScrollPic,
    },
     data() {
        return {
            imageSrc: require('@/assets/image/活动栏.png') ,// 使用 require 加载图片
            imageSrc2: require('@/assets/image/底边栏.png') // 使用 require 加载图片
        }
    }
}
</script>

<style>
.homePage {
    width: 1200px;
    margin: 0 auto;
    text-align: center;
    overflow: hidden;
}

.homePage>h3 {
    margin: 20px 0;
}
</style>