<template>
  <div>
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
              <a class="nav-link" href="#">Home</a>
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
              <a class="nav-link clickable-text" @click="LogOut">LogOut</a>
            </li>
          </ul>
        </div>
      </nav>
    </div>
    <div class="game-container">
      <h1 class="text-left" style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 3px; font-size: 30px">
        AVAILABLE GAMES
      </h1>
      <div style="margin-top: 15px;"></div>

      <!-- Iterate over the list of games -->
      <div v-for="(game, index) in games" :key="game.id" class="game-box">
        <div class="row">
          <div class="col-md-3">
            <!-- Add an image for each game -->
            <img :src="getGameImage(game.picture)" alt="Game Image" class="img-fluid game-image" />
          </div>
          <div class="col-md-9">
            <!-- Display game information -->
            <h5 style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 2px">{{ game.name }}</h5>
            <p style="font-family: 'Georgia', sans-serif">{{ game.description }}</p>
            <p style="font-family: 'Georgia', sans-serif">Category: {{ game.category }}</p>
            <p style="font-family: 'Georgia', sans-serif">Rating: {{ game.rating }}</p>
            <p style="font-family: 'Georgia', sans-serif">Price: ${{ game.price }}</p>
            <button class="btn btn-lg custom-book-button" @click="purchase(game)">Purchase</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const backendUrl = "http://localhost:8060"; // Hardcoded backend URL

export default {
  name: "GamesList",
  data() {
    return {
      games: [], // List of games fetched from the endpoint
      customerEmail: "",
    };
  },
  mounted() {
    this.customerEmail = this.$route.params.email || "";
    this.fetchGames();
  },
  methods: {
    async fetchGames() {
      try {
        const response = await axios.get(`${backendUrl}/games`);
        console.log("Response from /games endpoint:", response.data); // Log the full response
        this.games = response.data.games; // Assign the games data to the local variable
      } catch (error) {
        console.error("Error fetching games:", error);
        if (error.response) {
          console.error("Response data:", error.response.data);
          console.error("Response status:", error.response.status);
          console.error("Response headers:", error.response.headers);
        } else if (error.request) {
          console.error("Request was made but no response:", error.request);
        } else {
          console.error("Error setting up the request:", error.message);
        }
        alert("Failed to load games.");
      }
    },
    getGameImage(pictureName) {
      // Constructs the full path for the image using import.meta.url
      try {
        return new URL(`../../assets/${pictureName}`, import.meta.url).href;
      } catch (error) {
        console.error(`Image not found: ${pictureName}`, error);
        return new URL("../../assets/default-image.jpg", import.meta.url).href; // Fallback image
      }
    },
    async Orders() {
      await this.$router.push({path: '/orders/' + this.customerEmail})
    },
    async Wishlist() {
      await this.$router.push({path: '/wishlist/' + this.customerEmail})
    },
    async Account() {
      await this.$router.push({
        name: "CustomerAccount",
        params: { email: this.customerEmail },
      });
    },
    async LogOut() {
      alert('Successfully logged out.')
      await this.$router.push({name: 'home'})
    },
    async Home() {
      await this.$router.push({ path: "/CustomerHome/" + this.customerEmail });
    },
    async Reservations() {
      await this.$router.push({
        path: "/customer/reservation/" + this.customerEmail,
      });
    },
    async purchase(game) {
      if (!game) {
        console.error("Game object is undefined!");
        return;
      }

      console.log("Selected Game Details:", game); // Log the game details

      try {
        // Step 1: Fetch customer details
        const customerResponse = await axios.get(`${backendUrl}/customersEmail/${this.customerEmail}`);
        const customerDetails = customerResponse.data; // Extract customer details
        console.log("Fetched Customer Details:", customerDetails);

        // Step 2: Create an order for the customer
        const orderRequestDto = { customerId: customerDetails.id }; // Build the OrderRequestDto
        const orderResponse = await axios.post(`${backendUrl}/order`, orderRequestDto);
        const orderDetails = orderResponse.data; // Extract order details
        console.log("Order Created:", orderDetails);

        // Step 3: Add the game to the order
        const putOrderItemUrl = `${backendUrl}/orderitem/${orderDetails.orderId}/${game.id}`;
        const orderItemResponse = await axios.put(putOrderItemUrl);

        console.log("Game added to Order:", orderItemResponse.data); // Log the response for adding the game
        alert(`Successfully purchased ${game.name}. Congratulations! ${customerDetails.username}`);
      } catch (error) {
        console.error("Error during purchase process:", error);

        if (error.response && error.response.status === 404) {
          alert("Customer or order not found. Please check the details and try again.");
        } else if (error.response && error.response.status === 500) {
          alert("Server error. Please try again later.");
        } else {
          alert("An error occurred during the purchase process.");
        }
      }
    }




  },
};
</script>

<style scoped>
.navbar-brand {
  margin-right: 0;
}

.transparent-background {
  background-color: rgba(136, 136, 136, 0.4);
}

.navbar-container {
  top: 0;
  z-index: 1000;
}

.game-box {
  border: 1px solid #ddd;
  padding: 10px;
  margin-bottom: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.game-container {
  margin-top: 100px;
  margin-right: 40px;
  margin-left: 40px;
  padding: 20px;
  border-radius: 8px;
  border: none;
}

.custom-book-button {
  background-color: transparent;
  color: #888;
  border: 2px solid #888;
  transition: background-color 0.3s, color 0.3s;
}

.custom-book-button:hover {
  background-color: #888;
  color: white;
}

.game-image {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s;
}

.game-image:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}
</style>
