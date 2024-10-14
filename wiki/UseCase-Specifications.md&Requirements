## Use Case Specifications

### 1. Use Case: Create Account

- **ID**: UC1
- **Title**: Create Account
- **Description**: The system shall allow users (guests) to create an account by providing a username, email address, and password.
- **Actor**: Guest
- **Pre-Condition**: The guest is on the registration page.
- **Post-Condition**: The account is created, and the guest is now a registered customer.

#### Main Success Scenario:
1. Guest navigates to the registration page.
2. System displays the account creation form.
3. Guest fills out the form with a username, email address, and password.
4. System verifies that the information is valid and unique.
5. System creates the account and displays a confirmation message.

#### Alternate Scenarios:
- **4.a**: The email address is already in use.
  - **4.a.1**: System prompts the guest to use a different email address or log in with the existing account.
- **4.b**: The password does not meet security requirements.
  - **4.b.1**: System prompts the guest to enter a valid password.
- **4.c**: The guest enters a blank field.
  - **4.c.1**: System informs the guest that all fields must be filled out before submitting the form.
- **4.d**: The email format is invalid (e.g., missing "@" symbol).
  - **4.d.1**: System displays an error message stating that the email address is not valid.
  - **4.d.2**: The guest is prompted to re-enter the email.

**Author**: Sara Andari

---

### 2. Use Case: Purchase Games

- **ID**: UC2
- **Title**: Purchase Games
- **Description**: The system shall allow customers to purchase games from the catalog.
- **Actor**: Customer
- **Pre-Condition**: The customer is logged into their account and has added a game to their shopping cart.
- **Post-Condition**: The game is successfully purchased, and the transaction is reflected in the customer’s order history.

#### Main Success Scenario:
1. Customer selects a game from the catalog.
2. Customer adds the game to their shopping cart.
3. Customer proceeds to checkout.
4. Customer selects a payment method and confirms the purchase.
5. System processes the payment and completes the transaction.

#### Alternate Scenarios:
- **4.a**: Customer does not have a valid payment method.
  - **4.a.1**: System prompts the customer to add a valid payment method before proceeding.
- **5.a**: Payment fails due to insufficient funds or system error.
  - **5.a.1**: System notifies the customer of the payment failure and prompts them to retry or use another payment method.
- **3.a**: Customer abandons the cart before completing the purchase.
  - **3.a.1**: The system saves the cart for future purchases or sends a reminder email.

**Author**: Diana Bodik

---

### 3. Use Case: Submit Game Request

- **ID**: UC3
- **Title**: Submit Game Request
- **Description**: The system shall allow employees to submit a request to add a new game to the catalog.
- **Actor**: Employee
- **Pre-Condition**: The employee is logged into the system and has the necessary permissions.
- **Post-Condition**: The game addition request is submitted and awaiting owner approval.

#### Main Success Scenario:
1. Employee selects the option to submit a new game request.
2. System displays the game request form.
3. Employee fills out the form with game details and submits the request.
4. System confirms that the request has been submitted successfully.

#### Alternate Scenarios:
- **3.a**: The employee submits incomplete or invalid details in the form.
  - **3.a.1**: System prompts the employee to fill out missing fields or correct errors.
- **3.b**: A request for the same game has already been submitted.
  - **3.b.1**: System informs the employee that a request for this game is already pending approval and does not allow a duplicate submission.

**Author**: Alex Alberto Zhen

---

### 4. Use Case: Approve Game Request

- **ID**: UC4
- **Title**: Approve Game Request
- **Description**: The system shall allow the owner to approve or deny game requests submitted by employees.
- **Actor**: Owner
- **Pre-Condition**: The owner is logged into the system and has pending game requests from employees.
- **Post-Condition**: The game request is approved or denied, and the employee is notified.

#### Main Success Scenario:
1. Owner navigates to the list of submitted game requests.
2. System displays the pending game requests.
3. Owner selects a game request to review.
4. Owner reviews the details and chooses to approve or deny the request.
5. System updates the game request status and notifies the employee of the decision.

#### Alternate Scenarios:
- **4.a**: Owner requires additional information about the game before making a decision.
  - **4.a.1**: Owner sends the request back to the employee for clarification, and the use case continues when the employee resubmits the request.

**Author**: Tessa Hason

---

### 5. Use Case: Update Inventory

- **ID**: UC5
- **Title**: Update Inventory
- **Description**: The system shall allow employees to update the number of games available in the inventory.
- **Actor**: Employee
- **Pre-Condition**: The employee is logged into the system and has the necessary permissions to modify inventory.
- **Post-Condition**: The inventory is updated to reflect the new stock levels.

#### Main Success Scenario:
1. Employee selects a game to update from the inventory.
2. System displays the current stock level of the selected game.
3. Employee updates the stock level and confirms the changes.
4. System updates the inventory and reflects the changes in the game catalog.

#### Alternate Scenarios:
- **3.a**: Employee enters an invalid stock value (e.g., negative number, letters).
  - **3.a.1**: System prompts the employee to correct the stock value.
- **3.b**: Employee does not enter any value in the stock update field.
  - **3.b.1**: System prompts the employee to enter a valid stock value.
  - **3.b.2**: The use case continues once the employee enters a valid value.

**Author**: Deniz Karakus

---

### 6. Use Case: Return Games

- **ID**: UC6
- **Title**: Return Games
- **Description**: The system shall allow customers to return a game within 7 days of purchase.
- **Actor**: Customer
- **Pre-Condition**: The customer is logged into their account, and the game is eligible for return (within 7 days of purchase).
- **Post-Condition**: The game is successfully returned, and the customer's payment is refunded.

#### Main Success Scenario:
1. Customer navigates to their order history.
2. Customer selects the game they wish to return.
3. System verifies the game’s eligibility for return.
4. Customer confirms the return request.
5. System processes the return and refunds the customer.

#### Alternate Scenarios:
- **3.a**: The game is not eligible for return (e.g., beyond 7 days).
  - **3.a.1**: System informs the customer that the return is not possible and provides no further actions.
- **5.a**: Refund fails due to payment processing issues.
  - **5.a.1**: System notifies the customer that the refund could not be processed and
