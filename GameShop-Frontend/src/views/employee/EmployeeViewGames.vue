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
          <!-- View Games Section -->
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
                        <td colspan="8" class="text-center">No games found.</td>
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
  name: "EmployeeViewGames",
  data() {
    return {
      games: [], 
      editingGameId: null, 
      personId: 123, 
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
    async saveGame(game) {
      if (this.editingGameId !== game.id) return;

      try {
        const updatedGame = {
          id: game.id,
          name: game.name,
          description: game.description,
          category: game.category,
          price: game.price,
          rating: game.rating,
        };
        await axios.post(`${backendUrl}/game/updatebyid`, updatedGame);
        alert("Game updated successfully.");
        this.editingGameId = null;
        this.fetchAllGames();
      } catch (error) {
        console.error("Error saving game:", error);
        alert("Failed to save the game.");
      }
    },
    async deleteGame(gameId) {
      const confirmed = confirm("Are you sure you want to delete this game?");
      if (!confirmed) return;

      try {
        await axios.delete(`${backendUrl}/game`, {
          data: { id: gameId, personId: this.personId },
        });
        alert("Game deleted successfully.");
        this.fetchAllGames();
      } catch (error) {
        console.error("Error deleting game:", error);
        alert("Failed to delete the game.");
      }
    },
    async Home() {
      this.$router.push("/home");
    },
    async Employee() {
      this.$router.push(`/EmployeeAccount/${this.$route.params.param1}/${this.$route.params.param2}`);
    },
    async SubmitGameRequest() {
      this.$router.push(`/EmployeeGameRequest/${this.$route.params.param1}/${this.$route.params.param2}`);
    },
    async ViewOrders() {
      this.$router.push(`/EmployeeViewOrders/${this.$route.params.param1}/${this.$route.params.param2}`);
    },
    async LogOut() {
      alert("Successfully logged out.");
      this.$router.push("/home");
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
</style>
