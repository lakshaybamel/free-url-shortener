<div align="center">

<img src="static/images/biteu-logo.png" alt="BiteU Logo" width="110">

# 🔗 BiteU.fun — URL Shortener

**A fast, simple, and modern URL shortener with custom aliases, QR codes, and expiry support.**

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?logo=mysql)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Live Demo](https://img.shields.io/badge/demo-biteu.fun-purple)](https://biteu.fun)

[🌐 Live Demo](https://biteu.fun) • [💻 GitHub](https://github.com/lakshaybamel/free-url-shortener) • [🐛 Report Bug](https://github.com/lakshaybamel/free-url-shortener/issues) • [✨ Request Feature](https://github.com/lakshaybamel/free-url-shortener/issues)

</div>

---

## 📖 About

**BiteU.fun** is a full-stack URL shortening application built with **Java** and **Spring Boot**. It converts long URLs into short, easy-to-share links, with support for custom aliases, QR code generation, and link expiry.

The application is deployed on **AWS EC2** behind an **Nginx** reverse proxy with **HTTPS/SSL** via Let's Encrypt.

> 🦷 **BiteU** — because it "bites" a long URL down into something small.

<p align="center">
  <img src="static/images/biteu-preview.png" alt="BiteU Preview" width="850">
</p>

---

## ✨ Features

| | |
|---|---|
| 🔗 | Shorten long URLs instantly |
| 🎯 | Custom URL aliases |
| ⏳ | URL expiry support |
| 📱 | QR code generation |
| 🔄 | Automatic redirection |
| 🚫 | Duplicate alias prevention |
| 🎨 | Modern, responsive UI |
| 🔒 | HTTPS enabled end-to-end |
| ☁️ | AWS cloud deployment |
| ⚙️ | Automatic startup via systemd |

---

## 🛠️ Tech Stack

<table>
<tr>
<td valign="top" width="25%">

**Backend**
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate

</td>
<td valign="top" width="25%">

**Database**
- MySQL

</td>
<td valign="top" width="25%">

**Frontend**
- HTML5
- CSS3
- JavaScript

</td>
<td valign="top" width="25%">

**Infra & Tools**
- AWS EC2 / Ubuntu
- Nginx
- Let's Encrypt (SSL)
- systemd
- Maven, Git

</td>
</tr>
</table>

---

## 🏗️ Architecture

```text
                    🌐 User
                       │
                       ▼
              https://biteu.fun
                       │
                       ▼
                ┌─────────────┐
                │    Nginx    │
                │   :80/:443  │
                └──────┬──────┘
                       │
                       ▼
              ┌────────────────┐
              │  Spring Boot   │
              │     :8080      │
              └───────┬────────┘
                       │
                       ▼
              ┌────────────────┐
              │     MySQL      │
              │     :3306      │
              └────────────────┘
```

---

## 📁 Project Structure

```text
free-url-shortener/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/url_shortener/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       ├── UrlMapping.java
│   │   │       └── UrlShortenerApplication.java
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   │
├── pom.xml
├── README.md
└── .gitignore
```

---

## ⚙️ Run Locally

### 1. Clone the repository

```bash
git clone https://github.com/lakshaybamel/free-url-shortener.git
cd free-url-shortener
```

### 2. Requirements

Make sure you have installed:

- Java 17+
- Maven
- MySQL
- Git

### 3. Create the database

```sql
CREATE DATABASE url_shortener;
```

Create a MySQL user and grant it access to the database.

### 4. Configure the application

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

app.base-url=http://localhost:8080
```

> ⚠️ **Never commit real database credentials or secrets to GitHub.**

### 5. Build the project

```bash
mvn clean package
```

### 6. Run

```bash
java -jar target/url-shortener-0.0.1-SNAPSHOT.jar
```

Then open **[http://localhost:8080](http://localhost:8080)**

---

## ☁️ AWS Deployment

BiteU is deployed on an AWS EC2 Ubuntu server:

```text
AWS EC2
  ├── Ubuntu
  ├── Java 17
  ├── MySQL
  ├── Spring Boot
  ├── Nginx
  └── systemd
```

### Deployment flow

1. Launch EC2 instance
2. Install Java 17 and Maven
3. Clone the GitHub repository
4. Install and configure MySQL
5. Build the Spring Boot application
6. Create a systemd service for BiteU
7. Configure Nginx as a reverse proxy
8. Connect the custom domain
9. Configure HTTPS using Let's Encrypt
10. Remove direct public access to Spring Boot port `8080`

### Production request flow

```text
https://biteu.fun → Nginx → localhost:8080 → Spring Boot → MySQL
```

---

## 🔌 API Endpoints

### Shorten a URL

`POST /api/shorten`

**Request**

```json
{
  "url": "https://example.com",
  "alias": "example"
}
```

**Response**

```json
{
  "shortUrl": "https://biteu.fun/u/example"
}
```

### Redirect

`GET /u/{shortCode}`

Redirects the user to the original URL.

### Analytics

`GET /api/analytics/{shortCode}`

Returns URL-related information such as click count and creation time.

---

## 🔒 Security & Production

- ✅ HTTPS enabled using Let's Encrypt
- ✅ MySQL is not publicly exposed
- ✅ Spring Boot runs behind Nginx
- ✅ Port `8080` is not publicly exposed in production
- ✅ Application runs as a systemd service
- ✅ Database credentials are kept outside the public repository

---

## 🌐 Live Project

🚀 **[BiteU.fun](https://biteu.fun)**

Create a short link, add a custom alias, generate a QR code, and share it instantly.

---

## 🔮 Roadmap

- [ ] 📊 Advanced URL analytics dashboard
- [ ] 👤 User accounts and URL management
- [ ] 📈 Detailed click statistics
- [ ] 🌍 Geographic analytics
- [ ] 🔐 Authentication and authorization
- [ ] 📁 User-specific URL collections
- [ ] 🚦 Rate limiting
- [ ] 🐳 Docker-based deployment
- [ ] ⚡ CI/CD pipeline

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

1. Fork the project
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Lakshay Bamel**

[![GitHub](https://img.shields.io/badge/GitHub-lakshaybamel-181717?logo=github)](https://github.com/lakshaybamel)

<div align="center">

If you find this project useful, consider giving it a ⭐ on GitHub!

</div>
