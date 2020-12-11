import Vue from 'vue'
import Router from 'vue-router'
import home from '@/home/index'
import login from '@/user/Login'
import register from '@/user/Register'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/home',
      name: 'Home',
      component: home
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    }
  ]
})
