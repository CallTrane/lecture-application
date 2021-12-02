import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Home = () => import('views/Home')
const HomeDesk = () => import('views/HomeDesk')
const Login = () => import('views/login/Login')

const routes = [
  {
    path: '/',
    redirect: '/Login',
    name: 'Home',
    component: Home
  },
  {
    path: '/HomeDesk',
    name: 'HomeDesk',
    component: HomeDesk
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
