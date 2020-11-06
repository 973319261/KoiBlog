<template>
  <div class="lbox" >
    <div class="whitebg lanmu"> <img src="../../assets/images/lm01.jpg">
      <h1>个人博客日记</h1>
      <p>个人博客日记，记录一些优秀个人站长是如何制作个人博客，建个人博客、以及经营个人网站的，本站还会推荐一些优秀的个人博客站长网站。</p>
    </div>
    <div class="tab_box whitebg" >
      <div class="tab_buttons">
        <ul>
          <li @click="current(-1,0)" :class="{ newscurrent:changeCurrent == -1}">全部</li>
          <li :class="{ newscurrent:changeCurrent == index}" @click="current(index,item.category_id)" v-for="(item,index) in category"
            :id="item.category_id" :key="item.category_id">{{item.category_name}}</li>
        </ul>
      </div>
      <div class="bloglist" >
        <ul>
          <transition-group name="listcss">
          <!--单图-->
          <li v-for="(item,index) in articles" :key='index'>
            <h3 class="blogtitle">
              <span class="type" v-if="item.article_type==0">原创</span>
              <span class="type" v-else-if="item.article_type==1">转载</span>
              <span class="type" v-else>翻译</span>
              <router-link  :to="{path:'/info',query:{id:item.article_id}}">{{item.article_title}}</router-link>
              </h3>
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
  </div>
  <!--bloglist end-->
</template>
<script>
import {
  getRequest
} from '../../utils/api'
export default {
  data () {
    return {
      changeCurrent: -1, // 更改当前分类
      changePageCurrent: 1, // 更改当前页
      category: [], // 文章分类
      articles: [], // 所有文章
      totalCount: 0, // 总数
      pageSize: 10, // 页面大小
      id: 0, // 当前文章id
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
  computed: {
    indexs: function () { // 计算分页数
      if (this.totalCount < this.pageSize) {
        return 1
      }
      var mod = this.totalCount % this.pageSize
      return mod == 0 ? this.totalCount / this.pageSize : parseInt(this.totalCount / this.pageSize) + 1
    }
  },
  mounted: function () {
    this.$emit('show', this.show) // 向父组件传值
  },
  created: function () {
    this.getCategory()
    this.current(-1, 0)
  },
  methods: {
    getCategory () { // 获取分类
      getRequest('/category/findCategoryAll').then(resp => {
        this.category = resp.data
      })
    },
    current (index, id) { // 切换分类
      this.changeCurrent = index // 当前类型
      this.changePageCurrent = 1 // 初始化
      this.id = id
      this.getArticles(1)
    },
    pageCurrent (index) { // 换页
      this.changePageCurrent = index // 当前页
      this.getArticles(index)
    },
    getArticles (index) { // 获取文章
      var url = '/article/allBlog?state=0&page=' + index + '&count=' + this.pageSize + '&category_id=' + this.id
      getRequest(url).then(resp => {
        this.totalCount = resp.data.totalCount
        this.articles = resp.data.articles
      })
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
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active 用于 2.1.8 以下版本 */ {
  transform: translateX(0px) translateY(0);
  opacity: 0;
}

.listcss-enter-active,.listcss-leave-active{
   transition:all 2s ease;
}
.listcss-leave-active {
  transition: all 0s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.listcss-enter,.listcss-leave-to{
    transform: translateX(0px) translateY(100px);
    opacity:0;
}
</style>
