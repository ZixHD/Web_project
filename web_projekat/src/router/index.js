import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '../views/HomePage.vue'
import DestinationPage from "@/components/DestinationPage.vue";
import ArticlePage from "@/components/ArticlePage.vue";
import ActivityPage from "@/components/ActivityPage.vue";




Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HomePage',
    component: HomePage,
    meta: {
      authRequired: true,
    },
  },
  {
    path: '/mostRead',
    name: 'MostReadPage',
    meta: {
      authRequired: true,
    },
    component: () => import(/* webpackChunkName: "mostRead" */ '../views/MostReadPage.vue'),
  },
  {
    path: '/article/:id',
    name: 'Article',
    component: ArticlePage,
    meta:{
      authRequired: true,
    }
  },
  {
    path: '/activity/:id',
    name: 'Activity',
    component: ActivityPage,
    meta:{
      authRequired: true,
    }
  },
  {
    path: '/destination/:id',
    name: 'Destination',
    component: DestinationPage,
    meta: {
      authRequired: true,
    },
  },
  {
    path: '/login',
    name: 'LoginPage',
    component: () => import(/* webpackChunkName: "about" */ '../views/LoginPage.vue')
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.authRequired) {
    const jwt = localStorage.getItem('jwt');

    if (!jwt) {
      next({name: 'LoginPage'});
      return;
    }

    const payload = JSON.parse(atob(jwt.split('.')[1]));
    const expDate = new Date(payload.exp * 1000);
    if (expDate < new Date()) {
      next({name: 'LoginPage'});
      return;
    }
  }

  next();
});

export default router
