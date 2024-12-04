<template>
    <div>
        <div id="makeOrder">
            <div class="background">
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
                        <a class="nav-link clickable-text" @click="Home">Home</a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" href="#">Orders (Current)</a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link clickable-text" @click="Wishlist">Wishlist</a>
                      </li>

                      <li class="nav-item">
                        <a class="nav-link clickable-text" @click="Account">Account</a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link clickable-text" @click="LogOut">LogOut</a>
                      </li>
                    </ul>
                  </div>
                </nav>
              </div>

                <div class="orders-container">
                  <div class="luxurious-text" style="font-family: 'Montserrat', sans-serif; color: #888; letter-spacing: 3px">
                    <h3>YOUR ORDERS</h3>
                    <div class="table-responsive subheading">
                      <table class="table table-bordered">
                        <thead>
                          <tr>
                            <th scope="col" class="text-center subheading">Order Id</th>
                            <th scope="col" class="text-center subheading">Order Date</th>
                            <th scope="col" class="text-center subheading">Game Info</th>
                            <th scope="col" class="text-center subheading">Total Price</th>
                           
                            
                            <th scope="col" class="text-center subheading">Cancel</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr v-for="order in orders" :key="order?.id">
                            <td class="text-center" style="background: white; min-width: 90px">
                              <input class="form-control text-center" :value="order?.orderId || 'N/A'" readonly>
                            </td>
                            <td class="text-center" style="background: white; min-width: 150px">
                              <input class="form-control text-center" :value="order?.date || 'N/A'" readonly>
                            </td>
                            <td class="text-center" style="background: white; min-width: 150px">
                              <input class="form-control text-center" :value="order?.gameInfo || 'N/A'" readonly>
                            </td>
                            <td class="text-center" style="background: white; min-width: 90px">
                              <input class="form-control text-center" :value="`$${order?.totalPrice?.toFixed(2) || '0.00'}`" readonly>
                            </td>
                            
                           
                            <td class="text-center" style="background: white; min-width: 90px">
                              <button class="btn cancelButton" :class="order.paid ? 'refunButton' : 'cancelButton'" @click="order.paid ? refundOrder(order?.orderId) : deleteOrder(order?.orderId)"> {{ order.paid ? 'Refund' : 'Cancel' }}</button>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
            </div>
        </div>
    </div>



</template>


<script>
import axios from 'axios';
const BASE_URL = 'http://localhost:8060/order';
const backendUrl = "http://localhost:8060"; // Hardcoded backend URL

var axiosClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Access-Control-Allow-Origin': '*',
  },
});

export default {
  name: 'OrderManagement',
  props: {
    customerEmail: {
      type: String,
      required: true, // Or false if you handle fallback
    },
  },
  data() {
    return {
      orders: [], // List of all orders
      orderDetails: null, // Details of a specific order
      showCreateOrderPopup: false, // Popup visibility for creating an order
      errorMessage: "", // Error messages for feedback
      currOrder: {
        customerId: "", // Customer ID for creating an order
      },
    };
  },

  methods: {
    // Open the create order popup
    openCreateOrderPopup() {
      this.currOrder = {
        customerId: "",
      };
      this.showCreateOrderPopup = true;
      this.errorMessage = "";
    },

    // Close the create order popup
    closeCreateOrderPopup() {
      this.showCreateOrderPopup = false;
      this.errorMessage = "";
    },

    async fetchOrders() {
      const customerEmail = this.$route.params.customerEmail; // Extract customerId from route params
      console.log("Customer Email:", customerEmail); // Debugging customerId

      try {
        // fetch the Customer ID using the email
        const customerResponse = await axios.get(`http://localhost:8060/customersEmail/${this.customerEmail}`);
        const customerId = customerResponse.data.id;
        console.log("Customer ID:", customerId);

        // Fetch the orders for the customer
        const response = await axios.get(`${BASE_URL}/customer/${customerId}`);
        const orders = response.data.orders;

        // Fetch all order items 
        const orderItemsPromises = orders.map(order =>
          axios
            .get(`http://localhost:8060/orderitems/${order.orderId}`)
            .then(res => ({ orderId: order.orderId, items: res.data.orderitems }))
            .catch(error => {
              console.error(`Failed to fetch order items for orderId ${order.orderId}:`, error);
              return { orderId: order.orderId, items: [] }; // Return an empty list on failure
            })
        );


        // Resolve all promises
        const orderItemsResults = await Promise.all(orderItemsPromises);

        // Map game info and total price to corresponding orders
        orders.forEach(order => {
          const orderItems = orderItemsResults.find(result => result.orderId === order.orderId)?.items || [];

          // Add game info
          order.gameInfo = orderItems.map(item => item.gameTitle).join(', ');

          // Calculate total price
          order.totalPrice = orderItems.reduce((sum, item) => sum + (item.price || 0), 0); // Assuming each item has a `price` field
        });

        // Update the orders list with game info and total price
        this.orders = orders;
        this.errorMessage = "";
      } catch (error) {
        this.errorMessage = error.response?.data?.message || "Failed to fetch orders.";
        console.error("Error fetching orders:", error);
      }
    },
    // Create a new order
    async createOrder() {
      console.log("Create Order button clicked!");
      try {
        const payload = {
          customerId: this.currOrder.customerId,
        };
        const response = await axios.post(BASE_URL, payload);
        console.log("Order created successfully");
        this.fetchOrders(); // Refresh orders list
        this.closeCreateOrderPopup();
      } catch (error) {
        this.errorMessage = error.response?.data?.message || "Failed to create order.";
        console.error("Error creating order: ", error);
      }
    },

    // Delete an order by ID
    async deleteOrder(orderId) {
      const isConfirmed = window.confirm("Are you sure you want to cancel this order?");
      if (!isConfirmed) {
        console.log("Order cancellation aborted by the user.");
        return; // Exit if the user cancels
      }

      try {
        await axios.delete(`${BASE_URL}/${orderId}`);
        console.log(`Order with ID ${orderId} deleted.`);
        this.fetchOrders(); // Refresh orders list
      } catch (error) {
        this.errorMessage = error.response?.data?.message || `Failed to delete order with ID ${orderId}.`;
        console.error(`Error deleting order with ID ${orderId}: `, error);
      }
    },

    // Refund an order by ID
    async refundOrder(orderId) {
      const isConfirmed = window.confirm("Are you sure you want to refund this order?");
      if (!isConfirmed) return;

      try {
        // Call the refund endpoint
        await axios.delete(`${BASE_URL}/${orderId}/refund`);
        console.log(`Order with ID ${orderId} has been refunded.`);
        
        // Refresh the orders list
        this.fetchOrders(); 
      } catch (error) {
        this.errorMessage = error.response?.data?.message || `Failed to refund order with ID ${orderId}.`;
        console.error(`Error refunding order with ID ${orderId}:`, error);
      }
    },

    // Delete all orders
    async deleteAllOrders() {
      try {
        await axios.delete(`${BASE_URL}/deleteAll`);
        console.log("All orders deleted.");
        this.fetchOrders(); // Refresh orders list
      } catch (error) {
        this.errorMessage = error.response?.data?.message || "Failed to delete all orders.";
        console.error("Error deleting all orders: ", error);
      }
    },

    async payOrder(orderId) {
      // Show confirmation dialog
      const isConfirmed = window.confirm("Are you sure you want to pay for this order?");
      if (!isConfirmed) {
        console.log("Payment canceled by the user.");
        return; // Exit if the user clicks "No"
      }

      try {
        // Call the backend API to mark the order as paid
        const response = await axios.put(`http://localhost:8060/order/${orderId}/pay`);
        console.log(`Order ID ${orderId} marked as paid on backend.`);

        // Update the order in the frontend state
        const updatedOrder = response.data; // This contains the updated order from the backend
        const orderIndex = this.orders.findIndex(order => order.orderId === orderId);

        if (orderIndex !== -1) {
          // Update the specific order in the orders list
          this.orders[orderIndex] = {
            ...this.orders[orderIndex],
            ...updatedOrder,
          };
          alert(`Order ID: ${orderId} has been paid.`);
        } else {
          console.error(`Order with ID ${orderId} not found in frontend state.`);
        }
      } catch (error) {
        console.error("Error processing payment: ", error);
        this.errorMessage = error.response?.data?.message || "Failed to process payment.";
      }
    },

    async Orders() {
      await this.$router.push({path: '/orders/' + this.customerEmail})
    },

    async Wishlist() {
      await this.$router.push({path: '/wishlist/' + this.customerEmail})
    },

    async Account() {
      await this.$router.push({path: '/CustomerAccount/' + this.customerEmail})
    },
    
    async LogOut() {
      alert('Successfully logged out.')
      await this.$router.push({name: 'home'})
    },

    async Home() {
      await this.$router.push({ path: "/CustomerHome/" + this.customerEmail });
    },

  },

  // Fetch orders when the component is created
  created() {
    console.log("Customer Email from prop:", this.customerEmail);
    console.log("Orders from backend:", this.orders);

    if (!this.customerEmail || typeof this.customerEmail !== 'string') {
      console.error("Invalid Customer Email: Must be a non-empty string.");
    } else {
      this.fetchOrders();
    }
  },
};

</script>






<style scoped>
.background {
  width: 100%;
  height: 100%;
  position: absolute;
  background: url('../../assets/gameshopBackground.jpg') center center no-repeat;
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
  background-color: rgba(255, 255, 255, 0.3);
}


.orders-container {
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
