# Loci

Real-time messaging app built with Spring Boot, Angular, and Hexagonal Architecture.

![demo](./assets/realtime-message-delivery.gif)

---

## Run it locally


```bash
git clone https://github.com/trung-kieen/loci-chat.git
cd loci-backend
cp .env.example .env

# mvn flyway:baseline
# mvn flyway:migrate
mvn spring-boot:run
```

```
cd loci-frontend
npm install 
npm run start 
```

| Service        | URL                    |
|----------------|------------------------|
| App            | http://localhost:4200  |
| Keycloak admin | http://localhost:9090|
| Minio console  | http://localhost:9001  |

---

## Architecture


Real-time delivery goes through WebSocket/STOMP. 

Media storage runs on Minio for S3-compatible. 

![detailed architecture](./assets/architecture.png)


The backend follows Hexagonal Architecture (Ports & Adapters).
![hexagonal architecture](./assets/hexagonal.png)


DDD domain model at the core. 
![ddd modeling](./assets/ddd-modeling.png)

---

## Features

**Real-time messaging**  
![messaging](./assets/realtime-message-delivery.gif)  
1:1 chats with status tracking.

**Group messaging**  
![group messaging](./assets/group-messaging.gif)  
Create groups, manage members, chat together.

**Media sharing**  
![media sharing](./assets/media-sharing.gif)  
Images, video, files stored in Minio. 

**User presence tracking**  
![user presence](./assets/user-presence-tracking.gif)  
See who's online in real time.

**Contact management**  

![connect friend](./assets/connect-friend.png)  
Send and accept friend requests.

![request friend](./assets/request-friend.png)  
![contact manager](./assets/contact-manager.png)  

Manage contacts and block list.

![block list](./assets/block-list.png)

**Group management**  
![group creation](./assets/group-creation.png)  
![group manager](./assets/group-manager.png)

**Profile & auth**  
![profile](./assets/profile.png)  
![keycloak login](./assets/keycloak.png)  
Full authentication flow handled by Keycloak

---

## Tech

**Backend**:  Spring Boot 3, Spring Security + Keycloak,  
WebSocket/STOMP, Spring Data JPA, PostgreSQL, Minio

**Frontend**: Angular 17, RxJS, RxStomp

**DevOps**: Docker, Jenkins

---

## License

Licensed under the Apache License, Version 2.0

---

<div align="center">
  <strong>Built with ❤️& ☕ by Trung Kien</strong>
</div>
