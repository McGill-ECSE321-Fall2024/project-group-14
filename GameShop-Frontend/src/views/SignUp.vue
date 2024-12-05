<template>
  <div class="signup">
    <div class="background">

      <div class="signup-container">
        <div class="signup-box">
          <div class="card-header">
            <h3 class="text-center" style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 2px">SIGN UP</h3>
          </div>
          <div class="card-body">
            <form>
              <div class="input-group form-group">
                <input id="username" v-model="username" type="text" class="form-control" style="font-family: 'Georgia', sans-serif" placeholder="Username">
              </div>
              <div class="input-group form-group">
                <input id="email" v-model="email" type="email" class="form-control" style="font-family: 'Georgia', sans-serif" placeholder="Email Address">
              </div>
              <div class="input-group form-group">
                <input id="password" v-model="password" type="password" class="form-control" style="font-family: 'Georgia', sans-serif" placeholder="Password">
              </div>
              <div class="input-group form-group">
                <input id="passwordConfirm" v-model="passwordConfirm" type="password" class="form-control" style="font-family: 'Georgia', sans-serif" placeholder="Confirm Password">
              </div>
              <div class="input-group form-group">
                <input id="address" v-model="address" type="text" class="form-control" style="font-family: 'Georgia', sans-serif" placeholder="Address">
              </div>
              <div class="input-group form-group">
                <input id="cardNumber" v-model="cardNumber" type="text" class="form-control" style="font-family: 'Georgia', sans-serif" placeholder="Card Number">
              </div>

              <div class="input-group form-group">
                <input id="cardExpiryDate" v-model="cardExpiryDate" type="date" class="form-control" style="font-family: 'Georgia', sans-serif">
              </div>
              <div class="form-group">
                <button @click="createCustomer()" type="button"
                        class="btn btn-primary btn-block mb-4 signinbutton">Sign Up</button>
              </div>
              <div class="d-flex align-items-center links">
                <a class="nav-link" style="cursor: pointer" @click="Login">Already have an account? Log In</a>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div class="background-image">
        <div class="layout-background-image">
        </div>
      </div>

      <div class="navbar-container">
        <nav class="navbar navbar-expand-lg navbar-light transparent-background">
          <a class="navbar-brand" href="#">
            <img src="@/assets/gameshopLogo.jpg" alt="Your Logo" height="60">
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
                <a class="nav-link clickable-text" @click="Login">Login</a>
              </li>
              <li class="nav-item active">
                <a class="nav-link" href="#">SignUp<span class="sr-only"> (Current)</span></a>
              </li>
            </ul>
          </div>
        </nav>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios'
var frontendUrl = 'http://localhost:5173'
var backendUrl = 'http://localhost:8060'
var axiosClient = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'SignUp',
  data() {
    return {
      email: '',
      password: '',
      passwordConfirm: '',
      username: '',
      address: '',
      user: '',
      cardNumber: '',
      cardExpiryDate: '',
      errorMsg: '',
      new_customer: [],
      new_account: [],
      id: 0,
    };
  },
  methods: {
    createCustomer(){
      this.email = document.getElementById("email").value;
      this.password = document.getElementById("password").value;
      this.passwordConfirm = document.getElementById("passwordConfirm").value;
      this.username = document.getElementById("username").value;
      this.address = document.getElementById("address").value;
      this.cardExpiryDate = document.getElementById("cardExpiryDate").value;
      this.cardNumber = document.getElementById("cardNumber").value;

      if(this.password === this.passwordConfirm){

              const customer_request = {username: this.username, email: this.email, password: this.password, cardNumber: this.cardNumber, cardExpiryDate: this.cardExpiryDate, address: this.address}
              axiosClient.post("/customers", customer_request)
                  .then((response) => {
                    alert('Account successfully created.')
                    this.new_customer = response
                    this.id = response.data.id
                    this.$router.push({path: '/Login/'})
                  })
                  .catch((err) => {
                    this.errorMsg = `Failure creating account. Please try again.`
                    alert(this.errorMsg)
                  })

            .catch((err) => {
              this.errorMsg = `Failure creating account. Please try again.`
              alert(this.errorMsg)
            })
      } else {
        alert('Passwords do not match.')
      }

    },
    async Login() {
      await this.$router.push({path: '/Login/'})
    },
    async Home(){
      await this.$router.push({name: 'home'})
    },
  }
};
</script>

<style scoped>
.background {
  width: 100%;
  height: 100%;
  position: absolute;
  background: white;
  background-size: cover;
}

.signup {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.signup-box {
  width: 65%;
}

.background {
  flex: 1;
  display: flex;
}

.background-image {
  flex: 1;
  background-image: url('@/assets/gameshopBackground.jpg');
  background-size: cover;
  background-position: center;
}

.signup-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.navbar-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
}

.transparent-background {
  background-color: rgba(136, 136, 136, 0.3);
}

.signinbutton {
  width: 50%;
  margin-top: 5%;
  margin-left: 25%;
  background-color: white;
  border: 2px solid #888888;
  color: #888888;
}

.signinbutton:hover {
  border: #888888;
  background-color: #888888;
  border: 2px solid #888888;
  color: white;
}

.clickable-text:hover {
  cursor: pointer;
  color: white !important;
}

.card-header {
  background: white;
  border-bottom: white;
}

.navbar .nav-item.active > .nav-link {
  cursor: default;
  color: white !important; 
  pointer-events: none; 
}


</style>
