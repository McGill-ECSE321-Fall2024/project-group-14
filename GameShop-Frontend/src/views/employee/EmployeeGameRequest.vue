<template>
  <div>
    <div id="main"></div>
    <div class="hero-section">
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
              <li class="nav-item active">
                <a class="nav-link clickable-text" @click="Home">Home<span class="sr-only"></span></a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Account">Account</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="SubmitGameRequest">Submit Game Request</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewGames">View Games</a>
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

      <div class="table-container">
        <div class="container mt-5">
          <div class="row">
            <!-- Game Request Form -->
            <div class="col-lg-5 mb-5">
              <div class="prettyheader">
                <h3>SUBMIT GAME REQUEST</h3>
              </div>
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
                <button type="submit" class="btn btn-primary submitbutton">Submit</button>
              </form>
            </div>

            <!-- Game Request List -->
            <div class="col-lg-7">
              <div class="prettyheader">
                <h3>GAME REQUEST LIST</h3>
              </div>
              <table class="table table-bordered">
                <thead>
                <tr>
                  <th>ID</th>
                  <th>Game Name</th>
                  <th>Description</th>
                  <th>Category</th>
                  <th>Status</th>
                  <th>Creator ID</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="request in gameRequests" :key="request.id">
                  <td>{{ request.id }}</td>
                  <td>{{ request.name }}</td>
                  <td>{{ request.description }}</td>
                  <td>{{ request.category }}</td>
                  <td>{{ request.status }}</td>
                  <td>{{ request.requestCreatorId }}</td>
                </tr>
                </tbody>
              </table>
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
    async Home() {
      await this.$router.push({path: "/EmployeeHome/" + this.email + "/" + this.username});
    },
    async Account() {
      await this.$router.push({path: "/EmployeeAccount/" + this.email + "/" + this.username});
    },
    async SubmitGameRequest() {
      await this.$router.push({path: "/EmployeeGameRequest/" + this.email + "/" + this.username});
    },
    async ViewOrders() {
      await this.$router.push({path: "/EmployeeViewOrders/" + this.email + "/" + this.username});
    },
    async ViewGames() {
      await this.$router.push({path: "/EmployeeViewGames/" + this.email + "/" + this.username});
    },
    async LogOut() {
      await this.$router.push({path: "/"});
    },
  },
};
</script>

<style scoped>
.hero-section {
  background: url("../../assets/gameshopBackground.jpg") center/cover no-repeat;
  min-height: 100vh;
}

.navbar-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  background-color: rgba(255, 255, 255, 0.6); /* Transparent background */
}

.navbar .nav-link {
  color: white !important; /* White text for navbar links */
}

.navbar .nav-link:hover {
  color: #ddd !important; /* Light gray hover effect */
}

.table-container {
  background-color: rgba(255, 255, 255, 1);
  padding: 2%;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  position: absolute;
  top: 25%;
  left: 10%;
  right: 10%;
}

.prettyheader {
  font-family: "Montserrat", sans-serif;
  color: #888;
  letter-spacing: 2px;
  margin-bottom: 20px;
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
