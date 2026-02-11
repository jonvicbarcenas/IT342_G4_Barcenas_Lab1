# Authentication System Mini-App

A full-stack authentication system featuring a Spring Boot backend, a React-based web frontend, and an Android mobile application. This project demonstrates a unified authentication flow across different platforms.

## Tech Stack

### Backend
- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Security** (Authentication & Authorization)
- **Spring Data JPA** (Database ORM)
- **MySQL** (Database)
- **Maven** (Build Tool)

### Web Frontend
- **React 19**
- **Vite** (Build Tool & Dev Server)
- **React Router Dom** (Navigation)
- **Fetch API** (Network Requests)

### Mobile App
- **Kotlin**
- **Android SDK** (Min SDK 24, Target SDK 34)
- **Retrofit & OkHttp** (Networking)
- **Gson** (JSON Parsing)

---

## Database Setup

1. **Install MySQL**: Ensure you have MySQL Server installed and running on your machine.
2. **Create Database**: Run the following SQL command in your MySQL terminal or workbench:
   ```sql
   CREATE DATABASE authdb;
   ```
3. **Configuration**: The backend is configured to use `root` as the username with no password by default. If your setup differs, update `backend/src/main/resources/application.properties`.
4. **Table Generation**: The system uses `hibernate.ddl-auto=update`, meaning tables will be automatically created when the backend starts.

---

## How to Run

### 1. Backend (Spring Boot)
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Run the application using the Maven wrapper:
   - **Windows**:
     ```bash
     .\mvnw.cmd spring-boot:run
     ```
   - **macOS/Linux**:
     ```bash
     ./mvnw spring-boot:run
     ```
3. The server will start on `http://localhost:8080`.

### 2. Web Frontend (React)
1. Navigate to the web directory:
   ```bash
   cd web
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
4. Open the URL provided in the terminal (usually `http://localhost:5173`).

### 3. Mobile App (Android)
1. Open **Android Studio**.
2. Select **Open** and navigate to the `mobile/` directory.
3. Wait for **Gradle Sync** to finish.
4. **Configuration**: 
   - If running on a **physical device**, update the `base_url` in `mobile/app/src/main/res/values/strings.xml` to your computer's local IP address (e.g., `http://192.168.1.XX:8080/`).
   - If running on an **emulator**, you can use `http://10.0.2.2:8080/`.
5. Click **Run** to install and launch the app on your device/emulator.

---

## Configuration & Environment

### Backend
- Configuration is located in `backend/src/main/resources/application.properties`.
- Key settings: Server port, Database URL, Username, and Password.

### Web
- Supports environment variables via a `.env` file.
- `VITE_API_BASE_URL`: Override the default `http://localhost:8080` for API calls.

### Mobile
- API base URL is defined in `mobile/app/src/main/res/values/strings.xml` under the `base_url` key.

---

## API Endpoints

All endpoints are prefixed with `/api/auth`.

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/register` | Registers a new user with first name, last name, email, and password. |
| `POST` | `/login` | Authenticates a user and returns their profile information. |
| `POST` | `/logout` | Simple endpoint to signal session termination. |
