# 🚀 HireHub – Job Portal Backend (Spring Boot + JWT + MySQL)

### HireHub is a full-featured Job Portal Backend built using Spring Boot, providing secure role-based access for Job Seekers, Employers, and Admins.It includes authentication, job postings, applications, resume uploads, and profile management. ###
---

## 📌 Features Overview

### **🔐 Authentication & Security**
- ✔️ JWT-based Authentication (Login / Register)
- ✔️ Role-Based Access Control 
    - **ROLE_USER** → Job Seeker
    - **ROLE_EMPLOYER** → Employer / Recruiter
    - **ROLE_ADMIN** → Admin
- ✔️ Secured API endpoints using Spring Security
- ✔️ Custom JWT Filter + Token Validation
---
### **👤 User Module (Job Seeker)**

- Register as Job Seeker
- Login & receive JWT token
- Update profile (education, skills, experience, projects, etc.)
- Upload resume (PDF)
- View uploaded resume
- Apply for jobs
- Track application status
---
### **🧑‍💼 Employer Module**

- Create, update & delete job postings
- View all posted jobs
- Review applicants
- Download applicant resumes
- Manage employer/company profile
- Employer dashboard stats:
    - Total Jobs Posted
    - Active Jobs
    - Applications Received
---
### **🛠️ Admin Module**

- Manage all users
- Approve / Reject job postings
- Manage categories
- Future scope → System analytics dashboard
---

### **📄 Public APIs (No Login Required)**

|   API     |      Descripton    |
| --------- | ---------------- |
| GET /api/public/jobs     | Fetch all approved jobs |
| GET /api/public/jobs/search?keyword=   | Search jobs |
---
### **🏗️ Tech Stack**

## 🔧 Backend
- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- Maven
## 🗄️ Database
- MySQL
## ⚙️ Other Tools & Libraries
- Lombok
- Validation API
- CORS configuration for React frontend
---

### **📂 Project Folder Structure**
```text
src/
│
├── main/                 
│   ├── java/com/hirehub/
│   │   ├── congfig/        # Security & CORS configs
│   │   ├── controller/     # API Controller
|   |   ├── dto/            # Request/Response DTOs
│   │   ├── entity/         # JPA Entities
│   │   ├── repository/     # Spring Data JPA
|   |   ├── security/       # JWT Filter, Util, UserDetails
|   |   ├── service/        # Business logic layer
│   └── resources/
│       ├── application.properties
│       ├── static/
│       └── templates/
```
---
## ⚙️ Setup Instructions
### 1️⃣ Clone Repository
```bash
git clone https://github.com/santos021/HireHub-job-portal-Backend.git
cd HireHub-job-portal-Backend
```

### 2️⃣ Configure MySQL
### Create a database:
```bash
CREATE DATABASE hirehub;
```
### Update your ```application.properties:```
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/your-database-name
spring.datasource.username=your-username
spring.datasource.password=your-password

jwt.secret=Your32CharacterSecretKey
jwt.expirationMs=86400000
```
### 3️⃣ Install Dependencies
```bash
mvn clean install
```
### 4️⃣ Run the Application
```bash
mvn spring-boot:run
```
---
## 🔑 Authentication Example
### Login Request (Postman)
```bash
POST /api/auth/login
{
  "email": "test@gmail.com",
  "password": "123456"
}
```
Login Response
```bash
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "test@gmail.com",
  "role": "ROLE_USER"
}
```
---
## 📝 Future Enhancements

- Email notification system
- Admin analytics dashboard
- Chat system (Job Seeker ↔ Employer)
- AI-based resume screening
- Job recommendations system
---
## 🤝 Contributing

Contributions are welcome!
Feel free to fork the repo and submit a pull request.

## 🧑‍💻 Developer

**Santos Kumar Biswal**
- 📌 GitHub: https://github.com/santos021
- 📌 LinkedIn: https://www.linkedin.com/in/santos-biswal07/
- 📌 Portfolio: https://santoskumarbiswal.com/
---
### ⭐ Support the Project

If this project helped you, please ⭐ star the repository to support development!

### ***THANK YOU***😍🤎 ###