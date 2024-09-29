<template>
  <div class="allPage">
    <img :src="imageSrc" alt="商家广告" width="1360" style="align-items: center;" />
    <div class="commodity">
      <!-- <div class="img-preview">
        <img class="imgClass" :src="require('@/assets/image/' + commodity.photoOne)" v-if="commodity.photoOne" />
      </div> -->
      <el-carousel :interval="5000" trigger="click" class="product-carousel">
      <el-carousel-item v-for="(image, index) in images" :key="index">
        <img :src="require('@/assets/image/' + image.photo)" alt="Product Image" class="carousel-image"/>
      </el-carousel-item>
      </el-carousel>

      <div>
        <div class="commodityInfo">
          <h3>{{ commodity.name }}</h3>


          <p><span>商家：{{ commodity.shopName }}</span></p>
          <p><span>平均分：{{ commodity.score }}</span></p>
          <p>库存：{{ commodity.inventory }}</p>



          <p>
            <span class="factPrice">
              {{ commodity.price | factPrice(commodity.discount) | currency }}
            </span>
            <span v-if="commodity.discount != 1" class="discount">
              [{{ commodity.discount | formatDiscount }}]
            </span>
            <span v-if="commodity.discount != 1">[定价 <i class="price">{{ commodity.price | currency }}</i>]</span>
          </p>

        </div>

        <div class="addCart" v-if="user">
          <AddSubtractButton :quantity="quantity" @updateQuantity="handleUpdate" />
          <button class="addCartButton" @click="addCart(commodity)"><i class="el-icon-shopping-cart-2" style="color: rgb(236, 236, 7);"></i>加入购物车</button>
          <button class="addCartButton"><i class="el-icon-collection-tag" style="color: rgb(236, 236, 7);"></i>收藏</button>
          <button class="addCartButton"><i class="el-icon-sunny" style="color: rgb(236, 236, 7);"></i>喜欢</button>
        </div>

      </div>
      <commodityTabComponent :content="commodity.instruction" />
      <img :src="imageSrc4" alt="好货" width="1200px" style="align-items: center;" />
      <img :src="imageSrc1" alt="顺丰" width="1200px" style="align-items: center;" />
      <img :src="imageSrc2" alt="顺丰2" width="1200px" style="align-items: center;" />
      <img :src="imageSrc3" alt="百亿" width="1200px" style="align-items: center;" />
    </div>





  </div>
</template>

<script>
import AddSubtractButton from '@/components/AddSubtractButton'
import commodityTabComponent from '@/components/CommodityTabComponent'
import { mapActions, mapState } from 'vuex'
export default {
  name: 'commodity',
  data() {
    return {
      commodity: {},
      quantity: 0,
      imageSrc: require('@/assets/image/商品页面广告.png'),
      imageSrc1: require('@/assets/image/顺丰快递.png'),
      imageSrc2: require('@/assets/image/顺丰快递2.png'),
      imageSrc3: require('@/assets/image/百亿补贴.png'),
      imageSrc4: require('@/assets/image/工厂直销.png'),
      images:["default.jpg"]
    }
  },
  components: {
    AddSubtractButton,
    commodityTabComponent
  },
  computed: {
    ...mapState('user', ['user'])
  },

  created() {
    let url = this.$route.fullPath;
    if (url.indexOf("/shop/commodity") != -1) {
      url = "/commodity/" + this.$route.params.id;
    }
    this.axios.get(url)
      .then(response => {
        if (response.status == 200) {
          this.commodity = response.data.data;
        }
      }).catch(error => alert(error));

      this.axios.get("/commodityPhoto/" + this.$route.params.id )
      .then(response => {
        if (response.status == 200) {
          this.images = response.data.data;
        }
      }).catch(error => alert(error));
  },
  methods: {
    // 子组件AddSubtractButton的自定义事件updateQuantity的处理函数
    handleUpdate(value) {
      this.quantity = value;
    },
    factPrice(price, discount) {
      return price * discount;
    },
    //加入购物车
    addCart(commodity) {
      if (!this.user) {
        this.$router.push("/login");
        return;
      }
      let quantity = this.quantity;

      if (quantity === 0) {
        quantity = 1;
      }
      if (commodity.inventory < quantity) {
        this.$message("库存不够");
        return;
      }
      this.axios.post('/shoppingCart', { commodityId: commodity.id, quantity: quantity, userId: this.user.id }).then(response => {
        if (response.data.code == 0) {
          this.$router.push('/cart');
        } else {
          this.$message("加入购物车失败")
        }
      }).catch(error => alert(error))

    },
    ...mapActions('cart', [
      'addProductToCart'
    ])
  },

  filters: {
    // 格式化折扣数据
    formatDiscount(value) {
      if (value) {
        let strDigits = value.toString().substring(2);
        strDigits += "折";
        return strDigits;
      }
      else
        return value;
    },

  }
}
</script>
<style scoped>
.img {
  width: 80%;
}

.commodity {
  width: 1200px;
  margin: 0 auto;
  margin-top: 5px;
  font-size: 16px;
  text-align: left;
  color: #000000;
  background: linear-gradient(to bottom, #848DFF, #F3F1FF);
}

.imgClass {
  margin-left: 50px;
  float: left;
  width: 250px;
  height: 300px;
}

.product-carousel {
 margin-left: 50px;
  float: left;
  width: 250px;
  height: 300px;
}
.carousel-image {
   width: 250px;
  height: 300px;
  object-fit: cover; /* Ensures images cover the area without distortion */
}
.commodity .commodityInfo {
  margin-left: 20px;
  float: left;
  width: 800px;
  background: linear-gradient(to bottom, #858EFF, #9CA3FF);
  padding-left: 20px;
  padding-top: 5px;
}

.commodity .addCart {
  margin-left: 30px;
  float: left;
  margin-top: 10px;

}

.commodity .addCartButton {
  padding: 5px 10px 6px;
  color: #fff;
  border: none;
  border-bottom: solid 1px #222;
  border-radius: 5px;
  box-shadow: 0 1px 3px #999;
  text-shadow: 0 -1px 3px #444;
  cursor: pointer;
  background-color: #e33100;
  display: block;
  margin-top: 5px;

}

.addCartButton:hover {
  text-shadow: 0 1px 1px #444;
}

.commodity span {
  padding-right: 15px;
}

.commodity .price {
  color: gray;
  text-decoration: line-through;
}

.commodity .discount {
  color: red;
}

.commodity .factPrice {
  color: red;
  font-weight: bold;
}

.allPage {
  background: linear-gradient(to bottom, #848DFF, #F3F1FF);
}
</style>