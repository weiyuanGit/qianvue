/*左侧导航栏菜单生成的内容*/
const  menu=[
    {

        title:'内容',
        icon:'course_shouye',
        child:[
            {
                path:'/contentList',
                title:'内容列表',
            },
            {
                path:'/examineList',
                title:'审核列表',
            },
            {
                path:'/addContent',
                title:'添加内容',
            },
            {
                path:'/addVideoContent',
                title:'添加视频'
            }
        ]
    },
    {
        title:'专栏',
        icon:'course_shouye',
        child:[
            {
                path:'/svipList',
                title:'专栏列表'
            },
            {
                path:'/addSvip',
                title:'添加专栏'
            },
            {
                path:'/addSvipTag',
                title:'添加课程'
            },
			{
			    path:'/svipTagList',
			    title:'课程列表'
			}
			
        ]
    },
    {
        title:'任务墙',
        icon:'course_shouye',
        child:[
            {
                path:'/taskList',
                title:'任务列表'
            },
            {
                path:'/examineTask',
                title:'任务审核'
            }
        ]
    },
    {
        path:'/templateList',
        title:'任务模板',
        icon:'course_shouye',
        child:[],
    },
    {
        path:'/userList',
        title:'用户',
        icon:'course_shouye',
        child:[]
    },
	{ 
	    title:'标签',
	    icon:'course_shouye',
	    child:[
	        {
	            path:'/homeTag',
	            title:'首页内容标签'
	        },
	        {
	            path:'/vipTag',
	            title:'vip页标签'
	        },
	        {
	            path:'/channelTag',
	            title:'模板标签'
	        },
	        {
	            path:'/tagGroup',
	            title:'创建标签分组'
	        }
	    ]
	},
]



export default {
    menu
}
