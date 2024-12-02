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
                <a class="nav-link clickable-text" @click="Home">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Account">Account</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="SubmitGameRequest">Submit Game Request</a>
              </li>
              <li class="nav-item active">
                <a class="nav-link clickable-text" @click="ViewGames">View Games</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">View Orders<span class="sr-only"></span></a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="LogOut">Log Out</a>
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
                      </tr>
                      <tr v-if="orders.length === 0">
                        <td colspan="7" class="text-center">No orders found.</td>
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
  name: 'EmployeeViewOrders',
  data() {
    return {
      orders: [],
      email: this.$route.params.param1,
      username: this.$route.params.param2,
    };
  },
  methods: {
    async fetchOrders() {
      try {
        // fetch all orders
        const response = await axios.get(`${BASE_URL}/all`);
        const orders = response.data.orders;

        // fetch additional details for each order, including customer information and order items
        const orderDetailsPromises = orders.map((order) =>
          Promise.all([
            axios
              .get(`${CUSTOMER_DETAILS_API}${order.customerEmail}`)
              .then((res) => res.data)
              .catch((error) => {
                console.error(`Failed to fetch customer details for email ${order.customerEmail}:`, error);
                return {
                  email: order.customerEmail,
                  address: 'Unknown',
                  cardNumber: 'Unknown',
                  cardExpiryDate: 'Unknown',
                };
              }),
            axios
              .get(`${ORDER_ITEMS_API}${order.orderId}`)
              .then((res) => res.data.orderitems)
              .catch((error) => {
                console.error(`Failed to fetch order items for orderId ${order.orderId}:`, error);
                return [];
              }),
          ])
        );

        const orderDetailsResults = await Promise.all(orderDetailsPromises);

        // combine orders with customer details and order items
        orders.forEach((order, index) => {
          const [customerDetails, orderItems] = orderDetailsResults[index];

          // assign the fetched customer details to the order
          order.customerEmail = customerDetails.email;
          order.address = customerDetails.address || 'Unknown';
          order.cardNumber = customerDetails.cardNumber || 'Unknown';
          order.cardExpiry = customerDetails.cardExpiryDate || 'Unknown';

          // assign game titles from the fetched order items
          order.gameTitles = orderItems.map((item) => item.gameTitle);

          // assign order date if available
          order.orderDate = order.date || 'Unknown';
        });

        this.orders = orders;
      } catch (error) {
        console.error('Error fetching orders:', error);
        alert('Failed to load orders.');
      }
    },
    async Home() {
      await this.$router.push({path: "/EmployeeHome/" + this.email + "/" + this.username});
    },
    async Account() {
      await this.$router.push({path: "/EmployeeAccount/" + this.email + "/" + this.username});
    },
    async SubmitGameRequest() {
      await this.$router.push({path: "/EmployeeGameRequest/" + this.email + "/" + this.username});
    },
    async ViewGames() {
      await this.$router.push({path: "/EmployeeViewGames/" + this.email + "/" + this.username});
    },
    async LogOut() {
      await this.$router.push({path: "/"});
    },
  },
  mounted() {
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
  background: url('../../assets/gameshopBackground.jpg') center/cover no-repeat;
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
