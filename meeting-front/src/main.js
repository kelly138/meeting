// 导入模块
import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import {
  store
} from './store/store'
import {
  routes
} from './routes'
// 导入vue-pagination插件
// import Pagination from 'vue-pagination-2'

// 注册vue-pagination分页插件--组件
// Vue.component('pagination', Pagination)

// 导入分页需要的文件
import './assets/Vue_Pageination_Demo/zpageNav.js'
import './assets/Vue_Pageination_Demo/zpageNav.css'
// import './assets/Vue_Pageination_Demo/vue.js'

// 加入vuerouter
Vue.use(VueRouter)

// 让ajax携带cookie
axios.defaults.withCredentials = true

// 配置axios默认根路径
axios.defaults.baseURL = 'http://127.0.0.1:8888'

// 配置Vue原型 (在任何组件中都可以正常使用axios)
Vue.prototype.$axios = axios


const router = new VueRouter({
  routes,
  mode: 'history', //如果不配置的话，就会在url中加一个 # 
})

new Vue({
  el: '#app',
  render: h => h(App),
  router,
  store
})