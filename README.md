# Claim Management SpringBoot Micro-Services

```
Java 17
Kotlin 1.9.25
SpringBoot 3.3.3
PostgreSQL
MongoDB
```

### **Microservices Overview**

This project is built using the **Microservices architecture**, where each service is designed to handle a specific domain of the business. Below are the individual services:

---

### **1. Config Server**
The **Config Server** is a centralized configuration service for all microservices. It stores and serves configuration properties for each service, ensuring consistency and flexibility. The configurations are usually stored in a remote Git repository, making it easier to manage and update settings across all microservices in the ecosystem.

- **Purpose**: Centralized configuration management.
- **Responsibilities**:
    - Externalized configurations for microservices.
    - Provides dynamic configuration updates without redeploying services.
    - Secure storage for sensitive data like credentials (can be encrypted in realtime).

---

### **2. Discovery Server**
The **Discovery Server** (implemented using **Eureka**) is responsible for maintaining a registry of all available microservices. It enables service discovery, where microservices can register themselves and discover other services dynamically, facilitating load balancing and fault tolerance.

- **Purpose**: Service discovery and dynamic load balancing.
- **Responsibilities**:
    - Keep track of available instances of microservices.
    - Allow services to find each other dynamically.
    - Ensure high availability through service registration and de-registration.

---

### **3. User Service**
The **User Service** manages user-related operations such as creating new users, retrieving user details, and updating user information. It serves as the main service for handling user data and is typically integrated with other services like Claim service.

- **Purpose**: Manage user data and provide user-related operations.
- **Responsibilities**:
    - User creation.
    - CRUD operations on user information.

---

### **4. Claim Service**
The **Claim Service** manages claim-related operations such as creating new claims, retrieving claim details, and updating claim information.

- **Purpose**: Manage claim data and provide claim-related operations.
- **Responsibilities**:
    - Claim creation will check who is representing claim with help of User Service, before requesting a claim user need to register, afterwords user able to create a claim request.
    - CRUD operations on claim information.
    - Used RestClient to establish communication between claim service to user service.

---

### **Application User Guide**

- Start Config Server.
- Start Discovery Server
- User Service.
- Claim Service.

#### ***For all user operations use below swagger URL:***
http://localhost:8882/swagger-ui/index.html#/

#### ***For all claim operations use below swagger URL:***
http://localhost:8881/swagger-ui/index.html#/
