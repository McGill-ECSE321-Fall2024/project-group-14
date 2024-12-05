<template>
    <div>
      <div id="writeReview"></div>
      <div class="background">
        <div class="navbar-container">
          <nav class="navbar navbar-expand-lg navbar-light transparent-background">
            <a class="navbar-brand" href="#">
              <img src="@/assets/gameshopLogo.jpg" alt="Your Logo" height="60">
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
                  <a class="nav-link clickable-text" @click="Orders">Orders</a>
                </li>
                <li class="nav-item">
                <a class="nav-link clickable-text" @click="Wishlist">Wishlist</a>
              </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" @click="Account">Account</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" @click="LogOut">Logout</a>
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
                  WRITE A GAME REVIEW
                </h3>
              </div>
              <div class="card-body">
                <form id="review_form">
                  <div class="input-group form-group">
                    <textarea
                      id="review-description"
                      v-model="newReview.description"
                      class="form-control"
                      style="font-family: 'Georgia', sans-serif"
                      placeholder="Enter your review for the game"
                    ></textarea>
                  </div>
                  <div class="form-group">
                    <label for="rating">RANKING</label>
                    <select v-model="newReview.ranking" class="form-control" id="ra">
                      <option value="FiveStar">5 Stars</option>
                      <option value="FourStar">4 Stars</option>
                      <option value="ThreeStar">3 Stars</option>
                      <option value="TwoStar">2 Stars</option>
                      <option value="OneStar">1 Star</option>
                    </select>
                  </div>
                  <div class="form-group">
                    <button
                      @click="createReview"
                      type="button"
                      class="btn btn-primary btn-block saveButton"
                    >
                      Post Review
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
                <h3>All Reviews for {{ gameData.name }}:</h3>
                <table class="table table-bordered">
                  <thead>
                    <tr>
                      <th scope="col" class="text-center">Description</th>
                      <th scope="col" class="text-center">Reviewer Id</th>
                      <th scope="col" class="text-center">Ranking</th>
                      <th scope="col" class="text-center">Manager Reply</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="r in reviews" :key="r.id">
                        <td class="text-center">
                        <textarea class="form-control text-center" readonly>{{ r.description }}</textarea>
                        </td>
                        <td class="text-center">{{ r.customerId }}</td>
                        <td class="text-center">{{ r.ranking }}</td>
                        <td class="text-center">
                        {{ replies[r.id] ? replies[r.id].message : "No reply" }}
                        </td>
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
import HomeView from "../HomeView.vue";
  const backendUrl = "http://localhost:8060";
  var axiosClient = axios.create({
  baseURL: backendUrl,
  headers: {
    'Access-Control-Allow-Origin': 'http://localhost:8087',
  },
});

  
  
  export default {
    name: "CustomerWriteReview",
    data() {
      return {
        gameData: {},
        newReview: {
          ranking: "",
          description: "",
          customerId: null,
          gameId: null,
        },
        reviews: [], //list of reviews fetched from the endpoint
        email: null,
        gameId: null,
        replies: [], //list of replies fetched from the endpoint
      };
    },
    mounted() {
      this.email = this.$route.params.param1;
      this.gameId = this.$route.params.param2;
  
      // fetch game data
  
      axiosClient
        .get(`/game/${this.gameId}`)
        .then((response) => {
          this.gameData = response.data || {};
        })
        .catch((err) => {
          console.error("Error fetching game:", err);
          alert(`Game Fetch Error: ${err.response?.data || err.message}`);
        });
  
      // fetch reviews
      this.fetchReviews();
    },
    methods: {
        fetchReviews() {
  axiosClient
    .get(`/game/${this.gameId}/reviews`)
    .then((response) => {
      console.log("Fetched reviews successfully");
      this.reviews = response.data.reviews || [];
      this.fetchReplies(); // fetch replies for the reviews
    })
    .catch((err) => {
      console.error("Error fetching reviews:", err);
      
    });
},
fetchReplies() {
  axiosClient
    .get("/replies")
    .then((response) => { console.log("Replies:", response.data)
      const repliesList = response.data || [];
      this.replies = repliesList.reduce((map, reply) => {
        map[reply.reviewId] = reply;
        return map;
      }, {});
    })
    .catch((err) => {
      console.error("Error fetching replies:", err);
      alert(`Replies Fetch Error: ${err.response?.data || err.message}`);
    });
    },
  createReview() {
    this.newReview.gameId = parseInt(this.gameId, 10);

    // fetch customer ID by email
    axiosClient
      .get(`/customersEmail/${this.email}`) // assuming you have an endpoint to fetch customer by email
      .then((response) => {
        const customer = response.data;
        console.log("Fetched customer:", customer);
        this.newReview.customerId = customer.id;

        const plainReview = JSON.parse(JSON.stringify(this.newReview));

        console.log("Payload being sent:", plainReview);
        return axiosClient.post(`/review`, plainReview);
      })
      .then((response) => {
        console.log("Review created successfully:", response.data);
        this.reviews.push(response.data); // add the new review to the list
        alert("Review created successfully!");
        this.fetchReviews(); // refresh the reviews
      })
      .catch((err) => {
        console.error("Error creating review:", err);
        alert(`Error creating review: ${err.response?.data || err.message}`);
      });

    document.getElementById("review_form").reset();
    }
    ,
    async Orders() {
      await this.$router.push({path: '/orders/' + this.email})
    },
    async Wishlist() {
      await this.$router.push({path: '/wishlist/' + this.email})
    },
    async Account() {
      await this.$router.push({path: '/CustomerAccount/' + this.email})
    },
    async LogOut() {
      alert('Successfully logged out.')
      await this.$router.push({name: 'home'})
    },
    async BrowseGames(){
      await this.$router.push({path: '/CustomerBrowseGames/' + this.email})
    },
    async Home() {
      await this.$router.push({path: '/CustomerHome/' + this.email})
    }

  }

  };
  </script>
  
  <style scoped>
  .background {
    width: 100%;
    height: 100%;
    position: absolute;
    background: url("../../assets/gameshopBackground.jpg") center center no-repeat;
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
  
  .table-responsive {
    width: 100%;
    max-width: 100%;
    overflow-x: auto;
    overflow-y: auto;
    margin-bottom: 10px;
  }
  
  th,
  td {
    text-align: center;
  }

  .navbar .nav-item.active > .nav-link {
  cursor: default;
  color: white !important; 
  pointer-events: none; 
}

.clickable-text:hover {
  cursor: pointer;
  color: white !important;
}



  </style>
  
  