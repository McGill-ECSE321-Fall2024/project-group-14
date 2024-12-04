<template>
  <div>
    <div id="main"></div>
    <div class="hero-section">
      <!-- Navbar -->
      <div class="navbar-container">
        <nav class="navbar navbar-expand-lg navbar-light transparent-background">
          <a class="navbar-brand" href="#">
            <img src="../../assets/gameshopLogo.jpg" alt="Your Logo" height="60">
          </a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Home">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageEmployees">Employees</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePolicy">Policies</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGames">Games</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGameRequests">Game Requests</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePromotion">Promotions</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Account">Account</a>
              </li>
              <li class="nav-item active">
                <a class="nav-link" href="#">Orders (Current)</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="LogOut">Logout</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>

      <!-- Page Content -->
      <div class="container content-container">
        <div class="row mt-5">
          <div class="col-md-12">
            <div class="card viewOrders-container shadow">
              <div class="card-header text-center">
                <h3>ORDERS</h3>
              </div>
              <div class="card-body">
                <div class="table-scroll">
                  <table class="table table-hover">
                    <thead>
                      <tr>
                        <th>Order ID</th>
                        <th>Customer</th>
                        <th>Games Ordered</th>
                        <th>Order Date</th>
                        <th>Card Number</th>
                        <th>Card Expiry</th>
                        <th>Address</th>
                        <th>Cancel Order</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(order, index) in orders" :key="index">
                        <td>{{ order.orderId }}</td>
                        <td>{{ customers[index].email }}</td>
                        <td>{{ gameTitles[index] }}
                          
                        </td>
                        <td>{{ order.date }}</td>
                        <td>{{ customers[index].cardNumber  }}</td>
                        <td>{{ customers[index].cardExpiryDate }}</td>
                        <td>{{ customers[index].address }}</td>
                        <td>
                          <button
                            class="btn btn-danger btn-sm"
                            @click.stop="cancelOrder(order.orderId)"
                          >
                            Cancel
                          </button>
                        </td>
                      </tr>
                      <tr v-if="orders.length === 0">
                        <td colspan="8" class="text-center">No orders found.</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
const CBASE_URL = 'http://localhost:8060/customers';
const BASE_URL = 'http://localhost:8060/order';
const CUSTOMER_DETAILS_API = 'http://localhost:8060/customersEmail/';
const ORDER_ITEMS_API = 'http://localhost:8060/orderitems/';

export default {
  name: 'ManagerViewOrders',
  data() {
    return {
      orders: [],
      managerEmail: '',
      customers: [],
      gameTitles: [],
    };
  },
  methods: {
    async fetchOrders() {
  try {
    // Fetch all orders
    const response = await axios.get(`${BASE_URL}/all`);
    const orders = response.data.orders;
    console.log('Fetched orders:', orders);
    this.orders = orders

    // Check if orders are returned
    if (!orders || orders.length === 0) {
      console.log('No orders found.');
      this.orders = [];
      return;
    }


    //loop through orders and get game titles
    for (let i = 0; i < orders.length; i++) {
      const order = orders[i];
      const orderId = order.orderId;
      const customerId= order.customerId;
      //get customer info
      const response1 = await axios.get(`${CBASE_URL}/id/${customerId}`);
      console.log("resopnse1", response1);
      const customa = response1.data;
      console.log("customa", customa);
      this.customers[i] = customa;
      const response2 = await axios.get(`${BASE_URL}items/${orderId}`);
      const orderItems = response2.data.orderitems[0];
      this.gameTitles[i] = orderItems.gameTitle;
    }


  } catch (error) {
    console.error('Error fetching orders:', error);
    alert('Failed to load orders.');
    this.orders = [];
  }
},
    
    async cancelOrder(orderId) {
      try {
        const isConfirmed = window.confirm('Are you sure you want to cancel this order?');
        if (!isConfirmed) {
          return; // exit if user cancels
        }

        console.log(`Attempting to cancel order with ID: ${orderId}`);
        await axios.delete(`${BASE_URL}/${orderId}`);
        alert('Order has been canceled!');
        this.fetchOrders(); // refresh orders list
      } catch (error) {
        console.error('Error canceling order:', error);
        alert('Failed to cancel order.');
      }
    },
    async ManageEmployees() {
      await this.$router.push({path: '/ManageEmployees/' + this.managerEmail})
    },
    async ManagePolicy() {
      await this.$router.push({path: '/ManagePolicy/' + this.managerEmail})
    },
    async ManageGames() {
      await this.$router.push({path: '/ManageGames/' + this.managerEmail})
    },
    async ManageGameRequests() {
      await this.$router.push({path: '/ManageGameRequests/' + this.managerEmail})
    },
    async Account() {
      await this.$router.push({path: '/ManagerAccount/' + this.managerEmail})
    },
    async ViewOrders() {
      await this.$router.push({path: '/ViewOrders/' + this.managerEmail})
    },
    async LogOut() {
      alert('Successfully logged out.')
      await this.$router.push({name: 'home'})
    },
    async ManagePromotion() {
      await this.$router.push({path: '/ManagerPromotion/' + this.managerEmail})
    },
    async Home(){
      await this.$router.push({path: '/ManagerHome/' + this.managerEmail})
    },
  },
  mounted() {
    console.log("route", this.$route);
    this.managerEmail = this.$route.params.email;
    this.fetchOrders();
  },
};
</script>

<style scoped>
.navbar-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
}

.navbar-brand {
  margin-right: 0;
}

.clickable-text:hover {
  cursor: pointer;
  color: white !important;
}

.nav-link:hover {
   cursor: pointer;
 }

.transparent-background {
  background-color: rgba(255, 255, 255, 0.3);
}

.hero-section {
  background: url("@/assets/gameshopBackground.jpg") center/cover no-repeat;
  padding: 200px 0;
  text-align: center;
  min-height: 100vh;
}

.content-container {
  margin-top: 200px;
}

.viewOrders-container {
  background-color: white;
  border-radius: 10px;
  padding: 20px;
}

.table-scroll {
  height: 300px;
  overflow-y: auto;
}

.prettylabel {
  color: #4e555b;
}

.navbar .nav-item.active > .nav-link {
  cursor: default;
  color: white !important; 
  pointer-events: none; 
}

.column-container {
  display: flex;
  flex-direction: column;
}
</style>
