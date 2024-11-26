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
      path: '/orders/:customerId',
      name: 'CustomerOrderView',
      component: CustomerOrderView,
      props: route => ({ customerid: Number(route.params.customerId) }), // Use 'customerId' here
    }
  ],
});

export default router;
