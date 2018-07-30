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
![index](https://github.com/itning/hexo-admin/blob/master/pic/index.png)
![new_post](https://github.com/itning/hexo-admin/blob/master/pic/new_post.png)
![update](https://github.com/itning/hexo-admin/blob/master/pic/update.png)