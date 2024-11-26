<template>
    <div>
        <div id="makeOrder">
            <div class="background">
                <div class = navbar-container>
                    <nav class="navbar navbar-expand-lg navbar-light transparent-background">
                        <a class="navbar-band" href="#">
                            <img src="../assets/marwaniottNoBG.png" alt="Your Logo" height="60">
                        </a>
                        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                <a class="nav-link clickable-text" >Home</a>
                                </li>
                                <li class="nav-item active">
                                <a class="nav-link" href="#">Reservations<span class="sr-only">(current)</span></a>
                                </li>
                                <li class="nav-item">
                                <a class="nav-link clickable-text" >Account</a>
                                </li>
                                <li class="nav-item">
                                <a class="nav-link clickable-text" >Log Out</a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
    </div>



</template>









<style scoped>
.background {
  width: 100%;
  height: 100%;
  position: absolute;
  background: url('../../assets/hotelView.png') center center no-repeat;
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
  background-color: rgba(255, 255, 255, 0.2);
}

.nav-link:hover {
  cursor: pointer;
}

.reservations-container {
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


</style>
