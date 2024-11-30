<template>
    <div>
      <div id="main"></div>
      <div class="hero-section">
        <!-- Navbar -->
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
                <li class="nav-item active">
                  <a class="nav-link" href="#">Manage Employees</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" @click="ManageGames">Manage Games</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" @click="ViewOrders">View Orders</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" @click="Account">Account</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link clickable-text" @click="LogOut">Log Out</a>
                </li>
              </ul>
            </div>
          </nav>
        </div>
  
        <!-- Page Content -->
        <div class="container content-container">
          <div class="row mt-5">
            <!-- Hire Employee Section -->
            <div class="col-md-6">
              <div class="card hire-container shadow">
                <div class="card-header text-center">
                  <h3>HIRE EMPLOYEE</h3>
                </div>
                <div class="card-body">
                  <form>
                    <div class="form-group">
                      <input
                        id="name"
                        v-model="name"
                        type="text"
                        class="form-control"
                        placeholder="Full Name"
                      />
                    </div>
                    <div class="form-group">
                      <input
                        id="employeeEmail"
                        v-model="employeeEmail"
                        type="email"
                        class="form-control"
                        placeholder="Email"
                      />
                    </div>
                    <div class="form-group">
                      <input
                        id="password"
                        v-model="password"
                        type="password"
                        class="form-control"
                        placeholder="Password"
                      />
                    </div>
                    <div class="form-group">
                      <input
                        id="salary"
                        v-model="salary"
                        type="number"
                        class="form-control"
                        placeholder="Salary"
                      />
                    </div>
                    <div class="form-group mt-4"> <!-- Added spacing -->
                      <button
                        @click="createEmployee"
                        type="button"
                        class="btn btn-primary btn-block hire-button"
                      >
                        Hire
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
  
            <!-- Manage Current Employees Section -->
            <div class="col-md-6">
              <div class="card viewEmployees-container shadow">
                <div class="card-header text-center">
                  <h3>MANAGE CURRENT EMPLOYEES</h3>
                </div>
                <div class="card-body">
                  <div class="table-scroll">
                    <table class="table table-hover">
                      <thead>
                        <tr>
                          <th>Name</th>
                          <th>Email</th>
                          <th>Salary</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr
                          v-for="(employee, index) in employees"
                          :key="index"
                          @click="selectEmployee(employee.personEmail)"
                          :class="{ 'selected-row': selectedEmployeeEmail === employee.personEmail }"
                        >
                          <td>{{ employee.personUsername }}</td>
                          <td>{{ employee.personEmail }}</td>
                          <td>${{ employee.salary }}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                  <div class="text-center">
                    <button
                      @click="fireEmployee"
                      type="button"
                      class="btn btn-danger fire-button"
                    >
                      Fire
                    </button>
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
  
  const axiosClient = axios.create({
    baseURL: "http://localhost:8080",
    headers: { "Access-Control-Allow-Origin": "*" },
  });
  
  export default {
    data() {
      return {
        employees: [],
        selectedEmployeeEmail: null,
        name: "",
        employeeEmail: "",
        password: "",
        salary: "",
      };
    },
    methods: {
      fetchAllEmployees() {
        axiosClient
          .get("/employees")
          .then((response) => {
            this.employees = response.data.dtos; 
          })
          .catch((error) => {
            console.error("Error fetching employees:", error);
          });
      },
      createEmployee() {
        const employeeRequest = {
          personUsername: this.name,
          personEmail: this.employeeEmail,
          personPassword: this.password,
          salary: parseFloat(this.salary) || 0, 
        };
        axiosClient
          .post("/employees", employeeRequest)
          .then(() => {
            alert("Employee successfully created!");
            this.fetchAllEmployees(); 
          })
          .catch((error) => {
            console.error("Error creating employee:", error);
          });
      },
      fireEmployee() {
        if (!this.selectedEmployeeEmail) {
          alert("Please select an employee to fire.");
          return;
        }
        axiosClient
          .delete(`/employees/${this.selectedEmployeeEmail}`)
          .then(() => {
            alert("Employee successfully fired.");
            this.fetchAllEmployees();
          })
          .catch((error) => {
            console.error("Error firing employee:", error);
          });
      },
      selectEmployee(email) {
        this.selectedEmployeeEmail = email;
      },
      Home() {
        this.$router.push("/home");
      },
      ManageGames() {
        this.$router.push("/games");
      },
      ViewOrders() {
        this.$router.push("/orders");
      },
      Account() {
        this.$router.push("/account");
      },
      LogOut() {
        alert("Successfully logged out.");
        this.$router.push("/home");
      },
    },
    mounted() {
      this.fetchAllEmployees();
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
  
  .nav-link {
    color: white !important;
  }
  
  .transparent-background {
    background-color: rgba(255, 255, 255, 0.2);
  }
  
  .hero-section {
    background: url("../../assets/gameshopBackground.jpg") center/cover no-repeat;
    padding: 200px 0; 
    text-align: center;
  }
  
  .content-container {
    margin-top: 200px;
  }
  
  .hire-container,
  .viewEmployees-container {
    background-color: white;
    border-radius: 10px;
    padding: 20px;
  }
  
  .table-scroll {
    height: 300px;
    overflow-y: auto;
  }
  
  .hire-button {
    background-color: #007bff;
    color: white;
  }
  
  .fire-button {
    margin-top: 10px;
  }
  
  .selected-row {
    background-color: #f0f0f0;
  }
  </style>
  