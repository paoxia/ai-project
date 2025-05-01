@echo off
echo 正在查找占用 8080 端口的进程...

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do (
    set pid=%%a
)

if defined pid (
    echo 发现进程 PID：%pid%
    tasklist /FI "PID eq %pid%"
    echo 正在尝试终止该进程...
    taskkill /PID %pid% /F
    echo 成功关闭占用 8080 的进程。
) else (
    echo 没有发现占用 8080 端口的进程。
)

pause
