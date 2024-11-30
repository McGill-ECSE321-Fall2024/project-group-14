import { createRouter, createWebHistory } from 'vue-router';
import ManagerManageEmployees from '../views/Manager/ManageEmployees.vue';
import ManageGames from '../views/Manager/ManageGames.vue';
import ViewOrders from '../views/Manager/ViewOrders.vue';
import ManageGameRequests from '../views/Manager/ManageGameRequests.vue';
import ManagerPromotion from '../views/Manager/ManagerPromotionView.vue';
import CustomerOrderView from '@/views/Customer/CustomerOrderView.vue';
import CustomerWishListView from '@/views/Customer/CustomerWishListView.vue';
import HomeView from '../views/HomeView.vue'
import SignUp from '../views/SignUp.vue'
import Login from '../views/LogIn.vue'
import CreateAccountSuggestion from "@/views/CreateAccountSuggestion.vue";
import CustomerHome from "@/views/Customer/CustomerHome.vue";
import CustomerBrowseGames from "@/views/Customer/CustomerBrowseGames.vue";
import EmployeeHome from "@/views/employee/EmployeeHome.vue";
import EmployeeViewGames from "@/views/employee/EmployeeViewGames.vue";
import EmployeeViewOrders from "@/views/employee/EmployeeViewOrders.vue";
import ManagerHome from "@/views/Manager/ManagerHome.vue";
import EmployeeGameRequest from "@/views/employee/EmployeeGameRequest.vue";
import CustomerWriteReview from "@/views/Customer/CustomerWriteReview.vue";
import ManagerWriteReply from "@/views/Manager/ManagerWriteReply.vue";
import ManagePolicy from '@/views/Manager/ManagePolicy.vue';

const router = createRouter({
  history: createWebHistory(), 
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/ManagerPromotion/:email',
      name: 'ManagerPromotion',
      component: ManagerPromotion,
      props: true
    },

    {
      path: '/orders/:customerEmail',
      name: 'CustomerOrderView',
      component: CustomerOrderView,
      props: route => ({ customerid: Number(route.params.customerId) }), 
    },

    {
      path: '/wishlist/:customerEmail',
      name: 'CustomerWishListView',
      component: CustomerWishListView,
      props: route => ({ customerid: Number(route.params.customerId) }), 
      path: '/SignUp/',
      component: SignUp
    },
    {
      path: '/Login/',
      component: Login
    },
    {
      path: '/CreateAccountSuggestion',
      name: 'CreateAccountSuggestion',
      component: CreateAccountSuggestion
    },
    {
      path: '/CustomerHome/:param1',
      name: 'CustomerHome',
      component: CustomerHome
    },
    {
      path: '/CustomerBrowseGames/:email',
      name: 'CustomerBrowseGames',
      component: CustomerBrowseGames,
      props: true, 
    },
    {
      path: '/EmployeeHome/:param1/:param2',
      name: 'EmployeeHome',
      component: EmployeeHome
    },
    {
      path: '/ManagerHome/:param1',
      name: 'ManagerHome',
      component: ManagerHome
    },
    {
      path: '/ManageEmployees/:param1',
      name: 'ManageEmployees',
      component: ManagerManageEmployees
    },
    {
      path: '/ManageGames/:param1',
      name: 'ManageGames',
      component: ManageGames,
    },  
    {
      path: '/ManageGameRequests/:param1',
      name: 'ManageGameRequests',
      component: ManageGameRequests,
    },   
    {
      path: '/ViewOrders/:email',
      name: 'ViewOrders',
      component: ViewOrders,
    }, 
    {
      path: '/EmployeeGameRequest/:param1/:param2',
      name: 'EmployeeGameRequest',
      component: EmployeeGameRequest
    },
    {
      path: '/ManagerHome/:param1',
        name: 'ManagerHome',
        component: ManagerHome
    },
    {
      path: '/writeReview/:param1/:param2',  // param1 = email, param2 = gameId
      name: 'CustomerWriteReview',
      component: CustomerWriteReview
    },
    {
      path: '/manager/managerWriteReply/:param1/:param2',  // param1 = email, param2 = gameId
      name: 'ManagerWriteReply',
      component: ManagerWriteReply
    },
    {
      path: '/ManagePolicy/:param1',  // param1 = email
      name: 'ManagePolicy',
      component: ManagePolicy
    }
  ],
});

export default router;
