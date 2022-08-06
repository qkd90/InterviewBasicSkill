# 部署yapi踩坑经验 

## 环境要求

- nodejs（7.6+)

  ```
  # 查看node版本
  node --version
  ```

  

- mongodb（2.6+）

  ```
  # 查看mongodb版本
  # docker_mongodb 是装载 mongodb 服务容器名称
  # mongo 是代表执行容器内的 mongo 命令，即进入 mongodb 命令行操作界面
  docker exec -it mongo mongo
  
  db.version();
  ```

## 安装

使用我们提供的 yapi-cli 工具，部署 YApi 平台是非常容易的。执行 yapi server 启动可视化部署程序，输入相应的配置和点击开始部署，就能完成整个网站的部署。部署完成之后，可按照提示信息，执行 node/{网站路径/server/app.js} 启动服务器。在浏览器打开指定url, 点击登录输入您刚才设置的管理员邮箱，默认密码为 ymfe.org 登录系统（默认密码可在个人中心修改）。

```
npm install -g yapi-cli --registry https://registry.npm.taobao.org
yapi server 
```

## 服务管理

利用pm2方便服务管理维护。

```
npm install pm2 -g  //安装pm2
cd  {项目目录}
pm2 start "vendors/server/app.js" --name yapi //pm2管理yapi服务
pm2 info yapi //查看服务信息
pm2 stop yapi //停止服务
pm2 restart yapi //重启服务pm
```

## 配置文件配置

### mongodb配置

