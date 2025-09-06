# Hướng dẫn Build EpicCraftItem Plugin

## Yêu cầu hệ thống

1. **Java Development Kit (JDK) 17+**
2. **Apache Maven 3.6+**

## Cài đặt Maven (Windows)

### Cách 1: Sử dụng Chocolatey
```powershell
# Cài đặt Chocolatey nếu chưa có
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

# Cài đặt Maven
choco install maven
```

### Cách 2: Cài đặt thủ công
1. Tải Maven từ: https://maven.apache.org/download.cgi
2. Giải nén vào thư mục (ví dụ: `C:\apache-maven-3.9.5`)
3. Thêm `C:\apache-maven-3.9.5\bin` vào PATH environment variable

## Build Plugin

### 1. Kiểm tra Java và Maven
```bash
java -version
mvn -version
```

### 2. Build plugin
```bash
# Clean và compile
mvn clean compile

# Package thành JAR file
mvn clean package
```

### 3. Tìm file JAR
Sau khi build thành công, file JAR sẽ được tạo tại:
```
target/EpicCraftItem-1.1.jar
```

## Cài đặt Plugin

1. Copy file `EpicCraftItem-1.1.jar` vào thư mục `plugins/` của server Minecraft
2. Restart server
3. Plugin sẽ tự động tạo các file cấu hình trong thư mục `plugins/EpicCraftItem/`

## Cấu trúc Project

```
EpicCraftItem/
├── pom.xml                          # Maven configuration
├── README.md                        # Documentation
├── BUILD_INSTRUCTIONS.md            # Build instructions
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── dragondirt/
        │           └── epiccraftitem/
        │               ├── EpicCraftItem.java      # Main plugin class
        │               ├── api/                    # API classes
        │               ├── command/                 # Command handlers
        │               ├── data/                    # Data management
        │               ├── event/                   # Custom events
        │               ├── listener/                # Event listeners
        │               └── util/                   # Utility classes
        └── resources/
            ├── plugin.yml           # Plugin configuration
            ├── config.yml           # Main config
            ├── message.yml          # Messages
            ├── craft/               # Craft configurations
            └── gui/                 # GUI configurations
```

## Troubleshooting

### Lỗi "mvn not found"
- Đảm bảo Maven đã được cài đặt và thêm vào PATH
- Restart terminal sau khi cài đặt

### Lỗi Java version
- Đảm bảo sử dụng JDK 17 hoặc cao hơn
- Kiểm tra JAVA_HOME environment variable

### Lỗi dependencies
- Chạy `mvn clean install` để tải dependencies
- Kiểm tra kết nối internet

## Support

Nếu gặp vấn đề, hãy kiểm tra:
1. Java version: `java -version`
2. Maven version: `mvn -version`
3. Log files trong thư mục `target/`
