<template>
	<m-navbar :theme="theme.theme.headerTheme">
		<m-navbar-brand>
			<i class="side-switch" :class="{
        'el-icon-more': !mini,
        'el-icon-more-outline': mini
      }" @click="handleSwitchSide"></i>
		</m-navbar-brand>
		<m-nav>
			<m-nav-item><a href="https://www.lanyueos.com" target="_blank">主页</a></m-nav-item>
			<m-nav-item><a href="https://blog.lanyueos.com" target="_blank">博客</a></m-nav-item>
			<m-nav-item><a href="https://www.lanyueos.com" target="_blank">关于</a></m-nav-item>
		</m-nav>
		<m-nav align="right">
			<m-nav-item padding="0">
				<m-dropdown align="right" v-if="user" padding="0 10px">
					<a href="#" style="display: inline-block; padding: 0px; color: inherit">
						<img :src="user.user_avatar" alt="" style="border-radius: 3px;vertical-align: middle;margin-right:5px;width: 20px;height: 20px;">
						<span>{{user.user_nickname}}</span>
						<span class="caret"></span>
					</a>
					<m-dropdown-panel>
						<m-dropdown-item><a @click="getUserInfo">用户信息</a></m-dropdown-item>
						<m-dropdown-item> <a @click="updatePass">修改密码</a></m-dropdown-item>
						<div class="test-line"></div>
						<m-dropdown-item><a @click="logout">退出账号</a></m-dropdown-item>
					</m-dropdown-panel>
				</m-dropdown>
			</m-nav-item>

			<m-nav-item padding="0">
				<m-dropdown align="right" padding="0 10px">
					<a href="#" style="padding:0 0px; color: inherit">
						<i class="fa fa-gears"></i>
					</a>
					<m-dropdown-panel style="width: 200px; min-height: 100px;">
						<el-form>
							<el-form-item label="侧边栏">
								<el-switch v-model="showAside" @change="handleSwitchHideSide" />
							</el-form-item>
							<el-form-item label="主题">
								<el-select v-model="themeType" style="width: 100px">
									<el-option v-for="theme in themes" :key="theme.label" :label="theme.label" :value="theme.name"></el-option>
								</el-select>
							</el-form-item>
						</el-form>
					</m-dropdown-panel>
				</m-dropdown>
			</m-nav-item>
			<m-nav-item padding="0 5px">
				<a href="#" @click.stop.prevent="handleSwitchScreen">
					<i class="fa" :class="isFullScreen ? 'fa-compress' : 'fa-expand'"></i>
				</a>
			</m-nav-item>

		</m-nav>
	</m-navbar>
</template>
<script type="text/javascript">
	import {
		requestFullScreen,
		exitFullscreen
	} from '@/utils'
	import themes from './theme'
	import {
		postRequest
	} from '../utils/api'
	export default {
		name: 'app-header',
		data() {
			return {
				mini: false,
				isFullScreen: false,
				themes,
				themeType: '',
				showAside: true,
				theme: {
					theme: {
						headerTheme: 'info'
					}
				},
				user: {
					user_id: -1,
					user_nickname: '',
					user_avatar: '',
				}
			}
		},
		computed: {

		},
		watch: {
			themeType(val) {
				this.theme = this.themes.find(e => e.name === val) || {}
				this.$emit('set-theme', this.theme)
				localStorage.setItem('theme', val)
			}
		},
		methods: {
			handleSwitchSide() {
				this.mini = !this.mini
				this.$emit('switch', this.mini)
			},
			handleSwitchScreen() {
				if (this.isFullScreen) {
					exitFullscreen()
					this.isFullScreen = false
				} else {
					requestFullScreen()
					this.isFullScreen = true
				}
			},
			handleSwitchHideSide() {
				this.$emit('hide-side')
			},
			getUserInfo() { //获取用户信息
				this.$router.push({
					path: '/usermanage/userInfo',
				});
			},
			updatePass() { //修改密码
				this.$router.push({
					path: '/usermanage/updatePass'
				});
			},
			logout() { //退出账号
				var url = "/user/cleanSession";
				postRequest(url).then(resp => {
					window.location.href = "login"
				});
			}
		},
		created() {
			//获取用户信息
			var url = "/user/findUserById";
			postRequest(url).then(resp => {
				if (resp != '') {
					this.user = resp;
				} else {
					window.location.href = "login"
				}
			});
			//获取主题
			var theme = localStorage.getItem('theme') || 'default'
			this.themeType = themevar

		}
	}
</script>
<style type="text/css">
	.side-switch {
		display: inline-block;
		font-size: 32px;
		cursor: pointer;
		color: inherit;
		margin-top: 10px;
	}

	.side-switch:hover {
		color: #fff;
	}
</style>
