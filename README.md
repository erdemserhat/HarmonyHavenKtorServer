# **Harmony Haven Server **

The Harmony Haven Server is a robust backend infrastructure that powers the Harmony Haven application. Built using Ktor for API services, this server is hosted on AWS, providing scalability and high performance. It supports user management, article access, and AI-powered notifications, along with secure data transmission and authentication via HTTPS and OAuth2/JWT. The Harmony Haven Server strengthens the user experience by offering a solid and reliable infrastructure that supports all features of the application.

## Access the Server API Services

<div align="center">

![1232](https://github.com/user-attachments/assets/cc10eb17-3ccf-4365-a066-c21a341975db)

To interact with the API, please visit: [http://51.20.136.184:5000/](http://51.20.136.184:5000/)

Detailed information about the endpoints is provided below.

</div>

## Overview of System Operations

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/b02ad5f5-0154-48bf-a813-33b750f34397)

## Technologies and Paradigms Used:

- MVC (Model-View-Controller) architecture: Architecture
- Clean Architecture: Architecture
- SOLID principles: Best Practices
- JWT Authentication and Authorization: Security
- Ktorm: ORM
- REST API: Networking
- OpenAI API: AI Integration
- FreeMarker: Templating
- Javax Mail Service: Email
- Google Guava: Utilities
- MySQL: Database
- Firebase (Server-Side Notifications): Notifications
- Django CAAS (Cryptography as a Service, our own application): Cryptography
- Swagger UI (API Documentation): API Documentation
- AWS EC2: Deployment

## 📡 **API** Endpoints

📌 **Root Endpoint**
- **GET `/`**: The default root endpoint returns "Harmony Haven Server" upon success.

📌 **Add Quote**
- **POST `/api/v1/add-quote`**: Adds a new quote. Requires a Quote object in the request body.

📌 **Add Quote Category**
- **POST `/api/v1/add-quote-category`**: Adds a new quote category. Requires a CategoryRequest object in the request body.

📌 **Delete Admin User**
- **DELETE `/api/v1/admin/delete-user`**: Deletes a user. Requires a Map_String object in the request body.

📌 **Get All Articles**
- **GET `/api/v1/articles`**: Retrieves all articles. Returns an array of Article objects.

📌 **Get Article by ID**
- **GET `/api/v1/articles/{id}`**: Retrieves an article by a specific ID. Requires the ID as a path parameter.

📌 **Get Articles by Category ID**
- **GET `/api/v1/articles/category/{id}`**: Retrieves articles by category ID. Requires the ID as a path parameter.

📌 **Get Recent Articles**
- **GET `/api/v1/articles/recent/{size}`**: Retrieves the specified number of recent articles.

📌 **Get All Categories**
- **GET `/api/v1/categories`**: Retrieves all article categories.

📌 **Check Authentication Status**
- **GET `/api/v1/check-auth-status`**: Checks the user's authentication status.

📌 **Delete Quote Category**
- **DELETE `/api/v1/delete-quote-category/{categoryId}`**: Deletes a quote category by a specific category ID. Requires the categoryId as a path parameter.

📌 **Delete Quote**
- **DELETE `/api/v1/delete-quote/{quoteId}`**: Deletes a specific quote by quoteId. Requires the quoteId as a path parameter.

📌 **Get Quote by Category**
- **GET `/api/v1/get-quote-by-category/{categoryId}`**: Retrieves a quote by category ID. Requires the categoryId as a path parameter.

📌 **Get Quote Categories**
- **GET `/api/v1/get-quote-category`**: Retrieves all quote categories.

📌 **Send Specific Notification**
- **POST `/api/v1/notification/send-specific`**: Sends a specific notification.

📌 **OpenAI Request**
- **POST `/api/v1/openai-request`**: Sends a request to the OpenAI API. Requires an OpenAIPromptDto object in the request body.

📌 **Test Notification**
- **POST `/api/v1/test-notification`**: Tests the notification sending functionality.

📌 **Update Quote**
- **PATCH `/api/v1/update-quote`**: Updates an existing quote. Requires a Quote object in the request body.

📌 **Update Quote Category**
- **PATCH `/api/v1/update-quote-category`**: Updates an existing quote category. Requires a QuoteCategory object in the request body.

📌 **User Authentication**
- **POST `/api/v1/user/authenticate`**: Authenticates a user. Requires a UserAuthenticationRequest object in the request body.

📌 **Delete User**
- **DELETE `/api/v1/user/delete`**: Deletes a user.

📌 **User FCM Enrollment**
- **POST `/api/v1/user/fcm-enrolment`**: Enrolls a user for FCM (Firebase Cloud Messaging). Requires an FcmSetupDto object in the request body.

📌 **Forgot Password Authentication**
- **POST `/api/v1/user/forgot-password/auth`**: Authenticates a user for password reset. Requires a ForgotPasswordAuthModel object in the request body.

📌 **Send Forgot Password Email**
- **POST `/api/v1/user/forgot-password/mailer`**: Sends a password reset email. Requires a ForgotPasswordMailerModel object in the request body.

📌 **Reset Forgotten Password**
- **PATCH `/api/v1/user/forgot-password/reset-password`**: Resets a user's password. Requires a ForgotPasswordResetModel object in the request body.

📌 **User Registration**
- **POST `/api/v1/user/register`**: Registers a new user. Requires a UserInformationSchema object in the request body.

📌 **Update User**
- **PATCH `/api/v1/user/update`**: Updates user information. Requires a UserInformationSchema object in the request body.
