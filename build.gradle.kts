val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project


plugins {
    application
    id("io.ktor.plugin") version "3.1.2"
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.20"
}

group = "com.erdemserhat"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    // Ktor dependencies
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-freemarker-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")

    // Logging and utility libraries
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.google.guava:guava:33.1.0-jre")


    implementation("org.ktorm:ktorm-core:3.2.0")
    implementation("org.ktorm:ktorm-support-mysql:3.2.0")

    // JSON serialization
    implementation("io.ktor:ktor-client-serialization:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // Firebase
    implementation("com.google.firebase:firebase-admin:9.2.0")

    // Email
    implementation("com.sun.mail:javax.mail:1.6.2")

    // ChatGPT API

    // File transfer
    implementation("commons-net:commons-net:3.9.0")

    // Database migration tool
    implementation("org.flywaydb:flyway-core:9.3.0")

    // Quartz Scheduler
    implementation("org.quartz-scheduler:quartz:2.3.2")

    // Test dependencies
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")

    implementation("mysql:mysql-connector-java:8.0.29") // Ensure the MySQL driver is added

    // Ktor Client ve CIO motoru için gerekli bağımlılık
    implementation("io.ktor:ktor-client-cio:$ktor_version") // Veya kullandığınız sürüm

    // SSE (Server-Sent Events) desteği için gerekli plugin

    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-server-sse:$ktor_version")


}

