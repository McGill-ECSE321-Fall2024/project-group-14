<template>
    <div>
      <div class= "background">
        
        <div class="navbar-container">
          <nav class="navbar navbar-expand-lg navbar-light transparent-background">
            <a class="navbar-brand" href="#">
              <img src="#" alt="Your Logo" height="60">
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
              <ul class="navbar-nav">
                <li class="nav-item">
                  <a class="nav-link clickable-text" >Home</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" >??</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" >??</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" >??</a>
                </li>
                <li class="nav-item active">
                  <a class="nav-link" href="#">View Promotion<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" >??</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" >??</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" >Log Out</a>
                </li>
              </ul>
            </div>
          </nav>
        </div>
  
  
  
        <div class="table-container">
          <div class="buttons-container">
            <button class="prettybutton" @click="openCreatePromotionPopup">Create Promotion</button>
          </div>
          <table>
            <tr>
              <th>Promotion ID</th>
              <th>Game ID</th>
              <th>Discount</th>
              <th>Description</th>
              <th>Update</th>
              <th>Delete</th>
            </tr>
                <tr v-for="promotion in promotions" :key="promotion.id">
                  <td>{{ promotion.id }}</td>
                  <td>{{ promotion.gameId }}</td>
                  <td>{{ promotion.discount }}</td>
                  <td>{{ promotion.description }}</td>
                  <td>
                    <button class="prettybutton2" @click="openUpdatePromotionPopup(promotion)">Update</button>
                  </td>
                  <td>
                    <button class="prettybutton2" @click="deletePromotion(promotion.id)">Delete</button>
                  </td>
                </tr>
          </table>
          <div class="centerbuttoncontainer">
            <label class="prettylabel" v-if="promotions.length === 0"> {{ errorMessage }}</label>
        
          </div>
        </div>
      </div>

      <div class="popup-container">
        <div class="overlay" v-if="showCreatePromotionPopup">
          <div class="popup">
            <h2 class="prettyheader">Create New Promotion</h2>

            <div class="inputrow">
              <label class="prettylabel">Game ID</label>
              <input v-model="currPromotion.gameId" type="text" id="newPromotionGameTitle" name="newPromotionGameTitle" class="styledinput" placeholder="Game id">
            </div>
            <div class="inputrow">
              <label class="prettylabel">Discount</label>
              <input v-model="currPromotion.discount" type="text" id="newPromotionDiscount" name="newPromotionDiscount" class="styledinput" placeholder="Discount percentage">
            </div>
            <div class="inputrow">
              <label class="prettylabel">Description</label>
              <input v-model="currPromotion.description" type="text" id="newPromotionDescription" name="newPromotionDescription" class="styledinput" placeholder="Description of promotion">
            </div>
            <div class="centerbuttoncontainer">
              <button class="prettybutton2" @click="closeCreatePromotionPopup">Close</button>
              <button class="prettybutton" @click="createPromotion" v-if="!isUpdate">Create</button>
              <button class="prettybutton" @click="updatePromotion" v-if="isUpdate">Update</button>
            </div>
            <div class="centerbuttoncontainer">
              <label>{{ errorMessage }}</label>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/promotion';

var axiosClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Access-Control-Allow-Origin': 'http://localhost:8087',
  },
});

export default {
  name: 'PromotionManagement',
  data() {
    return {
      promotions: [],
      promotionDetails:null,
      showCreatePromotionPopup: false,
      errorMessage:"",
      currPromotion:{
        gameId:"",
        description:"",
        discount:0,
      },
      isUpdate:false,
      currid:null,
    }
  },

  methods:{

    openCreatePromotionPopup(){
      this.currPromotion = {
        gameId:"",
        description:"",
        discount:0,
      }
      this.showCreatePromotionPopup = true;
      this.isUpdate = false;
      this.errorMessage = "";
    },


    openUpdatePromotionPopup(promotion){
      this.currPromotion = {
        gameId:promotion.gameId,
        description:promotion.description,
        discount:promotion.discount,
      }
      this.currid = promotion.id;
      this.showCreatePromotionPopup = true;
      this.isUpdate = true;
    },

    closeCreatePromotionPopup(){
      this.showCreatePromotionPopup = false;
      this.errorMessage = "";
    },

    // Get all promotions
    async fetchPromotions(){
      try{
        const response = await axios.get(`${BASE_URL}/promotions`);
        this.promotions = response.data.promotions;
      }
      catch(error){
        this.errorMessage = error.response.data.message;
        console.error("Error fetching promotions: ", error);
      }
    },

    // get a specific promotion
    async fetchPromotionDetails(id){
      try{
        const response = await axios.get(`${BASE_URL}/promotion/${id}`);
        this.promotionDetails = response.data;
        this,errorMessage = "";
      }
      catch(error){
        this.errorMessage = error.response?.data?.message || "Failed to fetch promotion details.";
      }
    },

    // Create a new promotion
    async createPromotion(){
      console.log("Create Promotion button clicked!");
      try{
        const payload = {
          description : this.currPromotion.description,
          discount : this.currPromotion.discount,
        }
        const response = await axios.put(`${BASE_URL}/${this.currPromotion.gameId}`, payload);
        console.log("Promotion created successfully");
        this.fetchPromotions();
        this.closeCreatePromotionPopup();
      }
      catch(error){
        this.errorMessage = error.response?.data?.message || "Failed to create promotion.";
      }
    },

    // Update a promotion
    async updatePromotion(){
      try{
        const payload = {
          description : this.currPromotion.description,
          discount : this.currPromotion.discount,
          gameId : this.currPromotion.gameId,
        }
        const response = await axios.put(`${BASE_URL}/update/${this.currid}`, payload);
        console.log("Promotion updated successfully");
        this.fetchPromotions();
        this.closeCreatePromotionPopup();
      }
      catch(error){
        this.errorMessage = error.response?.data?.message || "Failed to update promotion.";
      }
    },

    // Delete a promotion
    async deletePromotion(id) {
      try {
        await axios.delete(`${BASE_URL}/${id}`);
        console.log(`Promotion with ID ${id} deleted.`);
        this.fetchPromotions(); // Refresh the list after deletion
      } catch (error) {
        console.error(`Error deleting promotion with ID ${id}:`, error);
        this.errorMessage = `Could not delete promotion with ID ${id}.`;
      }
    },
  },

  created(){
      this.fetchPromotions();
    },

};

</script>





<style scoped>
.background {
  width: 100%;
  height: 100%;
  position: absolute;
  background: url('../assets/hotelLobby.jpeg') center center no-repeat;
  background-size: cover;
}

.transparent-background {
  background-color: rgba(255, 255, 255, 0.6);
}

body {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  margin: 0;
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
  min-height: 500px;
}

.buttons-container {
  margin-bottom: 1%;
}

table {
  border-collapse: collapse;
  margin: 0 auto;
  width: 100%;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

.styledinput {
  border-radius: 5px;
  background-color: white;
  border: 1px solid #888888;
  color: #888888;
}

.prettybutton {
  border-radius: 5px;
  background-color: white;
  border: 1px solid #721c24;
  color: #721c24;
}

.prettybutton2 {
  border-radius: 5px;
  background-color: white;
  border: 1px solid #888888;
  color: #888888;
}

.prettybutton:hover {
  border-radius: 5px;
  border: #721c24;
  background-color: #721c24;
  border: 1px solid #721c24;
  color: white;
}

.prettybutton2:hover {
  border-radius: 5px;
  border: #888888;
  background-color: #888888;
  border: 1px solid #888888;
  color: white;
}

.centerbuttoncontainer {
  margin-top: 5%;
  text-align: center;
}

.inputrow {
  display:flex;
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
  color: #721c24;
  margin-bottom: 10%;
}

/* Dropdown styles */
.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
  border-radius: 5px;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {
  background-color: #888888;
  color: white;
}

.dropdown:hover .dropdown-content {
  display: block;
}

/* Button specific styles */
.dropdown-button {
  background-color: white;
  border: none;
  width: 100%;
  text-decoration: none;
  display: inline-block;
  text-align: left;
  cursor: pointer;
  color: #888888;
}

.dropdown-button:hover {
  background-color: #888888;
  color: white;
}

/* Styles for overlay */
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
