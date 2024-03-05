import { createRouter, createWebHistory } from 'vue-router'
import NavBtnView from '../views/NavBtnView.vue'
import MainView from '../views/MainView.vue'
import UploadView from '../views/UploadView.vue'
import FilesView from '../views/FilesView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      components: { default: MainView, nav: NavBtnView }
    },
    {
      path: '/upload',
      name: 'upload',
      components: { default: UploadView, nav: NavBtnView }
    },
    {
      path: '/files',
      name: 'files',
      components: { default: FilesView, nav: NavBtnView }
    }
  ]
})

export default router
