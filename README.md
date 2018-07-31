# Hexo 后台管理
[![GitHub stars](https://img.shields.io/github/stars/itning/hexo-admin.svg?style=social&label=Stars)]()
[![GitHub forks](https://img.shields.io/github/forks/itning/hexo-admin.svg?style=social&label=Fork)]()
[![GitHub watchers](https://img.shields.io/github/watchers/itning/hexo-admin.svg?style=social&label=Watch)]()
[![GitHub followers](https://img.shields.io/github/followers/itning.svg?style=social&label=Follow)]()

[![GitHub license](https://img.shields.io/github/license/itning/hexo-admin.svg)](https://github.com/itning/hexo-admin/blob/master/LICENSE)
[![GitHub last commit](https://img.shields.io/github/last-commit/itning/hexo-admin.svg)]()
[![GitHub release](https://img.shields.io/github/release/itning/hexo-admin.svg)]()
[![GitHub repo size in bytes](https://img.shields.io/github/repo-size/itning/hexo-admin.svg)]()
[![language](https://img.shields.io/badge/language-JAVA-orange.svg)]()

## 介绍

hexo是一个优秀的开源博客系统，但是后端文章管理方面只能写好MarkDown后放在文件夹中，很不方便，所以才有了这个项目。

项目使用JAVA语言开发，使用Netty作为内嵌服务器，备份文章选用sqllite数据库实现。

## 拓展

[windows 平台下 脚本](https://github.com/itning/hexo-admin/tree/master/cmd)

[阿里云VPS搭建自己的Hexo博客](https://segmentfault.com/a/1190000005723321)

## 实现功能
1. 写新文章
2. 更新文章
3. 删除文章
4. 备份文章(使用sqlite数据库)
5. 夜间模式

## 如何使用
1. [获取最新Releases](https://github.com/itning/hexo-admin/releases)

2. 通过 

   ```shell
   java -jar .\hexo-admin-1.0.0-release.jar --user-props.db-file-path=C:\Users\wangn\Desktop\c.db --user-props.mark-down-path=C:\Users\wangn\Desktop\post
   ```

3. user-props.db-file-path 表示备份文件位置(随便写)

4. user-props.mark-down-path 表示文章目录 G:\xxx\itningblog\source\\_posts\

5. 浏览器打开 [http://localhost:8080/](http://localhost:8080/)
## 如何

1. 更改端口

   ```shell
   java -jar xxx.jar --server.port=80
   ```
## 预览

- 主页
![index](https://github.com/itning/hexo-admin/blob/master/pic/index.png)
- 新文章
![new](https://github.com/itning/hexo-admin/blob/master/pic/new.png)
- 修改
![editor](https://github.com/itning/hexo-admin/blob/master/pic/editor.png)
![editor_sure](https://github.com/itning/hexo-admin/blob/master/pic/editor_sure.png)
- 删除
![delete](https://github.com/itning/hexo-admin/blob/master/pic/delete.png)
- 夜间模式
![black](https://github.com/itning/hexo-admin/blob/master/pic/black.png)
- 手机端效果
![phone_index](https://github.com/itning/hexo-admin/blob/master/pic/phone_index.png)
![phone_nav](https://github.com/itning/hexo-admin/blob/master/pic/phone_nav.png)
![phone_editor](https://github.com/itning/hexo-admin/blob/master/pic/phone_editor.png)