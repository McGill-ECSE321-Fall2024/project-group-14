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
                <a class="nav-link clickable-text" @click="ManagerManageEmployees">Manage Employees</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePolicy">Manage Policy</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGames">Manage Games</a>
              </li>
              <li class="nav-item active">
                <a class="nav-link" href="#">Manage Game Requests</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Account">Account</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewOrders">View Orders</a>
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
            <div class="card viewGames-container shadow">
              <div class="card-header text-center">
                <h3>MANAGE GAME REQUESTS</h3>
              </div>
              <div class="card-body">
                <div class="table-scroll">
                  <table class="table table-hover">
                    <thead>
                      <tr>
                        <th>Request ID</th>
                        <th>Employee</th>
                        <th>Title</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Picture</th>
                        <th>Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(request, index) in gameRequests" :key="index">
                        <td>{{ request.id }}</td>
                        <td>
                          <div class="column-container">
                            <label>{{ request.employeeName }}</label>
                            <label class="prettylabel">{{ request.employeeEmail }}</label>
                          </div>
                        </td>
                        <td>{{ request.title }}</td>
                        <td>{{ request.category }}</td>
                        <td>{{ request.description }}</td>
                        <td>
                          <img :src="request.picture" alt="Game Picture" class="game-picture" />
                        </td>
                        <td>
                          <button
                            class="btn btn-success btn-sm"
                            @click.stop="updateStatus(request.id, 'Approved')"
                          >
                            Accept
                          </button>
                          <button
                            class="btn btn-danger btn-sm"
                            @click.stop="updateStatus(request.id, 'Rejected')"
                          >
                            Decline
                          </button>
                        </td>
                      </tr>
                      <tr v-if="gameRequests.length === 0">
                        <td colspan="7" class="text-center">No game requests found.</td>
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
      gameRequests: [], // List of game requests
    };
  },
  methods: {
    fetchGameRequests() {
      axiosClient
        .get("/gameapproval")
        .then((response) => {
          this.gameRequests = response.data.map((request) => ({
            id: request.id,
            employeeName: request.requestCreatorName,
            employeeEmail: request.requestCreatorEmail,
            title: request.name,
            category: request.category,
            description: request.description,
            picture: request.picture,
            status: request.status,
          }));
          console.log("Fetched game requests:", this.gameRequests); 
        })
        .catch((error) => {
          console.error("Error fetching game requests:", error);
        });
    },
    updateStatus(requestId, status) {
      axiosClient
        .put(`/gameapproval/${requestId}`, { status })
        .then(() => {
          alert(`Request has been ${status.toLowerCase()}!`);
          this.fetchGameRequests(); 
        })
        .catch((error) => {
          console.error(`Error updating status to ${status}:`, error);
        });
    },
    Home() {
      this.$router.push("/home");
    },
    ManageEmployees() {
      this.$router.push("/ManageEmployees");
    },
    ManagePolicy() {
      this.$router.push("/ManagePolicy");
    },
    ManageGames() {
      this.$router.push("/ManageGames");
    },
    Account() {
      this.$router.push("/account");
    },
    ViewOrders() {
      this.$router.push("/orders");
    },
    LogOut() {
      alert("Successfully logged out.");
      this.$router.push("/home");
    },
  },
  mounted() {
    this.fetchGameRequests(); 
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

.game-picture {
  width: 100px;
  height: auto;
}

.prettylabel {
  color: #4e555b;
}
</style>
