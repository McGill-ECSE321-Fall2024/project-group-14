import { createRouter, createWebHistory } from 'vue-router';
import ManagerPromotion from '../views/ManagerPromotionView.vue';

// Create the router instance
const router = createRouter({
  history: createWebHistory(), // Use HTML5 history mode
  routes: [
    {
      path: '/1',
      name: 'ManagerPromotion',
      component: ManagerPromotion,
    },
  ],
});

export default router;
