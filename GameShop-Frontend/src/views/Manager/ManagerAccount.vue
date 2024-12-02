<template>
    <div class="managerAccount">
      <div class="background">
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
              <li class="nav-item active">
                <a class="nav-link clickable-text" @click="Home">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageEmployees">Manage Employees</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePolicy">Manage Policy</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGames">Manage Games</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGameRequests">Manage Game Requests</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePromotion">Promotion</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#"> Account</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewOrders">View Orders</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="LogOut">LogOut</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>
  
        <div class="profile-box">
          <div class="container rounded bg-white mt-5 mb-5 account-box shadow">
            <div class="row">
              <div class="col-md-3 border-right">
                <div class="d-flex flex-column align-items-center justify-content-center text-center p-3 py-5 image-pos">
                  <img class="rounded-circle" width="100%" src="../../assets/gameshopLogo.jpg" alt="Profile Photo" />
                </div>
              </div>
              <div class="col-md-9">
                <div class="p-3 py-5">
                  <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="text-right" style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 3px">
                      ACCOUNT DETAILS
                    </h4>
                  </div>
                  <div class="image-pos">
                    <div class="row mt-3">
                      <div class="col-md-6">
                        <label class="labels">Username</label>
                        <input class="form-control" id="username" v-model="username" readonly />
                      </div>
                      <div class="col-md-6">
                        <label class="labels">Email</label>
                        <input class="form-control" id="email" v-model="email" readonly />
                      </div>
                      <div class="col-md-6">
                        <label class="labels">Password</label>
                        <input class="form-control" id="password" type="password" v-model="password" readonly />
                      </div>
                    </div>
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
  
  const backendUrl = "http://localhost:8060";
  const axiosClient = axios.create({
    baseURL: backendUrl,
    headers: { "Access-Control-Allow-Origin": "*" },
  });
  
  export default {
    name: "ManagerAccount",
    data() {
      return {
        email: this.$route.params.param1,
        username: "",
        password: "", // Added password field
        errorMsg: "",
        id: null,
      };
    },
    created() {
      this.fetchManagerInfo();
    },
    methods: {
      async fetchManagerInfo() {
        try {
          const response = await axiosClient.get(`/managers/${this.email}`);
          const manager = response.data;
          this.id = manager.id;
          this.username = manager.username;
          this.password = manager.password || "";  // Ensure password is fetched correctly
        } catch (error) {
          this.errorMsg = `Error fetching manager info: ${error.response?.data || error.message}`;
          alert(this.errorMsg);
        }
      },
      async ManageEmployees() {
      await this.$router.push({path: '/ManageEmployees/' + this.email})
    },
    async ManagePolicy() {
      await this.$router.push({path: '/ManagePolicy/' + this.email})
    },
    async ManageGames() {
      await this.$router.push({path: '/ManageGames/' + this.email})
    },
    async ManageGameRequests() {
      await this.$router.push({path: '/ManageGameRequests/' + this.email})
    },
    async Account() {
      await this.$router.push({path: '/ManagerAccount/' + this.email})
    },
    async ViewOrders() {
      await this.$router.push({path: '/ViewOrders/' + this.email})
    },
    async LogOut() {
      alert('Successfully logged out.')
      await this.$router.push({name: 'home'})
    },
    async ManagePromotion() {
      await this.$router.push({path: '/ManagerPromotion/' + this.email})
    },
    async Home(){
      await this.$router.push({path: '/ManagerHome/' + this.email})
    },
    },
  };
  </script>
  
  <style scoped>
  .background {
    background: url("../../assets/gameshopBackground.jpg") center center no-repeat;
    background-size: cover;
    height: 100vh;
  }
  
  .navbar-container {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
  }
  
  .profile-box {
    position: absolute;
    top: 15%;
    left: 20%;
    right: 20%;
  }
  
  .account-box {
    background-color: rgba(255, 255, 255, 0.8);
    padding: 20px;
    border-radius: 10px;
  }
  
  .image-pos {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  
  .labels {
    font-size: 12px;
    color: #444;
  }
  
  .nav-link {
    color: white !important;
  }
  </style>
  