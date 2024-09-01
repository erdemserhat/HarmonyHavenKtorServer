# **Harmony Haven Server (Geliştirilme Aşamasında)**

Harmony Haven Server, Harmony Haven uygulamasının arka planında çalışan, gelişmiş bir altyapıya sahip sunucudur. Ktor kullanarak API hizmetlerini sağlayan bu sunucu, AWS üzerinde barındırılarak ölçeklenebilirlik ve yüksek performans sunar. Kullanıcı yönetimi, makale erişimi ve AI destekli bildirimlerin yanı sıra, HTTPS ve OAuth2/JWT ile güvenli veri iletimi ve kimlik doğrulama sağlar. Harmony Haven Server, uygulamanın tüm özelliklerini destekleyen sağlam ve güvenilir bir altyapı sunarak kullanıcı deneyimini güçlendirir.

## Sunucu API Hizmetlerine Erişin 



<div align="center">

![1232](https://github.com/user-attachments/assets/cc10eb17-3ccf-4365-a066-c21a341975db)

API ile etkileşimde bulunmak için şu adresi ziyaret edebilirsiniz: [http://51.20.136.184:5000/](http://51.20.136.184:5000/)

</div>


## Sistem İşleyişi Genel Görünüm

![image](https://github.com/erdemserhat/HarmonyHavenAndroidClient/assets/116950260/b02ad5f5-0154-48bf-a813-33b750f34397)

## Sunucu Mimari Genel Görünüm

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
            ├── remote
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

## Kullanılan Teknolojiler ve Paradigmalar:

- MVC (Model-View-Controller) mimarisi: Mimari
- Clean Architecture: Mimari
- SOLID prensipleri: En İyi Uygulamalar
- JWT Kimlik Doğrulama ve Yetkilendirme: Güvenlik
- Ktorm: ORM
- REST API: Ağ
- OpenAI API: AI Entegrasyonu
- FreeMarker: Şablonlama
- Javax Mail Service: E-posta
- Google Guava: Araçlar
- MySQL: Veritabanı
- Firebase (Sunucu Tarafı Bildirimleri): Bildirimler
- Django CAAS (Cryptography as a Service, kendi uygulamam): Kriptografi
- Swagger UI (API Dokümantasyonu): API Dokümantasyonu
- AWS EC2: Dağıtım

## 📡 **API** Uç Noktaları

📌 **Kök Uç Nokta**
- **GET `/`**: Varsayılan kök uç noktası, başarılı olduğunda "Harmony Haven Server" döner.

📌 **Alıntı Ekle**
- **POST `/api/v1/add-quote`**: Yeni bir alıntı ekler. İstek gövdesinde bir Quote nesnesi gerektirir.

📌 **Alıntı Kategorisi Ekle**
- **POST `/api/v1/add-quote-category`**: Yeni bir alıntı kategorisi ekler. İstek gövdesinde bir CategoryRequest nesnesi gerektirir.

📌 **Yönetici Kullanıcı Sil**
- **DELETE `/api/v1/admin/delete-user`**: Bir kullanıcıyı siler. İstek gövdesinde bir Map_String nesnesi gerektirir.

📌 **Tüm Makaleleri Al**
- **GET `/api/v1/articles`**: Tüm makaleleri alır. Bir dizi Article nesnesi döner.

📌 **ID'ye Göre Makale Al**
- **GET `/api/v1/articles/{id}`**: Belirli bir ID'ye göre makale alır. Yol parametresi olarak id gerektirir.

📌 **Kategori ID'ye Göre Makaleleri Al**
- **GET `/api/v1/articles/category/{id}`**: Kategori ID'ye göre makale alır. Yol parametresi olarak id gerektirir.

📌 **Son Makaleleri Al**
- **GET `/api/v1/articles/recent/{size}`**: Belirtilen sayıda son makaleyi alır.

📌 **Tüm Kategorileri Al**
- **GET `/api/v1/categories`**: Tüm makale kategorilerini alır.

📌 **Kimlik Doğrulama Durumunu Kontrol Et**
- **GET `/api/v1/check-auth-status`**: Kullanıcının kimlik doğrulama durumunu kontrol eder.

📌 **Alıntı Kategorisini Sil**
- **DELETE `/api/v1/delete-quote-category/{categoryId}`**: Belirli bir kategori ID'sine göre alıntı kategorisini siler. Yol parametresi olarak categoryId gerektirir.

📌 **Alıntı Sil**
- **DELETE `/api/v1/delete-quote/{quoteId}`**: Belirli bir alıntıyı siler. Yol parametresi olarak quoteId gerektirir.

📌 **Kategoriye Göre Alıntı Al**
- **GET `/api/v1/get-quote-by-category/{categoryId}`**: Kategori ID'sine göre alıntı alır. Yol parametresi olarak categoryId gerektirir.

📌 **Alıntı Kategorilerini Al**
- **GET `/api/v1/get-quote-category`**: Tüm alıntı kategorilerini alır.

📌 **Belirli Bildirim Gönder**
- **POST `/api/v1/notification/send-specific`**: Belirli bir bildirim gönderir.

📌 **OpenAI İsteği**
- **POST `/api/v1/openai-request`**: OpenAI API'ye bir istek gönderir. İstek gövdesinde bir OpenAIPromptDto nesnesi gerektirir.

📌 **Bildirim Testi**
- **POST `/api/v1/test-notification`**: Bildirim gönderme işlevselliğini test eder.

📌 **Alıntıyı Güncelle**
- **PATCH `/api/v1/update-quote`**: Var olan bir alıntıyı günceller. İstek gövdesinde bir Quote nesnesi gerektirir.

📌 **Alıntı Kategorisini Güncelle**
- **PATCH `/api/v1/update-quote-category`**: Var olan bir alıntı kategorisini günceller. İstek gövdesinde bir QuoteCategory nesnesi gerektirir.

📌 **Kullanıcı Kimlik Doğrula**
- **POST `/api/v1/user/authenticate`**: Bir kullanıcıyı kimlik doğrular. İstek gövdesinde bir UserAuthenticationRequest nesnesi gerektirir.

📌 **Kullanıcı Sil**
- **DELETE `/api/v1/user/delete`**: Bir kullanıcıyı siler.

📌 **Kullanıcı FCM Kaydı**
- **POST `/api/v1/user/fcm-enrolment`**: Bir kullanıcıyı FCM (Firebase Cloud Messaging) için kaydeder. İstek gövdesinde bir FcmSetupDto nesnesi gerektirir.

📌 **Şifre Unutma Kimlik Doğrulama**
- **POST `/api/v1/user/forgot-password/auth`**: Şifre sıfırlama için kullanıcıyı kimlik doğrular. İstek gövdesinde bir ForgotPasswordAuthModel nesnesi gerektirir.

📌 **Şifre Unutma E-posta Gönderimi**
- **POST `/api/v1/user/forgot-password/mailer`**: Şifre sıfırlama e-postası gönderir. İstek gövdesinde bir ForgotPasswordMailerModel nesnesi gerektirir.

📌 **Şifre Unutma Sıfırlama**
- **PATCH `/api/v1/user/forgot-password/reset-password`**: Kullanıcının şifresini sıfırlar. İstek gövdesinde bir ForgotPasswordResetModel nesnesi gerektirir.

📌 **Kullanıcı Kaydı**
- **POST `/api/v1/user/register`**: Yeni bir kullanıcı kaydeder. İstek gövdesinde bir UserInformationSchema nesnesi gerektirir.

📌 **Kullanıcı Güncelle**
- **PATCH `/api/v1/user/update`**: Kullanıcı bilgilerini günceller. İstek gövdesinde bir UserInformationSchema nesnesi gerektirir.
