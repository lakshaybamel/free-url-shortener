# 🦷 BiteU.fun

<p align="center">
  <img src="src/main/resources/static/images/biteu-logo.png" alt="BiteU Logo" width="140">
</p>

<h3 align="center">Take a bite out of long URLs.</h3>

<p align="center">
  A simple, fast and fun URL shortener built with Java and Spring Boot.
</p>

<p align="center">
  <a href="https://biteu.fun">🌐 Live Demo</a> •
  <a href="https://github.com/lakshaybamel/free-url-shortener">💻 GitHub</a>
</p>

---

## 📖 About

**BiteU.fun** is a full-stack URL shortener that converts long URLs into short, shareable links.

It supports automatically generated **Base62 short codes**, **custom aliases**, and **QR code generation**.

### Example

```text
Long URL
https://github.com/lakshaybamel/free-url-shortener

        ↓ BiteU

Short URL
https://biteu.fun/u/Ab3xK9
```

Custom aliases are also supported:

```text
https://biteu.fun/u/~github
```

The `~` namespace is automatically added for custom aliases.

---

## ✨ Features

- 🔗 Shorten long URLs using Base62 encoding
- ⚡ Generate a new short code for every new submission
- ✏️ Create custom aliases
- 🚫 Prevent duplicate aliases
- 📱 Generate QR codes for shortened URLs
- 📥 Download QR codes
- 📋 Copy shortened links
- 🔄 Redirect short URLs to original URLs
- 📊 Backend click tracking
- 📱 Responsive and modern UI
- 🆓 No signup required

---

## 🖼️ Preview

<p align="center">
  <img src="src/main/resources/static/images/biteu-poster.png" alt="BiteU Preview" width="750">
</p>

---

## 🛠️ Tech Stack

**Backend**
- Java 22
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Maven

**Frontend**
- HTML5
- CSS3
- Vanilla JavaScript
- QRCode.js

**Tools**
- Git & GitHub
- IntelliJ IDEA
- MySQL

---

## 🏗️ Project Structure

```text
src/
├── main/
│   ├── java/com/example/url_shortener/
│   │   ├── controller/
│   │   │   └── UrlController.java
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── repository/
│   │   │   └── UrlRepository.java
│   │   ├── service/
│   │   │   └── UrlService.java
│   │   ├── util/
│   │   │   └── Base62Encoder.java
│   │   ├── UrlMapping.java
│   │   └── UrlShortenerApplication.java
│   │
│   └── resources/
│       ├── static/
│       │   ├── images/
│       │   │   ├── biteu-logo.png
│       │   │   └── biteu-poster.png
│       │   ├── index.html
│       │   ├── style.css
│       │   └── app.js
│       └── application.properties
│
├── pom.xml
└── README.md
```

---

## 🔄 How It Works

**Without alias**

```text
User submits URL
       ↓
Generate database ID
       ↓
Convert ID to Base62
       ↓
Create short link
```

**With alias**

```text
User submits URL + alias
       ↓
Check alias
       ↓
Already exists → 409 Conflict
       ↓
Available → store custom alias
```

Custom aliases are represented using the `~` namespace:

```text
github
  ↓
~github
  ↓
/u/~github
```

---

## 🔌 API

### Shorten URL

`POST /api/shorten`

**Request**

```json
{
  "url": "https://example.com",
  "alias": ""
}
```

**Response**

```json
{
  "shortUrl": "http://localhost:8080/u/Ab3xK9"
}
```

### Redirect

`GET /u/{shortCode}`

### Analytics

`GET /api/analytics/{shortCode}`

> The analytics API exists in the backend, while the current frontend does not expose an analytics dashboard.

---

## 🚀 Run Locally

**1. Clone**

```bash
git clone https://github.com/lakshaybamel/free-url-shortener.git
cd free-url-shortener
```

**2. Create MySQL database**

```sql
CREATE DATABASE url_shortener;
```

**3. Configure database**

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update

app.base-url=http://localhost:8080
```

**4. Run**

```bash
mvn spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

Open:

```text
http://localhost:8080
```

---

## 🧪 Example

**URL**

```text
https://github.com/lakshaybamel
```

**Alias**

```text
github
```

**Result**

```text
http://localhost:8080/u/~github
```

You can then copy the link or scan/download its QR code.

---

## 🗺️ Future Plans

- ☁️ AWS deployment
- 🌐 Custom domain: biteu.fun
- 🔒 HTTPS / SSL
- 📊 Advanced analytics dashboard
- ⏳ Link expiration
- 👤 User accounts
- 🗂️ Link management
- 🛡️ Rate limiting
- 🐳 Docker & CI/CD

---

## 👨‍💻 Author

**Lakshay Bamel**
MCA — BIT Mesra

- GitHub: [@lakshaybamel](https://github.com/lakshaybamel)
- LinkedIn: [Lakshay Bamel](https://www.linkedin.com/in/lakshaybamel)

---

## 📄 License

This project is licensed under the MIT License.
