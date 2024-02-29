import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  base: './', // 基础路径，如果你的项目部署在子路径下，需要设置
  server: {
    port: 3000, // 开发服务器端口
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 后端API的地址
        changeOrigin: true,
        //rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  },
})
