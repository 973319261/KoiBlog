import Vue from 'vue'
import Router from 'vue-router'

import AppView from '@/components/app-view'
import Home from '@/pages/home'

Vue.use(Router)

const page = name => () => import('@/pages/' + name)

export default new Router({
	mode: 'history',
	routes: [{
			path: '',
			component: AppView,
			children: [{
					path: '/',
					name: 'home',
					component: Home
				},
				{
					path: '/blogmanage/newblog',
					name: 'blogmanage-newblog',
					component: page('blogmanage/newblog')
				},
				{
					path: '/blogmanage/allblog',
					name: 'blogmanage-allblog',
					component: page('blogmanage/allblog')
				},
				{
					path: '/blogmanage/sort',
					name: 'blogmanage-sort',
					component: page('blogmanage/sort')
				},
				{
					path: '/blogmanage/tag',
					name: 'blogmanage-tag',
					component: page('blogmanage/tag')
				},
				{
					path: '/blogmanage/comment',
					name: 'blogmanage-comment',
					component: page('blogmanage/comment')
				},
				{
					path: '/mentmanage/addment',
					name: 'mentmanage-addment',
					component: page('mentmanage/addment')
				},
				{
					path: '/mentmanage/mentlist',
					name: 'mentmanage-mentlist',
					component: page('mentmanage/mentlist')
				},
				{
					path: '/usermanage/adduser',
					name: 'usermanage-adduser',
					component: page('usermanage/adduser')
				},
				{
					path: '/usermanage/userlist',
					name: 'usermanage-userlist',
					component: page('usermanage/userlist')
				},
				{
					path: '/usermanage/updatePass',
					name: 'usermanage-updatePass',
					component: page('usermanage/updatePass')
				},
				{
					path: '/usermanage/userInfo',
					name: 'usermanage-userInfo',
					component: page('usermanage/userInfo')
				},
				{
					path: '/permissionmanage/role',
					name: 'permissionmanage-role',
					component: page('permissionmanage/role')
				},
				{
					path: '/permissionmanage/permission',
					name: 'permissionmanage-permission',
					component: page('permissionmanage/permission')
				},
				{
					path: '/logmanage/log',
					name: 'logmanage-log',
					component: page('logmanage/log')
				},
				{
					path: '/404',
					name: '404',
					component: page('404')
				},
				{
					path: '/500',
					name: '500',
					component: page('500')
				},
				{
					path: 'blogDetail',
					name: 'blogmanage-allblog',
					component: page('components/BlogDetail')
				},
			]
		},
		{
			path: '/login',
			name: 'login',
			component: page('login')
		},
		{
			path: '/home-login',
			name: 'p-login',
			component: page('home-login')
		},
		{
			path: '/register',
			name: 'p-register',
			component: page('register')
		},
		{
			path: '*',
			redirect: {
				name: '404'
			}
		}
	]
})
