<template>
	<div class="page-body">
		<div class="page-header">
			<el-breadcrumb>
				<el-breadcrumb-item :to="{path: '/'}">首页</el-breadcrumb-item>
				<el-breadcrumb-item>修改密码</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<el-container class="article_list">
			<el-main class="main">
				<el-card class="box-card" v-loading="loading">
					<div slot="header" class="clearfix">
						<span>修改密码</span>
					</div>
					<div>

						<el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="form">
							<el-form-item label="旧密码" prop="oldPass">
								<el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
							</el-form-item>
							<el-form-item label="新密码" prop="newPass">
								<el-input type="password" v-model="ruleForm.newPass" autocomplete="off"></el-input>
							</el-form-item>
							<el-form-item label="确认密码" prop="checkPass">
								<el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
							</el-form-item>

							<el-form-item>
								<el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
								<el-button @click="resetForm('ruleForm')">重置</el-button>
							</el-form-item>
						</el-form>
					</div>
				</el-card>
			</el-main>
		</el-container>
	</div>
</template>
<script>
	import {
		postRequest
	} from "../../utils/api"
	export default {
		data() {
			var validateOldPass = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入旧密码'));
				} else {
					if (this.ruleForm.checkPass !== '') {
						this.$refs.ruleForm.validateField('checkPass');
					}
					callback();
				}
			};
			var validateNewPass = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入新密码'));
				} else {
					if (this.ruleForm.checkPass !== '') {
						this.$refs.ruleForm.validateField('checkPass');
					}
					callback();
				}
			};
			var validateCheckPass = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请再次输入密码'));
				} else if (value !== this.ruleForm.newPass) {
					callback(new Error('两次输入密码不一致!'));
				} else {
					callback();
				}
			};
			return {
				ruleForm: {
					oldPass: '',
					newPass: '',
					checkPass: ''
				},
				rules: {
					oldPass: [{
						validator: validateOldPass,
						trigger: 'blur'
					}],
					newPass: [{
						validator: validateNewPass,
						trigger: 'blur'
					}],
					checkPass: [{
						validator: validateCheckPass,
						trigger: 'blur'
					}]
				}
			};
		},
		methods: {
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						postRequest("/user/updatePass",{
						   oldPass:this.ruleForm.oldPass,
						   newPass:this.ruleForm.newPass
						}).then(resp=>{
							this.$message({
								type: resp.status,
								message: resp.msg
							});
						})
					} else {
						return false;
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			}
		}
	}
</script>
<style>
	.box-card {
		width: 700px;
		height: 750px;
	}
	.form .el-input {
		width: 300px!important;
	}
</style>
