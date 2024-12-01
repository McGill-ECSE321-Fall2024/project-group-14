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
import Login from '../views/Login.vue'
import CreateAccountSuggestion from "@/views/CreateAccountSuggestion.vue";
import CustomerHome from "@/views/customer/CustomerHome.vue";
import CustomerBrowseGames from "@/views/customer/CustomerBrowseGames.vue";
import EmployeeHome from "@/views/employee/EmployeeHome.vue";
import EmployeeViewGames from "@/views/employee/EmployeeViewGames.vue";
import EmployeeViewOrders from "@/views/employee/EmployeeViewOrders.vue";
import ManagerHome from "@/views/Manager/ManagerHome.vue";
import EmployeeGameRequest from "@/views/employee/EmployeeGameRequest.vue";
const router = createRouter({
  history: createWebHistory(), // Use HTML5 history mode
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
      props : true
    },

    {
      path: '/wishlist/:customerEmail',
      name: 'CustomerWishListView',
      component: CustomerWishListView,
      props: true
    },

    {
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
      props: true, // Pass the email as a prop to the component
    },
    {
      path: '/EmployeeHome/:param1/:param2',
      name: 'EmployeeHome',
      component: EmployeeHome
    },
    {
      path: '/EmployeeViewGames/:param1/:param2',
      name: 'EmployeeViewGames',
      component: EmployeeViewGames
    },
    {
      path: '/EmployeeViewOrders/:param1/:param2',
      name: 'EmployeeViewOrders',
      component: EmployeeViewOrders
    },
    {
      path: '/ManagerHome/:email',
      name: 'ManagerHome',
      component: ManagerHome
    },
    {
      path: '/ManageEmployees/:email',
      name: 'ManageEmployees',
      component: ManagerManageEmployees
    },
    {
      path: '/ManageGames/:email',
      name: 'ManageGames',
      component: ManageGames,
    },  
    {
      path: '/ManageGameRequests/:email',
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
    }
  ],
});

export default router;
