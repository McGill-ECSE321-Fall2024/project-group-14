<template>
  <div>
    <div id="main"></div>
    <div class="hero-section">
      <!-- Navbar -->
      <div class="navbar-container">
        <nav class="navbar navbar-expand-lg navbar-light transparent-background">
          <a class="navbar-brand" href="#">
            <img src="../../assets/gameshopLogo.jpg" alt="Your Logo" height="60" />
          </a>
          <button
            class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('Home')">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('ManageEmployees')">Manage Employees</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('ManagePolicy')">Manage Policy</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('ManageGames')">Manage Games</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('ManageGameRequests')">Manage Game Requests</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('Account')">Account</a>
              </li>
              <li class="nav-item active">
                <a class="nav-link" href="#">View Orders</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('LogOut')">Log Out</a>
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
                        <td>{{ order.customerEmail }}</td>
                        <td>
                          <ul>
                            <li v-for="(game, gameIndex) in order.gameTitles" :key="gameIndex">
                              {{ game }}
                            </li>
                          </ul>
                        </td>
                        <td>{{ order.orderDate }}</td>
                        <td>{{ order.cardNumber }}</td>
                        <td>{{ order.cardExpiry }}</td>
                        <td>{{ order.address }}</td>
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

const BASE_URL = 'http://localhost:8060/order';
const CUSTOMER_DETAILS_API = 'http://localhost:8060/customersEmail/';
const ORDER_ITEMS_API = 'http://localhost:8060/orderitems/';

export default {
  name: 'ManagerViewOrders',
  data() {
    return {
      orders: [],
      managerEmail: '',
    };
  },
  methods: {
    async fetchOrders() {
      try {
        console.log("Fetching all orders...");
        // fetch all orders
        const response = await axios.get(`${BASE_URL}/all`);
        const orders = response.data.orders;

        // fetch additional details for each order, including customer information and order items
        const orderDetailsPromises = orders.map((order) =>
          Promise.all([
            this.fetchCustomerDetails(order.customerEmail),
            this.fetchOrderItems(order.orderId),
          ])
        );

        const orderDetailsResults = await Promise.all(orderDetailsPromises);

        // combine orders with customer details and order items
        orders.forEach((order, index) => {
          const [customerDetails, orderItems] = orderDetailsResults[index];

          // assign the fetched customer details to the order
          order.customerEmail = customerDetails.email || order.customerEmail;
          order.address = customerDetails.address || 'Unknown';
          order.cardNumber = customerDetails.cardNumber || 'Unknown';
          order.cardExpiry = customerDetails.cardExpiryDate || 'Unknown';

          // assign game titles from the fetched order items
          order.gameTitles = orderItems.map((item) => item.gameTitle);

          // assign order date if available
          order.orderDate = order.date || 'Unknown';
        });

        this.orders = orders;
        console.log("Orders successfully fetched and updated: ", this.orders);
      } catch (error) {
        console.error('Error fetching orders:', error);
        alert('Failed to load orders.');
      }
    },
    async fetchCustomerDetails(email) {
      try {
        console.log(`Fetching customer details for email: ${email}`);
        const response = await axios.get(`${CUSTOMER_DETAILS_API}${email}`);
        console.log(`Successfully fetched customer details for email: ${email}`, response.data);
        return response.data;
      } catch (error) {
        console.error(`Failed to fetch customer details for email ${email}:`, error);
        return {
          email: email,
          address: 'Unknown',
          cardNumber: 'Unknown',
          cardExpiryDate: 'Unknown',
        };
      }
    },
    async fetchOrderItems(orderId) {
      try {
        console.log(`Fetching order items for orderId: ${orderId}`);
        const response = await axios.get(`${ORDER_ITEMS_API}${orderId}`);
        console.log(`Successfully fetched order items for orderId: ${orderId}`, response.data.orderitems);
        return response.data.orderitems;
      } catch (error) {
        console.error(`Failed to fetch order items for orderId ${orderId}:`, error);
        return [];
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
    navigateTo(route) {
      const routes = {
        Home: `/ManagerHome/${this.managerEmail}`,
        ManageEmployees: `/ManageEmployees/${this.managerEmail}`,
        ManagePolicy: `/ManagePolicy/${this.managerEmail}`,
        ManageGames: `/ManageGames/${this.managerEmail}`,
        ManageGameRequests: `/ManageGameRequests/${this.managerEmail}`,
        Account: `/CustomerAccount/${this.managerEmail}`,
        ViewOrders: `/ViewOrders/${this.managerEmail}`,
        LogOut: '/',
      };
      if (route === 'LogOut') {
        alert('Successfully logged out.');
      }
      this.$router.push(routes[route]);
    },
  },
  mounted() {
    this.managerEmail = this.$route.params.email || '';
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

.nav-link {
  color: white !important;
}

.transparent-background {
  background-color: rgba(255, 255, 255, 0.2);
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

.column-container {
  display: flex;
  flex-direction: column;
}
</style>
