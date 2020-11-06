const menus = [
  {id: '0', label: '控制面板', path: '/', icon: 'fa fa-dashboard'},
  {
    id: 'components',
    icon: 'fa fa-microchip',
    label: '博客管理',
    submenu: [
        {id: 'f1', name: 'blogmanage-newblog', label: '发表文章', icon: 'fa fa-pencil-square-o'},
        {id: 'f2', name: 'blogmanage-allblog', label: '文章列表', icon: 'fa fa-file-text-o'},
        {id: 'f3', name: 'blogmanage-sort', label: '博客分类', icon: 'fa fa-list'},
        {id: 'f4', name: 'blogmanage-tag', label: '博客标签', icon: 'fa fa-tags'},
				{id: 'f5', name: 'blogmanage-comment', label: '评论管理', icon: 'fa fa-commenting-o'}
      ]
  },
	{
    id: 'menus',
    icon: 'fa fa-bars',
    label: '菜单管理',
    submenu: [
        {id: 'f1', name: 'mentmanage-addment', label: '添加菜单', icon: 'fa fa-plus-square-o'},
        {id: 'f2', name: 'mentmanage-mentlist', label: '菜单列表', icon: 'fa fa-list'},
      ]
  },
  {
    id: 'users',
    icon: 'fa fa-address-book',
    label: '用户管理',
    submenu: [
        {id: 'f1', name: 'usermanage-adduser', label: '添加用户', icon: 'fa fa-user-plus'},
        {id: 'f2', name: 'usermanage-userlist', label: '用户列表', icon: 'fa fa-users'},
      ]
  },{
    id: 'permissions',
    icon: 'fa fa-address-book-o',
    label: '权限管理',
    submenu: [
        {id: 'f1', name: 'permissionmanage-role', label: '角色分配', icon: 'fa fa-users'},
        {id: 'f2', name: 'permissionmanage-permission', label: '权限分配', icon: 'fa fa-retweet'},
      ]
  },
	{id: '0', label: '日志管理', name: 'logmanage-log', icon: 'fa fa-list-alt'},
	{
	  id: 'setting ',
	  icon: 'fa fa-cogs',
	  label: '系统设置',
	  submenu: [
	      {id: 'f1', name: 'c-button', label: '常规设置', icon: 'fa fa-cog'},
	      {id: 'f2', name: 'c-switch', label: '文章设置', icon: 'fa fa-file-text-o'},
	      {id: 'f3', name: 'c-checkbox', label: '评论设置', icon: 'fa fa-comment-o'},
	      {id: 'f4', name: 'c-input', label: '其他设置', icon: 'fa fa-wrench'},
	    ]
	}
]
export default menus
