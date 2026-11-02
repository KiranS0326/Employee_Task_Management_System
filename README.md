# ETMS — Employee & Task Management System

A full-stack HR operations platform built with **Spring Boot, Spring Security (JWT), Oracle Database, and React** — going beyond basic CRUD to model real workplace workflows: attendance tracking, leave approvals, task collaboration, and role-based access control.

**Live Demo:** _add your deployed link here_
**Video Walkthrough:** _add a Loom/YouTube link here (optional but strongly recommended)_

---

## Why This Project Is Different

Most fresher portfolio projects stop at basic CRUD. ETMS was built to reflect how real internal HR/PM tools actually work:

- **Role-based workflows** — Admins/Managers approve leave requests; Employees can only act on their own data
- **Operational data, not just records** — attendance check-in/out with timestamps, leave approval states, task status pipelines
- **Analytics that mean something** — task distribution and department workload charts computed from live data, not hardcoded
- **Production patterns** — JWT stateless auth, DTO-based API boundaries, layered architecture (Controller → Service → Repository), sequence-based ID generation for Oracle compatibility

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.3, Spring Security, Spring Data JPA (Hibernate) |
| Database | Oracle Database (sequence + JPA-managed ID generation) |
| Auth | JWT (jjwt), BCrypt password hashing, role-based `@PreAuthorize` |
| Frontend | React 18, React Router, Axios, Recharts |
| API Docs | Swagger / OpenAPI (springdoc) |
| Build Tools | Maven, npm |

---

## Core Features

### Identity & Access
- JWT-based authentication with stateless sessions
- Three roles: `ROLE_ADMIN`, `ROLE_MANAGER`, `ROLE_EMPLOYEE`
- Method-level authorization via `@PreAuthorize`

### Employee & Department Management
- Full CRUD with relational integrity (`Employee → Department`, `Employee → User`)
- Search, filter by department, and pagination on large lists

### Task Management
- Task assignment to employees with status pipeline (`PENDING → IN_PROGRESS → DONE`)
- Task comments / activity timeline for collaboration

### Attendance Tracking
- Employee check-in / check-out with automatic timestamping
- Daily attendance status per employee

### Leave Management
- Employees apply for leave (sick, casual, vacation, emergency)
- Admin/Manager approval workflow with status tracking

### Analytics Dashboard
- Real-time task status distribution (pie chart)
- Department workload comparison (bar chart)
- Summary cards for employee count, pending tasks, pending leaves

### Notifications
- In-app notification system triggered on task assignment

---

## Architecture

```
React (localhost:3000)
   │  Axios + JWT Bearer token
   ▼
Spring Boot REST API (localhost:8080)
   │  Controller → Service → Repository
   ▼
Oracle Database (JPA / Hibernate)
```

### Database Schema (simplified ER overview)
```
roles ─┐
       ├─< users >─── employees ─┬─< tasks ─< comments
departments ─────────< employees ┤
                                  ├─< attendance
                                  └─< leave_requests
```

---

## Project Structure

```
etms-fullstack/
 ├── backend/
 │    └── src/main/java/com/etms/backend/
 │         ├── entity/        # JPA entities
 │         ├── repository/    # Spring Data JPA repositories
 │         ├── dto/           # Request/response DTOs
 │         ├── service/        # Business logic
 │         ├── controller/    # REST endpoints
 │         ├── security/      # JWT filter, UserDetailsService, JwtUtil
 │         └── config/        # Security configuration
 │
 └── frontend/
      └── src/
           ├── api/           # Axios instance with JWT interceptor
           ├── context/       # Auth context (login/logout state)
           ├── components/    # Navbar, shared UI
           └── pages/         # Login, Dashboard, Employees, Departments, Tasks, Attendance, Leaves
```

---

## Getting Started

### Prerequisites
- Java 17+, Maven, Oracle Database (or adjust `application.properties` for another DB)
- Node.js 18+, npm

### Backend Setup
```bash
cd backend
# update src/main/resources/application.properties with your DB credentials
mvn clean install
mvn spring-boot:run
```
Backend runs at `http://localhost:8080`. Swagger docs at `http://localhost:8080/swagger-ui.html`.

### Frontend Setup
```bash
cd frontend
npm install
npm start
```
Frontend runs at `http://localhost:3000`.

### First-time Use
1. Register a user via `/register` (or `POST /api/auth/register`)
2. Log in
3. As an Admin, create a Department, add an Employee, assign a Task

---

## API Overview

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/auth/register` | Register a new user | Public |
| POST | `/api/auth/login` | Login, returns JWT | Public |
| GET/POST/PUT/DELETE | `/api/employees` | Employee CRUD | Authenticated |
| GET/POST/PUT/DELETE | `/api/departments` | Department CRUD | Admin |
| GET/POST/PUT/DELETE | `/api/tasks` | Task CRUD + status updates | Authenticated |
| POST | `/api/attendance/checkin/{empId}` | Check in | Authenticated |
| PUT | `/api/attendance/checkout/{id}` | Check out | Authenticated |
| POST | `/api/leaves` | Apply for leave | Authenticated |
| PUT | `/api/leaves/{id}/status` | Approve/reject leave | Admin/Manager |
| GET | `/api/comments/task/{taskId}` | Task comments | Authenticated |
| GET | `/api/notifications/user/{userId}` | User notifications | Authenticated |

Full interactive documentation available via Swagger UI once the backend is running.

---

## Roadmap

- [ ] Email notifications on task assignment / leave decisions
- [ ] File attachments on tasks (beyond profile photos)
- [ ] Unit and integration tests (JUnit 5 + Mockito)
- [ ] Docker Compose setup for one-command local environment
- [ ] CI/CD pipeline (GitHub Actions)

---

## Screenshots

_Add screenshots here: Login page, Dashboard with charts, Employees table, Leave approval flow_

---

## Author

**Sujeet Kharat**
Built as a portfolio project to demonstrate full-stack Java development for a Java Developer role.
