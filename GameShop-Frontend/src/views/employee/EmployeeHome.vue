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
                <a class="nav-link" href="#">Home<span class="sr-only">(current)</span></a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="EmployeeAccount">Account</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="SubmitGameRequest">Submit Game Request</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewGames">View Games</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewOrders">View Orders</a>
              </li>
              <li>
                <a class="nav-link clickable-text" @click="LogOut">Log Out</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>

      <div class="container">
        <div class="row">
          <div class="col-md-12 mx-auto text-center">
            <h1 class="text-center" style="font-family: 'Montserrat', serif; color: white; letter-spacing: 5px; font-size: 45px" >GAMESHOP EMPLOYEE PORTAL</h1>
            <p class="luxurious-text" style="font-weight: normal; color: white;">Welcome, {{username}}!</p>
          </div>
        </div>
      </div>
    </div>

    <div id = "intro"></div>
    <div class="custom-box">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <h2 class="text-center mb-4" style="font-family: 'Montserrat', serif; color: #888; letter-spacing: 2px">YOUR PORTAL</h2>
            <p class="text-center" style="font-family: 'Georgia', serif">Welcome to your GameShop Employee Portal. Enjoy your shift! </p>
          </div>
        </div>
      </div>
    </div>

    <div class="footer-container">
      <footer class="footer">
        <div class="d-flex align-items-center justify-content-center">
          <img src="../../assets/gameshopLogo.jpg" alt="Your Logo" height="65">
        </div>
        <div class="d-flex align-items-center justify-content-center">
          <p class="text-right" style="font-size: 10px">©GameShop 2024. All Rights Reserved.</p>
        </div>
      </footer>
    </div>
  </div>
</template>
<script>

export default {
  name: 'EmployeeHome',

  data() {
    return {
      isLoggedIn: false,
      username: '',
      email: '',
      storePolicy :''
    };
  },

  mounted() {
    this.email = this.$route.params.param1;
    this.username = this.$route.params.param2;
    this.fetchPolicy();
  },

  methods: {
    async EmployeeAccount() {
      await this.$router.push({path: '/EmployeeAccount/' + this.email })
    },
    async LogOut() {
      await this.$router.push({name: "home"})
    },
    async SubmitGameRequest() {
      await this.$router.push({path: '/EmployeeGameRequest/' + this.email + '/' + this.username})
    },
    async ViewOrders() {
      await this.$router.push({path: '/EmployeeViewOrders/' + this.email + '/' + this.username})
    },

    async ViewGames() {
      await this.$router.push({path: '/EmployeeViewGames/' + this.email + '/' + this.username})
    },
    async fetchPolicy() {
      try {
        const response = await fetch("http://localhost:8060/policy"); // Update with the correct backend URL
        const policies = await response.json();
        console.log("Fetched policies:", policies);

        if (policies.length > 0) {
          // Display the first policy's description
          this.storePolicy = `GameShop Policy: ${policies[0].description}`;
        } else {
          // Default message if no policies exist
          this.storePolicy = "GameShop Policy: buy games.";
        }
      } catch (error) {
        console.error("Error fetching policy:", error);
        // Fallback message in case of an error
        this.storePolicy = "GameShop Policy: buy games.";
      }
    }
  }
};
</script>

<style scoped>
.navbar-brand {
  margin-right: 0;
}

.hero-section {
  background: url('../../assets/gameshopBackground.jpg') center/cover no-repeat;
  padding: 400px 0;
  text-align: center;
}

.luxurious-text {
  font-family: 'Georgia', sans-serif;
  font-weight: bold;
  color: black;
}

.custom-box {
  margin: 20px;
  padding: 30px;
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

.transparent-background {
  background-color: rgba(255, 255, 255, 0.6);
}


</style>
