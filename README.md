# project-group-14

# Project Overview
The goal of this project is to develop a web-based application for an independent game shop. The owner seeks to expand their business by providing an online store where customers can browse and purchase video games. The project involves designing, developing, and testing a multi-tier software solution, as well as ensuring the system meets the specified requirements.
* Customers: buy/return games from the online shop; add games to wishlist; search for games by genre and rating; review games with discussion posts.
* Employees: update inventory; make recommendation requests to purchase new games; review games with discussion posts.
* Owner: approve employee game requests; manage store employees; review customers' order history.
* Games: games have different categories, ratings (R, G, PG-13, etc.) and reviews where discussion can occur.

# How to Run the Project:

* First, clone the repository to your local machine.
* Navigate to the application.properties file located in the src/main/resources folder. Update the following lines to match your local PostgreSQL database settings:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/xxx
spring.datasource.username=xxx
spring.datasource.password=xxx
```
* Once your database is set up and the properties are updated, you can run the build using
```./gradlew build -xtest ```

NOTE: You will have to create a new database for our test to run, so please do so by using ``` CREATE DATABASE xxx ```

* Next, navigate to the backend folder: ```cd GameShop-Backend```
* Start the backend service: ``` ./gradlew bootRun ```
* The backend service will be available at: http://localhost:8060. To change the backend server port, edit the server.port property in the application.properties file.
* Next, navigate to frontend folder: ```cd GameShop-Frontend```
* Install dependencies: ```npm install```
* Start the development server : ```npm run dev```
* Open your browser and go to: http://localhost:8087. To change the frontend port, modify the port property in the vite.config.js file.

**An account for the manager is given here: Email: manager@gameshop.com, Password: password**

NOTE: Ensure that PostgreSQL is running locally before starting the backend service. If you encounter issues, double-check that the database credentials match your PostgreSQL setup. The backend and frontend ports are configurable via their respective configuration files.During the development process for this application, our team will follow the main software engineering activities: specification, development, validation, and evolution.

# The Team 👥 
| Name  | Github | Major | Year | Fun Fact |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| Tessa Hason  | [tessahas](https://github.com/tessahas)  | Software Engineering | U3 | Has a twin sister |
| Diana Bodik | [dianabodik](https://github.com/dianabodik)  | Software Engineering | U2 | Speaks russian |
| Deniz Karakus  | [denizkarakus123](https://github.com/denizkarakus123)  | Computer Engineering | U2 | Has a pet parrot |
| Cody Choo-Foo  | [codyc-f](https://github.com/codyc-f)  | Computer Engineering | U3 | Hates cheese |
| Sara Andari | [sara14and](https://github.com/sara14and)  | Computer Engineering | U3 | Middle name is George |
| Alex Alberto Zhen | [AlexZ1207](https://github.com/AlexZ1207)  | Computer Engineering | U3 | Can hold his breath for 3 min underwater|

# Overview Table 🥇 
| Name  | Role | Sprint 1 Hours | Sprint 2 Hours | Sprint 3 Hours | Presentation Hours | Total Effort Hours |
| ------------- | ------------- | ------------- | ------------- | ------------- | ------------- | ------------- |
| Tessa Hason  |Software Developer |15 | | | | |
| Diana Bodik  |Software Developer |20 | | | | |
| Deniz Karakus |Software Developer |15 | | | | |
| Cody Choo-Foo  |Software Developer |20 | | | | |
| Sara Andari |Software Developer |19 | | | | |
| Alex Alberto Zhen |Software Developer |20 | | | | |

# Sprint 1 🏃 
## Effort Table
| Name  | Contributions | Hours |
| ------------- | ------------- | ------------- |
| Tessa Hason  |Domain Model Drafting, Use Case Diagram, Use Case Specifications, Write Testing Files, Create Project, Wiki |15 |
| Diana Bodik  |Domain Model Drafting, Requirements, Create Issues, Write Repo Files, |20 |
| Deniz Karakus  |Domain Model Drafting, Requirements, Use Case Diagram, Use Case Specifications |20 |
| Cody Choo-Foo  |Domain Model Drafting, Requirements, Write Repo Files, Write Testing Files, Create Project, Create Issues, Repo and Test File Debugging |20 |
| Sara Andari |Domain Model Drafting, Requirements, Use Case Diagram, Use Case Specifications |19 |
| Alex Alberto Zhen |Domain Model Drafting, Create the Final Domain Model, Requirements, Write Repo Files, Write Testing Files, Repo and Test File Debugging|20 |


[Project Report](https://github.com/McGill-ECSE321-Fall2024/project-group-14/wiki)
