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
                <a class="nav-link clickable-text" @click="SubmitGameRequest">Game Requests</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Games<span class="sr-only"> (Current)</span></a>
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
                      <th>Quantity</th>
                      <th>Update</th>
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
                        <input
                            v-model="game.quantity"
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
      email: "",
      username: ""
    };
  },
  mounted() {
    this.email = this.$route.params.param1 || "";
    this.username = this.$route.params.param2 || "";
    this.fetchAllGames();
  },
  methods: {
    async fetchAllGames() {
      try {
        console.log("Fetching all games...");
        const response = await axios.get(`${backendUrl}/games`);
        this.games = response.data.games || []; 
        console.log("Games fetched successfully:", this.games);
      } catch (error) {
        console.error("Error fetching games:", error);
        alert("Failed to fetch games.");
      }
    },

    // enable editing for a specific game
    enableEditing(gameId) {
      this.editingGameId = gameId;
    },

    // save the updated game
    async saveGame(game) {
      const requestBody = {
        id: game.id,
        name: game.name,
        description: game.description,
        category: game.category,
        price: game.price,
        rating: game.rating,
        quantity: game.quantity,
        picture: "image-url.jpg", // placeholder for picture
      };

      try {
        console.log("Updating game with:", requestBody);
        await axios.post(`${backendUrl}/game/updatebyid`, requestBody);
        alert(`Game "${game.name}" has been successfully updated.`);
        this.editingGameId = null; 
        this.fetchAllGames(); 
      } catch (error) {
        console.error("Error updating game:", error);
        alert("Failed to update the game.");
      }
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


.transparent-background {
  background-color: rgba(255, 255, 255, 0.3);
}

.hero-section {
  background: url('../../assets/gameshopBackground.jpg') center/cover no-repeat;
  padding: 200px 0;
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

.clickable-text:hover {
  cursor: pointer;
  color: white !important;
}

.selected-row {
  background-color: #f0f0f0;
}
</style>
