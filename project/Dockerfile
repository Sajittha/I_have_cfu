# ใช้ Java 17 เป็น Base Image
FROM eclipse-temurin:17-jdk-jammy

# ตั้งค่า Working Directory ภายใน Image
WORKDIR /app

# คัดลอกไฟล์ Build (pom.xml) และ Source Code เข้าไป
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

# รันคำสั่ง Build ของ Maven เพื่อสร้างไฟล์ .jar
RUN ./mvnw package -DskipTests

# บอกว่าแอปพลิเคชันจะทำงานที่ Port 8080
EXPOSE 8080

# คำสั่งสุดท้ายที่จะรันเมื่อ Container เริ่มทำงาน
CMD ["java", "-jar", "target/*.jar"]