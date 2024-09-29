// import Vue from 'vue'
// import App from './App.vue'
// import router from './router/router'
// import store from './store/store'
// import ElementUI from 'element-ui';
// import 'element-ui/lib/theme-chalk/index.css';
// import VueAxios from 'vue-axios'  //安装插件
// import axios from 'axios';
// import msgBox from './plugin/vue-msgbox'
// import { currency, factPrice } from './utils/filters'

// Vue.config.productionTip = false
// Vue.use(ElementUI)
// Vue.use(msgBox)
// Vue.use(VueAxios, axios)
// axios.defaults.baseURL = "/api"
// Vue.prototype.HOST = "/api"
// Vue.filter('currency', currency)
// Vue.filter('factPrice', factPrice)

// new Vue({
//   router,
//   store,
//   render: h => h(App)
// }).$mount('#app')

import Vue from 'vue'
import App from './App.vue'
import router from './router/router'
import store from './store/store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueAxios from 'vue-axios'  //安装插件
import axios from 'axios';
import msgBox from './plugin/vue-msgbox'
import { currency, factPrice } from './utils/filters'
import * as echarts from 'echarts'

Vue.prototype.$echarts = echarts
Vue.config.productionTip = false

// 使用 ElementUI 和自定义插件
Vue.use(ElementUI)
Vue.use(msgBox)
Vue.use(VueAxios, axios)

// 配置 Axios 的默认基础 URL
axios.defaults.baseURL = "/api"
Vue.prototype.HOST = "/api"

// 添加全局过滤器
Vue.filter('currency', currency)
Vue.filter('factPrice', factPrice)

// 添加请求拦截器
axios.interceptors.request.use(
  config => {
    // 从 sessionStorage 中获取 token
    const token = sessionStorage.getItem('token');
    if (token) {
      // 在请求头中添加 token
      config.headers.authorization = token;
    }
    return config;
  },
  error => {
    // 处理请求错误
    return Promise.reject(error);
  }
);

// 创建 Vue 实例并挂载
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
