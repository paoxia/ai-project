#!/bin/bash

echo "正在查找占用 8080 端口的进程..."

PIDS=$(lsof -t -i:8080)

if [ -z "$PIDS" ]; then
    echo "没有发现占用 8080 端口的进程。"
else
    echo "发现以下进程占用 8080 端口: $PIDS"
    echo "尝试终止进程..."
    for PID in $PIDS; do
        kill -9 $PID && echo "已终止 PID: $PID"
    done
    echo "所有占用 8080 的进程已终止。"
fi
