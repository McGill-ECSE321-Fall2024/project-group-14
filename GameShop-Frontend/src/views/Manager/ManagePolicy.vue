<template>
  <div>
    <div class="hero-section">
      <div class="navbar-container">
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-light transparent-background">
          <a class="navbar-brand" href="#">
            <img src="../../assets/gameshopLogo.jpg" alt="Your Logo" height="60">
          </a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item active">
                <a class="nav-link clickable-text" @click="Home">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link clickable-text" @click="ManageEmployees">Employees</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Policies (Current)</a>
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
          <div class="col-md-12">
            <div class="card manage-policies-container shadow">
              <div class="card-header text-center">
                <h3>MANAGE POLICIES</h3>
              </div>
              <div class="card-body">
                <div class="table-scroll">
                  <table class="table table-hover">
                    <thead>
                      <tr>
                        <th>Policy ID</th>
                        <th>Description</th>
                        <th>Update</th>
                        <th>Delete</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="policy in policies" :key="policy.policyId">
                        <td>{{ policy.policyId }}</td>
                        <td>{{ policy.description }}</td>
                        <td>
                          <button class="btn btn-info btn-sm" @click="openUpdatePolicyPopup(policy)">Update</button>
                        </td>
                        <td>
                          <button class="btn btn-danger btn-sm" @click="deletePolicy(policy.policyId)">Delete</button>
                        </td>
                      </tr>
                      <tr v-if="policies.length === 0">
                        <td colspan="4" class="text-center">No policies found.</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div class="buttons-container text-center mt-4">
                  <button class="btn btn-success btn-sm" @click="openCreatePolicyPopup">Create Policy</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="popup-container">
        <div class="overlay" v-if="showCreatePolicyPopup">
          <div class="popup">
            <h2 class="prettyheader">Create New Policy</h2>
            <div class="inputrow">
              <label class="prettylabel">Description</label>
              <input v-model="currPolicy.description" type="text" id="newPolicyDescription" name="newPolicyDescription"
                class="styledinput" placeholder="Description of policy">
            </div>
            <div class="centerbuttoncontainer">
              <button class="btn btn-danger btn-sm" @click="closeCreatePolicyPopup">Close</button>
              <button class="btn btn-success btn-sm" @click="createPolicy" v-if="!isUpdate">Create</button>
              <button class="btn btn-success btn-sm" @click="updatePolicy" v-if="isUpdate">Update</button>
            </div>
            <div class="centerbuttoncontainer">
              <label>{{ errorMessage }}</label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

const baseURL = "http://localhost:8060";

export default {
  name: 'ManagePolicy',
  data() {
    return {
      email: this.$route.params.param1, // Email from the URL
      policies: [],
      policyDetails: null,
      showCreatePolicyPopup: false,
      errorMessage: "",
      currPolicy: {
        description: "",
      },
      isUpdate: false,
      currid: null,
    }
  },

  methods: {
    navigateTo(route) {
      const routes = {
        Home: `/ManagerHome/${this.email}`,
        ManageEmployees: `/ManageEmployees/${this.email}`,
        ManagePolicy: `/ManagePolicy/${this.email}`,
        ManageGames: `/ManageGames/${this.email}`,
        ManageGameRequests: `/ManageGameRequests/${this.email}`,
        Account: `/CustomerAccount/${this.email}`,
        ViewOrders: `/ViewOrders/${this.email}`,
        ManagePromotions: `/ManagerPromotion/${this.email}`,
        LogOut: "/",
      };
      if (route === "LogOut") {
        alert("Successfully logged out.");
      }
      this.$router.push(routes[route]);
    },

    openCreatePolicyPopup() {
      this.currPolicy = {
        description: "",
      }
      this.showCreatePolicyPopup = true;
      this.isUpdate = false;
      this.errorMessage = "";
    },

    openUpdatePolicyPopup(policy) {
      this.currPolicy = {
        description: policy.description,
      }
      this.currid = policy.policyId;
      this.showCreatePolicyPopup = true;
      this.isUpdate = true;
    },

    closeCreatePolicyPopup() {
      this.showCreatePolicyPopup = false;
      this.errorMessage = "";
    },

    // Get all policies
    async fetchPolicies() {
      try {
        const response = await axios.get(`http://localhost:8060/policy`);
        this.policies = response.data;
      } catch (error) {
        this.errorMessage = error.response?.data?.message || "Failed to fetch policies.";
      }
    },

    // Create a new policy
    async createPolicy() {
      try {
        const payload = {
          description: this.currPolicy.description,
        }
        await axios.post('http://localhost:8060/policy', payload);
        this.fetchPolicies();
        this.closeCreatePolicyPopup();
      } catch (error) {
        this.errorMessage = error.response?.data?.message || "Failed to create policy.";
      }
    },

    // Update a policy
    async updatePolicy() {
      try {
        const payload = {
          description: this.currPolicy.description,
        }
        await axios.post(`http://localhost:8060/policy/update/${this.currid}`, payload);
        this.fetchPolicies();
        this.closeCreatePolicyPopup();
      } catch (error) {
        this.errorMessage = error.response?.data?.message || "Failed to update policy.";
      }
    },

    // Delete a policy
    async deletePolicy(id) {
      try {
        await axios.delete(`http://localhost:8060/policy/${id}`);
        this.fetchPolicies(); // Refresh the list after deletion
      } catch (error) {
        this.errorMessage = `Could not delete policy with ID ${id}.`;
      }
    },

    async Home() {
      await this.$router.push({ path: `/ManagerHome/${this.email}` });
    },
    async ManageEmployees() {
      await this.$router.push({ path: `/ManageEmployees/${this.email}` });
    },
    async ManagePolicy() {
      await this.$router.push({ path: `/ManagePolicy/${this.email}` });
    },
    async ManageGames() {
      await this.$router.push({ path: `/ManageGames/${this.email}` });
    },
    async ManageGameRequests() {
      await this.$router.push({ path: `/ManageGameRequests/${this.email}` });
    },
    async Account() {
      await this.$router.push({ path: `/ManagerAccount/${this.email}` });
    },
    async ViewOrders() {
      await this.$router.push({ path: `/ViewOrders/${this.email}` });
    },
    async LogOut() {
      alert('Successfully logged out.');
      await this.$router.push({ name: 'home' });
    },
    async ManagePromotion() {
      await this.$router.push({ path: `/ManagerPromotion/${this.email}` });
    },
  },

  created() {
    this.fetchPolicies();
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

.manage-policies-container {
  background-color: white;
  border-radius: 10px;
  padding: 20px;
}

.table-scroll {
  height: 300px;
  overflow-y: auto;
}

.btn-sm {
  padding: 5px 10px;
  font-size: 14px;
}

.buttons-container {
  text-align: center;
  margin-top: 10px;
}

.centerbuttoncontainer {
  text-align: center;
  margin-top: 10px;
}

.inputrow {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin-bottom: 10px;
  width: 100%;
}

.prettylabel {
  width: 25%;
  color: #4e555b;
}

.prettyheader {
  color: #4e555b;
  margin-bottom: 10%;
}

.overlay {
  display: flex;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  justify-content: center;
  align-items: center;
  z-index: 2;
}

.popup-container {
  display: block;
  position: relative;
  z-index: 3;
}

.popup {
  width: 350px;
  height: 250px;
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.clickable-text:hover {
  cursor: pointer;
  color: white !important;
}

</style>
