<template>
  <transition name="slide-fade">
    <div v-show="isShow">
      <app-header></app-header>
      <article>
        <loading v-if="isLoading"></loading>
        <router-view @show="getShow" :key="$route.fullPath"></router-view>
        <!-- :key="$route.fullPath" 解决地址参数改变数据不变问题-->
        <app-side class="side" :showCard="show.showCard" :showPaiHang="show.showPaiHang" :showCloud="show.showCloud" :showGuangGao="show.showGuangGao"
          :showWenZi="show.showWenZi" :showTongJi="show.showTongJi"></app-side>
      </article>
      <app-footer></app-footer>
    </div>
  </transition>
</template>
<script type="text/javascript">
  import AppHeader from '@/components/app-header'
  import AppFooter from '@/components/app-footer'
  import AppSide from '@/components/app-side'
  import Loading from '@/components/loading'
  export default {
    name: 'app-view',
    data() {
      return {
        isShow: false,
        isLoading: true,
        show: {}
      }
    },
    components: {
      AppHeader,
      AppFooter,
      AppSide,
      Loading
    },
    created: function() {
      // this.isLoading = true
    },
    methods: {
      getShow(show) {
        this.show = show
        this.isLoading = false
        this.isShow = true
      }
    }
  }
</script>
<style scoped>
  /* 可以设置不同的进入和离开动画 */
  /* 设置持续时间和动画函数 */
  .slide-fade-enter-active {
    transition: all 2s ease;
  }

  .slide-fade-leave-active {
    transition: all 2s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .slide-fade-enter,
  .slide-fade-leave-to

  /* .slide-fade-leave-active 用于 2.1.8 以下版本 */
    {
    transform: translateX(0px) translateY(0px);
    opacity: 0;
  }
  .side{
    position: relative;
    top: auto;
    float: right;
  }
</style>
