EventHive - A Dynamic Event Management System 🚀
![alt text](https://img.shields.io/badge/Java-8%2B-blue?style=for-the-badge&logo=java)
![alt text](https://img.shields.io/badge/Apache-Tomcat-F8981D?style=for-the-badge&logo=apache)
![alt text](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql)
![alt text](https://img.shields.io/badge/Apache-Maven-C71A36?style=for-the-badge&logo=apache-maven)
EventHive is a full-stack, role-based event management web application built with the Java Enterprise stack. This project demonstrates a robust MVC architecture for creating, discovering, and managing events for three distinct user roles.
<p align="center">
<img src="C:\Users\RAJAT CHAUHAN\Downloads\yoyo\EventHive\OutputImages\frontPage.png" alt="EventHive Application Showcase" width="800"/>
</p>
✨ Role-Based Features
The platform's functionality is meticulously tailored to each user role, providing a seamless and powerful experience.
👤 Attendee Features
Event Discovery: Browse and view details for all upcoming events.
One-Click Registration: Securely register for any event with a single click.
Personalized Dashboard: A dedicated dashboard to view and manage your upcoming events and review past ones.
Ticket Cancellation: Easily cancel your registration for an event you can no longer attend.
🎭 Organizer Features
Full Event Management: Complete CRUD (Create, Read, Update, Delete) functionality for the events you own.
Time-Saving "Clone" Feature: Duplicate an existing event with one click to quickly set up a recurring or similar event.
Centralized Dashboard: A comprehensive overview to manage all of your live events.
Attendee Tracking: View a detailed list of all users registered for each of your events.
Revenue Dashboard: Track your financial success with a breakdown of revenue per event and a calculated grand total.
👑 Administrator Features
Platform Analytics Dashboard: An exclusive admin panel with high-level statistics, including Total Users, Total Events, and Total Platform Revenue.
Central User Management: View a comprehensive list of all Organizers and Attendees registered on the platform.
Secure Access: A protected, admin-only area to monitor the health and activity of the entire application.
🛠️ Tech Stack
Backend: Java 8, Java Servlets, jBCrypt (Password Hashing)
Frontend: JSP (JavaServer Pages), JSTL, HTML5, CSS3
Database: MySQL
Build Tool: Apache Maven
Server: Apache Tomcat
🚀 Quickstart
Get a local instance running in a few steps.
Prerequisites: JDK 8+, Maven, MySQL, Tomcat.
1. Clone the repository:
code
Sh
git clone https://github.com/your_username/EventHive.git
cd EventHive
2. Configure the Database:
Create a MySQL database named eventhive_db.
Execute the setup script found in the repository to create tables.
Update your database credentials in src/main/java/com/eventhive/db/DatabaseManager.java.
3. Build and Deploy:
Package the application using Maven:
code
Sh
mvn clean install
```-   Copy the generated `target/EventHive.war` file to your Tomcat `webapps` directory.
Start your Tomcat server.
4. Access the App:
Navigate to http://localhost:8080/EventHive/