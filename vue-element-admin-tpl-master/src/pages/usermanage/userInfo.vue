<template>
	<div class="page-body">
		<div class="page-header">
			<el-breadcrumb>
				<el-breadcrumb-item :to="{path: '/'}">首页</el-breadcrumb-item>
				<el-breadcrumb-item>个人信息</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<el-container class="article_list">
			<el-main class="main">
				<el-card class="box-card" v-loading="loading">
					<div slot="header" class="clearfix">
						<span>个人信息</span>
					</div>
					<div>
						<el-form :model="user" :rules="rules" ref="user" label-width="80px" class="form">
							<el-form-item label="用户名">
								<el-input v-model="user.user_name" :disabled="true"></el-input>
							</el-form-item>
							<el-form-item label="昵称" prop="user_nickname">
								<el-input v-model="user.user_nickname"></el-input>
							</el-form-item>
							<el-form-item label="性别">
								<el-radio-group v-model="user.user_sex">
									<el-radio :label="0">男</el-radio>
									<el-radio :label="1">女</el-radio>
								</el-radio-group>
							</el-form-item>
							<el-form-item label="邮箱" prop="user_email">
								<el-input v-model="user.user_email"></el-input>
							</el-form-item>
							<el-form-item label="用户头像">
								<div style="float:left;width:200px;height:200px;">
									<div class="img-box text-center">
										<img :src="user.user_avatar" alt="" id="imgPicture" @dblclick="imgPicture" />
										<input type="file" style="margin-left:3px;margin-top:10px;font-size:10px;display: none;" name="filePicture"
										 id="Picture" @change="imgChange" />
									</div>

								</div>
							</el-form-item>
							<el-form-item label="备注" prop="user_comment">
								<el-input type="textarea" :autosize="{ minRows: 4, maxRows: 8}" placeholder="请输入内容" v-model="user.user_comment">
								</el-input>
							</el-form-item>
							<el-form-item>
								<el-button type="primary" @click="submitForm('user')">保存</el-button>
								<el-button @click="resetForm('user')">重置</el-button>
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
	} from '../../utils/api'
	import {
		uploadFileRequest
	} from '../../utils/api'
	var imgReader = new FileReader();
	var regexImgeFilter = /^(?:image\/bmp|image\/png|image\/jpeg|image\/jpg)$/i;
	imgReader.onload = function (evt) {
	    $("#imgPicture").attr('src', evt.target.result);
	}
	export default {
		data() {
			return {
				loading: false,
				user: {
					user_id: -1,
					user_name: "",
					user_nickname: '',
					user_sex: 0,
					user_email: '',
					user_avatar: '',
					user_comment: ''
				},
				rules: {
					user_nickname: [{
							required: true,
							message: '请输入用户昵称',
							trigger: 'blur'
						},
						{
							min: 1,
							max: 15,
							message: '长度在 1 到 15 个字符',
							trigger: 'blur'
						}
					],
					user_email: [{
							required: true,
							message: '请输入邮箱地址',
							trigger: 'blur'
						},
						{
							type: 'email',
							message: '请输入正确的邮箱地址',
							trigger: ['blur', 'change']
						}
					],
					user_comment: [{
						min: 0,
						max: 100,
						message: '最大只能输入 100 个字符',
						trigger: 'blur'
					}]
				}
			};
		},
		mounted: function() {
			this.loading = true;
			postRequest("/user/findUserById").then(resp => {
				this.user = resp;
			})
			this.loading = false;
			
		},
		methods: {
			imgPicture(){
				 $("#Picture").click();
			},//图片更改
			imgChange(){
				var imgfFile = $("#Picture").prop('files')[0];
				if (!regexImgeFilter.test(imgfFile.type)) {
					this.$message.error('选择的不是一张有效的图片文件!');
					return;
				}
				imgReader.readAsDataURL(imgfFile);
				var newData = new FormData();
				newData.append("image", imgfFile);
				uploadFileRequest("/user/uploadAvatar", newData).then(resp => {
					if (resp.status == "success") {
						this.$message.success('上传头像成功!');
						this.user.user_avatar = resp.msg;
					} else {
						this.$message.error('上传头像失败!');
					}
				}).catch(resp => {
					this.$message({
						type: 'error',
						message: '上传头像失败!'
					});
				})
			},
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						postRequest("/user/updateUser", {
							user_id: this.user.user_id,
							user_nickname: this.user.user_nickname,
							user_sex: this.user.user_sex,
							user_email: this.user.user_email,
							user_avatar: this.user.user_avatar,
							user_comment: this.user.user_comment
						}).then(resp => {
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
	};
</script>
<style>
	.form input {
		width: 300px;
	}

	.text {
		font-size: 14px;
	}

	.item {
		margin-bottom: 18px;
	}

	.clearfix:before,
	.clearfix:after {
		display: table;
		content: "";
	}

	.clearfix:after {
		clear: both
	}

	.box-card {
		width: 700px;
	}

	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	} 
	#imgPicture {
            display:block;
            width:200px;
            height:200px;
           border-radius: 5px;
          box-shadow: 0 0 20px 3px #eee;
        }
</style>
