//从vue库中导入了createApp函数,此函数用来创建vue应用
import { createApp } from 'vue';

//导入css文件
// import './style.css'

//从element-plus框架中导入ElementPlus组件
import ElementPlus from 'element-plus';

//导入ele的国际化的中文包
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';

// 导入element-plus框架的css样式
import 'element-plus/dist/index.css';

import * as ElementPlusIconsVue from '@element-plus/icons-vue';

import router from './router/router';

//导入App.vue文件,此文件通常为vue的主组件
import App from './App.vue'; //根组件(地基)

import LoginView from './view/LoginView.vue';
import { doGet } from './http/httpRequest';

let app = createApp(App);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}
//el：指令所绑定到的页面dom元素。这可以用于直接操作DOM。
//binding：是一个对象，里面包含很多属性，重点看value属性：传递给指令的值。我们传的是 clue:delete 这个值
app.directive('hasPermission', (el, binding) => {
    // 这会在 `mounted` 和 `updated` 时都调用
    doGet('/api/login/info', {}).then((resp) => {
        let user = resp.data.data;
        let permissionList = user.permissionLIst;

        let flag = false;

        for (let key in permissionList) {
            if (permissionList[key] === binding.value) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            //没有权限，把页面元素隐藏掉
            //el.style.display = 'none';
            //把没有权限的按钮dom元素删除
            el.parentNode && el.parentNode.removeChild(el);
        }
    });
});
//创建一个vue应用并把APP组件传进去,接着把此vue应用挂载到dom中id为app的元素上
//这样就可以控制此html里面的元素和子元素,来渲染vue的内容
app.use(ElementPlus, { locale: zhCn }).use(router).mount('#app');
