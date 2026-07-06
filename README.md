# 🤖 AI Resume Screener

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-green?style=for-the-badge)
![RestClient](https://img.shields.io/badge/Spring-RestClient-success?style=for-the-badge)
![Gemini](https://img.shields.io/badge/Google-Gemini-blue?style=for-the-badge)
![OpenAI](https://img.shields.io/badge/OpenAI-GPT-black?style=for-the-badge)
![Anthropic](https://img.shields.io/badge/Anthropic-Claude-purple?style=for-the-badge)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge)
![Actuator](https://img.shields.io/badge/Spring-Actuator-brightgreen?style=for-the-badge)

A production-ready **AI-powered Resume Screening REST API** built with **Spring Boot 3**, capable of evaluating resumes using **Google Gemini**, **OpenAI GPT**, or **Anthropic Claude**.

The application extracts text from uploaded PDF resumes, sends it to the selected AI provider, and returns structured screening feedback.

---

# ✨ Features

- Upload resume as PDF
- Extract text using Apache PDFBox
- Screen resumes using:
  - Google Gemini
  - OpenAI GPT
  - Anthropic Claude
- Switch AI providers dynamically using Strategy Pattern
- Modern Spring Boot RestClient
- Swagger/OpenAPI documentation
- Spring Boot Actuator endpoints
- Clean layered architecture
- Global exception handling
- Configuration-driven provider selection

---

# 🏗 Architecture

```
Client
   │
   ▼
ResumeController
   │
   ▼
ResumeScreeningService
   │
   ▼
AIProviderFactory
   │
   ├───────────────┬────────────────┬─────────────────┐
   ▼               ▼                ▼
GeminiService   OpenAIService   AnthropicService
   │               │                │
   └───────────────┴────────────────┘
            REST APIs
```

---

# 🎯 Strategy Pattern

This project uses the **Strategy Pattern** to make AI providers interchangeable without changing business logic.

Instead of tightly coupling the application to a single provider, every provider implements the same interface.

```
                   AIProvider
                       ▲
      ┌────────────────┼────────────────┐
      │                │                │
      │                │                │
GeminiService   OpenAIService   AnthropicService
```

The service simply asks the factory for the requested provider.

```java
AIProvider provider = providerFactory.getProvider(providerName);
return provider.screenResume(resumeText);
```

Adding another provider (Azure OpenAI, Groq, Mistral, etc.) only requires creating another implementation.

---

# 📂 Project Structure

```
src
└── main
    ├── controller
    ├── service
    │     ├── GeminiService
    │     ├── OpenAIService
    │     ├── AnthropicService
    │     └── ResumeScreeningService
    ├── factory
    ├── config
    ├── exception
    ├── model
    └── util
```

---

# ⚙ Technologies Used

- Java 21
- Spring Boot 3
- Spring Web
- Spring RestClient
- Apache PDFBox
- OpenAPI / Swagger
- Spring Boot Actuator
- Google Gemini API
- OpenAI API
- Anthropic Claude API
- Maven

---

# 📄 PDF Parsing

Uploaded resumes are parsed using **Apache PDFBox**.

Flow:

```
PDF Upload

      │

      ▼

Apache PDFBox

      │

      ▼

Extract Text

      │

      ▼

AI Provider
```

Supported format:

- PDF

---

# 🔄 AI Providers

The provider is selected using a query parameter.

## Google Gemini

```
POST /screen?provider=gemini
```

Recommended because it has a generous free tier.

---

## OpenAI

```
POST /screen?provider=openai
```

Uses GPT models through the OpenAI API.

Requires API credits.

---

## Anthropic

```
POST /screen?provider=anthropic
```

Uses Claude models.

Requires API credits.

---

# 🚀 Running the Application

Clone the repository

```bash
git clone https://github.com/yourusername/ai-resume-screener.git

cd ai-resume-screener
```

Run

```bash
mvn spring-boot:run
```

Server starts on

```
http://localhost:8080
```

---

# 🔑 Configuration

Configure your API keys inside:

```properties
application.properties
```

Example

```properties
gemini.api.key=YOUR_GEMINI_KEY

openai.api.key=YOUR_OPENAI_KEY

anthropic.api.key=YOUR_ANTHROPIC_KEY
```

---

# 🔐 Getting API Keys

## Google Gemini

1. Visit

https://aistudio.google.com/

2. Sign in

3. Create API Key

4. Copy into

```properties
gemini.api.key=YOUR_KEY
```

---

## OpenAI

1. Visit

https://platform.openai.com/

2. Create API Key

3. Add billing/credits if required

4. Copy into

```properties
openai.api.key=YOUR_KEY
```

---

## Anthropic

1. Visit

https://console.anthropic.com/

2. Create an API key

3. Copy into

```properties
anthropic.api.key=YOUR_KEY
```

---

# 📬 Postman Example

### Request

```
POST http://localhost:8080/screen?provider=gemini
```

Body

```
form-data

Key: resume
Type: File
Value: resume.pdf

Key: jobDescription
Type: Text
Value: "We are looking for a Java developer with Spring Boot, REST APIs, MySQL, AWS, and Kubernetes experience..."
```

Response

```json
{
  "summary": "...",
  "strengths": "...",
  "weaknesses": "...",
  "recommendation": "..."
}
```

---

# 📖 Swagger Documentation

```
http://localhost:8080/swagger-ui/index.html
```

---

# ❤️ Spring Boot Actuator

Health

```
http://localhost:8080/actuator/health
```

Info

```
http://localhost:8080/actuator/info
```

Metrics

```
http://localhost:8080/actuator/metrics
```

---

# 🧪 Example Requests

### Gemini

```
POST http://localhost:8080/screen?provider=gemini
```

### OpenAI

```
POST http://localhost:8080/screen?provider=openai
```

### Anthropic

```
POST http://localhost:8080/screen?provider=anthropic
```

---

# 📌 Future Improvements

- JWT Authentication
- ATS keyword matching
- Resume vs Job Description comparison
- Docker support
- Kubernetes deployment
- Caching AI responses
- Async processing
- Database persistence
- Rate limiting

---

# 📸 Relevant Screenshots

- Postman
  
  <img width="821" height="743" alt="Screenshot 2026-07-06 at 6 59 50 PM" src="https://github.com/user-attachments/assets/a5e6ab10-e056-4377-a678-42b59b9151a4" />

---

# 👨‍💻 Author

**Gayatri Shetkar**

Backend Developer | Java | Spring Boot | REST APIs | Microservices

---
