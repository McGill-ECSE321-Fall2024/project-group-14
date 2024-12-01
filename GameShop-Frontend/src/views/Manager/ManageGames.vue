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
                <a class="nav-link clickable-text" @click="ManageEmployees">Manage Employees</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePolicy">Manage Policy</a>
              </li>
              <li class="nav-item active">
                <a class="nav-link" href="#">Manage Games</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGameRequests">Manage Game Requests</a>
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
                        <th>Number</th>
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
    };
  },
  mounted() {
    this.fetchAllGames();
  },
  methods: {
    async fetchAllGames() {
      try {
        const response = await axios.get(`${backendUrl}/games`);
        this.games = response.data.games || [];
      } catch (error) {
        console.error("Error fetching games:", error);
        alert("Failed to fetch games.");
      }
    },
    enableEditing(gameId) {
      this.editingGameId = gameId;
    },
    async saveGame(gameId) {
  let name = document.getElementById(`name${gameId}`).value;
  let description = document.getElementById(`description${gameId}`).value;
  let category = document.getElementById(`category${gameId}`).value;
  let price = document.getElementById(`price${gameId}`).value;
  let rating = document.getElementById(`rating${gameId}`).value;

  const gameRequest = {
    id: gameId,
    name: name,
    description: description,
    category: category,
    price: parseFloat(price), 
    rating: rating,
    quantity: 0, 
    picture: "",
  };

  console.log("Payload sent to backend for saving:", gameRequest); 

  try {
    const response = await axios.post(`${backendUrl}/game/updatebyid`, gameRequest);
    console.log("Save response from backend:", response.data); 
    alert("Game updated successfully.");
    this.editingGameId = null;
    this.fetchAllGames();
  } catch (error) {
    console.error("Error saving game:", error);
    alert(`Failed to save the game: ${error.response?.data || error.message}`);
  }
},


    enableEditing(gameId) {
      this.editingGameId = gameId;
      document.getElementById(`name${gameId}`).removeAttribute("readonly");
      document.getElementById(`description${gameId}`).removeAttribute("readonly");
      document.getElementById(`category${gameId}`).removeAttribute("readonly");
      document.getElementById(`price${gameId}`).removeAttribute("readonly");
      document.getElementById(`rating${gameId}`).removeAttribute("readonly");
      document.getElementById(`save${gameId}`).style.display = "inline";
      document.getElementById(`edit${gameId}`).style.display = "none";
    }
  },
  async deleteGame(gameId) {
    const confirmed = confirm("Are you sure you want to delete this game?");
    if (!confirmed) return;

    try {
      const gameToDelete = this.games.find((game) => game.id === gameId);
      if (!gameToDelete) {
        alert("Game not found.");
        return;
      }

      const deletePayload = {
        name: gameToDelete.name,
        personId: this.personId, 
      };

      await axios.delete(`${backendUrl}/game`, { data: deletePayload });
      alert("Game deleted successfully.");
      this.fetchAllGames();
    } catch (error) {
      console.error("Error deleting game:", error);
      alert(`Failed to delete the game: ${error.response?.data || error.message}`);
    }
  },
    viewReviews(gameId) {
      alert(`Viewing reviews for game ID: ${gameId}`);
    },
    async Home() {
      this.$router.push("/home");
    },
    async ManageEmployees() {
      this.$router.push("/ManageEmployees");
    },
    async ManagePolicy() {
      this.$router.push("/ManagePolicy");
    },
    async ManageGameRequests() {
      this.$router.push("/ManageGameRequests");
    },
    async Account() {
      this.$router.push("/account");
    },
    async ViewOrders() {
      this.$router.push("/orders");
    },
    async LogOut() {
      alert("Successfully logged out.");
      this.$router.push("/home");
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
