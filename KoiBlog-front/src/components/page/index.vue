<template>
  <div class="lbox">
    <div class="banbox">
      <div class="banner">
        <div id="banner" class="fader">
          <li class="slide"><a href="/" target="_blank"><img src="../../assets/images/1.jpg"></a></li>
          <li class="slide"><a href="/" target="_blank"><img src="../../assets/images/2.jpg"></a></li>
          <li class="slide"><a href="/" target="_blank"><img src="../../assets/images/3.jpg"></a></li>
          <li class="slide"><a href="/" target="_blank"><img src="../../assets/images/4.jpg"></a></li>
          <div class="fader_controls">
            <div class="page prev" data-target="prev"></div>
            <div class="page next" data-target="next"></div>
            <ul class="pager_list">
            </ul>
          </div>
        </div>
      </div>
    </div>
    <!--banbox end-->
    <!--headline begin-->
    <div class="headline">
      <ul>
        <li><a href="/" title="为什么说10月24日是程序员的节日？"><img src="../../assets/images/h1.jpg" alt="为什么说10月24日是程序员的节日？"><span>为什么说10月24日是程序员的节日？</span></a></li>
        <li><a href="/" title="个人网站做好了，百度不收录怎么办？来，看看他们怎么做的"><img src="../../assets/images/h2.jpg" alt="个人网站做好了，百度不收录怎么办？来，看看他们怎么做的。"><span>个人网站做好了，百度不收录怎么办？来，看看他们怎么做的。</span></a></li>
      </ul>
    </div>
    <!--headline end-->
        <div class="clearblank"></div>
        <!--tab_box end-->
        <div class="ad whitebg"> <img src="../../assets/images/longad.jpg"> </div>
    <div class="newsbox">
      <section v-for="item in articleToCategory">
        <div class="news">
          <h2 class="newstitle"><span><router-link :to="{path:'/blog'}" target="_blank">更多</router-link></span><b>{{item.category_name}}</b></h2>
          <ul>
            <li v-for="a in item.article">
              <router-link :to="{path:'/info',query:{id:a.article_id}}"><span> {{a.article_publishDate | formatDate}}
                </span>{{a.article_title}}</router-link>
            </li>
          </ul>
        </div>
      </section>
    </div>
    <div class="whitebg bloglist">
      <h2 class="htitle">最新博文</h2>
      <ul>
        <transition-group name="listcss">
          <!--单图-->
          <li v-for="(item,index) in articles" :key='index'>
            <h3 class="blogtitle">
              <span class="type" v-if="item.article_type==0">原创</span>
              <span class="type" v-else-if="item.article_type==1">转载</span>
              <span class="type" v-else>翻译</span>
               <router-link  :to="{path:'/info',query:{id:item.article_id}}">{{item.article_title}}</router-link></h3>
            <p class="blogtext">{{item.article_mdContent}}</p>
            <p class="bloginfo"><i class="avatar"><img :src="item.user.user_avatar"></i><span>{{item.user.user_nickname}}</span>
              <span>【<a href="info.html">{{item.category.category_name}}</a>】</span>
              <span><i class="fa fa-clock-o"></i> {{item.article_publishDate}}</span>
              <span><i class="fa fa-eye"></i> {{item.article_pageView}}</span>
              <span>标签分类：
                <a v-if="item.tags.length>0" class="type" v-for="tag in item.tags" :id="tag.tag_id">{{tag.tag_name}}</a>
                <a v-if="item.tags.length==0" style="color: #999;">未分类</a>
              </span>
            </p>
            <router-link class="viewmore" :to="{path:'/info',query:{id:item.article_id}}">阅读全文</router-link>
          </li>
          <span class="pagelist" v-if="this.totalCount==0">☹ 找不到相关文章...</span>
        </transition-group>
      </ul>
    </div>
    <!--bloglist end-->
  </div>
</template>
<script src="../../utils/date.js"></script>
<script>
  import {
    getRequest
  } from '../../utils/api'
  export default {
    data() {
      return {
        articleToCategory: [], //
        articles: [], //所有文章
        category: [],
        totalCount: 0, //总数
        show: {
          showCard: true,
          showPaiHang: true,
          showCloud: true,
          showGuangGao: true,
          showWenZi: true,
          showTongJi: true,
        }
      }
    },
    mounted: function() {
      this.$emit('show', this.show) // 向父组件传值
    },
    created: function() {
      this.getNewArticles()
      this.getCategory()
      this.getArticleToCategory()
    },
    methods: {
      getNewArticles() { //获取最新文章
        var url = '/article/allBlog?state=0&page=1&count=10'
        getRequest(url).then(resp => {
          this.totalCount = resp.data.totalCount
          this.articles = resp.data.articles
        })
      },
      getCategory() {
        getRequest('/category/findCategoryAll').then(resp => {
          this.category = resp.data
        })
      },
      getArticleToCategory() {
        getRequest('/category/findArticleToCategory').then(resp => {
          this.articleToCategory = resp.data
        })
      }
    }
  }
</script>
<style scoped>
  .listcss-enter-active,
  .listcss-leave-active {
    transition: all 2s ease;
  }

  .listcss-leave-active {
    transition: all 2s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .listcss-enter,
  .listcss-leave-to {
    transform: translateX(0px) translateY(100px);
    opacity: 0;
  }
</style>
