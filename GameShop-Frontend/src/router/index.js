import { createRouter, createWebHistory } from 'vue-router';
import ManagerManageEmployees from '../views/manager/ManageEmployees.vue';
import ManageGames from '../views/Manager/ManageGames.vue';
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
      path: '/1',
      name: 'ManagerPromotion',
      component: ManagerPromotion,
    },

    {
      path: '/orders/:customerId',
      name: 'CustomerOrderView',
      component: CustomerOrderView,
      props: route => ({ customerid: Number(route.params.customerId) }), // Use 'customerId' here
    },

    {
      path: '/wishlist/:customerId',
      name: 'CustomerWishListView',
      component: CustomerWishListView,
      props: route => ({ customerid: Number(route.params.customerId) }), // Use 'customerId' here
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
    path: '/EmployeeGameRequest/:param1/:param2',
    name: 'EmployeeGameRequest',
    component: EmployeeGameRequest
    }
  ],
});

export default router;
