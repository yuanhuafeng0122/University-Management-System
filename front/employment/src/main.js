import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import 'default-passive-events'

import Plugin from 'v-fit-columns';
Vue.use(Plugin);



import Print from 'vue-print-nb'
Vue.use(Print)


import axios from "axios";
import {Message} from "element-ui";
Vue.config.productionTip = false

import VueWechatTitle from 'vue-wechat-title'
Vue.use(VueWechatTitle)

Vue.prototype.$axios = axios

axios.defaults.baseURL= "http://10.60.87.68:8000"


import * as echarts from 'echarts'
//需要挂载到Vue原型上
Vue.prototype.$echarts = echarts


Vue.prototype.$message = function (msg){
  return Message({
    message:msg,
    duration:700
  })
}


// 分别对success、warning和error等样式进行设置
Vue.prototype.$message.success = function (msg) {
  return Message.success({
    message: msg,
    duration: 700
  })
}
Vue.prototype.$message.warning = function (msg) {
  return Message.warning({
    message: msg,
    duration: 700
  })
}
Vue.prototype.$message.error = function (msg) {
  return Message.error({
    message: msg,
    duration: 700
  })
}




// axios.interceptors.request.use(
//     config => {
//       config.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
//       return config
//     },
//     error => {
//       console.log(error)
//       Promise.reject(error)
//     }
// )



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
