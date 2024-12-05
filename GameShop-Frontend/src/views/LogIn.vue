<template>
  <div class="login">
    <div class="background">

      <div class="login-container">
        <div class="login-box">
          <div class="card-header">
            <h3 class="text-center" style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 2px">LOG IN</h3>
          </div>
          <div class="card-body">
            <form>
              <div class="input-group form-group">
                <input id="email" v-model="email" type="email" class="form-control" style="font-family: 'Georgia', sans-serif" placeholder="Email Address">
              </div>
              <div class="input-group form-group">
                <input id="password" v-model="password" type="password" class="form-control" style="font-family: 'Georgia', sans-serif" placeholder="Password">
              </div>

              <div class="form-check form-check-inline">
                <input v-model="user" class="form-check-input" type="radio" name="user" id="manager" value="Manager">
                <label class="form-check-label" for="manager">Manager</label>
              </div>

              <div class="form-check form-check-inline">
                <input v-model="user" class="form-check-input" type="radio" name="user" id="customer" value="Customer">
                <label class="form-check-label" for="customer">Customer</label>
              </div>
              <div class="form-check form-check-inline">
                <input v-model="user" class="form-check-input" type="radio" name="user" id="employee" value="Employee">
                <label class="form-check-label" for="employee">Employee</label>
              </div>
              <div class="form-group">
                <button @click="loginUser()" type="button"
                        class="btn btn-primary btn-block mb-4 signinbutton">Sign in</button>
              </div>
            </form>
          </div>
          <div class="card-footer">
            <div class="d-flex align-items-center links">
              <a class="nav-link" @click="SignUp">Don't have an account? Sign Up</a>
            </div>
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
              <li class="nav-item active">
                <a class="nav-link" href="#">Login<span class="sr-only"> (Current)</span></a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="SignUp">SignUp</a>
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
  name: "Login",
  data() {
    return {
      email: "",
      password: "",
      user: "",
      errorMsg: "",
    };
  },
  methods: {
    loginUser() {
      if (!this.user) {
        alert("Please select a user type to log in.");
        return;
      }

      const loginEndpoint = {
        Customer: "/customers/login",
        Employee: "/employees/login",
        Manager: "/managers/login",
      };

      const requestBody = {
        email: this.email,
        password: this.password,
        username: this.username
      };

      axiosClient
          .post(loginEndpoint[this.user], requestBody)
          .then((response) => {
            this.username = response.data.username;
            alert("Successfully logged in.");
            // Redirect to the respective home page based on the user type
            if (this.user === "Customer") {
              this.$router.push({ path: `/CustomerHome/${this.email}` });
            } else if (this.user === "Employee") {
              this.$router.push({ path: `/EmployeeHome/${this.email}/${this.username}` });
            } else if (this.user === "Manager") {
              this.$router.push({ path: `/ManagerHome/${this.email}` });
            }
          })
          .catch((err) => {
            this.errorMsg = `Error logging in. Please try again.`;
            alert(this.errorMsg);
          });
    },
    async SignUp() {
      await this.$router.push({ path: "/SignUp/" });
    },
    async Home() {
      await this.$router.push({ name: "home" });
    },
  },
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

.login {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.login-box {
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

.login-container {
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

.card-footer {
  background: white;
  border-top: white;
  cursor: pointer;
}

.navbar .nav-item.active > .nav-link {
  cursor: default;
  color: white !important; 
  pointer-events: none; 
}


</style>
