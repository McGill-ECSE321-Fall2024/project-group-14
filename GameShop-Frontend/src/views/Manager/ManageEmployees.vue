<template>
  <div>
    <div class="hero-section">
      <!-- Navbar -->
      <div class="navbar-container">
        <nav class="navbar navbar-expand-lg navbar-light transparent-background">
          <a class="navbar-brand" href="#">
            <img src="../../assets/gameshopLogo.jpg" alt="Your Logo" height="60" />
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
                <a class="nav-link" href="#">Employees (Current)</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePolicy">Policies</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGames">Games</a>
              </li>
            
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageGameRequests">Game Requests</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManagePromotion">Promotions</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="Account">Account</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ViewOrders">Orders</a>
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
                  <div class="form-group mt-4">
                    <button
                      @click.prevent="createEmployee"
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
                        <th>Fire</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr
                        v-for="(employee, index) in employees"
                        :key="employee.personId"
                        :class="{ 'selected-row': selectedEmployeeEmail === employee.personEmail }"
                      >
                        <td>{{ employee.personUsername }}</td>
                        <td>{{ employee.personEmail }}</td>
                        <td>
                          <button
                            @click="fireEmployee(employee.personId)"
                            class="btn btn-danger fire-button"
                          >
                            Fire
                          </button>
                        </td>
                      </tr>
                      <tr v-if="employees.length === 0">
                        <td colspan="3" class="text-center">No employees found.</td>
                      </tr>
                    </tbody>
                  </table>
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
  baseURL: "http://localhost:8060",
  headers: { "Access-Control-Allow-Origin": "*" },
});

export default {
  data() {
    return {
      employees: [],
      name: "",
      employeeEmail: "",
      password: "",
      selectedEmployeeEmail: null,
      managerEmail: this.$route.params.email || "",
    };
  },
  mounted() {
    this.fetchAllEmployees();
  },
  methods: {
    async fetchAllEmployees() {
      try {
        const response = await axiosClient.get("/employees");
        this.employees = response.data.persons || [];
      } catch (error) {
        console.error("Error fetching employees:", error);
        alert("Failed to fetch employees.");
      }
    },
    async createEmployee() {
      if (!this.name || !this.employeeEmail || !this.password) {
        alert("All fields are required.");
        return;
      }
      try {
        const employeeRequest = {
          personUsername: this.name,
          personEmail: this.employeeEmail,
          personPassword: this.password,
        };
        await axiosClient.post("/employees", employeeRequest);
        alert("Employee successfully hired!");
        this.name = "";
        this.employeeEmail = "";
        this.password = "";
        this.fetchAllEmployees();
      } catch (error) {
        console.error("Error creating employee:", error);
        alert("Failed to create employee.");
      }
    },
    async fireEmployee(employeeId) {
      if (!confirm("Are you sure you want to fire this employee?")) return;
      try {
        await axiosClient.delete(`/employees/${employeeId}`);
        alert("Employee successfully fired.");
        this.fetchAllEmployees();
      } catch (error) {
        console.error("Error firing employee:", error);
        alert("Please delete all associated game requests before firing employee.");
      }
    },
    async Home() {
      await this.$router.push({ path: `/ManagerHome/${this.managerEmail}` });
    },
    async ManagePolicy() {
      await this.$router.push({ path: `/ManagePolicy/${this.managerEmail}` });
    },
    async ManageGames() {
      await this.$router.push({ path: `/ManageGames/${this.managerEmail}` });
    },
    async ManageGameRequests() {
      await this.$router.push({ path: `/ManageGameRequests/${this.managerEmail}` });
    },
    async ManagePromotion() {
      await this.$router.push({ path: `/ManagerPromotion/${this.managerEmail}` });
    },
    async Account() {
      await this.$router.push({ path: `/ManagerAccount/${this.managerEmail}` });
    },
    async ViewOrders() {
      await this.$router.push({ path: `/ViewOrders/${this.managerEmail}` });
    },
    async LogOut() {
      alert("Successfully logged out.");
      await this.$router.push({ name: "home" });
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

.navbar .nav-item.active > .nav-link {
  cursor: default;
  color: white !important; 
  pointer-events: none; 
}

.navbar .nav-link:hover {
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

.fire-button {
  background-color: #dc3545;
  color: white;
}

.hire-button {
  background-color: #007bff;
  color: white;
}
</style>
