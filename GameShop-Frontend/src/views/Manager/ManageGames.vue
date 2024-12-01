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
                <a class="nav-link clickable-text" @click="navigateTo('Home')">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('ManageEmployees')">Manage Employees</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('ManagePolicy')">Manage Policy</a>
              </li>
              <li class="nav-item active">
                <a class="nav-link" href="#">Manage Games</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('ManageGameRequests')">Manage Game Requests</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('Account')">Account</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('ViewOrders')">View Orders</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="navigateTo('LogOut')">Log Out</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>

      <!-- Page Content -->
      <div class="container content-container">
        <div class="row mt-5">
          <!-- Manage Current Games Section -->
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
                      <th>Id</th>
                      <th>Title</th>
                      <th>Price</th>
                      <th>Category</th>
                      <th>Description</th>
                      <th>Rating</th>
                      <th>View Reviews</th>
                      <th>Update</th>
                      <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr
                        v-for="(game, index) in games"
                        :key="game.id"
                        :class="{ 'selected-row': editingGameId === game.id }"
                    >
                      <td>{{ game.id }}</td>
                      <td>
                        <input
                            v-model="game.name"
                            :readonly="editingGameId !== game.id"
                            class="form-control"
                        />
                      </td>
                      <td>
                        <input
                            v-model="game.price"
                            :readonly="editingGameId !== game.id"
                            class="form-control"
                        />
                      </td>
                      <td>
                        <input
                            v-model="game.category"
                            :readonly="editingGameId !== game.id"
                            class="form-control"
                        />
                      </td>
                      <td>
                          <textarea
                              v-model="game.description"
                              :readonly="editingGameId !== game.id"
                              class="form-control"
                          ></textarea>
                      </td>
                      <td>
                        <input
                            v-model="game.rating"
                            :readonly="editingGameId !== game.id"
                            class="form-control"
                        />
                      </td>
                      <td>
                        <button
                            class="btn btn-info btn-sm"
                            @click="viewReviews(game.id)"
                        >
                          View
                        </button>
                      </td>
                      <td>
                        <button
                            class="btn btn-warning btn-sm"
                            v-if="editingGameId !== game.id"
                            @click="enableEditing(game.id)"
                        >
                          Edit
                        </button>
                        <button
                            class="btn btn-success btn-sm"
                            v-else
                            @click="saveGame(game)"
                        >
                          Save
                        </button>
                      </td>
                      <td>
                        <button
                            class="btn btn-danger btn-sm"
                            @click="deleteGame(game.id)"
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                    <tr v-if="games.length === 0">
                      <td colspan="9" class="text-center">No games found.</td>
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

const backendUrl = "http://localhost:8060";
export default {
  name: "ManageGames",
  data() {
    return {
      games: [],
      editingGameId: null,
      managerEmail: "",
    };
  },
  mounted() {
    this.managerEmail = this.$route.params.email || "";
    this.fetchAllGames();
  },
  methods: {
    // Fetch all games from the server
    async fetchAllGames() {
      try {
        const response = await axios.get(`${backendUrl}/games`);
        this.games = response.data.games || [];
      } catch (error) {
        console.error("Error fetching games:", error);
        alert("Failed to fetch games.");
      }
    },

    // Enable editing for a specific game
    enableEditing(gameId) {
      this.editingGameId = gameId;
    },

    // Save the updated game
    saveGame(game) {
      const requestBody = {
        id: game.id,
        name: game.name,
        description: game.description,
        category: game.category,
        price: game.price,
        rating: game.rating,
        quantity: 200, // Set default quantity
        picture: "image-url.jpg", // Placeholder picture
      };

      console.log("Updating game with:", requestBody);

      axios
          .post(`${backendUrl}/game/updatebyid`, requestBody)
          .then(() => {
            alert(`Game "${game.name}" has been successfully updated.`);
            this.editingGameId = null; // Exit editing mode
            this.fetchAllGames(); // Refresh the game list
          })
          .catch((error) => {
            console.error("Error updating game:", error);
            alert("Failed to update the game.");
          });
    },

    // Delete a game
    deleteGame(gameId) {
      const game = this.games.find((g) => g.id === gameId);
      if (!game) {
        console.error(`Game with ID ${gameId} not found.`);
        return;
      }

      axios
          .get(`${backendUrl}/managers`)
          .then((response) => {
            const personId = response.data.personId;
            console.log("Fetched personId:", personId);

            if (confirm(`Are you sure you want to delete the game "${game.name}"?`)) {
              const requestBody = {
                name: game.name,
                personId: personId,
              };

              console.log("Deleting game with:", requestBody);

              axios
                  .delete(`${backendUrl}/game`, { data: requestBody })
                  .then(() => {
                    alert(`Game "${game.name}" has been successfully deleted.`);
                    this.fetchAllGames();
                  })
                  .catch((error) => {
                    console.error("Error deleting game:", error);
                    alert("Failed to delete the game.");
                  });
            }
          })
          .catch((error) => {
            console.error("Error fetching personId:", error);
            alert("Failed to fetch manager information.");
          });
    },

    navigateTo(route) {
      const routes = {
        Home: `/ManagerHome/${this.managerEmail}`,
        ManageEmployees: `/ManageEmployees/${this.managerEmail}`,
        ManagePolicy: `/ManagePolicy/${this.managerEmail}`,
        ManageGames: `/ManageGames/${this.managerEmail}`,
        ManageGameRequests: `/ManageGameRequests/${this.managerEmail}`,
        Account: `/CustomerAccount/${this.managerEmail}`,
        ViewOrders: `/ViewOrders/${this.managerEmail}`,
        LogOut: "/",
      };
      if (route === "LogOut") {
        alert("Successfully logged out.");
      }
      this.$router.push(routes[route]);
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
