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
              <li class="nav-item active">
                <a class="nav-link" href="#">Game Requests (Current)</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePromotion">Promotions</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Account">Account</a>
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
                        <th>Employee Email</th>
                        <th>Title</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(request, index) in gameRequests" :key="index">
                        <td>{{ request.id }}</td>
                        <td>{{ request.employeeEmail || "Unknown" }}</td>
                        <td>{{ request.name }}</td>
                        <td>{{ request.category }}</td>
                        <td>{{ request.description }}</td>
                        <td :class="{ 'text-success': request.status === 'Approved', 'text-danger': request.status === 'Rejected' }">
                          {{ request.status }}
                        </td>
                        <td>
                          <div v-if="request.status === 'PendingApproval'">
                            <button
                              class="btn btn-success btn-sm"
                              @click.stop="approveRequest(request, index)"
                            >
                              Approve
                            </button>
                            <button
                              class="btn btn-danger btn-sm"
                              @click.stop="rejectRequest(request, index)"
                            >
                              Reject
                            </button>
                          </div>
                          <div v-else>
                            <button
                              class="btn btn-danger btn-sm"
                              @click.stop="deleteRequest(request.id, index)"
                            >
                              Delete Request
                            </button>
                          </div>
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
  baseURL: "http://localhost:8060",
  headers: { "Access-Control-Allow-Origin": "*" },
});

export default {
  data() {
    return {
      gameRequests: [],
      managerEmail: "",
    };
  },
  methods: {
    async fetchGameRequests() {
      try {
        const response = await axiosClient.get("/gameapproval");
        const requests = response.data;

        const enrichedRequests = await Promise.all(
          requests.map(async (request) => {
            try {
              const employeeResponse = await axiosClient.get(
                `/employees/id/${request.requestCreatorId}`
              );
              const employee = employeeResponse.data;
              return {
                ...request,
                employeeEmail: employee.email || "Unknown",
              };
            } catch {
              return {
                ...request,
                employeeEmail: "Unknown",
              };
            }
          })
        );

        this.gameRequests = enrichedRequests;
      } catch (error) {
        console.error("Error fetching game requests:", error);
        alert("Failed to fetch game requests.");
      }
    },
    async approveRequest(request, index) {
      
  try {
    // Update the request status to 'Approved'
    const updatedRequest = {
      ...request,
      status: "Approved",
    };

    // Send the updated request to the backend
    await axiosClient.put(`/gameapproval/${request.id}`, updatedRequest);

    // Create a new game based on the approved request
    const newGame = {
      name: request.name,
      description: request.description,
      category: request.category,
      price: request.price || 0, // Default price
      rating: request.rating || 0, // Default rating
      quantity: 100, // Default quantity
      picture: request.picture || "default-image.jpg", // Default picture if missing
    };

    await axiosClient.post("/game", newGame);

    // Notify success and refresh the request list
    alert(`Request ${request.id} has been approved. Please edit game info in Games. `);
    this.fetchGameRequests(); // Refresh the list of game requests
  } catch (error) {
    console.error(`Error approving request ${request.id}:`, error);
    alert("Failed to approve the request. Please try again.");
  }
},

    async rejectRequest(request, index) {
      if (!confirm(`Are you sure you want to reject request ${request.id}?`)) return;

      try {
        // update the request status to Rejected 
        const updatedRequest = {
          ...request,
          status: "Rejected",
        };
        
        await axiosClient.put(`/gameapproval/${request.id}`, updatedRequest);

        alert(`Request ${request.id} has been rejected.`);
        this.fetchGameRequests();
      } catch (error) {
        console.error(`Error rejecting request ${request.id}:`, error);
        alert(`Failed to reject request ${request.id}.`);
      }
    },
    async deleteRequest(requestId, index) {
      if (!confirm(`Are you sure you want to delete request ${requestId}?`)) return;

      try {
        // delete the request from the backend
        await axiosClient.delete(`/gameapproval/${requestId}`);
        alert(`Request ${requestId} has been deleted.`);
        
        // remove the request from the local list to update the UI
        this.gameRequests.splice(index, 1);
      } catch (error) {
        console.error(`Error deleting request ${requestId}:`, error);
        alert(`Failed to delete request ${requestId}.`);
      }
    },
    async ManageEmployees() {
      await this.$router.push({path: '/ManageEmployees/' + this.managerEmail})
    },
    async ManagePolicy() {
      await this.$router.push({path: '/ManagePolicy/' + this.managerEmail})
    },
    async ManageGames() {
      await this.$router.push({path: '/ManageGames/' + this.managerEmail})
    },
    async ManageGameRequests() {
      await this.$router.push({path: '/ManageGameRequests/' + this.managerEmail})
    },
    async Account() {
      await this.$router.push({path: '/ManagerAccount/' + this.managerEmail})
    },
    async ViewOrders() {
      await this.$router.push({path: '/ViewOrders/' + this.managerEmail})
    },
    async LogOut() {
      alert('Successfully logged out.')
      await this.$router.push({name: 'home'})
    },
    async ManagePromotion() {
      await this.$router.push({path: '/ManagerPromotion/' + this.managerEmail})
    },
    async Home(){
      await this.$router.push({path: '/ManagerHome/' + this.managerEmail})
    },
  },
  mounted() {
    this.managerEmail = this.$route.params.email || "";
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

.clickable-text:hover {
  cursor: pointer;
  color: white !important;
}

.nav-link:hover {
   cursor: pointer;
 }

 .navbar .nav-item.active > .nav-link {
  cursor: default;
  color: white !important; 
  pointer-events: none; 
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

.text-success {
  color: green;
}

.text-danger {
  color: red;
}
</style>
