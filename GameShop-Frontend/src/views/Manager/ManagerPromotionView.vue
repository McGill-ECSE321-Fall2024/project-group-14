<template>
  <div>
    <div class="hero-section">
      <!-- Navbar -->
      <div class="navbar-container">
        <nav class="navbar navbar-expand-lg navbar-light transparent-background">
          <a class="navbar-brand" href="#">
            <img src="../../assets/gameshopLogo.jpg" alt="Your Logo" height="60">
          </a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item active">
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
                <a class="nav-link" href="#">Promotions (Current)</a>
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
            <div class="card manage-promotions-container shadow">
              <div class="card-header text-center">
                <h3>MANAGE PROMOTIONS</h3>
              </div>
              <div class="card-body">
                <div class="table-scroll">
                  <table class="table table-hover">
                    <thead>
                      <tr>
                        <th>Promotion ID</th>
                        <th>Game ID</th>
                        <th>Discount</th>
                        <th>Description</th>
                        <th>Update</th>
                        <th>Delete</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="promotion in promotions" :key="promotion.id">
                        <td>{{ promotion.id }}</td>
                        <td>{{ promotion.gameId }}</td>
                        <td>{{ promotion.discount }}</td>
                        <td>{{ promotion.description }}</td>
                        <td>
                          <button class="btn btn-info btn-sm" @click="openUpdatePromotionPopup(promotion)">Update</button>
                        </td>
                        <td>
                          <button class="btn btn-danger btn-sm" @click="deletePromotion(promotion.id)">Delete</button>
                        </td>
                      </tr>
                      <tr v-if="promotions.length === 0">
                        <td colspan="6" class="text-center">No promotions found.</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div class="buttons-container text-center mt-4">
                  <button class="btn btn-success btn-sm" @click="openCreatePromotionPopup">Create Promotion</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="popup-container">
        <div class="overlay" v-if="showCreatePromotionPopup">
          <div class="popup">
            <h2 class="prettyheader">Create New Promotion</h2>

            <div class="inputrow">
              <label class="prettylabel">Game ID</label>
              <input v-model="currPromotion.gameId" type="text" id="newPromotionGameTitle"
                name="newPromotionGameTitle" class="styledinput" placeholder="Game id">
            </div>
            <div class="inputrow">
              <label class="prettylabel">Discount</label>
              <input v-model="currPromotion.discount" type="text" id="newPromotionDiscount"
                name="newPromotionDiscount" class="styledinput" placeholder="Discount percentage">
            </div>
            <div class="inputrow">
              <label class="prettylabel">Description</label>
              <input v-model="currPromotion.description" type="text" id="newPromotionDescription"
                name="newPromotionDescription" class="styledinput" placeholder="Description of promotion">
            </div>
            <div class="centerbuttoncontainer">
              <button class="btn btn-danger btn-sm" @click="closeCreatePromotionPopup">Close</button>
              <button class="btn btn-success btn-sm" @click="createPromotion" v-if="!isUpdate">Create</button>
              <button class="btn btn-success btn-sm" @click="updatePromotion" v-if="isUpdate">Update</button>
            </div>
            <div class="centerbuttoncontainer">
              <label>{{ errorMessage }}</label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

const BASE_URL = 'http://localhost:8060/promotion';

export default {
  name: 'PromotionManagement',
  data() {
    return {
      promotions: [],
      promotionDetails: null,
      showCreatePromotionPopup: false,
      errorMessage: "",
      currPromotion: {
        gameId: "",
        description: "",
        discount: 0,
      },
      isUpdate: false,
      currid: null,
      email: this.$route.params.email,
    }
  },

  methods: {
    openCreatePromotionPopup() {
      this.currPromotion = {
        gameId: "",
        description: "",
        discount: 0,
      }
      this.showCreatePromotionPopup = true;
      this.isUpdate = false;
      this.errorMessage = "";
    },

    openUpdatePromotionPopup(promotion) {
      this.currPromotion = {
        gameId: promotion.gameId,
        description: promotion.description,
        discount: promotion.discount,
      }
      this.currid = promotion.id;
      this.showCreatePromotionPopup = true;
      this.isUpdate = true;
    },

    closeCreatePromotionPopup() {
      this.showCreatePromotionPopup = false;
      this.errorMessage = "";
    },

    async fetchPromotions() {
      try {
        const response = await axios.get(`${BASE_URL}/promotions`);
        this.promotions = response.data.promotions;
      } catch (error) {
        this.errorMessage = error.response.data.message;
        console.error("Error fetching promotions: ", error);
      }
    },

    async createPromotion() {
      try {
        const payload = {
          description: this.currPromotion.description,
          discount: this.currPromotion.discount,
        }
        await axios.put(`${BASE_URL}/${this.currPromotion.gameId}`, payload);
        this.fetchPromotions();
        this.closeCreatePromotionPopup();
      } catch (error) {
        this.errorMessage = error.response?.data?.message || "Failed to create promotion.";
      }
    },

    async updatePromotion() {
      try {
        const payload = {
          description: this.currPromotion.description,
          discount: this.currPromotion.discount,
          gameId: this.currPromotion.gameId,
        }
        await axios.put(`${BASE_URL}/update/${this.currid}`, payload);
        this.fetchPromotions();
        this.closeCreatePromotionPopup();
      } catch (error) {
        this.errorMessage = error.response?.data?.message || "Failed to update promotion.";
      }
    },

    async deletePromotion(id) {
      try {
        await axios.delete(`${BASE_URL}/${id}`);
        this.fetchPromotions(); // refresh the list after deletion
      } catch (error) {
        this.errorMessage = `Could not delete promotion with ID ${id}.`;
      }
    },
    
    async Home() {
      await this.$router.push({ path: `/ManagerHome/${this.email}` });
    },
    async ManageEmployees() {
      await this.$router.push({ path: `/ManageEmployees/${this.email}` });
    },
    async ManagePolicy() {
      await this.$router.push({ path: `/ManagePolicy/${this.email}` });
    },
    async ManageGames() {
      await this.$router.push({ path: `/ManageGames/${this.email}` });
    },
    async ManageGameRequests() {
      await this.$router.push({ path: `/ManageGameRequests/${this.email}` });
    },
    async ManagePromotion() {
      await this.$router.push({ path: `/ManagerPromotion/${this.email}` });
    },
    async Account() {
      await this.$router.push({ path: `/ManagerAccount/${this.email}` });
    },
    async ViewOrders() {
      await this.$router.push({ path: `/ViewOrders/${this.email}` });
    },
    async LogOut() {
      alert('Successfully logged out.');
      await this.$router.push({ name: 'home' });
    },
  },

  created() {
    this.fetchPromotions();
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

.manage-promotions-container {
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

.buttons-container {
  text-align: center;
  margin-top: 10px;
}

.centerbuttoncontainer {
  text-align: center;
  margin-top: 10px;
}

.inputrow {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 10px;
  width: 100%;
}

.prettylabel {
  width: 25%;
  color: #4e555b;
}

.prettyheader {
  color: #4e555b;
  margin-bottom: 10%;
}

.overlay {
  display: flex;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  justify-content: center;
  align-items: center;
  z-index: 2;
}

.popup-container {
  display: block;
  position: relative;
  z-index: 3;
}

.popup {
  width: 350px;
  height: 350px;
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.clickable-text:hover {
  cursor: pointer;
  color: white !important;
}


</style>
