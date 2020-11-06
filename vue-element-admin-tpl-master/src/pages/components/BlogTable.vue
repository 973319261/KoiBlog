<style type="text/css">
	.blog_table_footer {
		display: flex;
		box-sizing: content-box;
		padding-top: 10px;
		padding-bottom: 0px;
		margin-bottom: 0px;
		justify-content: space-between;
	}
</style>
<template>
	<div v-if="isRouterAlive">
		<div style="display: flex;justify-content: flex-start;margin-bottom: 20px;">
			<el-input placeholder="通过标题搜索该分类下的博客..." prefix-icon="el-icon-search" v-model="keywords" style="width: 400px">
			</el-input>
			<el-button type="primary" icon="el-icon-search" style="margin-left: 3px" @click="searchClick">搜索
			</el-button>
		</div>
		<!--<div style="width: 100%;height: 1px;background-color: #20a0ff;margin-top: 8px;margin-bottom: 0px"></div>-->
		<el-table ref="multipleTable" :data="articles" tooltip-effect="dark" style="width: 100%;overflow-x: hidden; overflow-y: hidden;"
		 max-height="390" @selection-change="handleSelectionChange" v-loading="loading">
			<el-table-column type="selection" width="35" align="center" v-if="showEdit || showDelete || showRenew">
			</el-table-column>
			<el-table-column label="标题" width="400" align="center">
				<template slot-scope="scope"><span style="color: #409eff;cursor: pointer;" @click="itemClick(scope.row)">{{ scope.row.article_title}}</span>
				</template>
			</el-table-column>
			<el-table-column label="最近编辑时间" width="340" align="center">
				<template slot-scope="scope">{{ scope.row.article_editTime}}</template>
			</el-table-column>
			<el-table-column prop="user.user_nickname" label="作者" width="120" align="center">
			</el-table-column>
			<el-table-column prop="category.category_name" label="所属分类" width="120" align="center">
			</el-table-column>
			<el-table-column label="操作" align="center" v-if="showEdit || showDelete || showRenew">
				<template slot-scope="scope">
					<el-button size="mini" plain @click="handleEdit(scope.$index, scope.row)" v-if="showEdit">编辑
					</el-button>
					<el-button size="mini" type="warning" plain @click="handleRenew(scope.$index, scope.row)" v-if="showRenew">恢复
					</el-button>
					<el-button size="mini" type="danger" plain @click="handleDelete(scope.$index, scope.row)" v-if="showDelete">删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<div class="blog_table_footer">
			
			<el-button type="danger" plain size="mini" v-show="this.articles.length>0 && showDelete"
			 :disabled="this.selItems.length==0" @click="deleteMany">批量删除
			</el-button>
			<el-button type="warning" plain size="mini" style="position:relative;right: 38%;" v-show="this.articles.length>0 && showRenew"
			 :disabled="this.selItems.length==0" @click="renewMany">批量恢复
			</el-button>
			<el-pagination background :page-size="pageSize" layout="prev, pager, next" :total="totalCount" @current-change="currentChange"
			 v-show="this.articles.length>0">
			</el-pagination>
		</div>
	</div>
</template>

<script>
	import {
		putRequest
	} from '../../utils/api'
	import {
		getRequest
	} from '../../utils/api'
	//  import Vue from 'vue'
	//  var bus = new Vue()

	export default {
		inject: ['reload'],
		data() {
			return {
				articles: [],
				selItems: [],
				loading: false,
				currentPage: 1,
				totalCount: -1,
				pageSize: 10,
				keywords: '',
				dustbinData: [],
				isRouterAlive: true
			}
		},
		mounted: function() {
			var _this = this;
			this.loading = true;
			this.loadBlogs(1, this.pageSize);
			var _this = this;
		},
		methods: {
			//向父组件传递文章总数
			acticleCount() {
				this.$emit('childFn', this.totalCount, this.state);
			},
			searchClick() {
				this.loadBlogs(1, this.pageSize);
			},
			itemClick(row) {
				this.$router.push({
					path: '/blogDetail',
					query: {
						data: row,
						falg: true
					}
				})
			},
			deleteMany() {
				var selItems = this.selItems;
				for (var i = 0; i < selItems.length; i++) {
					this.dustbinData.push(selItems[i].article_id)
				}
				this.deleteToDustBin(selItems[0].article_state)
			},
			renewMany() {
				var selItems = this.selItems;
				for (var i = 0; i < selItems.length; i++) {
					this.dustbinData.push(selItems[i].article_id)
				}
				this.renewArticle();
			},
			//翻页
			currentChange(currentPage) {
				this.currentPage = currentPage;
				this.loading = true;
				this.loadBlogs(currentPage, this.pageSize);
			},
			loadBlogs(page, count) {
				var _this = this;
				var url = "/article/allBlog?state=" + this.state + "&page=" + page + "&count=" + count + "&keywords=" + this.keywords;
				getRequest(url).then(resp => {
					_this.loading = false;
					_this.articles = resp.articles;
					_this.totalCount = resp.totalCount;
					this.acticleCount();
				}).catch(resp => {
					//压根没见到服务器
					_this.loading = false;
					_this.$message({
						type: 'error',
						message: '数据加载失败!'
					});
				})
			},
			handleSelectionChange(val) {
				this.selItems = val;
			},
			handleEdit(index, row) {
				this.$router.push({
					path: '/blogmanage/newblog',
					query: {
						from: this.activeName,
						data: row
					}
				});
			},
			handleRenew(index, row) {
				this.dustbinData.push(row.article_id);
				this.renewArticle();
			},
			renewArticle() {
				var _this = this;
				this.$confirm('将该恢复该文章，是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.loading = true;
					var url = '/article/renewArticle';
					putRequest(url, {
						aids: _this.dustbinData
					}).then(resp => {
						if (resp.status == "success") {
							_this.$message({
								type: resp.status,
								message: resp.msg
							});
							this.reload();
							_this.loading = false;

						} else {
							_this.$message({
								type: 'error',
								message: '恢复失败!'
							});
						}
						_this.loading = false;
						_this.dustbinData = []
					});
				})
			},
			handleDelete(index, row) {
				this.dustbinData.push(row.article_id);
				this.deleteToDustBin(row.article_state);
			},
			deleteToDustBin(state) {
				var _this = this;
				this.$confirm(state != 2 ? '将该文件放入回收站，是否继续?' : '永久删除该文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					_this.loading = true;
					var url = '/article/dustbinOrDelete';
					putRequest(url, {
						aids: _this.dustbinData,
						state: state
					}).then(resp => {
						if (resp.status == "success") {
							_this.$message({
								type: resp.status,
								message: resp.msg
							});
							this.reload();
							_this.loading = false;

						} else {
							_this.$message({
								type: 'error',
								message: '删除失败!'
							});
						}
						_this.loading = false;
						_this.dustbinData = []
					});
				}).catch(() => {
					_this.$message({
						type: 'info',
						message: '已取消删除'
					});
					_this.dustbinData = []
				});
			}
		},
		props: ['state', 'showEdit', 'showRenew', 'showDelete', 'activeName']
	}
</script>
