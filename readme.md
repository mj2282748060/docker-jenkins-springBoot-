#docker + jenkins +springBoot 自动化部署

## 参考博客
[基于Docker+Jenkins实现自动化部署](https://www.cnblogs.com/ming-blogs/p/10903408.html)

[shell脚本 docker: command not found](http://www.manongjc.com/detail/16-ijyzctbeoyoiugi.html)

[jenkins并且集成mvn，新手必踩的坑！](https://www.cnblogs.com/dreammer/p/13601282.html)

## 坑
1、  Jenkins 中 SERVER_NAME 变量似乎不起作用， 决定你打包后jar包名字的是我们项目中 pom.xml 文件中artifactId、name属性，暂不知为什么

2、配了JDK路径不管用的话，直接使用java安装的全路径， 如 （/opt/jdk/bin/java -jar xxx.jar）

## 自己使用的Jenkins shell命令

```shell
#!/bin/bash

source /etc/profile

#服务名称
SERVER_NAME=springBootTest

# 源jar路径,mvn打包完成之后，target目录下的jar包名称，也可选择成为war包，war包可移动到Tomcat的webapps目录下运行，这里使用jar包，用java -jar 命令执行  
JAR_NAME=springBootTest-0.0.1-SNAPSHOT

# 源jar路径  
#/usr/local/jenkins_home/workspace--->jenkins 工作目录

#demo 项目目录
#target 打包生成jar包的目录
JAR_PATH=/var/jenkins_home/workspace/springBootTest/target

# 打包完成之后，把jar包移动到运行jar包的目录--->work_daemon，work_daemon这个目录需要自己提前创建
# JAR_WORK_PATH=/var/jenkins_home/workspace/springBootTest/target

 
echo "查询进程id-->$SERVER_NAME"
PID=`ps -ef | grep "$SERVER_NAME" | awk '{print $2}'`
echo "得到进程ID：$PID"
echo "结束进程"

for id in $PID
do
kill -9 $id  
echo "killed $id"  
done

echo "结束进程完成"

 

#复制jar包到执行目录
#echo "复制jar包到执行目录:cp $JAR_PATH/$JAR_NAME.jar   $JAR_WORK_PATH"
#cp $JAR_PATH/$JAR_NAME.jar $JAR_WORK_PATH
#echo "复制jar包完成"

echo "进入工作路径  cd $JAR_PATH"
cd $JAR_PATH
#修改文件权限
echo "chmod 755 $JAR_NAME.jar"
chmod 755 $JAR_NAME.jar

# 防止子进程被杀
echo "BUILD_ID=dontKillMe"
BUILD_ID=dontKillMe

echo "nohup /opt/java/openjdk/bin/java -jar $JAR_NAME.jar >> out.log &&"
nohup /opt/java/openjdk/bin/java -jar $JAR_NAME.jar >> out.log &
```
## over