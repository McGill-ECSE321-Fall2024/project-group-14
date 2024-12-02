
<template>
  <div>
    <div id = "main"></div>
    <div class="hero-section">
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
              <li class="nav-item active">
                <a class="nav-link" href="#">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Login">LogIn</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="SignUp">SignUp</a>
              </li>
            </ul>
          </div>
        </nav>
      </div>

      <div class="container">
        <div class="row">
          <div class="col-md-12 mx-auto text-center">
            <h1 class="text-center" style="font-family: 'Montserrat', serif; color: #fff; letter-spacing: 5px; font-size: 45px" >GAMESHOP</h1>
            <p class="luxurious-text" style="font-weight: normal; color: #fff;">buy games</p>
            <button class="btn btn-lg mt-3 custom-login-button" @click="CreateAccountSuggestion">Browse Games</button>
          </div>
        </div>
      </div>
    </div>

    <div id = "intro"></div>
    <div class="custom-box">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <h2 class="text-center mb-4" style="font-family: 'Montserrat', serif; color: #888; letter-spacing: 2px">What Gameshop does</h2>
            <p class="text-center" style="font-family: 'Georgia', serif">GameShop is an innovative online platform revolutionizing the way gamers shop, offering a dynamic catalog of games curated by staff and approved by the owner for ultimate quality. With a sleek interface, customers can browse, purchase, track orders, save wish lists, and share reviews, making gaming more engaging than ever. Empowering the store owner and employees with a powerful dashboard to manage inventory, categories, promotions, and customer interactions, GameShop seamlessly bridges the gap between an indie game shop and a cutting-edge digital storefront.</p>
          </div>
        </div>
      </div>
    </div>

    <div id = "info"></div>
    <div class="container mt-4">
      <div class="row">
        <!-- Column 1 -->
        <div class="col-md-3 shadow p-3 mb-5 bg-white rounded">
          <img src="@/assets/purchaseGamesImg.jpg" alt="Image 1" class="img-fluid mb-3">
          <p class="text-center" style="font-family: 'Montserrat', serif; color: #888; letter-spacing: 2px; font-weight: bold">PURCHASE GAMES</p>
          <p class="text-center" style="font-family: 'Georgia', sans-serif">Easily purchase your favorite games at GameShop with a seamless checkout process designed for quick and secure transactions. Dive straight into your next gaming adventure with just a few clicks!</p>
        </div>

        <!-- Column 2 -->
        <div class="col-md-3 shadow p-3 mb-5 bg-white rounded">
          <img src="@/assets/wishlistLogo.png" alt="Image 2" class="img-fluid mb-3">
          <p class="text-center" style="font-family: 'Montserrat', serif; color: #888; letter-spacing: 2px; font-weight: bold">WISHLIST</p>
          <p class="text-center" style="font-family: 'Georgia', sans-serif">
            GameShop's wishlist feature lets you save your favorite games for future purchases, making it easier to keep track of what you love. Plan your next gaming spree and never lose sight of the titles you want most!</p>
        </div>

        <!-- Column 3 -->
        <div class="col-md-3 shadow p-3 mb-5 bg-white rounded">
          <img src="@/assets/gameReviewImg.jpg" alt="Image 3" class="img-fluid mb-3">
          <p class="text-center" style="font-family: 'Montserrat', serif; color: #888; letter-spacing: 2px; font-weight: bold">GAME REVIEWS</p>
          <p class="text-center" style="font-family: 'Georgia', sans-serif">GameShop allows customers to share their gaming experiences by posting reviews for the games they've purchased. Discover honest feedback from fellow gamers to help make your next gaming choice even better!</p>

        </div>

        <!-- Column 4 -->
        <div class="col-md-3 shadow p-3 mb-5 bg-white rounded">
          <img src="@/assets/browseGamesImg.png" alt="Image 4" class="img-fluid mb-3">
          <p class="text-center" style="font-family: 'Montserrat', serif; color: #888; letter-spacing: 2px; font-weight: bold">BROWSE GAMES</p>
          <p class="text-center" style="font-family: 'Georgia', sans-serif">Explore GameShop's vast collection of games with ease through its intuitive browsing interface. Quickly find your favorite titles or discover new ones using filters like category, genre, and popularity.</p>
        </div>
      </div>
    </div>

    <div class="footer-container">
      <footer class="footer transparent-background">
        <div class="d-flex align-items-center justify-content-center">
          <img src="@/assets/gameshopLogo.jpg" alt="Your Logo" height="65">
        </div>
        <div class="d-flex align-items-center justify-content-center">
          <p class="text-right" style="font-size: 10px">©The Gameshop 2024. All Right Reserved.</p>
        </div>
      </footer>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Home',
  data() {
    return {
      isLoggedIn: false,
    };
  },
  methods: {
    async Login() {
      await this.$router.push({path: '/Login/'})
    },
    async SignUp() {
      await this.$router.push({path: '/SignUp/'})
    },
    async CreateAccountSuggestion(){
      await this.$router.push({name: 'CreateAccountSuggestion'})
    },
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
  background: url('@/assets/gameshopBackground.jpg') center/cover no-repeat;
  padding: 300px 0;
  text-align: center;
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
