<template>
  <div>
    <footer>
      <div class="box">
        <div class="wxbox">
          <ul>
            <li><img src="../assets/images/wxgzh.jpg"><span>微信公众号</span></li>
            <li><img src="../assets/images/wx.png"><span>我的微信</span></li>
          </ul>
        </div>
        <div class="endnav">
          <p><b>关于我们</b></p>
          <p>js代码(www.jsdaima.com)是IT资源下载与IT技能学习平台。js代码网收集整理了最新IT开发视频教程、网站模板、网站源码、js特效(插件)等，充分满足IT用户对不同类型资源的下载需求。js代码网拒绝滥竽充数，我们只提供精品资源！</p>
          <p>&copy; 2019 XX公司&nbsp;&nbsp;ICP备XXXXXXXX号&nbsp;&nbsp;技术支持：<a href="http://www.jsdaima.com/" title="js代码"
              target="_blank">js代码（www.jsdaima.com）</a></p>
        </div>
      </div>
      <div class="top" @click="backTop"></div>
    </footer>
  </div>
</template>
<script>
  export default{
    // vue的两个生命钩子，这里不多解释。
    // window对象，所有浏览器都支持window对象。它表示浏览器窗口，监听滚动事件
    mounted () {
      window.addEventListener('scroll', this.scrollToTop)
    },
    destroyed () {
      window.removeEventListener('scroll', this.scrollToTop)
    },


    methods: {
      // 点击图片回到顶部方法，加计时器是为了过渡顺滑
      backTop () {
          const that = this
          let timer = setInterval(() => {
            let ispeed = Math.floor(-that.scrollTop / 5)
            document.documentElement.scrollTop = document.body.scrollTop = that.scrollTop + ispeed
            if (that.scrollTop === 0) {
              clearInterval(timer)
            }
          }, 16)
      },

      // 为了计算距离顶部的高度，当高度大于60显示回顶部图标，小于60则隐藏
      scrollToTop () {
        const that = this
        let scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
        that.scrollTop = scrollTop
        if (that.scrollTop > 60) {
          that.btnFlag = true
        } else {
          that.btnFlag = false
        }
      }
    }
  }
</script>
