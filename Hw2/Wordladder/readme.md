# Hw2
## Set up
该工程没有前端（用postman代替），因此仅需要启动后端即可

``` bash
cd Wordladder
mvn spring-boot:run
```
看见如下界面则启动成功：

![avatar](./READMEimgs/start.png)

## Project Architecture
> Updated Actuator and Security

![avatar](./READMEimgs/architecture.png)

### Dictionary文件夹
存放wordladder所用的dictionary.dictionary.txt为大的dictionary而smallladder.txt为用于debug的小dictionary.
### Controller
* Wordladder.java
实现Wordladder的内部逻辑。
* WordladderApplication
启动controller
* LadderController.java
用于实现WordladderControler的内容：获取html信息，调用Wordladder类生成wordladder。
### Configuration
* WebSecurityConfig
实现Spring Security的权限设置。
### Test
三个文件对应Controller的三个文件分别进行测试。
一个文件为Actuator的测试文件，对Actuator的各个endpoint分别进行了测试。

## Function
> The demostrations below are without Spring Security

利用postman发送post请求和get请求，得到的结果如图：

POST请求:

![avatar](./READMEimgs/post.png)

GET请求：

![avatar](./READMEimgs/get.png)

## Actuator/Security
加入Security和Actuator，并进行了权限设置。其中，Actuator没有权限要求("/actuator/**")，而wordladder的controller("/scanning")则要求登陆。用户名为"user"，密码为"password"。信息存在内存里。

## Unit Test
测试内容见上文Test部分
运行结果如下：
![avatar](./READMEimgs/unittest.png)
其中，WordladderTests测试后台逻辑的运行是否正确，而LadderControllerTests则利用了MockMVC来模拟post请求，对Controller进行测试。

## Git Flow
开发分支位于develop，已经通过一次pull request将一次修改提交至master

