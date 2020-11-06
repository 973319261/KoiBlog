<template>
	<div class="page-body">
		<div class="page-header">
			<el-breadcrumb>
				<el-breadcrumb-item :to="{path: '/'}">首页</el-breadcrumb-item>
				<el-breadcrumb-item>全部文章</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<el-container class="article_list">
			<el-main class="main">
				<el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
					<el-tab-pane name="post">
						<span slot="label"><i class="fa fa-paper-plane-o"></i> 已 发 表 ( {{post}} )</span>
						<blog_table state="0" :showEdit="true" :showDelete="true" :activeName="activeName" @childFn="parentFn"></blog_table>
					</el-tab-pane>
					<el-tab-pane name="draft">
						<span slot="label"><i class="fa fa-envelope-open-o"></i> 草 稿 箱 ( {{draft}} )</span>
						<blog_table state="1" :showEdit="true" :showDelete="true" :activeName="activeName"  @childFn="parentFn"></blog_table>
					</el-tab-pane>
					<el-tab-pane name="dustbin">
						<span slot="label"><i class="fa fa-trash-o"></i> 回 收 站 （ {{dustbin}} ）</span>
						<blog_table state="2" :showEdit="false" :showRenew="true" :showDelete="true" :activeName="activeName"  @childFn="parentFn"></blog_table>
					</el-tab-pane>
				</el-tabs>
			</el-main>

		</el-container>
	</div>
</template>
<script>
	import BlogTable from '../components/BlogTable'
	export default {
		data() {
			return {
				activeName: 'post',
				post: '',
				draft:'',
				dustbin:''
			};
		},
		methods: {
			//接收子组件传递的文章总数值
			parentFn(totalCount,state) {
				if(state==0){
					this.post=totalCount;
				}else if(state==1){
					this.draft=totalCount;
				}else{
					this.dustbin=totalCount;
				}
			},
			handleClick(tab, event) {
				console.log(tab, event);
			}
		},
		components: {
			'blog_table': BlogTable
		}
	};
</script>
<style>
	.article_list>.header {
		background-color: #ececec;
		margin-top: 10px;
		padding-left: 5px;
		display: flex;
		justify-content: flex-start;
	}

	.article_list>.main {
		/*justify-content: flex-start;*/
		display: flex;
		flex-direction: column;
		padding-left: 0px;
		background-color: #fff;
		padding-top: 0px;
		margin-top: 8px;
	}
</style>
