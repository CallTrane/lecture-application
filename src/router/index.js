import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Home = () => import('views/Home')
const Desktop = () => import('views/desktop/Desktop')
const Login = () => import('views/login/Login')

const routes = [
  {
    path: '/',
    redirect: '/Login',
    name: 'Home',
    component: Home
  },
  {
    path: '/deskTop',
    name: 'deskTop',
    component: Desktop
  },
  {
    path: '/Login',
    name: 'login',
    component: Login
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
