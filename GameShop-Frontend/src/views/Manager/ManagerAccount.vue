<template>
  <div>
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
              <li class="nav-item active">
                <a class="nav-link" href="#">Account (Current)</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewOrders">Orders</a>
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
        <div class="row mt-5 justify-content-center">
          <div class="col-md-8 col-lg-6">
            <div class="card account-details-container shadow">
              <div class="card-header text-center">
                <h3>ACCOUNT DETAILS</h3>
              </div>
              <div class="card-body">
                <div class="d-flex justify-content-center mb-4">
                  <img class="rounded-circle profile-picture" src="../../assets/gameshopLogo.jpg" alt="Profile Photo">
                </div>
                <div class="row mt-3 justify-content-center">
                  <div class="col-md-8 mb-3">
                    <label class="labels">Username</label>
                    <input class="form-control smaller-input" id="username" v-model="username" readonly />
                  </div>
                  <div class="col-md-8 mb-3">
                    <label class="labels">Email</label>
                    <input class="form-control smaller-input" id="email" v-model="email" readonly />
                  </div>
                  <div class="col-md-8">
                    <label class="labels">Password</label>
                    <input class="form-control smaller-input" id="password" v-model="password" readonly />
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
      password: "", // added password field
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
    this.username = manager.personUsername; // Adjusted for backend field naming
    this.password = manager.personPassword || ""; // Adjusted for backend field naming
  } catch (error) {
    const errorDetails =
      error.response?.data?.message || error.response?.data || error.message || "Unknown error";
    this.errorMsg = `Error fetching manager info: ${errorDetails}`;
    alert(this.errorMsg);
  }

    },
    async ManageEmployees() {
      await this.$router.push({ path: '/ManageEmployees/' + this.email });
    },
    async ManagePolicy() {
      await this.$router.push({ path: '/ManagePolicy/' + this.email });
    },
    async ManageGames() {
      await this.$router.push({ path: '/ManageGames/' + this.email });
    },
    async ManageGameRequests() {
      await this.$router.push({ path: '/ManageGameRequests/' + this.email });
    },
    async Account() {
      await this.$router.push({ path: '/ManagerAccount/' + this.email });
    },
    async ViewOrders() {
      await this.$router.push({ path: '/ViewOrders/' + this.email });
    },
    async LogOut() {
      alert('Successfully logged out.');
      await this.$router.push({ name: 'home' });
    },
    async ManagePromotion() {
      await this.$router.push({ path: '/ManagerPromotion/' + this.email });
    },
    async Home() {
      await this.$router.push({ path: '/ManagerHome/' + this.email });
    },
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
  margin-top: 100px;
}

.account-details-container {
  background-color: white;
  border-radius: 10px;
  padding: 20px;
}

.labels {
  font-size: 14px;
  color: #444;
}

.navbar .nav-item.active > .nav-link {
  cursor: default;
  color: white !important; 
  pointer-events: none; 
}

.smaller-input {
  max-width: 300px; 
  margin: 0 auto;
}

.profile-picture {
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 50%;
}
</style>
