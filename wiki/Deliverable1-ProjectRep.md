# Introduction
This report summarizes the key milestones that our team accomplished during the completion of Deliverable 1. These include outlining the system's function and non-functional requirements, developing a domain model in the form of a class diagram, putting together the project’s database as well as the code base, and implementing the tools required for project management. We aimed to establish a solid foundation for the remainder of the project, ensuring both types of requirements were carefully considered and well defined.


# Key Design Decisions

## 1. Requirements Engineering
The 10 functional requirements that we have idenified are all essential and testable. These requirements were selected to ensure they represent the core functionalities our system should offer.

Our non-functional requirements focus on server response time, overall system performance, and accessibility to guarantee a smooth user experience.

### Functional Requirements:
* FR1 - The system shall allow customers to create an account by providing a username, email address, and password.
* FR2 - The system shall allow customers to purchase games.
* FR3 - The system shall allow customers to track their order history.
* FR4 - The system shall allow customers to add reviews for the games that they have purchased.
* FR5 - The system shall allow a return on games within 7 days.
* FR6 - The system shall allow employees to update the number of games available in the inventory.
* FR7 - The system shall allow employees to submit a request to add new games.
* FR8 - The system shall allow the owner to approve the employees’ game addition requests.
* FR9 - The system shall allow employees to assign a category to added games.
* FR10 - The system must enable employees to remove games completely from the catalog.

### Non-Functional Requirements:
* NFR1: The system shall encrypt sensitive customer data, such as passwords and credit card information.
* NFR2: The system shall ensure a 1s response time for 95% of customer interactions (e.g., browsing games, adding items to cart, viewing order history).
* NFR3: The system shall provide an user friendly user interface.
* NFR4: The system shall be optimized for storage efficiency, ensuring that the database does not grow beyond 10GB in size in its initial version.
* NFR5: The system shall provide a recovery mechanism in case of unexpected crashes, the system should aim for an uptime of 95%.

## 2. Use Case Diagrams and Specifications
We created two use case diagrams representing the most critical functionalities of our system. The top six prioritized use cases are:

1. Create Account - Sara Andari
2. Purchase Games -  Diana Bodik
3. Submit Game Request - Alex Alberto Zhen
4. Approve Game Request - Tessa Hason
5. Update Inventory - Deniz Karakus
6. Return Games - Cody Choo-Foo

Detailed use case specifications are also provided for these scenarios, which can be found here.

[Use Case Specifications](https://github.com/McGill-ECSE321-Fall2024/project-group-14/blob/main/wiki/UseCase-Specifications&Requirements.md)

## 3. Domain Model
We developed a domain model in the form of a UML class diagram, which is linked in this report. The diagram captures the key entities and relationships in our GameShop system.

[Domain Model Diagram](https://cruise.umple.org/umpleonline/umple.php?model=241012cxzngptgex70&fbclid=IwZXh0bgNhZW0CMTAAAR2JUjVC8G-FysH0jYOKymH4hj5YJgux7Q2IwN-6qcDqErayH96QTxMH2pg_aem_WineI8c0r4vBYcQTbFV-AQ)

<img width="854" alt="Screenshot 2024-10-14 at 6 35 29 PM" src="https://github.com/user-attachments/assets/035173ed-5105-4904-a992-12eedd53130e">


## 4. Persistence Layer
To persist the data when the application shuts down, we implemented a persistence layer using PostgreSQL and the SpringBoot CRUD repository, incorporating Hibernate ORM technology. This layer ensures consistent data storage and retrieval, in alignment with our domain model.

## 5. Testing of Persistence Layer
A comprehensive test suite was developed to verify the persistence layer. The tests cover read, write, update, and delete operations for each entity in our domain model, ensuring successful persistence of objects and their relationships.

## 6. Build System
Our team set up a build system using Gradle to automate compilation, packaging, and testing. The system compiles the project, runs the persistence layer test suite, and guarantees the stability of our application.

## 7. Project Management
Task management is carried out using GitHub's issue tracking system, where we manage the project backlog, assign tasks, and track progress towards milestones.

Our repository contains a README file at the root, which provides an overview of the project, team roles, and contributions, along with links to important resources in our Wiki.

For meetings, we follow a structured template that outlines the purpose, agenda, notes, and next steps for every meeting. This approach helps maintain focus and track progress efficiently.
