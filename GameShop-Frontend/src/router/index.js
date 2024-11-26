import { createRouter, createWebHistory } from 'vue-router';
import ManagerPromotion from '../views/ManagerPromotionView.vue';
import CustomerOrderView from '@/views/CustomerOrderView.vue';
// Create the router instance
const router = createRouter({
  history: createWebHistory(), // Use HTML5 history mode
  routes: [
    {
      path: '/1',
      name: 'ManagerPromotion',
      component: ManagerPromotion,
    },

    {
      path: '/2',
      name: 'CustomerOrderView',
      component: CustomerOrderView,
    }
  ],
});

export default router;
