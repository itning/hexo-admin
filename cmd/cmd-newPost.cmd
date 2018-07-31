chcp 65001
@echo off
cls
set input=
set /p input=请输入文章标题:
hexo new post " %input% "