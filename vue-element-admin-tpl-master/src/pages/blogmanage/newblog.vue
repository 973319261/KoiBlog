<template>
	<div class="page-body">
		<div class="page-header">
			<el-breadcrumb>
				<el-breadcrumb-item :to="{path: '/'}">首页</el-breadcrumb-item>
				<el-breadcrumb-item>编辑文章</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<el-container v-loading="loading" class="post-article">
			<el-header class="header">
				<el-form :inline="true" label-width="80px">
					<el-input v-model="article.article_title" placeholder="请输入标题..." style="width: 250px;"></el-input>
					<el-form-item label="博客分类">
						<el-select v-model="article.category_id" filterable placeholder="请选择博客分类" style="width: 150px;margin-left: 10px">
							<el-option v-for="item in category" :key="item.category_id" :label="item.category_name" :value="item.category_id">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="文章类型">
						<el-select v-model="article.article_type" style="width: 150px;margin-left: 10px">
							<el-option label="原创" value="0"></el-option>
							<el-option label="转载" value="1"></el-option>
							<el-option label="翻译" value="2"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="开启评论">
						<el-radio-group v-model="article.article_isComment" size="medium">
							<el-radio border label="0" >是</el-radio>
							<el-radio border label="1" >否</el-radio>
						</el-radio-group>
					</el-form-item>
				</el-form>
				<el-tag :key="tag" v-for="tag in article.dynamicTags" closable :disable-transitions="false" @close="handleClose(tag)"
				 style="margin:4px 10px;">
					{{tag}}
				</el-tag>
				<el-input class="input-new-tag" v-if="tagInputVisible" v-model="tagValue" ref="saveTagInput" size="small"
				 @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm">
				</el-input>
				<el-button v-else class="button-new-tag" type="primary" size="small" @click="showInput">+Tag</el-button>
			</el-header>
			<el-main class="main">
				<div id="editor">
					<mavon-editor  :ishljs="true"  :codeStyle="code_style" style="height: 100%;width: 100%;"  ref=md @imgAdd="imgAdd" @imgDel="imgDel" v-model="article.article_mdContent"></mavon-editor>
				</div>
				<div style="display: flex;align-items: center;margin-top: 15px;justify-content: flex-end">
					<el-button @click="cancelEdit" v-if="from!=undefined">放弃修改</el-button>
					<template v-if="from==undefined || from=='draft'">
						<el-button @click="saveBlog(1)">保存到草稿箱</el-button>
						<el-button type="primary" @click="saveBlog(0)">发表文章</el-button>
					</template>
					<template v-else="from==post">
						<el-button type="primary" @click="saveBlog(0)">保存修改</el-button>
					</template>
				</div>
			</el-main>
			
		</el-container>
	</div>

</template>
<script>
	import {
		postRequest
	} from '../../utils/api'
	import {
		putRequest
	} from '../../utils/api'
	import {
		deleteRequest
	} from '../../utils/api'
	import {
		getRequest
	} from '../../utils/api'
	import {
		uploadFileRequest
	} from '../../utils/api'
	// Local Registration
	import {
		mavonEditor
	} from 'mavon-editor'
	// 可以通过 mavonEditor.markdownIt 获取解析器markdown-it对象
	import 'mavon-editor/dist/css/index.css'
	import {
		isNotNullORBlank
	} from '../../utils/utils'

	export default {
		mounted: function() {
			this.getCategory();
			var from = this.$route.query.from;
			var data = this.$route.query.data;
			var _this = this;
			_this.from=from;
			if (from != null && from != '' && from != undefined) {
				//this.loading = true;
				_this.article=data;
				this.article.article_type=_this.article.article_type.toString();
				this.article.article_isComment=_this.article.article_isComment.toString();
				var tags = data.tags;
				_this.article.dynamicTags = []
				for (var i = 0; i < tags.length; i++) {
					_this.article.dynamicTags.push(tags[i].tag_name)
				}
				_this.loading = false;
			}
		},
		components: {
			mavonEditor
		},
		methods: {
			cancelEdit() {
				this.$router.go(-1)
			},
			saveBlog(state) {
				if (!(isNotNullORBlank(this.article.article_title, this.article.article_mdContent, this.article.category_id,this.article.article_type))) {
					this.$message({
						type: 'error',
						message: '数据不能为空!'
					});
					return;
				}
				var _this = this;
				_this.loading = true;
				_this.article.article_state=state;
				var num=3,text='';
				postRequest("/article/saveArticle", {
					article_id: _this.article.article_id,
					article_title: _this.article.article_title,
					article_mdContent: _this.article.article_mdContent,
					article_htmlContent: _this.$refs.md.d_render,
					category_id: _this.article.category_id,
					article_state: _this.article.article_state,
					article_isComment: _this.article.article_isComment,
					article_type:_this.article.article_type,
					dynamicTags: _this.article.dynamicTags
				}).then(resp => {
					_this.loading = false;
					if (resp.status == 'success') {
						_this.article.article_id = resp.msg;
						_this.$message({
							type: 'success',
							message: state != 0 ? '保存成功!' : '发布成功,正在跳到文章页面...'
						});
							setTimeout(function(){
								_this.$router.replace({
									path: '/blogmanage/allblog'
								});
							},1000)
						
					}else{
						_this.loading = false;
						_this.$message({
							type: 'error',
							message: state == 0 ? '保存草稿失败!' : '博客发布失败!'
						});
					}
				})
			},
			imgAdd(pos, $file) {
				var _this = this;
				// 第一步.将图片上传到服务器.
				var formdata = new FormData();
				formdata.append('image', $file);
				uploadFileRequest("/article/uploadImg", formdata).then(resp => {
					if (resp.status == 'success') {
						_this.$refs.md.$imglst2Url([
							[pos, resp.msg]
						])
					} else {
						_this.$message({
							type: resp.status,
							message: resp.msg
						});
					}
				});
			},
			imgDel(pos) {

			},
			getCategory() {
				let _this = this;
				getRequest("/category/findCategoryAll").then(resp => {
					_this.category = resp;
				});
			},
			handleClose(tag) {
				this.article.dynamicTags.splice(this.article.dynamicTags.indexOf(tag), 1);
			},
			showInput() {
				var length = this.article.dynamicTags.length;
				if (length > 2) {
					this.$message({
						type: 'error',
						message: '标签不能超过3个!'
					});
					return;
				}
				this.tagInputVisible = true;
				this.$nextTick(_ => {
					this.$refs.saveTagInput.$refs.input.focus();
				});
			},
			handleInputConfirm() {
				let tagValue = this.tagValue;
				if (this.article.dynamicTags.indexOf(tagValue) > -1) {
					this.$message({
						type: 'error',
						message: '标签已重复!'
					});
				} else {
					if (tagValue.length < 16) {
						if (tagValue) {
							this.article.dynamicTags.push(tagValue);
						}
					} else {
						this.$message({
							type: 'error',
							message: '长度在1-15个字符!'
						});
					}

				}
				this.tagValue = '';
				this.tagInputVisible = false;
			}
		},
		data() {
			return {
				category: [],
				tagInputVisible: false,
				tagValue: '',
				loading: false,
				from: '',
				article: {
					article_id: -1,
			        article_title: '',
					article_mdContent: '',
					article_htmlContent: '',
					category_id: '',
					article_state: -1,
					article_isComment: '0',
					article_type:'',
					dynamicTags: [],
				}
			}
		}
	}
</script>
<style>
	.post-article>.main>#editor {
		width: 100%;
		height: 600px;
		text-align: left;
	}

	.post-article>.header {
		background-color: #ececec;
		margin-top: 10px;
		padding-left: 5px;
		display: flex;
		justify-content: flex-start;
	}

	.post-article>.main {
		/*justify-content: flex-start;*/
		display: flex;
		flex-direction: column;
		padding-left: 5px;
		background-color: #ececec;
		padding-top: 0px;
	}

	.post-article>.header>.el-tag+.el-tag {
		margin: 10px;
	}

	.post-article>.header>.button-new-tag {
		margin: 4px 10px;
		height: 32px;
		line-height: 30px;
		padding-top: 0;
		padding-bottom: 0;
	}

	.post-article>.header>.input-new-tag {
		width: 90px;
		margin: 4px 10px;
		vertical-align: bottom;
	}

	.post-article {}
</style>
