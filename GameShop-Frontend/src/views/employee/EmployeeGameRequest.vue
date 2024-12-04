<template>
  <div>
    <div id="main"></div>
    <div class="hero-section">
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
                <a class="nav-link clickable-text" @click="Account">Account</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Game Requests<span class="sr-only"> (Current)</span></a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewGames">Games</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewOrders">Orders</a>
              </li>
              <li>
                <a class="nav-link clickable-text" @click="LogOut">Logout</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>

      <!-- Page Content -->
      <div class="container content-container">
        <div class="row mt-5">
          <!-- Submit Game Request Section -->
          <div class="col-md-5 mb-5">
            <div class="card viewGames-container shadow">
              <div class="card-header text-center">
                <h3>SUBMIT GAME REQUEST</h3>
              </div>
              <div class="card-body">
                <form @submit.prevent="submitGameRequest">
                  <div class="form-group">
                    <label for="name">Game Name:</label>
                    <input
                      type="text"
                      id="name"
                      v-model="gameRequest.name"
                      class="form-control"
                      placeholder="Enter the name of the game"
                      required
                    />
                  </div>
                  <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea
                      id="description"
                      v-model="gameRequest.description"
                      class="form-control"
                      placeholder="Enter a description of the game"
                      required
                    ></textarea>
                  </div>
                  <div class="form-group">
                    <label for="category">Category:</label>
                    <input
                      type="text"
                      id="category"
                      v-model="gameRequest.category"
                      class="form-control"
                      placeholder="Enter the game's category (e.g., Action, RPG)"
                      required
                    />
                  </div>
                  <button type="submit" class="btn btn-primary btn-block submitbutton">Submit</button>
                </form>
              </div>
            </div>
          </div>

          <!-- Game Request List Section -->
          <div class="col-md-7">
            <div class="card viewGames-container shadow">
              <div class="card-header text-center">
                <h3>GAME REQUEST LIST</h3>
              </div>
              <div class="card-body">
                <div class="table-scroll">
                  <table class="table table-hover">
                    <thead>
                      <tr>
                        <th>Request ID</th>
                        <th>Game Name</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(request, index) in gameRequests" :key="request.id">
                        <td>{{ request.id }}</td>
                        <td>{{ request.name }}</td>
                        <td>{{ request.category }}</td>
                        <td>{{ request.description }}</td>
                        <td :class="{ 'text-success': request.status === 'Approved', 'text-danger': request.status === 'Rejected' }">
                          {{ request.status }}
                        </td>
                      </tr>
                      <tr v-if="gameRequests.length === 0">
                        <td colspan="5" class="text-center">No game requests found.</td>
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

const backendUrl = "http://localhost:8060"; // Update as needed
const axiosClient = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": "*" },
});

export default {
  name: "EmployeeGameRequest",
  data() {
    return {
      email: this.$route.params.param1, // Email from the URL
      username: this.$route.params.param2, // Username from the URL
      employeeId: null, // Store the employee ID after fetching
      managerId: null, // Store the manager ID after fetching
      gameRequest: {
        name: "",
        description: "",
        category: "",
        requestCreatorId: null, // Will be dynamically set after fetching employee ID
        requestApproverId: null, // Will be dynamically set after fetching manager ID
        picture: "Picture", // Optional
      },
      gameRequests: [], // Game requests to display
    };
  },
  mounted() {
    this.fetchEmployeeId();
    this.fetchManagerId();
    this.fetchGameRequests();
  },
  methods: {
    async fetchEmployeeId() {
      try {
        const response = await axiosClient.get(`/employees/` + this.email);
        this.employeeId = response.data.id;
        this.gameRequest.requestCreatorId = this.employeeId;
        console.log("Employee ID fetched successfully:", this.employeeId);
      } catch (error) {
        console.error("Error fetching employee ID:", error);
        alert("Failed to fetch employee details. Please try again.");
      }
    },
    async fetchManagerId() {
      try {
        const response = await axiosClient.get(`/managers`);
        this.managerId = response.data.personId;
        this.gameRequest.requestApproverId = this.managerId;
        console.log("Manager ID fetched successfully:", this.managerId);
      } catch (error) {
        console.error("Error fetching manager ID:", error);
        alert("Failed to fetch manager details. Please try again.");
      }
    },
    async submitGameRequest() {
      try {
        console.log("Submitting game request with payload:", this.gameRequest);

        const response = await axiosClient.post("/gameapproval", this.gameRequest);
        alert("Game request submitted successfully!");

        this.gameRequests.push(response.data); // Add to the list dynamically
        this.resetForm(); // Reset form fields
      } catch (error) {
        console.error("Error submitting game request:", error);
        alert("Failed to submit game request.");
      }
    },
    async fetchGameRequests() {
      try {
        const response = await axiosClient.get("/gameapproval");
        this.gameRequests = response.data;
        console.log("Fetched game requests:", this.gameRequests);
      } catch (error) {
        console.error("Error fetching game requests:", error);
      }
    },
    resetForm() {
      this.gameRequest = {
        name: "",
        description: "",
        category: "",
        requestCreatorId: this.employeeId, // Keep employee ID
        requestApproverId: this.managerId, // Use fetched manager ID
        picture: "Picture",
      };
    },
    async Account() {
      await this.$router.push({path: '/EmployeeAccount/' + this.email })
    },
    async LogOut() {
      await this.$router.push({name: "home"})
    },
    async SubmitGameRequest() {
      await this.$router.push({path: '/EmployeeGameRequest/' + this.email + '/' + this.username})
    },
    async ViewOrders() {
      await this.$router.push({path: '/EmployeeViewOrders/' + this.email + '/' + this.username})
    },

    async ViewGames() {
      await this.$router.push({path: '/EmployeeViewGames/' + this.email + '/' + this.username})
    },
    async Home() {
      await this.$router.push({path: '/EmployeeHome/' + this.email+ '/' + this.username })
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
  background: url("../../assets/gameshopBackground.jpg") center/cover no-repeat;
  padding: 200px 0;
  text-align: center;
  min-height: 100vh;
}

.content-container {
  margin-top: 100px;
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

.text-success {
  color: green;
}

.text-danger {
  color: red;
}

.submitbutton {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  margin-top: 10px;
  border-radius: 5px;
  transition: all 0.3s;
}

.submitbutton:hover {
  background-color: #0056b3;
  color: white;
  transform: scale(1.05);
}
</style>
