<template>
    <div>
      <div id="writeReply"></div>
      <div class="background">
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
                <a class="nav-link clickable-text" @click="ManageEmployees">Manage Employees</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePolicy">Manage Policy</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGames">Manage Games</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGameRequests">Manage Game Requests</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePromotion">Promotion</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Account">Account</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewOrders">View Orders</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="LogOut">LogOut</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>
  
        <div class="write-review-container">
          <div class="d-flex justify-content-center h-100">
            <div class="card">
              <div class="card-header">
                <h3
                  class="text-center"
                  style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 3px"
                >
                  WRITE A REPLY TO A REVIEW
                </h3>
              </div>
              <div class="card-body">
                <form id="reply_form">
                  <div class="form-group">
                    <label for="reviewId">Enter Review ID:</label>
                    <input
                      id="reviewId"
                      v-model.number="newReply.id"
                      type="number"
                      class="form-control"
                      placeholder="Enter Review ID"
                    />
                  </div>
                  <div class="input-group form-group">
                    <textarea
                      id="reply-description"
                      v-model="newReply.message"
                      class="form-control"
                      style="font-family: 'Georgia', sans-serif"
                      placeholder="Enter your reply to the review"
                    ></textarea>
                  </div>
                  <div class="form-group">
                    <button
                      @click.prevent="createReply"
                      type="button"
                      class="btn btn-primary btn-block saveButton"
                    >
                      Post Reply
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
  
          <div class="d-flex justify-content-center h-100">
            <div class="table-container">
              <div
                class="table-responsive luxurious-text"
                style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 3px"
              >
                <h3>All Reviews for Game #{{ gameData.id || "" }}:</h3>
                <table class="table table-bordered">
                  <thead>
                    <tr>
                      <th scope="col" class="text-center">Review ID</th>
                      <th scope="col" class="text-center">Description</th>
                      <th scope="col" class="text-center">Reviewer</th>
                      <th scope="col" class="text-center">Rating</th>
                      <th scope="col" class="text-center">Reply</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="r in reviews" :key="r.id">
                      <td class="text-center">{{ r.id }}</td>
                      <td class="text-center">{{ r.description || "No description available" }}</td>
                      <td class="text-center">{{ r.customerName || "Anonymous" }}</td>
                      <td class="text-center">{{ r.ranking ? r.ranking + " Stars" : "No rating" }}</td>
                      <td class="text-center"> {{replies.find((reply) => reply.reviewId === r.id)?.message || "No reply"}}</td>
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
  
  const backendUrl = "http://localhost:8060";
  const axiosClient = axios.create({
    baseURL: backendUrl,
    headers: {
      "Access-Control-Allow-Origin": "*",
    },
  });
  
  export default {
    name: "ManagerWriteReply",
    data() {
      return {
        gameData: {},
        reviews: [],
        replies: [], // Store all replies here
        newReply: { id: null, message: "", managerId: null },
        email: "",
        gameId: "",
      };
    },
    mounted() {
      this.email = this.$route.params.param1;
      this.gameId = this.$route.params.param2;
  
      // Fetch managerId using the email from route params
      axiosClient
        .get(`/managers/${this.email}`)
        .then((response) => {
          console.log("Manager Response: ", response.data);
          this.newReply.managerId = response.data.id; // Map the manager ID
        })
        .catch((err) => {
          console.error("Failed to fetch manager ID:", err);
        });
  
      // Fetch game details
      axiosClient
        .get(`/game/${this.gameId}`)
        .then((response) => {
          this.gameData = response.data;
        })
        .catch((err) => {
          console.error("Failed to fetch game data:", err);
        });
  
      // Fetch reviews
      axiosClient
        .get(`/game/${this.gameId}/reviews`)
        .then((response) => {
          this.reviews = response.data.reviews || [];
        })
        .catch((err) => {
          console.error("Failed to fetch reviews:", err);
        });
  
      // Fetch all replies
      axiosClient
        .get("/replies")
        .then((response) => {
          console.log("Replies: ", response.data);
          this.replies = response.data;
        })
        .catch((err) => {
          console.error("Failed to fetch replies:", err);
        });
    },
    methods: {

      async ManageEmployees() {
      await this.$router.push({path: '/ManageEmployees/' + this.email})
    },
    async ManagePolicy() {
      await this.$router.push({path: '/ManagePolicy/' + this.email})
    },
    async ManageGames() {
      await this.$router.push({path: '/ManageGames/' + this.email})
    },
    async ManageGameRequests() {
      await this.$router.push({path: '/ManageGameRequests/' + this.email})
    },
    async Account() {
      await this.$router.push({path: '/ManagerAccount/' + this.email})
    },
    async ViewOrders() {
      await this.$router.push({path: '/ViewOrders/' + this.email})
    },
    async LogOut() {
      alert('Successfully logged out.')
      await this.$router.push({name: 'home'})
    },
    async ManagePromotion() {
      await this.$router.push({path: '/ManagerPromotion/' + this.email})
    },
    async Home(){
      await this.$router.push({path: '/ManagerHome/' + this.email})
    },

        fetchReviews() {
            axiosClient
        .get(`/game/${this.gameId}/reviews`)
        .then((response) => {
          this.reviews = response.data.reviews || [];
        })
        .catch((err) => {
          console.error("Failed to fetch reviews:", err);
        });},
        
            fetchReplies() {
                axiosClient
        .get("/replies")
        .then((response) => {
          console.log("Replies: ", response.data);
          this.replies = response.data;
        })
        .catch((err) => {
          console.error("Failed to fetch replies:", err);
        });},

      createReply() {
        if (!this.newReply.id || !this.newReply.message.trim()) {
          alert("Please fill in all fields.");
          return;
        }
  
        // Log the raw data object for debugging
        console.log("Reply: ", this.newReply);
        const plainReply = JSON.parse(JSON.stringify(this.newReply));
        console.log("Payload before POST: ", plainReply);
  
        axiosClient
          .post("/reply", plainReply)
          .then((response) => {
            console.log("Reply Posted Successfully: ", response.data);
            this.replies.push(response.data); // Add the new reply to the replies array
            alert("Reply posted successfully");
            this.newReply.message = "";
            this.newReply.id = null;
            this.fetchReviews();
            this.fetchReplies();
          })
          .catch((err) => {
            console.error("Failed to post reply:", err);
          });
      },
    },
  };
  </script>
  
 <style scoped>
 .background {
   width: 100%;
   height: 100%;
   position: absolute;
   background: url('../../assets/gameshopBackground.jpg') center center no-repeat;
   background-size: cover;
 }
 
 .write-review-container {
   padding: 2%;
   border-radius: 10px;
   box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
   position: absolute;
   top: 20%;
   left: 30%;
   right: 30%;
   min-height: 300px;
   display: block;
   background: white;
 }
 
 .table-container {
   background-color: rgba(255, 255, 255, 1);
   padding: 2%;
   margin-top: 2%;
   border-radius: 10px;
   box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
   position: absolute;
   width: 100%;
   overflow-x: auto;
   display: block;
   clear: both;
 }
 
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
 
 .nav-link:hover {
   cursor: pointer;
 }
 
 .card {
   width: 100%;
 }
 
 .saveButton {
   width: 100%;
   background-color: white;
   border: 2px solid #0055FF;
   color: #0055FF;
 }
 
 .saveButton:hover {
   background-color: #0055FF;
   border: 2px solid #0055FF;
   color: white;
 }
 
 .btn-outline-primary {
   background-color: white;
   border: 2px solid #0055FF;
   color: #0055FF;
 }
 
 .btn-outline-primary:hover {
   background-color: #0055FF;
   color: white;
 }
 
 .table-responsive {
   width: 100%;
   max-width: 100%;
   overflow-x: auto;
   overflow-y: auto;
   margin-bottom: 10px;
 }
 
 th, td {
   text-align: center;
 }
 </style>
 