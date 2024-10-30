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

let app = createApp(App);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}

//创建一个vue应用并把APP组件传进去,接着把此vue应用挂载到dom中id为app的元素上
//这样就可以控制此html里面的元素和子元素,来渲染vue的内容
app.use(ElementPlus, { locale: zhCn }).use(router).mount('#app');
