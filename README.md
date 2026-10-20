# 橘子云图库（juzi_yunpicture）

一个面向个人与团队的照片 / 素材管理平台。支持图片上传与导入、AI 扩图、以图搜图与颜色搜索、空间（私有 / 团队）权限协作、多人实时协同编辑，以及空间维度的数据统计。

后端提供两套实现：**MVC 分层版**（`juzi_yunpicture_backend`）与 **DDD 分层重构版**（`juzi_yunpicture_backend_ddd`），二者对外暴露同一套 REST API。

---

## 功能特性

- **用户体系**：注册 / 登录 / 登出，Sa-Token + Redis Session 维持 30 天登录态，管理员与普通用户权限隔离
- **图片管理**：文件上传、URL 导入、批量创建、编辑、审核、删除、分页查询（含带缓存的 `list/page/vo/cache`）
- **图片检索**：以图搜图、按颜色搜索、按标签 / 分类筛选、关键词模糊搜索
- **空间协作**：私有空间与团队空间，成员角色（查看 / 编辑 / 上传 / 删除 / 管理）与细粒度权限校验
- **实时协同**：WebSocket 推送编辑动作，Disruptor 无锁队列承接图片编辑事件，支持多人同时编辑同一张图
- **AI 能力**：阿里云 AI 扩图（out-painting）异步任务
- **空间分析**：用量概览、分类统计、标签词云、文件大小分布、成员上传排行（ECharts 可视化）
- **数据分片**：按 `spaceId` 对 `picture` 表分库分表（ShardingSphere 5.2.0 + 自定义分片算法）
- **接口文档**：Knife4j / OpenAPI

---

## 技术栈

**后端**

| 类别 | 选型 |
|---|---|
| 语言 / 框架 | Java 8、Spring Boot 2.7.6 |
| 持久层 | MyBatis-Plus 3.5.9、MySQL 8、ShardingSphere 5.2.0 |
| 缓存 / 会话 | Redis、Spring Session、Caffeine |
| 认证授权 | Sa-Token 1.39.0（Redis Jackson 序列化） |
| 实时通信 | Spring WebSocket、LMAX Disruptor 3.4.2 |
| 第三方服务 | 腾讯云 COS、阿里云 AI、jsoup |
| 工具 / 文档 | Hutool 5.8、Lombok、Knife4j 4.4 |

**前端**

| 类别 | 选型 |
|---|---|
| 框架 | Vue 3.5、TypeScript、Vite 8 |
| UI / 状态 | Ant Design Vue 4.2、Pinia |
| 路由 / 请求 | Vue Router、Axios |
| 可视化 | ECharts 5.6、echarts-wordcloud |
| 图片处理 | vue-cropper、vue3-colorpicker |
| 工程化 | ESLint + oxlint + Prettier、vue-tsc、@umijs/openapi（接口代码生成） |

---

## 目录结构

```
juzi_yunpicture/
├── juzi_yunpicture_backend/              # 后端 MVC 分层版
│   ├── sql/create_table.sql              # 建库建表脚本
│   └── src/main/java/com/yupi/yun_picture/
│       ├── controller/                   # 接口层
│       ├── service/                      # 业务逻辑
│       ├── manager/                      # 通用能力：鉴权、上传、WebSocket、分片
│       ├── mapper/                       # MyBatis-Plus 数据访问
│       ├── model/                        # entity / dto / vo / enums
│       ├── api/                          # 外部服务封装（阿里云 AI、以图搜图）
│       └── config/ aop/ exception/ utils/
├── juzi_yunpicture_backend_ddd/          # 后端 DDD 分层重构版
│   └── src/main/java/com/yupi/yunpicture/
│       ├── interfaces/                   # 接口层：controller / dto / vo / assembler
│       ├── application/                  # 应用层：用例编排
│       ├── domain/                       # 领域层：entity / valueobject / repository / service
│       ├── infrastructure/               # 基础设施：mapper / api / manager / config
│       └── shared/                       # 共享：auth / sharding / websocket
└── juzi_yunpicture_frontend/             # 前端 Vue 3
    └── src/
        ├── api/                          # OpenAPI 生成的接口客户端
        ├── pages/                        # 页面：首页、图片、空间、管理后台
        ├── components/ layouts/           # 通用组件与布局
        ├── router/ stores/ utils/         # 路由、Pinia 状态、工具
        └── request.ts                     # Axios 实例与拦截器
```

---

## 快速开始

### 环境要求

- JDK 8、Maven 3.6+
- MySQL 8.x、Redis 6.x+
- Node.js `^22.18.0 || >=24.12.0`

### 1. 初始化数据库

```sql
CREATE DATABASE IF NOT EXISTS juzi DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

```bash
mysql -u root -p juzi < juzi_yunpicture_backend/sql/create_table.sql
```

### 2. 配置

后端配置位于 `juzi_yunpicture_backend/src/main/resources/`：

- `application.yml` —— 通用配置（端口 8081、`context-path: /api`、MyBatis-Plus、ShardingSphere）
- `application-prod.yml` —— 生产配置模板（需自行填写数据库、Redis、腾讯云 COS、阿里云 AI 的密钥）

> ⚠️ 含真实密钥的配置文件已被 `.gitignore` 排除，**请勿提交**。本地调试建议复制一份 `application-local.yml` 并加入忽略列表。

### 3. 启动后端

```bash
cd juzi_yunpicture_backend
mvn spring-boot:run
```

- 服务地址：`http://localhost:8081/api`
- 接口文档：`http://localhost:8081/api/doc.html`
- 健康检查：`GET http://localhost:8081/api/health`

DDD 版启动方式相同，进入 `juzi_yunpicture_backend_ddd` 执行即可。

### 4. 启动前端

```bash
cd juzi_yunpicture_frontend
npm install
npm run dev
```

默认访问 `http://localhost:5173`。

> 本地开发前请把 `src/request.ts` 与 `src/utils/pictureEditWebSocket.ts` 里的 `baseURL` 改回 `http://localhost:8081`（仓库默认值指向生产地址）。

### 5. 构建部署

```bash
# 后端：产出可执行 jar
mvn clean package -DskipTests
java -jar target/juzi_yunpicture_backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

# 前端：类型检查 + 构建（仅打包可用 npm run pure-build）
npm run build
```

---

## 数据库表

| 表名 | 说明 |
|---|---|
| `user` | 用户：账号、密码、昵称、头像、简介、角色 |
| `picture` | 图片：URL、名称、简介、分类、标签、尺寸、大小、审核状态 |
| `space` | 空间：名称、级别（普通 / 专业 / 旗舰）、类型（私有 / 团队）、额度 |
| `space_user` | 空间成员关联：空间 ID、用户 ID、空间角色 |

`picture` 表按 `spaceId` 分片，分片算法见 `PictureShardingAlgorithm`。

---

## 接口概览

| 前缀 | 说明 |
|---|---|
| `/user` | 注册、登录、登出、用户增删改查（管理员） |
| `/picture` | 上传（文件 / URL / 批量）、编辑、审核、检索、扩图任务 |
| `/space` | 空间增删改查、空间级别列表 |
| `/spaceUser` | 空间成员管理 |
| `/space/analyze` | 空间用量、分类、标签、大小、成员排行统计 |
| `/file` | 文件上传 / 下载测试接口 |
| `/health` | 健康检查 |

---

## 安全说明

本仓库为公开仓库，以下内容均已被 `.gitignore` 排除在版本控制之外：

- `application-prod.yml`、`application-local.yml`（数据库 / Redis / 腾讯云 COS / 阿里云 AI 密钥）
- `target/` 构建产物、`.idea/` IDE 配置、`HELP.md`

生产环境建议通过环境变量或配置中心注入密钥，不要写进仓库文件。

---

## 说明

项目结构参考自开源教学项目「鱼皮云图库」，在其基础上完成了业务功能扩展、DDD 分层重构与前端工程化改造，仅供学习交流使用。