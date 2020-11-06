<template>
	<div class="page-body">
		<div class="page-header">
			<el-breadcrumb>
				<el-breadcrumb-item :to="{path: '/'}">首页</el-breadcrumb-item>
				<el-breadcrumb-item>博客标签</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<el-card class="box-card" v-loading="loading">
			<el-tag class="tag" :key="tag" v-for="tag in tags" closable :type="color[parseInt(Math.random() * color.length)]"
			 :disable-transitions="false" @close="handleClose(tag)" effect="dark">
				{{tag.tag_name}}
			</el-tag>
			<el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" size="small"
			 @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm">
			</el-input>
			<el-button v-else class="button-new-tag" size="small" @click="showInput">+ 标签</el-button>
		</el-card>
	</div>
</template>
<script>
	import {
		postRequest
	} from '../../utils/api'
	export default {
		data() {
			return {
				color: ['', 'success', 'info', 'warning', 'danger'],
				tags: [],
				loading: false,
				inputVisible: false,
				inputValue: ''
			};
		},
		mounted: function() {
			this.findTagAll();
		},
		methods: {
			findTagAll(){
				postRequest('/tag/findTagAll').then(resp => {
					this.loading=true;
					this.tags=resp;
					this.loading=false;
				})
			},
			handleClose(tag) {
				this.$confirm('此操作将永久删除该标签, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.loading=true
					postRequest("/tag/deleteTagByTagId",{tag_id:tag.tag_id}).then(resp=>{
						this.$message({
							type:resp.status,
							message: resp.msg
						});
						if(resp.status=="success"){
							this.tags.splice(this.tags.indexOf(tag), 1);
						}
						this.loading=false
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},

			showInput() {
				this.inputVisible = true;
				this.$nextTick(_ => {
					this.$refs.saveTagInput.$refs.input.focus();
				});
			},

			handleInputConfirm() {
				let inputValue = this.inputValue;
				if (inputValue) {
					this.loading=true;
					postRequest("/tag/insertTag",{tag_name:inputValue}).then(resp=>{
						this.$message({
							type:resp.status,
							message: resp.msg
						});
						if(resp.status=="success"){
							this.findTagAll();
						}
						this.loading=false
					})
				}
				this.inputVisible = false;
				this.inputValue = '';
			}
		}
	}
</script>
<style>
	.tag {
		margin: 10px;
		padding: 0 20px;
		line-height: 35px;
		height: 35px;
	}

	.el-tag+.el-tag {
		margin-left: 10px;
	}

	.button-new-tag {
		padding: 0 20px;
		line-height: 35px;
		height: 35px;
		margin-left: 10px;
	}

	.input-new-tag {
		width: 90px;
			}
</style>
