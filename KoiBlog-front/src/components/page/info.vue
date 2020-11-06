<template>
  <transition name="slide-fade">
    <div class="lbox" v-show="isShow">
      <div class="content_box whitebg">
        <h2 class="htitle"><span class="con_nav">您现在的位置是：<a href="/">网站首页</a>&gt;<a href="/blog">技术博客</a></span>技术博客</h2>
        <h1 class="con_tilte">{{article.article_title}}</h1>
        <p class="bloginfo"><i class="avatar"><img :src="user_avatar"></i><span>{{user_nickname}}</span><span>{{article.article_publishDate}}</span><span>【<a
              href="/blog">{{category_name}}</a>】</span><span>{{article.article_pageView}} 个小伙伴已围观</span></p>

        <div class="con_text">
          <div class="markdown-body article-content" v-html="article.article_htmlContent"></div>
          <div class="tag">
            <span>所属分类：
              <router-link v-if="article.tags.length>0" class="type" :to="{path:'/tag',query:{tag_id:tag.tag_id,tag_name:tag.tag_name}}"
                v-for="tag in article.tags">{{tag.tag_name}}</router-link>
              <a v-if="article.tags.length==0" style="color: #999;">未分类</a>
            </span></div>
          <div class="gzhbox">
            <p style="margin: 10px 0;color: rgb(136, 136, 136)">关注下方微信公众号“Java精选”（w_z90110），回复关键字领取资料：如<code>Mysql</code>，<code>Hadoop</code>，<code>Dubbo</code>，<code>CAS</code>源码等等，免费领取视频教程、资料文档和项目源码。
            </p>
            <div class="share" style="margin-bottom: 20px;"><img src="https://www.yangqq.com/skin/jxhx/images/wxgzh.jpg">
             <div style="margin-left: 110px;">涵盖：程序人生、搞笑视频、算法与数据结构、黑客技术与网络安全、前端开发、<code>Java</code>、<code>Python</code>、<code>Redis</code>缓存、<code>Spring</code>源码、各大主流框架、Web开发、大数据技术、<code>Storm</code>、<code>Hadoop</code>、<code>MapReduce</code>、<code>Spark</code>、<code>ElasticSearch</code>、单点登录统一认证、分布式框架、集群、安卓开发、<code>iOS</code>开发、<code>C/C++</code>、<code>.NET</code>、<code>Linux</code>、<code>Mysql</code>、<code>Oracle</code>、<code>NoSQL</code>非关系型数据库、运维等。</div></div>
          </div>
          <div class="share_list shareBox">
            <p>
              <a class="ja_praise action action-like sharebtn abouts" @click="addLike" :title="like.msg" :class="{likes:like.isLike==false}"><i
                  class="fa fa-heart-o"></i> 赞 (<span>{{article.article_likeNumber}}</span>)</a>
              <a href="javascript:;" class="sharebtn pay-author" @click="dashang"><i class="fa fa-credit-card"></i> 打赏</a>
            </p>
            <div class="socialBox">
              <transition name="slide-fade">
                <div class="panel-reward" v-show="isDaShang">
                  <ul class="dsye">
                    <li class="alipay"><a href="http://uqseo.com/content/templates/FLY/img/alzf.png" target="_blank"
                        class="highslide swipebox" rel=""><img src="http://uqseo.com/content/templates/FLY/img/alzf.png"></a><b>支付宝扫一扫</b></li>
                    <li class="weixinpay"><a href="http://uqseo.com/content/templates/FLY/img/wxzf.png" target="_blank"
                        class="highslide swipebox" rel=""><img src="http://uqseo.com/content/templates/FLY/img/wxzf.png"></a><b>微信扫一扫</b></li>
                    <li class="txpay"><a href="http://uqseo.com/content/templates/FLY/img/txzf.png" target="_blank"
                        class="highslide swipebox" rel=""><img src="http://uqseo.com/content/templates/FLY/img/txzf.png"></a><b>QQ扫一扫</b></li>
                  </ul>
                </div>
              </transition>
            </div>
          </div>
          <div class="nextinfo">
            <p>上一篇：
              <router-link v-if="previousArticle!=''" :title="previousArticle.article_title" :to="{path:'/info',query:{id:previousArticle.article_id}}">{{previousArticle.article_title}}</router-link>
              <span v-else>没有了</span>
            </p>
            <p>下一篇：
              <router-link v-if="nextArticle!=''" :title="nextArticle.article_title" :to="{path:'/info',query:{id:nextArticle.article_id}}">{{nextArticle.article_title}}</router-link>
              <span v-else>没有了</span>
            </p>
          </div>
        </div>
      </div>
      <div class="whitebg">
        <h2 class="htitle">相关文章</h2>
        <ul class="otherlink">
          <li v-for="item in relationArticle">
            <router-link :title="item.article_title" :to="{path:'/info',query:{id:item.article_id}}">{{item.article_title}}</router-link>
          </li>
          <li v-if="length==0">暂无相关文章</li>
        </ul>
      </div>
      <div class="whitebg gbook">
        <h2 class="htitle">文章评论</h2>
      </div>
    </div>
  </transition>
</template>
<script>
import {
  getRequest,
  postRequest
} from '../../utils/api'
import Loading from '@/components/loading'
export default {
  components: {
    Loading
  },
  data () {
    return {
      id: 0, // 当前文章ID
      user_avatar:'',//用户头像
      user_nickname: '', // 用户昵称
      category_name: '', // 分类名称
      article: {}, // 当前文章
      articles: [], // 所有文章
      isDaShang: false, // 是否显示打赏
      previousArticle: {}, // 上一篇
      nextArticle: {}, // 下一篇
      relationArticle: [], // 相关文章
      length: 0, //
      isShow: false,
      like: {
        isLike: true,
        msg: '您尚未点赞过本文'
      }, // 是否可以点赞
      show: { // 显示组件
        showCard: false,
        showPaiHang: true,
        showCloud: true,
        showGuangGao: true,
        showWenZi: true,
        showTongJi: false
      }
    }
  },
  mounted: function () {
    this.$emit('show', this.show) // 向父组件传值
    this.id = this.$route.query.id // 接收通过路由传递的值
    this.getArticle() // 获取当前文章
    this.getPreArticle() // 上一篇
    this.getNextArticle() // 下一篇
    var isLikeKey = this.$cookies.isKey('like' + this.id)
    var isViewKey = this.$cookies.isKey('view' + this.id)
    if (isLikeKey) { // 判断是否已经已经点赞过
      this.likeState()
    }
    if (!isViewKey) {
      this.addView() // 第一次浏览增加访问量
    }
    this.isShow = true
  },
  methods: {
    /// 获取文章信息
    getArticle () {
      getRequest('/article/getArticleById?id=' + this.id).then(resp => {
        this.article = resp.data
        this.user_avatar = this.article.user.user_avatar
        this.user_nickname = this.article.user.user_nickname
        this.category_name = this.article.category.category_name
        this.getRelationArticle() // 查询相关文章
      })
    },
    // 获取上一篇文章
    getPreArticle () {
      getRequest('/article/getPreArticle?id=' + this.id).then(resp => {
        this.previousArticle = resp.data
      })
    },
    // 获取下一篇文章
    getNextArticle () {
      getRequest('/article/getNextArticle?id=' + this.id).then(resp => {
        this.nextArticle = resp.data
      })
    },
    // 获取相关文章
    getRelationArticle () {
      this.length = this.article.tags.length
      if (this.length > 0) { // 如果存在标签就查询相关文章
        var tags = []
        for (var i = 0; i < this.length; i++) {
          tags.push(this.article.tags[i].tag_name)
        }
        getRequest('/article/getRelationArticle?tags=' + tags + '&article_id=' + this.id).then(resp => {
          this.relationArticle = resp.data
        })
      }
    },
    addView () { // 增加访问量
      postRequest('/article/updatePageView?article_id=' + this.id).then(resp => {
        if (resp.data.status == 'success') {
          this.$cookies.set('view' + this.id, this.id, Infinity) // 如果已点赞就保存cookie(永不过期)
          this.article.article_pageView += 1
        }
      })
    },
    // 点赞
    addLike () {
      var isLikeKey = this.$cookies.isKey('like' + this.id)
      if (!isLikeKey) { // 判断是否已经已经点赞过
        postRequest('/article/updateLikeNumber?article_id=' + this.id).then(resp => {
          if (resp.data.status == 'success') {
            this.$cookies.set('like' + this.id, this.id, Infinity) // 如果已点赞就保存cookie（永不过期)
            this.article.article_likeNumber += 1
            this.likeState()
          } else {
            alert('点赞失败')
          }
        })
      } else {
        this.likeState()
      }
    },
    likeState () { // 根据是否点赞改变状态
      this.like.isLike = false // 禁止点赞
      this.like.msg = '您已赞过本文' // 禁止点赞
    },
    // 打赏
    dashang () {
      if (this.isDaShang) {
        this.isDaShang = false
      } else {
        this.isDaShang = true
      }
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
    transform: translateY(0);
    opacity: 0;
  }

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
