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
              <li class="nav-item active">
                <a class="nav-link" href="#">Account (Current)</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="SubmitGameRequest">Game Requests</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewGames">Games</a>
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
  name: "EmployeeAccount",
  data() {
    return {
      email: this.$route.params.param1,
      username: "",
      password: "", 
      errorMsg: "",
      id: null,
    };
  },
  created() {
    this.fetchEmployeeInfo();
  },
  methods: {
    async fetchEmployeeInfo() {
      try {
        const response = await axiosClient.get(`/employees/${this.email}`);
        const employee = response.data;
        this.id = employee.id;
        this.username = employee.username;
        this.password = employee.password || ""; 
      } catch (error) {
        this.errorMsg = `Error fetching employee info: ${
          error.response?.data?.message || error.message
        }`;
        alert(this.errorMsg);
      }
    },
    async EmployeeAccount() {
      await this.$router.push({ path: "/EmployeeAccount/" + this.email });
    },
    async LogOut() {
      await this.$router.push({ name: "home" });
    },
    async SubmitGameRequest() {
      await this.$router.push({
        path: "/EmployeeGameRequest/" + this.email + "/" + this.username,
      });
    },
    async ViewOrders() {
      await this.$router.push({
        path: "/EmployeeViewOrders/" + this.email + "/" + this.username,
      });
    },
    async ViewGames() {
      await this.$router.push({
        path: "/EmployeeViewGames/" + this.email + "/" + this.username,
      });
    },
    async Home() {
      await this.$router.push({
        path: "/EmployeeHome/" + this.email + "/" + this.username,
      });
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
