<template>
  <el-row v-loading="loading">
    <el-col :span="24">
      <div style="text-align: left;">
        <el-button type="text" icon="el-icon-back" @click="goBack" style="padding-bottom: 0px;">返回</el-button>
      </div>
    </el-col>
    <el-col :span="24">
      <div>
        <div><h2 style="text-align: center;">{{article.article_title}}</h2></div>
        <div style="width: 100%;margin-top: 5px;display: flex;justify-content: flex-end;align-items: center">
          <div style="display: inline; color: #20a0ff;margin-left: 50px;margin-right:20px;font-size: 12px;">
            <i class="fa fa-user" aria-hidden="true"></i> {{user_nickname}}
          </div>
          <span style="color: #20a0ff;margin-right:20px;font-size: 12px;"><i class="fa fa-eye"  aria-hidden="true"></i> {{article.article_pageView==null?0:article.article_pageView}}</span>
          <span style="color: #20a0ff;margin-right:20px;font-size: 12px;"><i class="fa fa-clock-o" aria-hidden="true"></i> {{article.article_editTime}}</span>
          <el-tag type="success" v-for="(item,index) in article.tags" :key="index" size="small"
                  style="margin-left: 8px">{{item.tag_name}}
          </el-tag>
          <span style="margin:0px 50px 0px 0px"></span>
        </div>
      </div>
    </el-col>
    <el-col>
      <div style="text-align: left" class="markdown-body" v-html="article.article_htmlContent">
      </div>
    </el-col>
  </el-row>
</template>

<script>
  export default{
    methods: {
      goBack(){
        this.$router.go(-1);
      }
    },
    mounted: function () {
      var data = this.$route.query.data;
			var falg=this.$route.query.falg;
			 var _this = this;
			this.loading = true;
			if(falg==true){
				 _this.article=data;
				 	_this.user_nickname=_this.article.user.user_nickname;
			}else{
				 this.$router.go(-1);
			}
			this.loading = false;
    },
    data(){
      return {
        article: {},
				user_nickname:'',
        loading: false,
      }
    }
  }
</script>
