
<template>
  <div>
    <div id = "main"></div>
    <div class="hero-section">
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

      <div class="container" style="margin-top: 70px;">
        <div class="row">
          <div class="col-md-12 mx-auto text-center">
            <h1 class="text-center" style="font-family: 'Montserrat', serif; color: #fff; letter-spacing: 5px; font-size: 45px" >GAMESHOP MANAGER PORTAL</h1>

          </div>
        </div>
      </div>
    </div>




  </div>
</template>

<script>

export default {
  name: 'ManagerHome',
  data() {
    return {
      email: "",
    };
  },
  mounted() {
    this.email = this.$route.params.param1
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
      await this.$router.push({path: '/CustomerAccount/' + this.email})
    },
    async ViewOrders() {
      await this.$router.push({path: '/ViewOrders/' + this.email})
    },
    async LogOut() {
      alert('Successfully logged out.')
      await this.$router.push({name: 'home'})
    }
  }
};
</script>

<style scoped>

.navbar-brand {
  margin-right: 0;
}

.nav-link {
  color: white !important;
}

.hero-section {
  background: url("@/assets/gameshopBackground.jpg") center/cover no-repeat;
  padding: 200px 0;
  text-align: center;
  min-height: 100vh;
}

.luxurious-text {
  font-family: 'Georgia', sans-serif;
  font-weight: bold;
  color: black;
}

.custom-login-button {
  background-color: transparent;
  color: #fff;
  border: 2px solid #fff;
  transition: background-color 0.3s, color 0.3s;
}

.custom-login-button:hover {
  background-color: #fff;
  color: #888;
}

.custom-box {
  border: 2px solid lightgray;
  margin: 20px;
  padding: 20px;
}

.transparent-background {
  background-color: rgba(255, 255, 255, 0.2);
}

.navbar-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
}

.footer-container {
  position: relative;
  margin-top: 100px;
}

.footer {
  position: absolute;
  bottom: 0;
  width: 100%;
  background-color: rgba(136, 136, 136, 0.2);
  padding-top: 15px;
}

.clickable-text:hover {
  cursor: pointer;
  color: white !important;
}

.img-fluid {
  max-height: 150px; /* Keep the image height uniform */
  object-fit: contain; /* Ensures the image fits without cropping */
  width: 100%; /* Ensure full width */
}

.col-md-3 {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  text-align: center;
  padding: 15px;
  min-height: 450px; /* Set a consistent height for all cards */
  box-sizing: border-box; /* Ensures padding doesn't affect height */
}

.col-md-3 p {
  font-family: 'Georgia', sans-serif;
  margin: 10px 15px;
  line-height: 1.6;
}

.row {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around; /* Adds spacing between columns */
}

</style>
