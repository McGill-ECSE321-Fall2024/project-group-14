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
                  <a class="nav-link clickable-text" @click="Employee">Account</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" @click="SubmitGameRequest">Submit Game Request</a>
                </li>
                <li class="nav-item active">
                  <a class="nav-link" href="#">View Games</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" @click="ViewOrders">View Orders</a>
                </li>
                <li>
                  <a class="nav-link clickable-text" @click="LogOut">Log Out</a>
                </li>
              </ul>
            </div>
          </nav>
        </div>
  
        <!-- Page Content -->
        <div class="container content-container">
          <div class="row mt-5">
            <!-- View Current Games Section -->
            <div class="col-md-12">
              <div class="card viewGames-container shadow">
                <div class="card-header text-center">
                  <h3>GAMES</h3>
                </div>
                <div class="card-body">
                  <div class="table-scroll">
                    <table class="table table-hover">
                      <thead>
                        <tr>
                          <th>Number</th>
                          <th>Title</th>
                          <th>Price</th>
                          <th>Category</th>
                          <th>Description</th>
                          <th>Rating</th>
                          <th>Update</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr
                          v-for="(game, index) in games"
                          :key="index"
                          @click="selectGame(game.id)"
                          :class="{ 'selected-row': selectedGameId === game.id }"
                        >
                          <td>{{ game.id }}</td>
                          <td>{{ game.title }}</td>
                          <td>{{ game.price }}</td>
                          <td>{{ game.category }}</td>
                          <td>{{ game.description }}</td>
                          <td>{{ game.rating }}</td>
                          <td>
                            <button
                              class="btn btn-warning btn-sm"
                              @click.stop="editGame(game.id)"
                            >
                              Edit
                            </button>
                          </td>
                        </tr>
                        <tr v-if="games.length === 0">
                          <td colspan="7" class="text-center">No games found.</td>
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
        games: [], 
        selectedGameId: null,
      };
    },
    methods: {
      fetchAllGames() {
        axiosClient
          .get("/games")
          .then((response) => {
            this.games = response.data.dtos;
            console.log("Fetched games:", this.games); 
          })
          .catch((error) => {
            console.error("Error fetching games:", error);
          });
      },
      editGame(gameId) {
        alert(`Editing game ID: ${gameId}`);
        
      },
      selectGame(gameId) {
        this.selectedGameId = gameId;
      },
      Home() {
        this.$router.push("/home");
      },
      Employee() {
        this.$router.push(`/EmployeeAccount/${this.$route.params.param1}/${this.$route.params.param2}`);
      },
      SubmitGameRequest() {
        this.$router.push(`/EmployeeGameRequest/${this.$route.params.param1}/${this.$route.params.param2}`);
      },
      ViewOrders() {
        this.$router.push(`/EmployeeViewOrders/${this.$route.params.param1}/${this.$route.params.param2}`);
      },
      LogOut() {
        alert("Successfully logged out.");
        this.$router.push("/home");
      },
    },
    mounted() {
      this.fetchAllGames(); 
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
    background: url("../../assets/gameshopBackground.jpg") center/cover no-repeat;
    padding: 200px 0;
    text-align: center;
    min-height: 100vh; 
  }
  
  .content-container {
    margin-top: 200px;
  }
  
  .viewGames-container {
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
  
  .selected-row {
    background-color: #f0f0f0;
  }
  </style>
  
