// import './assets/main.css'
//
// import { createApp } from 'vue'
// import App from './App.vue'
//
// createApp(App).mount('#app')

import {createApp} from 'vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import App from './App.vue';
import router from './router';
//import VueRouter from 'vue-router'
import Index from "@/components/Index.vue";
import Login from "@/components/Login.vue";

const app = createApp(App);
app.use(ElementPlus, router, /*VueRouter*/);
app.mount('#app');

// const routes = [{
//     path: "",
//     redirect: "/login" //默认显示推荐组件(路由的重定向)
// }, {
//     path: '/',
//     name: 'index',
//     component: Index
// }, {
//     path: '/login',
//     name: 'login',
//     component: Login
// }
// ]
