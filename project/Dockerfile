# ----------------------------------------------------------------------------------
# STAGE 1: BUILD THE APPLICATION (ใช้ Maven/JDK เพื่อคอมไพล์และสร้าง JAR)
# ----------------------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# กำหนด working directory ภายใน container
WORKDIR /app

# คัดลอกไฟล์ pom.xml และ maven wrapper เพื่อให้ Maven สามารถดาวน์โหลด dependencies ได้
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# ดาวน์โหลด dependencies ก่อน เพื่อใช้ประโยชน์จาก Docker Layer Caching
RUN ./mvnw dependency:go-offline -B

# คัดลอกไฟล์ source code ทั้งหมด
COPY src src

# รันคำสั่ง build: 'clean install'
# -DskipTests: สั่งข้ามการรัน Test เพื่อให้ build เร็วขึ้น
RUN ./mvnw clean install -DskipTests

# ----------------------------------------------------------------------------------
# STAGE 2: RUN THE APPLICATION (ใช้ JRE ขนาดเล็กเพื่อรันไฟล์ JAR ที่สร้างเสร็จแล้ว)
# ----------------------------------------------------------------------------------
# เลือก base image ที่เล็กกว่า มีแค่ Java Runtime Environment (JRE)
FROM eclipse-temurin:21-jre-alpine

# กำหนด working directory ภายใน container
WORKDIR /app

# คัดลอกไฟล์ JAR ที่สร้างเสร็จแล้วจาก Stage 'build'
# ชื่อไฟล์: app-0.0.1-SNAPSHOT.jar (ต้องตรงกับชื่อที่ build ออกมาใน log ก่อนหน้า)
COPY --from=build /app/target/app-0.0.1-SNAPSHOT.jar app.jar

# เปิดพอร์ตที่ Spring Boot ใช้ (ปกติคือ 8080)
EXPOSE 8080

# คำสั่งเริ่มต้นการรันแอปพลิเคชัน (Start Command)
ENTRYPOINT ["java", "-jar", "app.jar"]