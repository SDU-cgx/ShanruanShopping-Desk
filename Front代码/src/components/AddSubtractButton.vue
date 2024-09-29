<template>
  <div class="addSubtractButton">
    <!-- <div>
      <a class="add" href="javascript:;" @click="handleAdd">+</a>
      <a class="sub" @click="handleSubtract" 
        :class="{disabled: quantity === 0, actived: quantity > 0}" 
        href="javascript:;" >
        -
      </a>
    </div> -->
    <div>
      <button class="subtract-button" @click="handleAdd">+</button>
      <span class="quantity">{{ quantity }}</span>
      <button class="add-button" @click="handleSubtract">-</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      quantity: 0
    }
  },
  name: 'AddSubtractButton',
  methods: {
    handleAdd() {
      this.quantity++;
      this.$emit("updateQuantity", this.quantity);
    },
    handleSubtract() {
      this.quantity--;
      this.$emit("updateQuantity", this.quantity);
    }
  }
}
</script>
<style scoped>
.subtract-button:hover,
.add-button:hover {
  background-color: #e77c0a;
}

.add-subtract-container {
  display: flex;
  align-items: center;
  border: 1px solid #ccc;
  border-radius: 10px;
  /* 使整个容器具有圆角 */
  overflow: hidden;
  /* 确保子元素的圆角显示 */
  width: fit-content;
}

.subtract-button,
.add-button {
  background-color: #E33100;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  font-size: 16px;
  border-radius: 50%;
  /* 设置为圆形按钮 */
}

.quantity {

  display: inline-block;
  padding: 0 15px;
  font-size: 16px;
  text-align: center;
  min-width: 10px;
  /* 设置最小宽度以容纳数字 */
}

.addSubtractButton a.sub {
  margin-top: -3px;
  border-top: none;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none !important;
  margin: 0;
}

.disabled {
  pointer-events: none;
  color: gray;
  cursor: default;
}

.actived {
  color: black;
}</style>