CREATE DATABASE IF NOT EXISTS eventhive_db;
USE eventhive_db;

DROP TABLE IF EXISTS registrations;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Organizer', 'Attendee') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    venue VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    category VARCHAR(100) NOT NULL,
    coverImagePath VARCHAR(255) NOT NULL,
    organizerId INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (organizerId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE registrations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    eventId INT NOT NULL,
    attendeeId INT NOT NULL,
    registrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (eventId) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (attendeeId) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_registration (eventId, attendeeId)
);

INSERT INTO users (id, name, email, password, role) VALUES
(1, 'Admin User', 'admin@eventhive.com', '$2a$12$Ci..o4A64n46s4.gqjQ56O7C794Y5fnq9.p9s.t9/4B90EUCFj4rS', 'Admin'),
(2, 'Organizer Alice', 'organizer@eventhive.com', '$2a$12$Ci..o4A64n46s4.gqjQ56O7C794Y5fnq9.p9s.t9/4B90EUCFj4rS', 'Organizer'),
(3, 'Attendee Bob', 'attendee@eventhive.com', '$2a$12$Ci..o4A64n46s4.gqjQ56O7C794Y5fnq9.p9s.t9/4B90EUCFj4rS', 'Attendee');

INSERT INTO events (title, description, date, time, venue, price, category, coverImagePath, organizerId) VALUES
(
    'Future of Web Development Summit', 
    'Join leading experts and developers for a deep dive into the next generation of web technologies. Topics include WebAssembly, AI-driven development, and modern frontend frameworks.', 
    DATE_ADD(CURDATE(), INTERVAL 14 DAY),
    '09:00:00', 
    'Virtual Conference Center', 
    49.99, 
    'Tech', 
    'assets/images/event_covers/sample_tech.jpg', 
    2
);

INSERT INTO events (title, description, date, time, venue, price, category, coverImagePath, organizerId) VALUES
(
    'Summer Evening Jazz Festival', 
    'Enjoy a relaxing evening of live jazz music under the stars. This is a free, family-friendly event. Bring your own picnic blankets!', 
    DATE_ADD(CURDATE(), INTERVAL 30 DAY),
    '18:30:00', 
    'Central City Park', 
    0.00, 
    'Music', 
    'assets/images/event_covers/sample_music.jpg', 
    2
);

INSERT INTO registrations (eventId, attendeeId) VALUES
(1, 3);