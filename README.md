# **Harmony Haven Server (Work In Progress)**

**Harmony Haven** is an ongoing project under development, utilizing various libraries throughout its development process. Primarily a motivational application, *Harmony Haven* offers users a range of articles from different categories and delivers personalized notifications using artificial intelligence. It also provides users with personalized quotes, adding a touch of inspiration to their daily lives.

## System Operation Overview

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/b02ad5f5-0154-48bf-a813-33b750f34397)



## Server Architecture Overview


```plaintext
src
└── main
    └── kotlin
        └── com.erdemserhat
            ├── dto
            │   ├── requests
            │   └── responses
            ├── models
            ├── plugins
            ├── romote
            │   ├── database
            │   │   ├── article
            │   │   ├── article_category
            │   │   ├── notification
            │   │   ├── quote
            │   │   ├── quote_category
            │   │   └── user
            │   ├── ftp
            │   ├── mail
            │   └── repository
            │       ├── article
            │       ├── article_category
            │       ├── notification
            │       ├── quote
            │       ├── quote_category
            │       └── user
            ├── routes
            │   ├── admin
            │   ├── article
            │   ├── notification
            │   └── quote
            │       ├── category
            │       └── quote
            ├── service
            │   ├── configurations
            │   ├── di
            │   ├── openai
            │   ├── pwrservice
            │   └── security
            │       ├── hashing
            │       └── token
            ├── validation
            └── util
```

## Technologies and Paradigms Used:

- MVC (Model-View-Controller) architecture: Architecture
- Clean Architecture: Architecture
- SOLID principles: Best Practices
- JWT Authentication & Authorization: Security
- Ktorm: ORM
- REST API: Networking
- OpenAI API: AI Integration
- FreeMarker: Templating
- Javax Mail Service: Email
- Google Guava: Utilities
- MySQL: Database
- Firebase (Cloud Messaging Serverside): Notifications
- Django CAAS (Cryptography as a Service, my own implementation): Cryptography
- Swagger UI (API Documentation): API Docs
- AWS EC2: Deployment


## 📡 **API** Endpoints

📌 **Root Endpoint**
- **GET `/`**: Default root endpoint, returns "Harmony Haven Server" on success.

📌 **Add Quote**
- **POST `/api/v1/add-quote`**: Adds a new quote. Requires a Quote object in the request body.

📌 **Add Quote Category**
- **POST `/api/v1/add-quote-category`**: Adds a new quote category. Requires a CategoryRequest object in the request body.

📌 **Admin Delete User**
- **DELETE `/api/v1/admin/delete-user`**: Deletes a user. Requires a Map_String object in the request body.

📌 **Retrieve All Articles**
- **GET `/api/v1/articles`**: Retrieves all articles. Returns a list of Article objects.

📌 **Get Article by ID**
- **GET `/api/v1/articles/{id}`**: Retrieves an article by its ID. Requires the id parameter in the path.

📌 **Get Articles by Category ID**
- **GET `/api/v1/articles/category/{id}`**: Retrieves articles by category ID. Requires the id parameter in the path.

📌 **Get Recent Articles**
- **GET `/api/v1/articles/recent/{size}`**: Retrieves recent articles by specifying the number (size) in the path.

📌 **Retrieve All Categories**
- **GET `/api/v1/categories`**: Retrieves all article categories.

📌 **Check Auth Status**
- **GET `/api/v1/check-auth-status`**: Checks the authentication status of the user.

📌 **Delete Quote Category**
- **DELETE `/api/v1/delete-quote-category/{categoryId}`**: Deletes a quote category by its ID. Requires the categoryId parameter in the path.

📌 **Delete Quote**
- **DELETE `/api/v1/delete-quote/{quoteId}`**: Deletes a quote by its ID. Requires the quoteId parameter in the path.

📌 **Get Quote by Category**
- **GET `/api/v1/get-quote-by-category/{categoryId}`**: Retrieves quotes by category ID. Requires the categoryId parameter in the path.

📌 **Get Quote Category**
- **GET `/api/v1/get-quote-category`**: Retrieves all quote categories.

📌 **Send Specific Notification**
- **POST `/api/v1/notification/send-specific`**: Sends a specific notification.

📌 **OpenAI Request**
- **POST `/api/v1/openai-request`**: Sends a request to the OpenAI API. Requires an OpenAIPromptDto object in the request body.

📌 **Test Notification**
- **POST `/api/v1/test-notification`**: Tests notification sending functionality.

📌 **Update Quote**
- **PATCH `/api/v1/update-quote`**: Updates an existing quote. Requires a Quote object in the request body.

📌 **Update Quote Category**
- **PATCH `/api/v1/update-quote-category`**: Updates an existing quote category. Requires a QuoteCategory object in the request body.

📌 **User Authenticate**
- **POST `/api/v1/user/authenticate`**: Authenticates a user. Requires a UserAuthenticationRequest object in the request body.

📌 **Delete User**
- **DELETE `/api/v1/user/delete`**: Deletes a user.

📌 **User FCM Enrolment**
- **POST `/api/v1/user/fcm-enrolment`**: Enrolls a user for FCM (Firebase Cloud Messaging). Requires an FcmSetupDto object in the request body.

📌 **Forgot Password Authenticate**
- **POST `/api/v1/user/forgot-password/auth`**: Authenticates a user for password reset. Requires a ForgotPasswordAuthModel object in the request body.

📌 **Forgot Password Mailer**
- **POST `/api/v1/user/forgot-password/mailer`**: Sends a password reset email. Requires a ForgotPasswordMailerModel object in the request body.

📌 **Forgot Password Reset**
- **PATCH `/api/v1/user/forgot-password/reset-password`**: Resets a user's password. Requires a ForgotPasswordResetModel object in the request body.

📌 **User Register**
- **POST `/api/v1/user/register`**: Registers a new user. Requires a UserInformationSchema object in the request body.

📌 **User Update**
- **PATCH `/api/v1/user/update`**: Updates user information. Requires a UserInformationSchema object in the request body.

