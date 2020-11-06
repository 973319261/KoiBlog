<template>
  <aside class="rbox">
    <div class="card" v-show="showCard">
      <h2>我的名片</h2>
      <p>网名：www.jsdaima.com | IT资源下载平台</p>
      <p>职业：PHP全栈工程师</p>
      <p>现居：广西-南宁</p>
      <p>网址：www.jsdaima.com</p>
      <ul class="linkmore">
        <li><a href="http://www.jsdaima.com/" target="_blank" class="iconfont icon-zhuye" title="网站地址"></a></li>
        <li><a href="http://www.jsdaima.com/webpage/" target="_blank" class="iconfont icon-youxiang" title="我的邮箱"></a></li>
        <li><a href="http://www.jsdaima.com/yuanma/" target="_blank" class="iconfont icon---" title="QQ联系我"></a></li>
        <li id="weixin"><a href="http://www.jsdaima.com/video/" target="_blank" class="iconfont icon-weixin" title="关注我的微信"></a><i><img
              src="images/wx.png"></i></li>
      </ul>
    </div>
    <div class="whitebg tongji" v-show="showTongJi">
      <h2 class="htitle">网站概况</h2>
      <ul>
        <li><i class="fa fa-file-text-o" aria-hidden="true"></i> 文章总数：{{siteProfiles.articleCount}} 篇</li>
        <li><i class="fa fa-comments-o" aria-hidden="true"></i> 留言数量：{{siteProfiles.commentCount}} 条</li>
        <li><i class="fa fa-folder-o" aria-hidden="true"></i> 分类数量：{{siteProfiles.categoryCount}} 个</li>
        <li><i class="fa fa-tags" aria-hidden="true"></i> 标签总数：{{siteProfiles.tagCount}} 条</li>
        <li><i class="fa fa-chain" aria-hidden="true"></i> 链接数量：{{siteProfiles.linkCount}} 条</li>
        <li><i class="fa fa-eye" aria-hidden="true"></i> 浏览总量：{{siteProfiles.browseCount}} 次</li>
        <li><i class="fa fa-edit" aria-hidden="true"></i> 最后更新：<span>{{siteProfiles.lastUpdateDate}}</span></li>
        <!-- <li> 更多文章内容，请关注微信公众号...</li>
       <img src="../assets/images/wxgzh.jpg" class="tongji_gzh"> -->
      </ul>
    </div>
    <div class="whitebg paihang" v-show="showPaiHang">
      <h2 class="htitle">热门文章</h2>
      <ul>
        <li v-for="item in hotArticles"><i></i>
          <router-link :to="{path:'/info',query:{id:item.article_id}}">{{item.article_title}}</router-link>
        </li>
      </ul>
    </div>
    <div class="whitebg cloud" v-show="showCloud">
      <h2 class="htitle">热门标签</h2>
      <ul>
        <router-link :to="{path:'/tag',query:{tag_id:tag.tag_id,tag_name:tag.tag_name}}" v-for="tag in tags">{{tag.tag_name}}</router-link>
      </ul>
    </div>
    <div class="ad whitebg imgscale" v-show="showGuangGao">
      <ul>
        <a href="info.html"><img src="../assets/images/ad.jpg"></a>
      </ul>
    </div>
    <div class="whitebg wenzi" v-show="showWenZi">
      <h2 class="htitle">最新文章</h2>
      <ul>
        <li v-for="item in newArticles">
          <router-link :to="{path:'/info',query:{id:item.article_id}}">{{item.article_title}}</router-link>
        </li>
      </ul>
    </div>
    <div class="ad whitebg imgscale" v-show="showGuangGao">
      <ul>
        <a href="info.html"><img src="../assets/images/ad02.jpg"></a>
      </ul>
    </div>
  </aside>
</template>
<script>
  import {
    getRequest
  } from '../utils/api'
  export default {
    data() {
      return {
        newArticles: [], //最新文章
        hotArticles: [], //热门文章
        siteProfiles: {}, //网站概况
        tags: [], //热门标签
      }
    },
    created:function(){
      this.getTags()
      this.getNewArticles()
      this.getHotArticles()
      this.getSiteProfiles()

    },
    methods: {
      getSiteProfiles() { //获取最新文章
        var url = '/composite/getSiteProfiles'
        getRequest(url).then(resp => {
          this.siteProfiles = resp.data
        })
      },
      getTags() { //获取标签云
        getRequest('/tag/findTagAll').then(resp => {
          this.tags = resp.data
        })
      },
      getHotArticles() { //获取热门文章
        var url = '/article/getHotArticles'
        getRequest(url).then(resp => {
          this.hotArticles = resp.data
        })
      },
      getNewArticles() { //获取最新文章
        var url = '/article/allBlog?state=0&page=1&count=10'
        getRequest(url).then(resp => {
          this.newArticles = resp.data.articles
        })
      }
    },
    props: ['showCard', 'showPaiHang', 'showCloud',
      'showGuangGao', 'showWenZi', 'showTongJi'
    ]
  }
</script>
<style>
  [v-cloak] {
    display: none !important;
  }
  .rbox{
    position: fixed;
    top: auto;
  }
</style>
