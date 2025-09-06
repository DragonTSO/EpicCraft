# PowerShell script để push project lên GitHub
# Chạy script này sau khi đã cài đặt Git

Write-Host "=== EpicCraftItem - Push to GitHub ===" -ForegroundColor Green
Write-Host ""

# Kiểm tra Git đã được cài đặt chưa
try {
    $gitVersion = git --version
    Write-Host "✓ Git đã được cài đặt: $gitVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Git chưa được cài đặt!" -ForegroundColor Red
    Write-Host "Vui lòng cài đặt Git từ: https://git-scm.com/download/win" -ForegroundColor Yellow
    Write-Host "Hoặc chạy: choco install git" -ForegroundColor Yellow
    exit 1
}

Write-Host ""

# Khởi tạo Git repository
Write-Host "1. Khởi tạo Git repository..." -ForegroundColor Cyan
git init

# Thêm remote origin
Write-Host "2. Thêm remote origin..." -ForegroundColor Cyan
git remote add origin https://github.com/DragonTSO/EpicCraft.git

# Kiểm tra remote đã được thêm chưa
Write-Host "3. Kiểm tra remote repositories..." -ForegroundColor Cyan
git remote -v

# Add tất cả files
Write-Host "4. Thêm files vào staging area..." -ForegroundColor Cyan
git add .

# Commit với message
Write-Host "5. Commit changes..." -ForegroundColor Cyan
git commit -m "Initial commit: EpicCraftItem plugin for Minecraft 1.20.4

- Advanced crafting system for Minecraft 1.20.4
- Multi-level GUI system
- Probability-based crafting
- Plugin integration support (AdvancedEnchantments, MMOItems, FindItem)
- Item return system
- Admin tools and debug mode
- Package: com.dragondirt.epiccraftitem"

# Tạo branch main
Write-Host "6. Tạo branch main..." -ForegroundColor Cyan
git branch -M main

# Push lên GitHub
Write-Host "7. Push lên GitHub..." -ForegroundColor Cyan
Write-Host "Đang push lên https://github.com/DragonTSO/EpicCraft.git..." -ForegroundColor Yellow

try {
    git push -u origin main
    Write-Host ""
    Write-Host "✓ Push thành công!" -ForegroundColor Green
    Write-Host "Repository: https://github.com/DragonTSO/EpicCraft" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Bạn có thể truy cập repository tại:" -ForegroundColor White
    Write-Host "https://github.com/DragonTSO/EpicCraft" -ForegroundColor Blue
} catch {
    Write-Host ""
    Write-Host "✗ Có lỗi khi push!" -ForegroundColor Red
    Write-Host "Có thể do:" -ForegroundColor Yellow
    Write-Host "- Repository không tồn tại hoặc không có quyền truy cập" -ForegroundColor Yellow
    Write-Host "- Chưa cấu hình authentication (username/password hoặc token)" -ForegroundColor Yellow
    Write-Host "- Repository đã có nội dung và cần pull trước" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Thử các lệnh sau:" -ForegroundColor White
    Write-Host "git pull origin main --allow-unrelated-histories" -ForegroundColor Gray
    Write-Host "git push -u origin main" -ForegroundColor Gray
}

Write-Host ""
Write-Host "=== Hoàn thành ===" -ForegroundColor Green
