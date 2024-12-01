<template>
    <div>
      <div id="main"></div>
      <div class="hero-section">
        <!-- Navbar -->
        <div class="navbar-container">
          <nav class="navbar navbar-expand-lg navbar-light transparent-background">
            <a class="navbar-brand" href="#">
              <img src="@/assets/gameshopLogo.jpg" alt="Your Logo" height="60" />
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
                  <a class="nav-link clickable-text" @click="ViewGames">View Games</a>
                </li>
                <li class="nav-item active">
                  <a class="nav-link" href="#">View Orders</a>
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
                          <th>Game Title</th>
                          <th>Order Date</th>
                          <th>Card Number</th>
                          <th>Card Expiry</th>
                          <th>Address</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr v-for="(order, index) in orders" :key="index">
                          <td>{{ order.id }}</td>
                          <td>
                            <div class="column-container">
                              <label>{{ order.customerName }}</label>
                              <label class="prettylabel">{{ order.customerEmail }}</label>
                            </div>
                          </td>
                          <td>{{ order.gameTitle }}</td>
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
  import axios from "axios";
  
  const axiosClient = axios.create({
    baseURL: "http://localhost:8080",
    headers: { "Access-Control-Allow-Origin": "*" },
  });
  
  export default {
    data() {
      return {
        orders: [],
      };
    },
    methods: {
      fetchOrders() {
        axiosClient
          .get("/orders")
          .then((response) => {
            this.orders = response.data.map((order) => ({
              id: order.id,
              customerName: order.customer.name,
              customerEmail: order.customer.email,
              gameTitle: order.game.title,
              orderDate: order.orderDate,
              cardNumber: order.payment.cardNumber,
              cardExpiry: order.payment.cardExpiry,
              address: order.address,
            }));
            console.log("Fetched orders:", this.orders);
          })
          .catch((error) => {
            console.error("Error fetching orders:", error);
          });
      },
      Home() {
        this.$router.push("/EmployeeHome");
      },
      Account() {
        this.$router.push("/EmployeeAccount");
      },
      ViewGames() {
        this.$router.push("/EmployeeViewGames");
      },
      LogOut() {
        alert("Successfully logged out.");
        this.$router.push("/home");
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
  
  .btn-sm {
    padding: 5px 10px;
    font-size: 14px;
  }
  
  .prettylabel {
    color: #4e555b;
  }
  
  .column-container {
    display: flex;
    flex-direction: column;
  }
  </style>
  