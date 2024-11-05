plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

val springMvcVersion = "6.0.7"
val cloudLoadBalancerVersion = "4.1.4"
val nettyResolverVersion = "4.1.68.Final:osx-aarch_64"
val jjwtApiVersion = "0.11.2"
val jjwtImplVersion = "0.11.5"
val jjwtJacksonVersion = "0.11.1"
val junitPlatformLauncherVersion = "1.11.3"
val kotlinTestJunit5 = "2.0.21"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.springframework:spring-webmvc:$springMvcVersion")
    implementation("org.springframework.cloud:spring-cloud-loadbalancer:$cloudLoadBalancerVersion")
    implementation("io.netty:netty-resolver-dns-native-macos:$nettyResolverVersion")
    implementation ("io.jsonwebtoken:jjwt-api:$jjwtApiVersion")


    runtimeOnly ("io.jsonwebtoken:jjwt-impl:$jjwtImplVersion")
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:$jjwtJacksonVersion")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinTestJunit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformLauncherVersion")


}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
