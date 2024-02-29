// router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Login from '@/components/Login.vue';
import Index from "@/components/Index.vue";
import App from "@/App.vue";
import TheWelcome from "@/components/TheWelcome.vue";
// import WelcomeItem from "@/components/WelcomeItem.vue";
// import TheWelcome from "@/components/TheWelcome.vue";

const routes = [
    {
        path: '',
        redirect: '/login'
    },
    {
        path: '/index',
        component: Index
    },
    {
        path: '/login',
        component: Login
    }
    // ...其他路由
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;