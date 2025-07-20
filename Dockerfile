# Use a base image with Java 17
FROM openjdk:17

# Create working directory
WORKDIR /app

# Copy source files and dependencies
COPY ./src ./src
COPY ./web ./web
COPY gson-2.10.1.jar gson-2.10.1.jar
COPY mysql-connector-j-9.3.0.jar mysql-connector-j-9.3.0.jar

# Optional: Install findutils (if you prefer using find)
# RUN apt-get update && apt-get install -y findutils

# Compile Java source files
RUN mkdir out && \
    find src -name "*.java" > sources.txt && \
    javac -cp ".:gson-2.10.1.jar:mysql-connector-j-9.3.0.jar" -d out @sources.txt

# Expose Render's PORT (Render injects PORT environment variable)
EXPOSE 8000

# Run the main class (adjust package path if needed)
CMD ["sh", "-c", "java -cp 'out:gson-2.10.1.jar:mysql-connector-j-9.3.0.jar' com.student.Main"]