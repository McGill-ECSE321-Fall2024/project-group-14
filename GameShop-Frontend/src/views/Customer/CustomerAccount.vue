<template>
  <div>
    <div class="hero-section">
      <!-- Navbar -->
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
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Home">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Orders">Orders</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Wishlist">Wishlist</a>
              </li>
              <li class="nav-item active">
                <a class="nav-link" href="#">Account (Current)</a>
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
        <div class="row mt-5 justify-content-center">
          <div class="col-md-8 col-lg-6">
            <div class="card account-details-container shadow">
              <div class="card-header text-center">
                <h3>ACCOUNT DETAILS</h3>
              </div>
              <div class="card-body">
                <div class="d-flex justify-content-center mb-4">
                  <img class="rounded-circle profile-picture" src="../../assets/gameshopLogo.jpg" alt="Profile Photo">
                </div>
                <div class="row mt-3 justify-content-center">
                  <div class="col-md-8 mb-3 text-center">
                    <label class="labels">Username</label>
                    <input class="form-control smaller-input" id="username" v-model="username" :readonly="isReadonly" />
                  </div>
                  <div class="col-md-8 mb-3 text-center">
                    <label class="labels">Email</label>
                    <input class="form-control smaller-input" id="email" v-model="email" readonly />
                  </div>
                  <div class="col-md-8 mb-3 text-center">
                    <label class="labels">Card Number</label>
                    <input class="form-control smaller-input" id="cardNumber" v-model="cardNumber" :readonly="isReadonly" />
                  </div>
                  <div class="col-md-8 mb-3 text-center">
                    <label class="labels">Card Expiry Date</label>
                    <input class="form-control smaller-input" id="cardExpiryDate" type="date" v-model="cardExpiryDate" :readonly="isReadonly" />
                  </div>
                  <div class="col-md-8 mb-3 text-center">
                    <label class="labels">Address</label>
                    <input class="form-control smaller-input" id="address" v-model="address" :readonly="isReadonly" />
                  </div>
                  <div class="col-md-8 mb-3 text-center">
                    <label class="labels">Enter Your Password To Save Changes</label>
                    <input class="form-control smaller-input" id="password" type="password" v-model="password" :readonly="isReadonly" />
                  </div>
                </div>
                <div class="mt-5 text-center">
                  <div class="row justify-content-center">
                    <div class="col-md-5">
                      <button @click="editInfo" type="button" class="btn btn-primary btn-block mb-2 editbutton">
                        Edit Profile
                      </button>
                    </div>
                    <div class="col-md-5">
                      <button @click="saveInfo" type="button" class="btn btn-primary btn-block mb-2 savebutton">
                        Save Profile
                      </button>
                    </div>
                  </div>
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
const axiosClient = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": "*" },
});

export default {
  name: "CustomerAccount",
  data() {
    return {
      email: this.$route.params.param1,
      username: "",
      password: "",
      cardNumber: "",
      cardExpiryDate: "",
      address: "",
      errorMsg: "",
      id: null,
      isReadonly: true,
    };
  },
  created() {
    this.fetchCustomerInfo();
  },
  methods: {
    async fetchCustomerInfo() {
      try {
        const response = await axiosClient.get(`/customersEmail/${encodeURIComponent(this.email)}`);
        const customer = response.data;
        this.id = customer.id;
        this.username = customer.username;
        this.password = customer.password || "";
        this.cardNumber = customer.cardNumber;
        this.cardExpiryDate = customer.cardExpiryDate;
        this.address = customer.address;
      } catch (error) {
        this.errorMsg = `Error fetching customer info: ${error.response?.data || error.message}`;
        alert(this.errorMsg);
      }
    },
    async saveInfo() {
      try {
        const customerRequest = {
          username: this.username,
          email: this.email,
          password: this.password,
          cardNumber: this.cardNumber,
          cardExpiryDate: this.cardExpiryDate,
          address: this.address,
        };
        await axiosClient.put(`/customersEmail/${encodeURIComponent(this.email)}`, customerRequest);
        alert("Account details updated successfully.");
        this.isReadonly = true;
      } catch (error) {
        alert(`Please enter your password to make changes.`);
      }
    },
    editInfo() {
      this.isReadonly = false;
    },
    async Orders() {
      await this.$router.push({path: '/orders/' + this.email});
    },
    async Wishlist() {
      await this.$router.push({path: '/wishlist/' + this.email});
    },
    async Account() {
      await this.$router.push({path: '/CustomerAccount/' + this.email});
    },
    async LogOut() {
      alert('Successfully logged out.');
      await this.$router.push({name: 'home'});
    },
    async BrowseGames() {
      await this.$router.push({path: '/CustomerBrowseGames/' + this.email});
    },
    async Home() {
      await this.$router.push({path: '/CustomerHome/' + this.email});
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

.nav-link:hover {
  cursor: pointer;
  color: white !important;
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

.transparent-background {
  background-color: rgba(255, 255, 255, 0.3);
}

.hero-section {
  background: url("@/assets/gameshopBackground.jpg") center/cover no-repeat;
  padding: 200px 0;
  text-align: center;
  min-height: 100vh;
}

.content-container {
  margin-top: 100px;
}

.account-details-container {
  background-color: white;
  border-radius: 10px;
  padding: 20px;
}

.labels {
  font-size: 14px;
  color: #444;
}

.smaller-input {
  max-width: 300px; 
  margin: 0 auto;
}

.profile-picture {
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 50%;
}

.editbutton,
.savebutton {
  background-color: white;
  border: 2px solid #888;
  color: #888;
}

.editbutton:hover,
.savebutton:hover {
  background-color: #888;
  color: white;
}


</style>
