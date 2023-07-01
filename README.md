# 用户中心

## 1、项目流程

- 需求分析
- 设计（此过程一般是开发负责人简单设计）
- 详设（工时>40h需要专门SE进行设计）
- 技术选型（前后端分离开发，后端统一用企业自己的框架支撑）
- 实现功能（写demo—>实现业务逻辑）
- 测试/开发自检（一般包括增删改逻辑、控件效果、sonarlint扫描等）
- 提交代码
- 代码评审
- 更新部署

## 2、需求分析

用户中心分为

1. 登录/注册
2. 用户管理（**仅管理员可见**），对用户的查询或者修改
3. 用户校验（**仅星球用户可见，用户编号**）

## 3、技术选型

前台：

- Ant Design Pro
- Ant Design组件库
- Umi开发框架
- React开发框架

后台：

- java8
- spring（AOC）、springmvc、springboot
- JWT
- mybatis、mybatis-plus
- junit单元测试
- MySQL5.7

## 4、初始化项目

### 4.1前台初始化

#### 4.1.1环境准备

**ps：**用的是Ant Design Pro最新的文档（230513）

1. **nodejs版本16.14.2**

  用nodejs官网v18会有问题，我这里就降版本了

2. **安装yarn**

  ~~~shell
  npm install -g yarn
  npm install -g tyarn(可以不用装)
  
  yarn config set registry https://registry.npm.taobao.org（yarn镜像源/npm同理）
  ~~~

3. **安装umijs**

  ~~~shell
  yarn add @umijs/preset -ui -D
  ~~~

  **ps：**最新的官方文档已经没有了，但是还是可以使用的

  **ps：**webstorm要使用管理员模式打开，否则终端使用yarn会报错

#### 4.1.2前端瘦身

1. 移除国际化

	~~~txt
	执行il18n-remove：（为什么叫il18n？internationalization取首末字母中间18字符）
	~~~

	成功移除效果

	<img src="https://tyangjian.oss-cn-shanghai.aliyuncs.com/tmp/202305172012544.png" alt="image-20230517201228116" style="zoom: 67%;" />

2. 删除mock数据

3. 删除e2e自动化测试

4. swagger组件和openapi.json

5. test删除、playwright.config、jest.config

### 4.2后台初始化

IDEA2023.1.2

1. idea创建springboot（最简单的方式就是直接用idea的创建）

  <img src="https://tyangjian.oss-cn-shanghai.aliyuncs.com/tmp/202305172156771.png" alt="image-20230517215613300" style="zoom:67%;" />

2. 这么需要添加junit、MyBatis-Plus依赖并且通过阅读官网进行单元测试

   ~~~java
   @resource
   private UserMapper userMapper;
   // 只需要在interface上面加上@component就实现控制反转然后通过@AutoWired实现注入
   ~~~

3. 在Simple测试类中，如果使用@Test注解是junit的注解，需要看下idea的junit的插件有没有生效，如果发现没有启动按钮，大概是是junit插件被禁用


## 5、需求设计

### 5.1数据库设计

​	**ps：**数据库设计就是有哪些表？表的字段？字段类型（本人习惯直接复用ddl语句）

​	在建完表之后规整项目目录（这里省略）

### 5.2登录注册逻辑设计

#### 5.2.1后端

1. 规整目录结构

2. 实现数据库操作

   1. 模型user对象和数据表关联

   2. 测试能否操作数据库

      <img src="https://tyangjian.oss-cn-shanghai.aliyuncs.com/tmp/202305201907433.png" alt="image-20230520190659301" style="zoom:67%;" />

      **ps：**这个就是驼峰命名有问题，原因可能是生成的时候勾选是camel
      **解决方法：**在yml配置mybatis-plus

      ~~~yml
      mybatis-plus:
        configuration:
          map-underscore-to-camel-case: false
      ~~~

   3. **注册逻辑**
   
      1. 前端输入注册的账号密码以及检验码（redis生成）
      2. 检验用户的账户、密码、检验密码是否符合要求
         1. 账号不得小于4位
         2. 密码不小于6位
         3. 账号不能重复
         4. 账号不包含特殊字符
         5. 密码和校验密码相同
      3. 对密码进行加密（密码千万不要直接存储到数据库）
      4. 入库
   
   4. **登录逻辑**
   
      1. 检验用户账号和密码是否合法
         1. 非空
         2. 账户长度不小于4位
         3. 密码不小于6位
         4. 账户不包含特殊字符
   
      2. 检验密码是否正确，要和数据库密文密码去对比
   
         **ps：mybatis-plus会自己过滤掉逻辑删除的数据，在yml中配置**
   
         ~~~yml
         mybatis-plus:
           global-config:
             db-config:
               logic-delete-field: isDelete
               logic-delete-value: 1
               logic-not-delete-value: 0
         ~~~
   
         还需加上注解
   
         ~~~java
         @TableLogic
             private Integer isDelete;
         ~~~
   
      3. 返回用户数据（脱敏）
   
      4. 记录用户的登录态（session，用springboot自己封装的tomcat记录）**后期用AOP切片实现**
   
   5. **用户管理**（鉴权！！！）
   
      1. 用户查询
      2. 删除用户
   

#### 5.2.2前端

前端记录修改点

1. 注册控件的文本值（看LoginForm的源码）
2. Params修改
3. url白名单设置
4. login页面的复用
5. proxy代理解决跨域问题

ProComponents高级表单

1. 通过columns定义表格的列
2. columns属性
	- dataIndex：对应返回数据对象
	- titile：列名
	- copyable：是否允许复制
	- ellipsis：是否允许省略
	- valueType：这一列的类型

## 6、其他功能

### 6.1星球用户校验

**说明：**（用户可信情况，会存在编号被占用的情况）

- 让用户手填2-5位编号
- 后台对编号进行校验：长度/唯一性校验
- 前端补充输入框

**ps：后期拉取星球数据，定期清理违规**

1. 前端密码重复提示
2. 注册没有友好提示
3. 注销用户
4. 查询优化

### 6.2项目优化

1. 封装通用返回对象

	目的：给对象补充一些信息

	~~~json
	{
	    "code":"// 要个性化"
	    "data":{
		},
	    "msg":""
	}
	~~~

	**ps：统一相应没有序列化ID需要配置**

	<img src="https://tyangjian.oss-cn-shanghai.aliyuncs.com/tmp/202305251609631.png" alt="image-20230525160933024" style="zoom:67%;" />

	- 自定义错误码
	- 返回类支持返回正常和错误

2. 封装全局异常处理

	1. 定义全局异常处理

		1. 相较于java的异常类、支持更多字段
		2. 自定义构造函数，更加灵活（要用super的构造方法）/快捷的设置字段

	2. 编写全局异常处理器全局请求日志和登录校验

		1. 捕获代码中所有异常，让异常集中消耗，集中处理，返回更详细的错误
		2. 屏蔽掉项目框架本身的异常（不暴露服务器内部状态）
		3. 集中记录日志、集中处理

		实现：

		1. Spring AOP：让调用方法前进行额外处理

3. 全局请求日志和登录校验

### 6.3前端优化

1. 对接后端的返回值，取data
2. 全局响应处理：
	1. 应用场景，我们对接口的**通用响应**进行统一处理，比如从response中去除data；或者根据code去集中处理错误，比如用户未登录、请求错误等
	2. 优势：不用在每个接口请求中都去写相同的逻辑
	3. 实现：参考请求封装工具的官方文档，其他还有axios

## 7、代码评审

## 8、发布上线

### 8.1多环境

ps：多环境是：同一套代码在**不同阶段**根据需要根据实际情况来调整配置并且部署到不同机器上

why：

1. 每个环境互不影响
2. 区分不同阶段：开发/测试/生产
3. 对项目进行优化：
	1. 本地日志级别
	2. 精简依赖，节省项目体积
	3. 项目环境/参数可以调整，比如jvm参数

1. 本地开发：local（自己电脑）本地
2. 开发环境：dev（远程开发）同一机器，共同开发
3. 测试环境：test（测试）开发/测试/产品，单元测试/性能测试/功能测试/系统集成测试/独立数据库、服务器
4. 预发布环境：（体验服）：和正式环境一致，正式库环境，查出更多问题
5. 正式环境：（线上公开对外）：尽量不要动，保证代码能稳定运行
6. 沙箱环境：（实验环境）

### 8.2前端多环境

- 请求地址

	- 开发环境：localhost:8000
	- 线上环境：111.230.250.58:8088

	~~~ts
	startFront(env){
	    if(env === 'prod'){
	        // 不输出注释
	        // 项目优化
	        // 修改请求地址
		} else{
	        // 保持本地开发逻辑
	    }
	}
	~~~

	umi使用build时会自动传入env='production'

- 启动方式

	- 开发环境：npm run install（本地启动、监听端口、自动更新）
	- 线上环境：npm run build（项目构建打包）,可以用serve工具启动（npm i -g serve）

- 项目配置

	不同的项目（框架）都有不同的配置文件，umi的配置文件是config

	- 可以在配置文件后添加对应的环境名称后缀来区分开发环境和生产环境
	- 开发环境：config.dev.js
	- 生产环境：config.prod.js
	- 公共配置：config.js

### 8.3后端多环境

SpringBoot项目，application.yml不同后缀区分不同环境

可以在启动项目时传入环境变量

~~~bash
java -jar user-center-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
java -jar .\user-center-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
~~~

主要改

- 依赖的环境地址：
	- 数据库地址
	- 缓存地址
	- 消息队列地址
	- 项目端口号
- 服务器配置

### 8.4原始项目部署

需要Linux服务器（CentOS 8+ / 7.6+）

**原始部署**

从0开始

#### **8.4.1前端**

需要web服务器：**nginx**、apache、tomcat

安装nginx服务器：

1. 用系统自带的软件包管理器快速安装，比如centos的yum

2. 自己到官网找下载

```bash
# 下载
curl -o nginx-1.24.0.tar.gz http://nginx.org/download/nginx-1.24.0.tar.gz
# 解压
tar -zxvf nginx-1.24.0.tar.gz
# 检查缺包
./configure
# 安装库
yum install pcre pcre-devel -y
yum install openssl openssl-devel -y   (后面https要用)
# 开启https支持的系统参数
./configure --with-http_ssl_module --with-http_v2_module --with-stream
# 编译
make
# 安装
make install
# 查看编译后的sbin
ls /usr/local/nginx/sbin/nginx
# 配置环境变量
vim /etc/profile           					shift + 4 / shift + g到最后一行 按下o编辑 
export PATH=$PATH:/usr/local/nginx/sbin		esc + ：+ wq
source /etc/profile
nginx
netstat -ntlp查看端口启用情况  ps -ef|grep 'nginx' 查看进程
# 部署前端文件
mv * ../
unzip dist.zip -d user-center-front
# 部署完前端文件之后激活nginx配置（**/usr/local/nginx**）
vim ngix.conf 修改user为当前登录用户
nginx -s reload
```

#### **8.4.2后端**

- 部署

	~~~bash
	# 安装jdk（**/usr/lib/jvm**）
	yum install -y java-1.8.0-openjdk*
	# maven
	curl -o apache-maven-3.8.8-bin  https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz
	pwd获取路径
	# 配置环境变量，激活环境变量(略)
	# 服务器打jar包
	yum install -y git
	git clone xxxx
	mvn package -DskipTests (跳过测试直接打包)
	# 放置jar包后（有权限，若无权限：chod a+x user-center-backend-0.0.1-SNAPSHOT.jar）
	java -jar user-center-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
	java -jar ./user-center-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
	# 上述方式会随着窗口结束而结束
	nohup java -jar ./user-center-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &
	# 使用jobs Jps查看
	~~~

### 8.5宝塔Linux面板部署

**ps：注意放行端口**

### 8.6Docker部署

docker是容器，可以将项目的环境（比如java，nginx）和项目的代码一起打包成镜像，镜像更容易分发和移至。

再启动项目时，不需要敲太多命令，而是直接下载镜像，启动镜像就行。

docker可以理解为安装包



> Docker安装：官网安装https://www.docker.com/get-started/



Dockerfile用于指定构建Docker镜像的方法，一般不用从0写，建议直接去github和gitee找

Dockerfile编写：

- FROM依赖的基础镜像
- WORKDIR工作目录
- COPY从本机复制文件
- RUN执行命令
- CMD/ENTRYPOINT（可以附加额外参数）指定运行容器时默认的命令

根据Dockerfile构建镜像：

构建

~~~bash
# 后端
docker -build -t user-center-backend:v0.0.1 .

# 前端
docker -build -t user-center-frontend:v0.0.1 .
~~~

~~~bash
# 常用命令
docker images
~~~

docker run启动（本地端口:容器端口）

~~~bash
docker run -p 80:80 -v /data:/data -d nginx:latest
docker run -p 80:80 -v /data/app:/user/share/ngnix/html -d user-center-frontend:v0.0.1

docker run -p 80:80 -v /data/app:/app -d user-center-backend:v0.0.1
~~~

~~~bash
# 查看启动的容器
docker ps 
# 进入容器（exit）
docker exec -i -t id bin/bash
# 杀死容器
docker kill [id]
# 查看日志
docker logs [id] -f/-
# 删除镜像
docker rmi -f [name]
~~~

虚拟化

1. 端口映射：把本机的资源和容器内部的资源进行关联
2. 目录映射：把本机的端口和容器应用的端口进行关联

### 8.7容器平台

1. 云服务商的容器平台（腾讯云、阿里云）
2. 面向某个领域的容器平台（前端webify：要将代码放到托管平台、后端微信云托管）

容器平台的好处：

1. 不用输命令，更方便
2. 不用在控制台操作，更傻瓜式、更简单
3. 大厂运维比自己运维省心
4. 额外的能力，比如监控，告警，存储，负载均衡，自动扩容，流水线

### 8.8域名

**前端项目访问：**用户输入地址—>域名解析服务器（把网址解析为ip地址/交给其他域名解析服务cdn）—>服务器—>（防火墙）—>ngnix请求接收，找到对应文件，返回文件给前端

—>前端加载文件到浏览器中（js/css）—>渲染页面

**后端项目访问：**用户输入网址—>域名解析服务器—>服务器—>后端项目（8089）

### 8.9跨域问题

浏览器为了用户安全，仅允许向**同域名**、**同端口**的服务器发送请求

如果解决跨域

成功的解决方法：前后端使用同一个域名，nginx配置

~~~bash
location ^~ /api/{
    proxy_pass http://127.0.0.1:8101;
    proxy_set_header Host 127.0.0.1;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header REMOTE-HOST $remote_addr;
    add_header X-Cache $upstream_cache_status;
    #Set Nginx Cache
    set $static_fileo6zf1iSa 0;
    if ( $uri ~* "\.(gif|png|jpg|css|js|woff|woff2)$" ){
        set $static_fileo6zf1iSa 1;
        expires 12h;
    }
    if ( $static_fileo6zf1iSa = 0 ){
        add_header Cache-Control no-cache;
    }
}
~~~



1. 让服务器告诉浏览器：允许跨域（返回cross-origin-allow响应头）

2. 网关支持

	~~~bash
	location ^~ /api/ {
	    proxy_pass http://127.0.0.1:8089/api/;
	    add_header 'Access-Control-Allow-Origin' $http_origin;
	    add_header 'Access-Control-Allow-Credentials' 'true';
	    add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS';
	    add_header Access-Control-Allow-Headers '*';
	    if ($request_method = 'OPTIONS') {
	        add_header 'Access-Control-Allow-Credentials' 'true';
	        add_header 'Access-Control-Allow-Origin' $http_origin;
	        add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS';
	        add_header 'Access-Control-Allow-Headers' 'DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range';
	        add_header 'Access-Control-Max-Age' 1728000;
	        add_header 'Content-Type' 'text/plain; charset=utf-8';
	        add_header 'Content-Length' 0;
	        return 204;
	    }
	}
	~~~

3. 修改后端服务

	1.  @CrossOrign注解

	2. 添加web请求拦截器

		~~~java
		@Configuration
		public class WebMvcConfg implements WebMvcConfigurer {
		    @Override
		    public void addCorsMappings(CorsRegistry registry) {
		        //设置允许跨域的路径
		        registry.addMapping("/**")
		        //设置允许跨域请求的域名
		        //当Credentials为true时，Origin不能为星号，需为具体的ip地址【如果接口不带cookie,ip无需设成具体ip】
		        .allowedOrigins("http://localhost:8000","http://127.0.0.1:8000", "http://127.0.0.1:8089", "http://127.0.0.1:8088")
		        //是否允许证书 不再默认开启
		        .allowCredentials(true)
		        //设置允许的方法
		        .allowedMethods("*")
		        //跨域允许时间
		        .maxAge(3600);
		    }
		}
		~~~

4. 定义新的corsFilter Bean

**ps：idea插件（mybatisX1.5.5可能不适配2023版idea）**

<img src="https://tyangjian.oss-cn-shanghai.aliyuncs.com/tmp/202305211540851.png" alt="image-20230521154011231" style="zoom:67%;" />

## 9.项目扩充

1. 功能扩充
	1. 管理员创建用户、修改用户信息、删除用户
	2. 上传头像
	3. 按照更多条件去查询用户
	4. 更改权限
2. 修改bug
3. 项目登录改为分布式session（单点登录）
4. 通用性
	1. set-cookie domain域名更通用，比如*.xxx.com
	2. 把用户管理系统=>用户中心（之后所有的服务都请求后端）
5. 后台添加全局请求拦截器（统一去判断用户权限、统一记录请求日志）
