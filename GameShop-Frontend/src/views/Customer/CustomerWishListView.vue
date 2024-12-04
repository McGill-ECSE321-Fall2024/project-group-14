<template>
  <div>
    <div id="makeOrder">
      <div class="background">
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
                  <a class="nav-link clickable-text" @click="Orders">Orders</a>
                </li>
                <li class="nav-item active">
                  <a class="nav-link" href="#">Wishlist (Current)</a>
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

        <div class="wishlist-container">
          <div class="luxurious-text" style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 3px">
            <h3>YOUR WISHLIST</h3>
            <div class="table-responsive subheading">
              <table class="table table-bordered">
                <thead>
                  <tr>
                    <th class="text-center subheading">Game ID</th>
                    <th class="text-center subheading">Game Info</th>
                    <th class="text-center subheading">Price</th>
                    <th class="text-center subheading">Add to Orders</th>
                    <th class="text-center subheading">Remove from Wishlist</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="wishlist in wishlists" :key="wishlist.gameId">
                    <td class="text-center">{{ wishlist.gameId }}</td>
                    <td class="text-center">{{ wishlist.gameInfo }}</td>
                    <td class="text-center">
                      <span v-if="wishlist.discountedPrice && wishlist.discountedPrice !== wishlist.originalPrice">
                        <s style="color: red;">$ {{ wishlist.originalPrice.toFixed(2) }}</s>
                        <span style="color: green;"> $ {{ wishlist.discountedPrice.toFixed(2) }}</span>
                      </span>
                      <span v-else>
                        $ {{ wishlist.originalPrice.toFixed(2) }}
                      </span>
                    </td>
                    <td class="text-center">
                      <button class="btn payButton" @click="confirmAndPurchase(wishlist)">Purchase</button>
                    </td>
                    <td class="text-center">
                      <button class="btn cancelButton" @click="removeFromWishlist(wishlist.gameId)">Remove</button>
                    </td>
                  </tr>
                  <tr v-if="wishlists.length === 0">
                    <td colspan="5" class="text-center">Your wishlist is empty.</td>
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
const backendUrl = "http://localhost:8060"; // Backend URL

export default {
  name: "WishListManagement",
  props: {
    customerEmail: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      wishlists: [],
      customerId: null,
      promotions: [], // To store promotions for discounts
    };
  },
  methods: {
    async fetchWishlist() {
      try {
        const customerResponse = await axios.get(`${backendUrl}/customersEmail/${this.customerEmail}`);
        this.customerId = customerResponse.data.id;

        const wishlistResponse = await axios.get(`${backendUrl}/wishlist/${this.customerId}`);
        const wishlistItems = wishlistResponse.data.wishlists;

        const promotionsResponse = await axios.get(`${backendUrl}/promotion/promotions`);
        const promotions = promotionsResponse.data.promotions;

        // Map promotions to wishlist items for discounts
        this.wishlists = wishlistItems.map(item => {
          const promotion = promotions.find(promo => promo.gameId === item.gameId);
          return {
            gameId: item.gameId,
            gameInfo: item.gameName || "N/A",
            originalPrice: item.price || 0,
            discountedPrice: promotion
              ? item.price - (item.price * promotion.discount) / 100
              : item.price,
          };
        });
      } catch (error) {
        console.error("Error fetching wishlist or promotions:", error.message);
        alert("Failed to fetch wishlist.");
      }
    },

    async confirmAndPurchase(wishlistItem) {
      const isConfirmed = window.confirm(`Are you sure you want to purchase ${wishlistItem.gameInfo}?`);
      if (!isConfirmed) return;

      try {
        const orderResponse = await axios.post(`${backendUrl}/order`, { customerId: this.customerId });
        const orderId = orderResponse.data.orderId;

        await axios.put(`${backendUrl}/orderitem/${orderId}/${wishlistItem.gameId}`);
        await axios.delete(`${backendUrl}/wishlist/${wishlistItem.gameId}/${this.customerId}`);

        alert(`Successfully purchased ${wishlistItem.gameInfo}!`);
        this.fetchWishlist();
      } catch (error) {
        console.error("Error completing purchase:", error.message);
        alert("Failed to complete the purchase.");
      }
    },

    async removeFromWishlist(gameId) {
      const isConfirmed = window.confirm("Are you sure you want to remove this item from your wishlist?");
      if (!isConfirmed) return;

      try {
        await axios.delete(`${backendUrl}/wishlist/${gameId}/${this.customerId}`);
        this.fetchWishlist();
      } catch (error) {
        console.error("Error removing item from wishlist:", error.message);
        alert("Failed to remove item.");
      }
    },

    async Home() {
      await this.$router.push({ path: `/CustomerHome/${this.customerEmail}` });
    },
    async Orders() {
      await this.$router.push({ path: `/orders/${this.customerEmail}` });
    },
    async Account() {
      await this.$router.push({ path: `/CustomerAccount/${this.customerEmail}` });
    },
    async LogOut() {
      alert("Successfully logged out.");
      await this.$router.push({ name: "home" });
    },
  },
  created() {
    this.fetchWishlist();
  },
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

.navbar-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
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

.payButton {
  background-color: white;
  border: 2px solid #0055ff;
  color: #0055ff;
}

.payButton:hover {
  background-color: #0055ff;
  color: white;
}

.cancelButton {
  background-color: white;
  border: 2px solid #888888;
  color: #888888;
}

.cancelButton:hover {
  background-color: #888888;
  color: white;
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

.navbar-brand {
  margin-right: 0;
}



</style>
