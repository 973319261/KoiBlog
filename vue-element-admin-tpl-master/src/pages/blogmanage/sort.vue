<template>
	<div class="page-body">
		<div class="page-header">
			<el-breadcrumb>
				<el-breadcrumb-item :to="{path: '/'}">首页</el-breadcrumb-item>
				<el-breadcrumb-item>博客分类</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<el-form :inline="true" class="demo-form-inline table" >
			<el-form-item label="分类名称">
				<el-input placeholder="请输入博客分类名称" v-model="keywords" ></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="selectSort">查 询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button type="success" @click="addSort">添 加</el-button>
			</el-form-item>
		</el-form>
		<div class="tableBox">
			<el-table :data="tableData" style="width: 100%" v-loading="loading">
				<el-table-column label="分类名称" width="180" align="center">
					<template slot-scope="scope">
						<el-tag size="medium">{{ scope.row.category_name }}</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="日期" width="280" align="center">
					<template slot-scope="scope">
						<i class="el-icon-time"></i>
						<span style="margin-left: 10px">{{ scope.row.createDate }}</span>
					</template>
				</el-table-column>
				<el-table-column label="操作" align="center">
					<template slot-scope="scope">
						<el-button size="mini" plain @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
						<el-button size="mini" plain type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="blog_table_footer">
				<span></span>
				<el-pagination background :page-size="pageSize" layout="prev, pager, next" :total="totalCount" @current-change="currentChange"
				 v-show="this.tableData.length>0">
				</el-pagination>
			</div>
		</div>
	</div>
</template>

<script>
	import {
		postRequest
	} from '../../utils/api'
	export default {
		data() {
			return {
				keywords:'',
				loading: false,
				currentPage: 1,
				totalCount: -1,
				pageSize: 10,
				tableData: []
			}
		},
		mounted: function() {
			this.loadSort(1,this.pageSize);
		},
		methods: {
			selectSort(){
				this.loadSort(1,this.pageSize);
			},
			loadSort(page,count){
				this.loading = true;
				var _this = this;
				var url = "/category/findCategoryPageByName?&page=" + page + "&count=" + count + "&keywords=" + this.keywords;
				postRequest(url).then(resp => {
					_this.tableData = resp.category;
					_this.totalCount = resp.totalCount;
					_this.loading = false;
				}).catch(resp => {
					//压根没见到服务器
					_this.$message({
						type: 'error',
						message: '数据加载失败!'
					});
					_this.loading = false;
				})
				
			},
			//翻页
			currentChange(currentPage) {
				this.currentPage = currentPage;
				this.loadSort(currentPage, this.pageSize);
			},
			addSort() {
				this.$prompt('请输入分类', '新增分类', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					inputPattern: /^.{1,30}$/,
					inputErrorMessage: '字符1-30之间'
				}).then(({
					value
				}) => {
					postRequest("/category/insertCategory",{category_name:value}).then(resp => {
						this.$message({
							type: resp.status,
							message: resp.msg
						});
						this.loadSort(1,this.pageSize);
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '取消输入'
					});
				});
			},
			handleEdit(index, row) {
				this.$prompt('请输入分类', '修改分类', {
					inputValue: row.category_name,
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					inputPattern: /^.{1,30}$/,
					inputErrorMessage: '字符1-30之间'
				}).then(({
					value
				}) => {
					postRequest("/category/updateCategory",{category_id:row.category_id,category_name:value}).then(resp => {
						this.$message({
							type: resp.status,
							message: resp.msg
						});
						this.loadSort(1,this.pageSize);
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '取消输入'
					});
				});
			},
			handleDelete(index, row) {
				this.$confirm('此操作将永久删除该分类, 是否继续?', '删除分类', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					center: true
				}).then(() => {
					postRequest("/category/deleteCategory?category_id=" + row.category_id).then(resp => {
						this.$message({
							type: resp.status,
							message: resp.msg
						});
						this.loadSort(1,this.pageSize);
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
				
			}
		}
	}
</script>
<style type="text/css">
	.tableBox{
		width: 900px;
	}
	.blog_table_footer {
		display: flex;
		box-sizing: content-box;
		padding-top: 10px;
		padding-bottom: 0px;
		margin-bottom: 0px;
		justify-content: space-between;
	}
</style>