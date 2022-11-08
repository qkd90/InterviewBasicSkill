# PM2管理nodejs

PM2 是具有内置负载均衡器的 Node.js 应用程序的生产流程管理器。它允许您使应用程序永远保持活动状态，在不停机的情况下重新加载它们，并促进常见的系统管理任务。

在生产模式下启动应用程序非常简单：

```
$ pm2 start app.js
```

PM2 不断受到[1800 多项测试](https://app.travis-ci.com/github/Unitech/pm2)的攻击。

官网：[https ://pm2.keymetrics.io/](https://pm2.keymetrics.io/)

适用于 Linux（稳定）和 macOS（稳定）和 Windows（稳定）。从 Node.js 12.X 开始支持所有 Node.js 版本。

使用 NPM：

```
$ npm install pm2 -g
```

[您可以使用NVM](https://github.com/nvm-sh/nvm#installing-and-updating)或[ASDF](https://blog.natterstefan.me/how-to-use-multiple-node-version-with-asdf)轻松安装Node.js。

您可以像这样启动任何应用程序（Node.js、Python、Ruby、$PATH 中的二进制文件...）：

```
$ pm2 start app.js
```

您的应用程序现在已被守护、监控并永远保持活动状态。

启动应用程序后，您可以轻松管理它们：

[![进程列表](https://github.com/Unitech/pm2/raw/master/pres/pm2-ls-v2.png)](https://github.com/Unitech/pm2/raw/master/pres/pm2-ls-v2.png)

列出所有正在运行的应用程序：

```
$ pm2 list
```

管理应用程序很简单：

```
$ pm2 stop     <app_name|namespace|id|'all'|json_conf>
$ pm2 restart  <app_name|namespace|id|'all'|json_conf>
$ pm2 delete   <app_name|namespace|id|'all'|json_conf>
```

要获得有关特定应用程序的更多详细信息：

```
$ pm2 describe <id|app_name>
```

To monitor logs, custom metrics, application information:

```
$ pm2 monit
```

[More about Process Management](https://pm2.keymetrics.io/docs/usage/process-management/)

### Cluster Mode: Node.js Load Balancing & Zero Downtime Reload

The Cluster mode is a special mode when starting a Node.js application, it starts multiple processes and load-balance HTTP/TCP/UDP queries between them. This increase overall performance (by a factor of x10 on 16 cores machines) and reliability (faster socket re-balancing in case of unhandled errors).

[![支持的框架](https://raw.githubusercontent.com/Unitech/PM2/master/pres/cluster.png)](https://raw.githubusercontent.com/Unitech/PM2/master/pres/cluster.png)

Starting a Node.js application in cluster mode that will leverage all CPUs available:

```
$ pm2 start api.js -i <processes>
```

`<processes>` can be `'max'`, `-1` (all cpu minus 1) or a specified number of instances to start.

**Zero Downtime Reload**

Hot Reload allows to update an application without any downtime:

```
$ pm2 reload all
```

[More informations about how PM2 make clustering easy](https://pm2.keymetrics.io/docs/usage/cluster-mode/)

With the drop-in replacement command for `node`, called `pm2-runtime`, run your Node.js application in a hardened production environment. Using it is seamless:

```
RUN npm install pm2 -g
CMD [ "pm2-runtime", "npm", "--", "start" ]
```

[Read More about the dedicated integration](https://pm2.keymetrics.io/docs/usage/docker-pm2-nodejs/)

PM2 allows to monitor your host/server vitals with a monitoring speedbar.

To enable host monitoring:

```
$ pm2 set pm2:sysmonit true
$ pm2 update
```

[![支持的框架](https://raw.githubusercontent.com/Unitech/PM2/master/pres/vitals.png)](https://raw.githubusercontent.com/Unitech/PM2/master/pres/vitals.png)

[![监控](https://github.com/Unitech/pm2/raw/master/pres/pm2-monit.png)](https://github.com/Unitech/pm2/raw/master/pres/pm2-monit.png)

Monitor all processes launched straight from the command line:

```
$ pm2 monit
```

To consult logs just type the command:

```
$ pm2 logs
```

Standard, Raw, JSON and formated output are available.

Examples:

```
$ pm2 logs APP-NAME       # Display APP-NAME logs
$ pm2 logs --json         # JSON output
$ pm2 logs --format       # Formated output

$ pm2 flush               # Flush all logs
$ pm2 reloadLogs          # Reload all logs
```

To enable log rotation install the following module

```
$ pm2 install pm2-logrotate
```

[More about log management](https://pm2.keymetrics.io/docs/usage/log-management/)

PM2 can generate and configure a Startup Script to keep PM2 and your processes alive at every server restart.

支持的初始化系统：**systemd**、**upstart**、**launchd**、**rc.d**

```
# Generate Startup Script
$ pm2 startup

# Freeze your process list across server restart
$ pm2 save

# Remove Startup Script
$ pm2 unstartup
```

[有关启动脚本生成的更多信息](https://pm2.keymetrics.io/docs/usage/startup/)

```
# Install latest PM2 version
$ npm install pm2@latest -g
# Save process list, exit old PM2 & restore all processes
$ pm2 update
```

*PM2 更新是无缝的*

如果您使用 PM2 管理应用程序，PM2+ 可以轻松监控和管理跨服务器的应用程序。

[![https://app.pm2.io/](https://camo.githubusercontent.com/904a297b1b04f327ab90f9a10b4ca235c5ac15867c7ddce7e76cb4513625d521/68747470733a2f2f706d322e696f2f696d672f6170702d6f766572766965772e706e67)](https://camo.githubusercontent.com/904a297b1b04f327ab90f9a10b4ca235c5ac15867c7ddce7e76cb4513625d521/68747470733a2f2f706d322e696f2f696d672f6170702d6f766572766965772e706e67)

随意尝试：