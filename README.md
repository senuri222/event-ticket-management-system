# Movie Ticket Management System 🎟️

A real-time movie ticket management system built with React.js and Spring Boot.  
This project simulates a dynamic environment where multiple vendors release movie tickets and multiple customers purchase them concurrently. It uses the Producer-Consumer pattern to handle concurrency and ensure data integrity.

## 🚀 Technologies Used
- **Frontend**: React.js
- **Backend**: Spring Boot (Java)
- **Concurrency**: Java multithreading (Runnable, synchronized blocks)
- **Communication**: REST API (or WebSocket if implemented)
- **Build Tools**: Maven (Spring Boot), npm (React.js)

## ⚙️ Features
- Multi-threaded simulation of vendors (producers) and customers (consumers)
- Real-time ticket availability updates
- Thread-safe ticket pool management
- User input for system configuration (ticket count, release/retrieval rates, etc.)
- Basic system logging and error handling
- Clean and interactive user interface (manually coded)

## 🔧 Setup Instructions

### Prerequisites
- Java 17 or higher
- Node.js and npm
- Maven (or use `./mvnw` wrapper in project)

### Clone the Repository
git clone https://github.com/senuri222/movie-ticket-management-system.git
Run Backend (Spring Boot)
```bash
cd movie-ticket-management-system
cd backend
./mvnw spring-boot:run
```
Run Frontend (React.js)
```bash
cd frontend
npm install
npm start
```
The React app will typically run at http://localhost:3000, and the Spring Boot API at http://localhost:8080.

## 📊 Project Structure
<pre> ```
movie-ticket-management-system/
├── backend/         # Spring Boot backend
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/        # React frontend
│   ├── src/
│   ├── package.json
│   └── ...
└── README.md
 ``` </pre>
## 📽️ Demonstration
(You can add a link to a YouTube video or upload screenshots here)

## 📄 License
This project is licensed under the MIT License.
