EventHive - A Dynamic Event Management System 🚀
![alt text](https://img.shields.io/badge/Java-8%2B-blue?style=for-the-badge&logo=java)
![alt text](https://img.shields.io/badge/Apache-Tomcat-F8981D?style=for-the-badge&logo=apache)
![alt text](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql)
![alt text](https://img.shields.io/badge/Apache-Maven-C71A36?style=for-the-badge&logo=apache-maven)
EventHive is a robust, full-stack, role-based web application for event management. Motivated by the need for a streamlined alternative to complex existing platforms, this project provides an intuitive and powerful user experience for three distinct user roles: Administrators, Organizers, and Attendees. The system is built on the classic and powerful Java Web technology stack, demonstrating core principles of MVC architecture, database design, and web security.
✨ Key Features
The platform's functionality is tailored to the specific needs of each user role:
👤 For Attendees
Event Discovery: Browse a beautiful homepage with a dynamic carousel of upcoming events.
User Authentication: Secure registration and login.
One-Click Registration: Seamlessly register for any event with a single click.
Personalized Dashboard: A dedicated dashboard to view and manage upcoming and past registered events.
Ticket Cancellation: Ability to cancel a registration for an upcoming event.
🎭 For Organizers
Secure Authentication: Role-specific registration and login.
Full CRUD Functionality: Create, Read, Update, and Cancel events they own.
Time-Saving Clone Feature: Clone an existing event to quickly create a new one with pre-filled details.
Event Dashboard: A centralized dashboard to manage all created events.
Attendee Tracking: View a dedicated list of all attendees registered for a specific event.
Revenue Dashboard: A financial overview page that calculates and displays revenue on a per-event basis and as a grand total.
👑 For Administrators
Secure Login: Access to a protected, admin-only control panel.
Platform Analytics: A high-level dashboard with key statistics: Total Organizers, Total Attendees, Total Events, and Total Platform Revenue.
User Management View: A comprehensive table listing all users (Attendees and Organizers) on the platform.
📸 Application Demo & Screenshots
Here is a visual tour of the EventHive application in action.
Attendee Experience	Organizer Experience
Homepage & Event Registration Flow	Organizer Dashboard & Event Management
<!-- INSERT A GIF HERE OF A USER BROWSING, CLICKING AN EVENT, AND REGISTERING. A 15-20 SECOND GIF IS IDEAL. --> <br> [Or insert a static image: Homepage index.jsp]	<!-- INSERT A GIF HERE OF AN ORGANIZER LOGGING IN, VIEWING THEIR DASHBOARD, AND EDITING AN EVENT. --> <br> [Or insert a static image: organizer/dashboard.jsp]
Attendee Dashboard	Revenue & Attendee Tracking
[INSERT IMAGE HERE: A clear screenshot of the attendee's dashboard, showing the "Upcoming Events" and "Past Events" sections.]	[INSERT IMAGE HERE: A screenshot showing the Organizer's "My Revenue" page with its table.]
Administrator Panel	User Authentication
High-Level Platform Overview	Login & Registration Forms
[INSERT IMAGE HERE: A screenshot of the complete Admin Dashboard, showcasing the statistic cards and the user list.]	[INSERT IMAGE HERE: A combined image showing both the stylish login.jsp and register.jsp forms side-by-side.]
🛠️ Technology Stack & Architecture
This project was built using a classic Java Enterprise stack, emphasizing stability, scalability, and adherence to industry best practices.
Backend: Java 8, Java Servlets (API 3.0), jBCrypt (for password hashing)
Frontend: HTML5, CSS3, JavaScript (for client-side enhancements), JavaServer Pages (JSP) with JSTL
Database: MySQL (Relational Database)
Build & Dependency: Apache Maven
Web Server: Apache Tomcat 7+
Architecture
EventHive is architected using the Model-View-Controller (MVC) design pattern to ensure a clean separation of concerns:
Model: Comprises JavaBeans (POJOs) for data structure and Data Access Objects (DAOs) for database interaction via JDBC.
View: Handled by JavaServer Pages (JSP) with JSTL for dynamic rendering of HTML. No Java code (scriptlets) in the view layer.
Controller: Managed by Java Servlets, which handle all incoming HTTP requests, process user input, and orchestrate the data flow between the Model and the View.
🚀 Getting Started
To get a local copy up and running, follow these simple steps.
Prerequisites
You will need the following software installed on your machine:
Java Development Kit (JDK) 8 or higher
Apache Maven
MySQL Server
Apache Tomcat 7 or higher
1. Clone the Repository
code
Sh
git clone https://github.com/your_username/EventHive.git
cd EventHive
2. Database Setup
Open your MySQL Server.
Create a new database named eventhive_db.
Run the SQL script located in the repository (src/main/resources/schema.sql or similar path) to create all the necessary tables.
Important: Open the src/main/java/com/eventhive/db/DatabaseManager.java file and update the database URL, username, and password with your local MySQL credentials.
3. Build the Project
Use Maven to compile the source code and package the application into a .war file.
code
Sh
mvn clean install
This will generate a EventHive.war file in your target/ directory.
4. Deploy to Tomcat
Copy the EventHive.war file from the target/ directory.
Paste it into the webapps/ directory of your Apache Tomcat installation.
Start your Tomcat server. The application will be deployed automatically.
5. Access the Application
Open your web browser and navigate to:
http://localhost:8080/EventHive/
You can use the default administrator account to log in and explore the admin panel:
Email: admin@eventhive.com
Password: admin123
(Make sure this user is present in your database setup script)
