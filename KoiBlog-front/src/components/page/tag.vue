<template>
  <transition name="slide-fade">
    <div class="lbox" v-show="isShow">
      <div class="whitebg tab_box">
        <h2 class="htitle"><span class="con_nav">您现在的位置是：<a href="/">首页</a>&nbsp;&gt;&nbsp;标签&nbsp;&gt;&nbsp;{{tag_name}}</span>{{tag_name}}</h2>
        <div class="bloglist">
          <ul>
            <!--单图-->
           <transition-group name="listcss">
              <li v-for="(item,index) in articles" :key='index'>
                <h3 class="blogtitle">
                  <span class="type" v-if="item.article_type==0">原创</span>
                  <span class="type" v-else-if="item.article_type==1">转载</span>
                  <span class="type" v-else>翻译</span>
                  <a href="/info" target="_blank">{{item.article_title}}</a></h3>
                <p class="blogtext">{{item.article_mdContent}}</p>
                <p class="bloginfo"><i class="avatar"><img :src="item.user.user_avatar"></i><span>{{item.user.user_nickname}}</span>
                  <span>【<a href="info.html">{{item.category.category_name}}</a>】</span>
                  <span><i class="fa fa-clock-o"></i> {{item.article_publishDate}}</span>
                  <span><i class="fa fa-eye"></i> {{item.article_pageView}}</span>
                  <span>标签分类：{{item[index]}}
                    <a v-if="item.tags.length>0" class="type" v-for="tag in item.tags" :id="tag.tag_id">{{tag.tag_name}}</a>
                    <a v-if="item.tags.length==0" style="color: #999;">无</a>
                  </span>
                </p>
                <router-link class="viewmore" :to="{path:'/info',query:{id:item.article_id}}">阅读全文</router-link>
              </li>
            </transition-group>
            <div class="pagelist" v-show="this.totalCount==0">☹ 找不到相关文章...</div>
          </ul>
        </div>
        <div class="pagelist" v-show="this.indexs>1">
          <span>共 {{totalCount}} 篇 </span>
          <a href="#" @click="pageCurrent(1)" v-show="this.changePageCurrent<=indexs&&this.changePageCurrent!=1"> 首页</a>
          <a href="#" @click="pageCurrent(changePageCurrent-1)" v-show="this.changePageCurrent>1">上一页</a>
          <a href="#" v-for="index in indexs" @click="pageCurrent(index)" :class="{ newPage:changePageCurrent == index}">{{index}}</a>
          <a href="#" @click="pageCurrent(changePageCurrent+1)" v-show="this.changePageCurrent<indexs">下一页</a>
          <a href="#" @click="pageCurrent(indexs)" v-show="this.changePageCurrent!=indexs">尾页</a></div>
      </div>
      <!--bloglist end-->
    </div>
  </transition>
</template>

<script>
  import {
    getRequest
  } from '../../utils/api'
  export default {
    data() {
      return {
        tag_id: 0, // 标签ID
        tag_name: '', // 标签名字
        changePageCurrent: 1, // 更改当前页
        articles: [], // 所有文章
        totalCount: 0, // 总数
        pageSize: 10, // 页面大小
        isShow:false,
        show: { // 显示组件
          showCard: false,
          showPaiHang: true,
          showCloud: true,
          showGuangGao: true,
          showWenZi: false,
          showTongJi: false,
        }
      }
    },
    computed: {
      indexs: function() { // 计算分页数
        if (this.totalCount < this.pageSize) {
          return 1
        }
        var mod = this.totalCount % this.pageSize
        return mod == 0 ? this.totalCount / this.pageSize : parseInt(this.totalCount / this.pageSize) + 1
      }
    },
    mounted: function() {
      this.$emit('show', this.show) // 向父组件传值
    },
    created: function() {
      this.tag_id = this.$route.query.tag_id // 接收通过路由传递的值
      this.tag_name = this.$route.query.tag_name // 接收通过路由传递的值
      this.getArticleByTagId(1)
    },
    methods: {
      pageCurrent(index) { // 换页
        this.isLoading = true
        this.changePageCurrent = index // 当前页
        this.getArticleByTagId(index)
      },
      getArticleByTagId(index) { // 通过标签ID查询相关文章
        var url = '/article/getArticleByTagId?page=' + index + '&count=' + this.pageSize + '&tag_id=' + this.tag_id
        getRequest(url).then(resp => {
          this.totalCount = resp.data.totalCount
          this.articles = resp.data.articles
          this.isShow=true
        })
      },
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
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active 用于 2.1.8 以下版本 */ {
  transform: translateX(0px) translateY(0);
  opacity: 0;
}

.listcss-enter-active,.listcss-leave-active{
   transition:all 2s ease;
}
.listcss-leave-active {
  transition: all 2s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.listcss-enter,.listcss-leave-to{
    transform: translateX(0px) translateY(100px);
    opacity:0;
}
</style>
