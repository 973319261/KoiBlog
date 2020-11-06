import Vue from 'vue'
import App from './App'
import router from './router'
import VueCookies from 'vue-cookies'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
Vue.use(VueCookies)
import '@/assets/font-awesome-4.7.0/css/font-awesome.min.css' //图标
import '@/assets/css/base.css' //公共样式
import '@/assets/css/m.css'
import $ from 'jquery'
Vue.config.productionTip = false
//全局定义距离当前时间天数
Vue.filter('formatDate', function(valueTime) {
  if (valueTime) {
        // let newData = Date.parse(new Date() + '')
        // let diffTime = Math.abs(newData - valueTime)
        let diffTime = Math.abs(new Date().getTime() - new Date(valueTime).getTime())
        if (diffTime > 7 * 24 * 3600 * 1000) {
          let date = new Date(valueTime)
          // let y = date.getFullYear()
          let m = date.getMonth() + 1
          m = m < 10 ? ('0' + m) : m
          let d = date.getDate()
          d = d < 10 ? ('0' + d) : d
          let h = date.getHours()
          h = h < 10 ? ('0' + h) : h
          let minute = date.getMinutes()
          let second = date.getSeconds()
          minute = minute < 10 ? ('1' + minute) : minute
          second = second < 10 ? ('0' + second) : second
          return m + '-' + d + ' ' + h + ':' + minute
        } else if (diffTime < 7 * 24 * 3600 * 1000 && diffTime > 24 * 3600 * 1000) {
          // //注释("一周之内");
          // var time = newData - diffTime;
          let dayNum = Math.floor(diffTime / (24 * 60 * 60 * 1000))
          return dayNum + '天前'
        } else if (diffTime < 24 * 3600 * 1000 && diffTime > 3600 * 1000) {
          // //注释("一天之内");
          // var time = newData - diffTime;
          let dayNum = Math.floor(diffTime / (60 * 60 * 1000))
          return dayNum + '小时前'
        } else if (diffTime < 3600 * 1000 && diffTime > 0) {
          // //注释("一小时之内");
          // var time = newData - diffTime;
          let dayNum = Math.floor(diffTime / (60 * 1000))
          return dayNum + '分钟前'
        }
      }
})
//进度条配置
NProgress.configure({
    easing: 'ease',  // 动画方式
    speed: 500,  // 递增进度条的速度
    showSpinner: false, // 是否显示加载ico
    trickleSpeed: 200, // 自动递增间隔
    minimum: 0.3 // 初始化时的最小百分比
})
//当路由进入前
 router.beforeEach((to, from , next) => {
     // 每次切换页面时，调用进度条
    NProgress.start();
    // 这个一定要加，没有next()页面不会跳转的。这部分还不清楚的去翻一下官网就明白了
     next();
 });
//当路由进入后：关闭进度条
router.afterEach(() => {
    // 在即将进入新的页面组件前，关闭掉进度条
    NProgress.done()
})
new Vue({
  el: '#app',
  router,
  components: {
    App
  },
  template: '<App/>',

})
