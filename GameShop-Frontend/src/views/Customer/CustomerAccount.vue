<template>
  <div class="customerAccount">
    <div class="background">
      <div class="navbar-container">
        <nav class="navbar navbar-expand-lg navbar-light transparent-background">
          <a class="navbar-brand" href="#">
            <img src="../../assets/gameshopLogo.jpg" alt="GameShop Logo" height="60" />
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
              <li class="nav-item active">
                <a class="nav-link" href="#">Account<span class="sr-only">(current)</span></a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="LogOut">LogOut</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>

      <div class="profile-box">
        <div class="container rounded bg-white mt-5 mb-5 account-box shadow">
          <div class="row">
            <div class="col-md-3 border-right">
              <div class="d-flex flex-column align-items-center justify-content-center text-center p-3 py-5 image-pos">
                <img class="rounded-circle" width="100%" src="../../assets/gameshopLogo.jpg" alt="Profile Photo" />
              </div>
            </div>
            <div class="col-md-9">
              <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                  <h4 class="text-right" style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 3px">
                    ACCOUNT DETAILS
                  </h4>
                </div>
                <div class="image-pos">
                  <div class="row mt-3">
                    <div class="col-md-6">
                      <label class="labels">Username</label>
                      <input class="form-control" id="username" v-model="username" readonly />
                    </div>
                    <div class="col-md-6">
                      <label class="labels">Email</label>
                      <input class="form-control" id="email" v-model="email" readonly />
                    </div>
                    <div class="col-md-6">
                      <label class="labels">Card Number</label>
                      <input class="form-control" id="cardNumber" v-model="cardNumber" readonly />
                    </div>
                    <div class="col-md-6">
                      <label class="labels">Card Expiry Date</label>
                      <input class="form-control" id="cardExpiryDate" type="date" v-model="cardExpiryDate" readonly />
                    </div>
                    <div class="col-md-12">
                      <label class="labels">Address</label>
                      <input class="form-control" id="address" v-model="address" readonly />
                    </div>
                  </div>
                </div>
                <div class="mt-5 text-center">
                  <div class="row">
                    <div class="col-md-6">
                      <button @click="editInfo" type="button" class="btn btn-primary btn-block mb-2 editbutton">
                        Edit Profile
                      </button>
                    </div>
                    <div class="col-md-6">
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
      cardNumber: "",
      cardExpiryDate: "",
      address: "",
      errorMsg: "",
    };
  },
  created() {
    this.fetchCustomerInfo();
  },
  methods: {
    async fetchCustomerInfo() {
      try {
        const response = await axiosClient.get(`/customersEmail/${this.email}`);
        const customer = response.data;
        this.username = customer.username;
        this.cardNumber = customer.cardNumber;
        this.cardExpiryDate = customer.cardExpiryDate;
        this.address = customer.address;
      } catch (error) {
        this.errorMsg = `Error fetching customer info: ${error.response.data}`;
        alert(this.errorMsg);
      }
    },
    async saveInfo() {
      try {
        const customerRequest = {
          username: this.username,
          email: this.email,
          cardNumber: this.cardNumber,
          cardExpiryDate: this.cardExpiryDate,
          address: this.address,
        };
        await axiosClient.put(`/customers/${this.email}`, customerRequest);
        alert("Account details updated successfully.");
        this.makeFieldsReadOnly();
      } catch (error) {
        alert(`Error saving info: ${error.response.data}`);
      }
    },
    async editInfo() {
      document.getElementById("username").removeAttribute("readonly");
      document.getElementById("cardNumber").removeAttribute("readonly");
      document.getElementById("cardExpiryDate").removeAttribute("readonly");
      document.getElementById("address").removeAttribute("readonly");
    },
    makeFieldsReadOnly() {
      document.getElementById("username").setAttribute("readonly", "true");
      document.getElementById("cardNumber").setAttribute("readonly", "true");
      document.getElementById("cardExpiryDate").setAttribute("readonly", "true");
      document.getElementById("address").setAttribute("readonly", "true");
    },
    async Home() {
      await this.$router.push({path: `/CustomerHome/${this.email}`});
    },
    async Orders() {
      await this.$router.push({path: `/orders/${this.email}`});
    },
    async Wishlist() {
      await this.$router.push({path: `/wishlist/${this.email}`});
    },
    async LogOut() {
      await this.$router.push({name: "home"});
    },
  },
};
</script>

<style scoped>
.background {
  background: url("../../assets/gameshopBackground.jpg") center center no-repeat;
  background-size: cover;
  height: 100vh;
}

.navbar-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
}

.profile-box {
  position: absolute;
  top: 15%;
  left: 20%;
  right: 20%;
}

.account-box {
  background-color: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 10px;
}

.image-pos {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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

.labels {
  font-size: 12px;
  color: #444;
}

.nav-link {
  color: white !important;
}
</style>