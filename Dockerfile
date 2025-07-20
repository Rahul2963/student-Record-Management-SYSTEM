# Use Java 17 base image
FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy Java source files and dependencies
COPY ./src ./src
COPY gson-2.10.1.jar gson-2.10.1.jar
COPY mysql-connector-j-9.3.0.jar mysql-connector-j-9.3.0.jar

# Compile Java source files (safe approach)
RUN mkdir out && \
    javac -cp ".:gson-2.10.1.jar:mysql-connector-j-9.3.0.jar" \
    -d out \
    src/**/*.java || true

# Expose port
EXPOSE 8000

# Run main class (adjust if package name is different)
CMD ["sh", "-c", "java -cp 'out:gson-2.10.1.jar:mysql-connector-j-9.3.0.jar' com.student.Main"]