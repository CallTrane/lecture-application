import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const Home = () => import('views/home/Home')
const Login = () => import('views/login/Login')
const Selection = () => import('views/selection/Selection')
const Timetable = () => import('views/myinfo/Timetable')
const Myinfo = () => import('views/myinfo/Myinfo')
const Drop = () => import('views/selection/Drop')
const HomeTeacher = () => import('views/home/HomeTeacher')
const teacherClass = () => import('views/teacherClass/teacherClass')
const StuList = () => import('views/teacherClass/StuList')


const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {
      title: '登录'
    }
  },
  {
    path: '/HomeTeacher',
    component: HomeTeacher,
    meta: {
      title: '首页'
    },
    children: [
      {
        path: '/',
        redirect: 'teacherClass'
      },
      {
        path: 'teacherClass',
        component: teacherClass,
        meta: {
          title: '教师课表'
        }
      },
      {
        path: 'stulist',
        component: StuList,
        meta: {
          title: '学生名单'
        }
      }
    ]
  },
  {
    path: '/home',
    component: Home,
    meta: {
      title: '首页'
    },
    children: [
      {
        path: '/',
        redirect: 'selection'
      },
      {
        path: 'selection',
        component: Selection,
        meta: {
          title: '学生选课'
        }
      },
      {
        path: 'drop',
        component: Drop,
        meta: {
          title: '学生退课'
        }
      },
      {
        path: 'timetable',
        component: Timetable,
        meta: {
          title: '个人课表'
        }
      },
      {
        path: 'myinfo',
        component: Myinfo,
        meta: {
          title: '个人信息'
        }
      },
    ]
  },
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

// 设置标题
router.beforeEach((to, from, next) => {
  //beforeEach是router的钩子函数，在进入路由前执行
  if (to.meta.title) {
    //判断是否有标题
    // console.log(to.meta.title)
    document.title = to.meta.title
  } else {
    document.title = '学生选课系统'
  }
  next()
})


// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
  }
  else {
    //非跳转到登录界面时，判断本地存储userData是否为null或空，为空则跳回到登录界面，反之到相应的界面(此时有数据)。
    let myInfo = sessionStorage.getItem('myInfo')
    if (myInfo === null || myInfo === '') {
      next('/')
    }
    else {
      next()
    }
  }
});


export default router
