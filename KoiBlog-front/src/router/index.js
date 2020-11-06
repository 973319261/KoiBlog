import Vue from 'vue'
import Router from 'vue-router'
import AppView from '@/components/app-view'
import Index from '@/components/page/index'
import Info from '@/components/page/info'
import Blog from '@/components/page/blog'
import Tag from '@/components/page/tag'
import About from '@/components/page/about'
Vue.use(Router)
export default new Router({
 mode: 'history',
scrollBehavior (to, from, savedPosition) {
    	return { x: 0, y: 0 }
 },//跳到顶部
 routes: [{
 		path: '',
 		component: AppView,
 		children: [{
 				path: '/',
 				name: 'index',
 				component: Index
 			},
 			{
 				path: '/info',
 				name: 'info',
 				component: Info
 			},
 			{
 				path: '/blog',
 				name: 'blog',
 				component: Blog
 			},
      {
      	path: '/tag',
      	name: 'tag',
      	component: Tag
      },
 			{
 				path: '/about',
 				name: 'about',
 				component: About
 			}
 		]
 	}]
})
