@echo off
echo ========================================
echo Os-Easy 完整安装脚本
echo ========================================
echo.

:: 检查管理员权限
net session >nul 2>&1
if %errorLevel% == 0 (
    echo [OK] 检测到管理员权限
) else (
    echo [错误] 需要管理员权限运行此脚本
    echo 请右键点击此文件，选择"以管理员身份运行"
    pause
    exit /b 1
)

echo.
echo 步骤1：导入注册表配置...
regedit /s AutoStart.reg
if %errorLevel% == 0 (
    echo [OK] 自动启动配置导入成功
) else (
    echo [警告] 自动启动配置导入失败
)

regedit /s UninstallInfo.reg
if %errorLevel% == 0 (
    echo [OK] 卸载信息导入成功
) else (
    echo [警告] 卸载信息导入失败
)

regedit /s VirtualRenderService.reg
if %errorLevel% == 0 (
    echo [OK] VirtualRender服务配置导入成功
) else (
    echo [警告] VirtualRender服务配置导入失败
)

echo.
echo 步骤2：安装VirtualRender驱动...
if exist "Drivers\vrd.sys" (
    pnputil /add-driver "Drivers\vrd.sys" /install
    if %errorLevel% == 0 (
        echo [OK] VirtualRender驱动安装成功
    ) else (
        echo [警告] VirtualRender驱动安装失败，请手动安装
    )
) else (
    echo [错误] 找不到驱动文件 Drivers\vrd.sys
)

echo.
echo 步骤3：启动VirtualRender服务...
sc start VirtualRender
if %errorLevel% == 0 (
    echo [OK] VirtualRender服务启动成功
) else (
    echo [警告] VirtualRender服务启动失败，可能需要重启后生效
)

echo.
echo 步骤4：配置防火墙规则...
echo 正在为Os-Easy程序添加防火墙例外...

set "PROGRAM_PATH=C:\Program Files (x86)\Os-Easy\os-easy multicast teaching system"

netsh advfirewall firewall add rule name="Os-Easy Student" dir=in action=allow program="%PROGRAM_PATH%\Student.exe"
netsh advfirewall firewall add rule name="Os-Easy Teacher" dir=in action=allow program="%PROGRAM_PATH%\Teacher.exe"
netsh advfirewall firewall add rule name="Os-Easy ScreenSender" dir=in action=allow program="%PROGRAM_PATH%\ScreenSender.exe"
netsh advfirewall firewall add rule name="Os-Easy ScreenRender" dir=in action=allow program="%PROGRAM_PATH%\ScreenRender.exe"
netsh advfirewall firewall add rule name="Os-Easy StudentScreenHelper" dir=in action=allow program="%PROGRAM_PATH%\StudentScreenHelper.exe"

echo [OK] 防火墙规则配置完成

echo.
echo ========================================
echo 安装完成！
echo ========================================
echo.
echo 重要提示：
echo 1. 请重启计算机以确保所有组件正常工作
echo 2. 确保主程序文件已复制到正确位置
echo 3. 如果仍有问题，请检查系统事件日志
echo.
pause
