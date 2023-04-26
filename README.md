# easymall-microservice

easymall 商城是一个基于微服务架构的电商平台练手项目，使用了多个开源技术和框架，包括 SpringCloud、Eureka、SpringCloudGateway、Feign、SpringCloudConfig、MybatisPlus、RabbitMQ、Shardingsphere-Proxy、Redis、MySQL、Nginx 等。



## 功能特性

- 提供用户注册、登录、商品浏览、购物车、订单管理等电商基本功能。
- 实现了基于微服务架构的高可扩展性和高可维护性，便于拓展和升级。
- 使用了分布式缓存 Redis 提升系统性能和响应速度。
- 使用了 RabbitMQ 实现异步消息处理和解耦。
- 使用了 Spring Cloud Config 实现统一的配置管理和版本控制。
- 使用了 Shardingsphere-Proxy 进行数据库分库分表，提升数据库性能和扩展性。
- 使用了 MybatisPlus 简化数据库操作，提高开发效率。
- 使用了 Nginx 进行反向代理和负载均衡，提高系统的稳定性和可靠性。



## 项目结构

```text
easymall-microservice
├── easymall-common-repository       通用的持久层模块，包含持久层框架的配置信息
├── easymall-common-resource	     通用的资源模块，包含通用工具类、pojo、vo
├── easymall-config-client			 配置中心客户端模块，用于模拟测试从配置中心获取配置信息
├── easymall-config-server			 配置中心服务端模块，用于集中管理项目的配置信息
├── easymall-feign-api				 基于Feign的API模块，用于定义微服务之间的通信接口
├── easymall-gateway				 基于SpringCloudGateway的网关模块，用于处理微服务之间的请求和路由
├── easymall-microservice-cart		 购物车微服务模块，处理用户购物车相关的业务逻辑
├── easymall-microservice-order      订单微服务模块，处理订单相关的业务逻辑
├── easymall-microservice-pic		 图片微服务模块，处理图片相关的业务逻辑
├── easymall-microservice-product	 商品微服务模块，处理商品相关的业务逻辑
├── easymall-microservice-seckill  	 秒杀微服务模块，处理商品秒杀相关的业务逻辑
├── easymall-microservice-user		 用户微服务模块，处理用户相关的业务逻辑
└── eureka-server					 服务注册与发现模块，用于管理微服务的注册和发现

```





## 技术栈

- Spring Cloud：基于 Spring Boot 的微服务框架，提供了丰富的微服务组件，如 Eureka、Gateway、Feign、Config 等，用于构建分布式系统。
- Eureka：服务注册与发现中心，用于实现微服务的注册、发现和负载均衡。
- Spring Cloud Gateway：API 网关，用于对外提供统一的接口入口、鉴权和路由。
- Feign：声明式的 HTTP 客户端，用于实现微服务之间的调用。
- Spring Cloud Config：配置中心，用于集中管理和版本控制微服务的配置信息。
- RabbitMQ：开源的消息中间件，用于实现异步消息处理和解耦。
- Redis：开源的内存缓存数据库，用于提升系统性能和响应速度。
- Shardingsphere-Proxy：数据库分库分表中间件，用于实现数据库的水平拓展和性能优化。
- MybatisPlus：基于 Mybatis 的增强工具，简化数据库操作，提高开发效率。
- MySQL：关系型数据库，用于存储商城的业务数据。
- Nginx：高性能的反向代理服务器和负载均衡器，用于提高系统的稳定性和可靠性。







## 相关配置

### Nginx

nginx.conf

```json
    server{
        listen 80;
        # server_name localhost;
        server_name easymall.com;
        location /{
            root html/easymall-static;
            index index.html;
        }

        location /products{
            proxy_pass http://192.168.110.1:9005/gateway-product/product/manage;
			add_header 'Access-Control-Allow-Credentials' 'true';
			add_header 'Access-Control-Allow-Origin' '*'; 
        }
        location /user{
            proxy_pass http://192.168.110.1:9005/gateway-user/user/manage;
            add_header 'Access-Control-Allow-Credentials' 'true';
            add_header 'Access-Control-Allow-Origin' '*'; 
        }
        location /cart{
            proxy_pass http://192.168.110.1:9005/gateway-cart/cart/manage;
            add_header 'Access-Control-Allow-Credentials' 'true';
            add_header 'Access-Control-Allow-Origin' '*'; 
        }
        location /order{
            proxy_pass http://192.168.110.1:9005/gateway-order/order/manage;
            add_header 'Access-Control-Allow-Credentials' 'true';
            add_header 'Access-Control-Allow-Origin' '*'; 
        }
        location /seckills{
            proxy_pass http://192.168.110.1:9005/gateway-seckill/seckill/manage;
            add_header 'Access-Control-Allow-Credentials' 'true';
            add_header 'Access-Control-Allow-Origin' '*'; 
        }
        location /uploadImg{
            proxy_pass http://192.168.110.1:9005/gateway-pic/pic/upload;
            add_header 'Access-Control-Allow-Credentials' 'true';
			add_header 'Access-Control-Allow-Origin' '*'; 
        }

    }

    server{
        listen 80;
        server_name image.easymall.com;
        location /{
            root html/easymall-image;
        }
    }
```



### ShardingSphere-Proxy

config-sharding.yaml

```yaml
schemaName: easydb
dataSources:
  ds_1:
    url: jdbc:mysql://192.168.110.1:3306/easydb?serverTimezone=UTC&useSSL=false
    username: root
    password: abc123
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50
    minPoolSize: 1
  ds_0:
    url: jdbc:mysql://172.19.229.202:3306/easydb?serverTimezone=UTC&useSSL=false
    username: root
    password: abc123
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50
    minPoolSize: 1

rules:
  - !SHARDING
    tables:
      t_order:
        actualDataNodes: ds_${0..1}.t_order
        databaseStrategy:
          standard:
            shardingColumn: order_id
            shardingAlgorithmName: alg_hash_mod
        keyGenerateStrategy:
          column: order_id
          keyGeneratorName: snowflake
      t_order_item:
        actualDataNodes: ds_${0..1}.t_order_item
        databaseStrategy:
          standard:
            shardingColumn: order_id
            shardingAlgorithmName: alg_hash_mod
        keyGenerateStrategy:
          column: id
          keyGeneratorName: snowflake

    bindingTables:
      - t_order,t_order_item

    shardingAlgorithms:
      alg_mod:
        type: MOD
        props:
          sharding-count: 2
      alg_hash_mod:
        type: HASH_MOD
        props:
          sharding-count: 2

    keyGenerators:
      snowflake:
        type: SNOWFLAKE
```



### MySQL

```sql
DROP DATABASE IF EXISTS `easydb`;
CREATE DATABASE `easydb`;
USE `easydb`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `product_id` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `product_image` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `product_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `product_price` double NULL DEFAULT NULL,
  `num` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `order_id` char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '',
  `order_money` double NULL DEFAULT 0,
  `order_receiverinfo` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '',
  `order_paystate` int NULL DEFAULT 0,
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` char(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `product_id` char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `num` int NULL DEFAULT 0,
  `product_image` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `product_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `product_price` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 857638566681903107 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`  (
  `product_id` char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '',
  `product_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `product_price` double NULL DEFAULT 0,
  `product_category` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '',
  `product_imgurl` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '',
  `product_num` int NULL DEFAULT 0,
  `product_description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '',
  PRIMARY KEY (`product_id`) USING BTREE,
  UNIQUE INDEX `UN_product_name`(`product_name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` char(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户id uuid 主键',
  `user_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户登陆名称',
  `user_password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '\"\"' COMMENT '用户密码 md5',
  `user_nickname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '上帝' COMMENT '用户昵称',
  `user_email` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '\"\"' COMMENT '用户邮箱',
  `user_type` int NULL DEFAULT 0 COMMENT '用户状态 0(普通用户),1(管理员),2(超级管理员)',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `UN_user_name`(`user_name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;



DROP DATABASE IF EXISTS `seckill`;
CREATE DATABASE `seckill`
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill`  (
  `seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品名称',
  `number` int NOT NULL COMMENT '库存数量',
  `initial_price` bigint NOT NULL COMMENT '原价',
  `seckill_price` bigint NOT NULL COMMENT '秒杀价',
  `sell_point` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '卖点',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀创建时间',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开始时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
  PRIMARY KEY (`seckill_id`) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  INDEX `idx_start_time`(`start_time` ASC) USING BTREE,
  INDEX `idx_end_time`(`end_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '秒杀库存表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for success
-- ----------------------------
DROP TABLE IF EXISTS `success`;
CREATE TABLE `success`  (
  `success_id` bigint NOT NULL AUTO_INCREMENT COMMENT '秒杀成功id',
  `seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
  `user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户手机号',
  `state` tinyint NOT NULL DEFAULT -1 COMMENT '状态标志：-1：无效；0：成功；1：已付款；2：已发货',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '秒杀成功创建时间',
  PRIMARY KEY (`success_id`) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6424 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '秒杀成功明细表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

```



