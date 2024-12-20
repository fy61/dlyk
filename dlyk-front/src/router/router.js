//从vue-router库中导入createRouter, createWebHistory两个函数
import { createRouter, createWebHistory } from 'vue-router';

//定义一个变量
let router = createRouter({
    //路由历史
    history: createWebHistory(),

    //配置路由
    routes: [
        {
            //路由路径
            path: '/',
            //路由路径所对应的页面
            component: () => import('../view/LoginView.vue')
        },
        {
            //路由路径
            path: '/dashboard',
            //路由路径所对应的页面
            component: () => import('../view/Dashboard.vue'),
            //子路由(可以有多个,数组)
            children: [
                {
                    //路由路径 子路由路径不能有斜杠
                    path: '',
                    //路由路径所对应的页面
                    component: () => import('../view/StatisticView.vue')
                },
                {
                    //路由路径 子路由路径不能有斜杠
                    path: 'user',
                    //路由路径所对应的页面
                    component: () => import('../view/UserView.vue')
                },

                {
                    //路由路径 子路由路径不能有斜杠,id是动态变量,这个叫动态路由
                    path: 'user/:id',
                    //路由路径所对应的页面
                    component: () => import('../view/UserDetailView.vue')
                },

                {
                    //路由路径 子路由路径不能有斜杠,id是动态变量,这个叫动态路由
                    path: 'activity',
                    //路由路径所对应的页面
                    component: () => import('../view/ActivityView.vue')
                },
                {
                    //路由路径，子路由路径不能以斜杠开头
                    path: 'activity/add',
                    //路由路径所对应的页面
                    component: () => import('../view/ActivityRecordView.vue')
                },
                {
                    //路由路径，子路由路径不能以斜杠开头
                    path: 'activity/edit/:id',
                    //路由路径所对应的页面
                    component: () => import('../view/ActivityRecordView.vue')
                },
                {
                    //路由路径，子路由路径不能以斜杠开头
                    path: 'activity/:id',
                    //路由路径所对应的页面
                    component: () => import('../view/ActivityDetailView.vue')
                },
                {
                    //路由路径，子路由路径不能以斜杠开头
                    path: 'clue',
                    //路由路径所对应的页面
                    component: () => import('../view/ClueView.vue')
                },
                {
                    //路由路径，子路由路径不能以斜杠开头
                    path: 'clue/add',
                    //路由路径所对应的页面
                    component: () => import('../view/ClueRecordView.vue')
                },
                {
                    //路由路径，子路由路径不能以斜杠开头
                    path: 'clue/edit/:id',
                    //路由路径所对应的页面
                    component: () => import('../view/ClueRecordView.vue')
                },
                {
                    //路由路径，子路由路径不能以斜杠开头
                    path: 'clue/detail/:id',
                    //路由路径所对应的页面
                    component: () => import('../view/ClueDetailView.vue')
                },
                {
                    //路由路径，子路由路径不能以斜杠开头
                    path: 'customer',
                    //路由路径所对应的页面
                    component: () => import('../view/CustomerView.vue')
                },
                {
                    //子路由不能以斜杆开头，这个叫动态路由，id是一个变量，值是动态的
                    path: 'customer/detail/:id',
                    //当访问 /dashboard/customer/detail/5 路由的时候，就渲染显示CustomerDetailView.vue页面
                    component: () => import('../view/CustomerDetailView.vue')
                },
                {
                    //子路由不能以斜杆开头
                    path: 'tran',
                    //当访问 /dashboard/tran 路由的时候，就渲染显示TranView.vue页面
                    component: () => import('../view/TranView.vue')
                },
                {
                    //子路由不能以斜杆开头，这个叫动态路由，id是一个变量，值是动态的
                    path: 'tran/detail/:id',
                    //当访问 /dashboard/tran/detail/5 路由的时候，就渲染显示TranDetailView.vue页面
                    component: () => import('../view/TranDetailView.vue')
                }
            ]
        },
        //测试hello
        {
            //路由路径
            path: '/hello',
            //路由路径所对应的页面
            component: () => import('../components/HelloWorld.vue')
        }
    ]
});
export default router;
