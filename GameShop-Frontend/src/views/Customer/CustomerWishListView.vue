<template>
    <div>
        <div id="makeOrder">
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
                <a class="nav-link clickable-text" @click="Orders">Orders</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Wishlist (Current)</a>
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

                <div class="wishlist-container">
                  <div class="luxurious-text" style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 3px">
                    <h3>YOUR WISHLIST</h3>
                    <div class="table-responsive subheading">
                      <table class="table table-bordered">
                        <thead>
                          <tr>
                            <th scope="col" class="text-center subheading">Game Id</th>
                            <th scope="col" class="text-center subheading">Game Info</th>
                            <th scope="col" class="text-center subheading">Total Price</th>
                            <th scope="col" class="text-center subheading">Add To Order</th>
                            <th scope="col" class="text-center subheading">Cancel</th>
                          </tr>
                        </thead>
                        <tbody>
                            <tr v-for="wishlist in wishlists" :key="wishlist.customerId">
                                <td class="text-center" style="background: white; min-width: 90px">
                                <input class="form-control text-center" :value="wishlist.gameId || 'N/A'" readonly>
                                </td>
                                <td class="text-center" style="background: white; min-width: 150px">
                                <input class="form-control text-center" :value="wishlist.gameInfo || 'N/A'" readonly>
                                </td>
                                <td class="text-center" style="background: white; min-width: 90px">
                                <input class="form-control text-center" :value="`$${wishlist.totalPrice?.toFixed(2) || '0.00'}`" readonly>
                                </td>
                                <!-- Add to Order Button -->
                                <td class="text-center" style="background: white; min-width: 90px">
                                <button class="btn payButton"@click="addWishlistToOrder(wishlist.customerId, wishlist.gameId)">Add to Order</button>
                                </td>
                                <td class="text-center" style="background: white; min-width: 90px">
                                <button class="btn cancelButton" @click="clearWishlist(wishlist.gameId)">Cancel</button>
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
import axios from 'axios';
const BASE_URL = 'http://localhost:8060/wishlist';
const backendUrl = "http://localhost:8060"; // Hardcoded backend URL

var axiosClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Access-Control-Allow-Origin': '*',
  },
});

export default {
  name: 'WishListManagement',
  props: {
    customerEmail: {
      type: String,
      required: true, // or false if you handle fallback
    },
  },
  data() {
    return {
        wishlists: [], // renamed from orders to reflect wishlist context
        wishlistDetails: null, // details of a specific wishlist item
        errorMessage: "", // error messages for feedback
        currWishlist: {
            customerId: "", // customer ID for adding to the wishlist
            gameId: "", // game ID to add to the wishlist
        },
        };
    },

  methods: {
    // open the create order popup
    openCreateOrderPopup() {
      this.currOrder = {
        customerId: "",
      };
      this.showCreateOrderPopup = true;
      this.errorMessage = "";
    },

    // close the create order popup
    closeCreateOrderPopup() {
      this.showCreateOrderPopup = false;
      this.errorMessage = "";
    },

    async fetchWishlists() {
        try {
            //fetch the Customer ID using the email
            const customerResponse = await axios.get(`http://localhost:8060/customersEmail/${this.customerEmail}`);
            const customerid = customerResponse.data.id;
            this.customerid = customerid;
            const response = await axios.get(`http://localhost:8060/wishlist/${customerid}`);
            const wishlists = response.data.wishlists;

            // map game info and total price directly from backend response
            this.wishlists = wishlists.map(wishlist => ({
                gameId: wishlist.gameId,
                customerId: wishlist.customerId,
                gameInfo: wishlist.gameName || 'N/A', // use gameName for display
                totalPrice: wishlist.price || 0,      // use price field for total price
            }));
            this.errorMessage = "";
            console.log("Wishlist Response:", response.data.wishlists);
            console.log("Customer ID:", this.customerid);
        } catch (error) {
            console.error("Error fetching wishlists:", error.response?.data?.message || error.message);
            this.errorMessage = "Failed to fetch wishlists.";
        }
    },

    async createWishlist(gameId) {
        const customerId = this.customerid; // use the provided customer ID prop
        try {
            const response = await axios.post(`http://localhost:8060/wishlist/${gameId}/${customerId}`);
            console.log("Wishlist created successfully:", response.data);

            // refresh the wishlists
            this.fetchWishlists();
        } catch (error) {
            this.errorMessage = error.response?.data?.message || "Failed to create wishlist.";
            console.error("Error creating wishlist:", error);
        }
    },

    async clearWishlist(gameId) {
        const customerId = this.customerid; // use the provided customer ID prop
        const isConfirmed = window.confirm("Are you sure you want to clear this wishlist item?");
        if (!isConfirmed) {
            console.log("Wishlist clearing aborted by the user.");
            return; // exit if the user cancels
        }

        try {
            await axios.delete(`http://localhost:8060/wishlist/${gameId}/${customerId}`);
            console.log(`Wishlist item with Game ID ${gameId} cleared.`);

            // refresh the wishlists
            this.fetchWishlists();
        } catch (error) {
            this.errorMessage = error.response?.data?.message || `Failed to clear wishlist for Game ID ${gameId}.`;
            console.error(`Error clearing wishlist for Game ID ${gameId}:`, error);
        }
    },


    async createOrder(customerId) {
        console.log("Creating order for Customer ID:", customerId);
        const payload = { customerId };
        try {
            const response = await axios.post('http://localhost:8060/order', payload);
            return response.data.orderId; // return the created order ID
        } catch (error) {
            console.error("Error creating order:", error);
            throw error;
        }
    },

        async addGameToOrder(orderId, gameId) {
        try {
            await axios.put(`http://localhost:8060/orderitem/${orderId}/${gameId}`);
            console.log(`Game ID ${gameId} added to Order ID ${orderId}`);
        } catch (error) {
            console.error(`Error adding game ${gameId} to order ${orderId}:`, error);
            throw error;
        }
    },

    async addWishlistToOrder(customerId, gameId) {
        console.log("Adding Game ID to Order:", gameId);

        try {
            // step 1: Create an Order for the Customer
            const orderId = await this.createOrder(customerId);
            console.log("Created Order ID:", orderId);

            // step 2: Add the Single Game to the Order
            await this.addGameToOrder(orderId, gameId);
            console.log(`Game ID ${gameId} successfully added to Order ID ${orderId}!`);

            // step 3: Remove the Game from the Wishlist
            await this.clearWishlist(gameId);
            console.log(`Game ID ${gameId} removed from Wishlist.`);
            
            // optionally refresh wishlists or other related data
            this.fetchWishlists();
        } catch (error) {
            console.error("Error adding game to order:", error);
            this.errorMessage = "Failed to add game to order.";
        }
    },
    async Orders() {
      await this.$router.push({path: '/orders/' + this.customerEmail})
    },
    async Wishlist() {
      await this.$router.push({path: '/wishlist/' + this.customerEmail})
    },
    async Account() {
      await this.$router.push({path: '/CustomerAccount/' + this.customerEmail})
    },
    async LogOut() {
      alert('Successfully logged out.')
      await this.$router.push({name: 'home'})
    },
    async BrowseGames(){
      await this.$router.push({path: '/CustomerBrowseGames/' + this.customerEmail})
    },
    async Home(){
      await this.$router.push({path: '/CustomerHome/' + this.customerEmail})
    },
  },

  // fetch orders when the component is created
  created() {
        console.log("Customer Email from prop:", this.customerEmail);
        if (!this.customerEmail || typeof this.customerEmail !== 'string') {
          console.error("Invalid Customer Email: Must be a non-empty string.");
        } else {
            this.fetchWishlists(); // fetch wishlists instead of orders
        }
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


.wishlist-container {
  background-color: rgba(255, 255, 255, 1);
  padding: 2%;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  position: absolute;
  top: 25%;
  left: 3%;
  right: 3%;
  min-height: 500px;
}

.table-responsive {
  font-family: 'Georgia', sans-serif;
  font-weight: bold;
  color: black;
  width: 100%;
  max-width: 100%;
  overflow-x: auto;
  overflow-y: auto;
  display: block;
  clear: both;
  margin-bottom: 10px;
}

.payButton {
  width: 100%;
  background-color: white;
  border: 2px solid #0055FF;
  color: #0055FF;
}

.payButton:hover {
  width: 100%;
  background-color: #0055FF;
  border: 2px solid #0055FF;
  color: white;
}

.cancelButton {
  width: 100%;
  background-color: white;
  border: 2px solid #888888;
  color: #888888;
}

.cancelButton:hover {
  border: #888888;
  background-color: #888888;
  border: 2px solid #888888;
  color: white;
}


.luxurious-text {
  font-family: 'Georgia', sans-serif;
  font-weight: bold;
  color: black;
}

.subheading {
  font-family: 'Montserrat', sans-serif;
  color: #888;
}

.clickable-text:hover {
  cursor: pointer;
  color: white !important;
}

.navbar-brand {
  margin-right: 0;
}

.luxurious-text {
  font-family: 'Georgia', sans-serif;
  color: #888;
}

.navbar-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
}

.hero-section {
  background: url('../../assets/gameshopBackground.jpg') center/cover no-repeat;
  padding: 300px 0;
  text-align: center;
}


</style>
