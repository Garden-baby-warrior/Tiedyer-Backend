
# Tiedyer

- [用户相关](#用户相关)
  - [POST 根据refreshToken刷新accessToken](#post-根据refreshtoken刷新accesstoken)
    - [Params](#params)
    - [Responses](#responses)
    - [Responses Data Schema](#responses-data-schema)
  - [GET 获取用户信息](#get-获取用户信息)
    - [Params](#params-1)
    - [Responses](#responses-1)
    - [Responses Data Schema](#responses-data-schema-1)
  - [PUT 更新用户信息](#put-更新用户信息)
    - [Params](#params-2)
    - [Responses](#responses-2)
    - [Responses Data Schema](#responses-data-schema-2)
  - [POST 用户登录](#post-用户登录)
    - [Params](#params-3)
    - [Responses](#responses-3)
    - [Responses Data Schema](#responses-data-schema-3)
  - [POST 用户退出登录](#post-用户退出登录)
    - [Params](#params-4)
    - [Responses](#responses-4)
    - [Responses Data Schema](#responses-data-schema-4)
  - [POST 新增用户收货地址](#post-新增用户收货地址)
    - [Params](#params-5)
    - [Responses](#responses-5)
    - [Responses Data Schema](#responses-data-schema-5)
  - [GET 获取用户收货地址](#get-获取用户收货地址)
    - [Params](#params-6)
    - [Responses](#responses-6)
    - [Responses Data Schema](#responses-data-schema-6)
  - [PUT 更新用户收货地址](#put-更新用户收货地址)
    - [Params](#params-7)
      - [Description](#description)
    - [Responses](#responses-7)
    - [Responses Data Schema](#responses-data-schema-7)
  - [DELETE 删除用户收货地址](#delete-删除用户收货地址)
    - [Params](#params-8)
    - [Responses](#responses-8)
    - [Responses Data Schema](#responses-data-schema-8)
  - [POST 更新用户头像](#post-更新用户头像)
    - [Params](#params-9)
    - [Responses](#responses-9)
    - [Responses Data Schema](#responses-data-schema-9)
- [答题游戏](#答题游戏)
  - [GET 获取每日问题](#get-获取每日问题)
    - [Params](#params-10)
    - [Responses](#responses-10)
    - [Responses Data Schema](#responses-data-schema-10)
  - [POST 用户答案验证](#post-用户答案验证)
    - [Params](#params-11)
    - [Responses](#responses-11)
    - [Responses Data Schema](#responses-data-schema-11)
- [商品模块](#商品模块)
  - [GET 获取推荐列表](#get-获取推荐列表)
    - [Params](#params-12)
      - [Description](#description-1)
    - [Responses](#responses-12)
    - [Responses Data Schema](#responses-data-schema-12)
  - [GET 根据分类获取对应列表](#get-根据分类获取对应列表)
    - [Params](#params-13)
      - [Description](#description-2)
    - [Responses](#responses-13)
    - [Responses Data Schema](#responses-data-schema-13)
  - [GET 获取商品详情](#get-获取商品详情)
    - [Params](#params-14)
    - [Responses](#responses-14)
    - [Responses Data Schema](#responses-data-schema-14)
  - [GET 商品搜索](#get-商品搜索)
    - [Params](#params-15)
      - [Description](#description-3)
    - [Responses](#responses-15)
    - [Responses Data Schema](#responses-data-schema-15)
- [购物车](#购物车)
  - [GET 获取购物车信息](#get-获取购物车信息)
    - [Params](#params-16)
    - [Responses](#responses-16)
    - [Responses Data Schema](#responses-data-schema-16)
  - [POST 将商品保存进购物车](#post-将商品保存进购物车)
    - [Params](#params-17)
    - [Responses](#responses-17)
    - [Responses Data Schema](#responses-data-schema-17)
  - [PUT 更新预选商品的的数量](#put-更新预选商品的的数量)
    - [Params](#params-18)
    - [Responses](#responses-18)
    - [Responses Data Schema](#responses-data-schema-18)
  - [DELETE 删除购物车中的商品](#delete-删除购物车中的商品)
    - [Params](#params-19)
    - [Responses](#responses-19)
    - [Responses Data Schema](#responses-data-schema-19)
  - [POST 购物车创建订单](#post-购物车创建订单)
    - [Params](#params-20)
    - [Responses](#responses-20)
    - [Responses Data Schema](#responses-data-schema-20)
- [订单模块](#订单模块)
  - [POST 创建单个订单](#post-创建单个订单)
    - [Params](#params-21)
    - [Responses](#responses-21)
    - [Responses Data Schema](#responses-data-schema-21)
  - [GET 获取用户订单列表](#get-获取用户订单列表)
    - [Params](#params-22)
    - [Responses](#responses-22)
    - [Responses Data Schema](#responses-data-schema-22)
  - [POST 订单取消接口](#post-订单取消接口)
    - [Params](#params-23)
    - [Responses](#responses-23)
    - [Responses Data Schema](#responses-data-schema-23)
- [徽章模块](#徽章模块)
  - [GET 获取用户的徽章兑换情况](#get-获取用户的徽章兑换情况)
    - [Params](#params-24)
    - [Responses](#responses-24)
    - [Responses Data Schema](#responses-data-schema-24)
  - [POST 兑换徽章](#post-兑换徽章)
    - [Params](#params-25)
    - [Responses](#responses-25)
    - [Responses Data Schema](#responses-data-schema-25)
- [支付模块](#支付模块)
  - [POST 支付订单](#post-支付订单)
    - [Params](#params-26)
    - [Responses](#responses-26)
    - [Responses Data Schema](#responses-data-schema-26)
- [物流模块](#物流模块)
  - [GET 物流查询](#get-物流查询)
    - [Params](#params-27)
    - [Responses](#responses-27)
    - [Responses Data Schema](#responses-data-schema-27)




# 用户相关

## POST 根据refreshToken刷新accessToken

POST /auth/token/refresh

通过refreshToken获取新的accessToken
当使用accessToken访问接口失败时，或者再次登录时，使用此接口获取新的accessToken
accessToken有效期：2小时(不会刷新有效期，2小时后固定失效)，refreshToken有效期：60天(距上一次调用本接口，也就是说，在成功调用本接口时，refreshToken有效期将自动刷新)
在请求头中需要Authorization的地方都是accessToken，refreshToken仅用于本接口

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|refreshToken|query|string| yes |none|

> Response Examples

> 200 Response

```json
{
  "status": "string",
  "message": "string",
  "data": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none||none|
|» message|string|true|none||none|
|» data|string|true|none||none|

## GET 获取用户信息

GET /user/info

### Params

|Name|Location|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string| yes |accessToken|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "id": "1717583679276060672",
    "nickName": "测试",
    "avatarPath": "测试",
    "points": 0
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» id|string|true|none|用户Id|none|
|»» nickName|string|true|none|昵称|none|
|»» avatarPath|string|true|none|头像|none|
|»» points|integer|true|none|游戏积分|none|

## PUT 更新用户信息

PUT /user/info

> Body Parameters

```json
{
  "userId": "1717583679276060672",
  "nickName": "Test"
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|
|body|body|object| no ||none|
|» userId|body|string| yes | 用户id|none|
|» nickName|body|string| yes | 用户昵称|none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": null
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|null|true|none|数据|none|

## POST 用户登录

POST /auth/wx-login

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|js_code|query|string| yes ||wx.login获取的code|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "userId": "1717583679276060672",
    "role": "user",
    "newUser": false,
    "refreshToken": "Tr_XzhlanYoFKySh_TlwrSuOpYid3Bk_",
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNzE3NTgzNjc5Mjc2MDYwNjcyIiwicm9sZSI6InVzZXIiLCJpYXQiOjE2OTg3NTY5NTMsImV4cCI6MTY5ODc2NDE1M30.fZtOc7sDwvzFMGuXMdMyM3EVPsDKy7pZr10tnUWXopY"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none||none|
|» message|string|true|none||none|
|» data|object|true|none||none|
|»» userId|string|true|none|用户id|none|
|»» role|string|true|none|用户角色|none|
|»» newUser|boolean|true|none|是否是新用户|如果为true，需要调用更新用户信息接口，更新用户信息|
|»» refreshToken|string|true|none|refreshToken|用于获取accessToken|
|»» accessToken|string|true|none|accessToken|用于访问需要验证的资源，也就是请求头Authorization对应的值|

## POST 用户退出登录

POST /auth/logout

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| no ||none|

> Response Examples

> 200 Response

```json
{
  "status": "string",
  "message": "string",
  "data": "string"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none||none|
|» message|string|true|none||none|
|» data|string|true|none||none|

## POST 新增用户收货地址

POST /shipping-address/save

> Body Parameters

```json
{
  "contactName": "张三",
  "contactPhone": "18--secret--789",
  "area": [
    "福建省",
    "福州市",
    "闽侯县"
  ],
  "address": "福州大学",
  "defaultAddress": true
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|
|body|body|object| no ||none|
|» contactName|body|string| yes | 联系人|none|
|» contactPhone|body|string| yes | 联系电话|none|
|» area|body|[string]| yes | 地区|none|
|» address|body|string| yes | 具体地址|none|
|» defaultAddress|body|boolean| yes | 是否为默认地址|如果为首次添加收货地址，无论为true还是flase，都将自动设置成默认地址|

> Response Examples

> 200 Response

```json
{
  "status": "string",
  "message": "string",
  "data": null
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|null|true|none|数据|none|

## GET 获取用户收货地址

GET /shipping-address/list

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": [
    {
      "addressId": "1722185131575349250",
      "userId": "1717583679276060672",
      "contactName": "张三",
      "contactPhone": "18--secret--789",
      "area": [
        "福建省",
        "福州市",
        "闽侯县"
      ],
      "address": "福州大学",
      "priority": 1
    },
    {
      "addressId": "1722183574934933505",
      "userId": "1717583679276060672",
      "contactName": "张三",
      "contactPhone": "18--secret--789",
      "area": [
        "广东省",
        "广州市",
        "越秀区"
      ],
      "address": "海珠广场",
      "priority": 2
    },
    {
      "addressId": "1722184110044237826",
      "userId": "1717583679276060672",
      "contactName": "张三",
      "contactPhone": "18--secret--789",
      "area": [
        "福建省",
        "龙岩市",
        "新罗区"
      ],
      "address": "市政府",
      "priority": 3
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|[object]|true|none|数据|none|
|»» addressId|string|true|none|收货地址Id|none|
|»» userId|string|true|none|用户Id|none|
|»» contactName|string|true|none|联系人|none|
|»» contactPhone|string|true|none|联系电话|none|
|»» area|[string]|true|none|地区|none|
|»» address|string|true|none|具体地址|none|
|»» priority|integer|true|none|优先级|请根据优先级对收货地址进行升序排序，同时优先级为1的为默认收货地址|

## PUT 更新用户收货地址

PUT /shipping-address

> Body Parameters

```json
{
  "addressId": "1722185131575349250",
  "contactName": "张三",
  "contactPhone": "18--secret--789",
  "area": [
    "福建省",
    "福州市",
    "闽侯县"
  ],
  "address": "学园路2号 福州大学",
  "defaultAddress": true
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|
|body|body|object| no ||none|
|» addressId|body|string| yes | 收货地址ID|none|
|» contactName|body|string| yes | 联系人|none|
|» contactPhone|body|string| yes | 联系电话|none|
|» area|body|[string]| yes | 地区|none|
|» address|body|string| yes | 具体地址|none|
|» defaultAddress|body|boolean| yes | 是否为默认地址|注意：更新操作中该选项为单项操作|

#### Description

**» defaultAddress**: 注意：更新操作中该选项为单项操作
即：原本false可以改成true，但是原本true不能改成false，否则将报"不允许将默认地址设置成非默认地址" 错误

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": null
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|null|true|none|数据|none|

## DELETE 删除用户收货地址

DELETE /shipping-address/{addressId}

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|addressId|path|string| yes ||收货地址Id|
|Authorization|header|string| yes ||none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": null
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|null|true|none|数据|none|

## POST 更新用户头像

POST /user/upload/avatar

> Body Parameters

```yaml
avatar: []

```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|
|body|body|object| no ||none|
|» avatar|body|string(binary)| yes ||头像文件|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": "/avatar/2023/11/27/cfa38847f0404ef8a31b91c4534132d8.png"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|string|true|none|头像路径|none|

# 答题游戏

## GET 获取每日问题

GET /question/daily

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||accessToken|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": [
    {
      "id": "1720069199092649988",
      "title": "哪种天然染料通常用于蓝色扎染？",
      "image": "/question/bd387353c63a44e2aac36c3f70b340f7.jpg",
      "options": {
        "A": "胭脂红",
        "B": "蓝莓汁",
        "C": "靛蓝",
        "D": "茶叶"
      }
    },
    {
      "id": "1729705354314518528",
      "title": "扎染中常见的染料“蓝靛”是由什么植物提取得到的？",
      "image": "/question/7d1dabf25c434918ab2adacb3ace9ece.jpg",
      "options": {
        "A": "蓝莓",
        "B": "蓼草",
        "C": "靛蓝树",
        "D": "蓝藻"
      }
    },
    {
      "id": "1720069199092649992",
      "title": "扎染与普通织物染色的主要不同之处是什么？",
      "image": "/question/24c40ed190b5423197946fb11e4e4fee.jpg",
      "options": {
        "A": "扎染需要更长时间来完成",
        "B": "扎染只能用于棉织物",
        "C": "扎染在染色前需要折叠或绑缚织物",
        "D": "扎染使用更多染料"
      }
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|[object]|true|none|问题集合|none|
|»» id|string|true|none|问题id|none|
|»» title|string|true|none|题目|none|
|»» image|string|true|none|图片|none|
|»» options|object|true|none|选项|none|
|»»» A|string|true|none|选项A|none|
|»»» B|string|true|none|选项B|none|
|»»» C|string|true|none|选项C|none|
|»»» D|string|true|none|选项D|none|

## POST 用户答案验证

POST /question/daily/verify

> Body Parameters

```json
{
  "questionId": "1720069199092649992",
  "answer": "C"
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| no ||none|
|body|body|object| no ||none|
|» questionId|body|string| yes | 问题Id|none|
|» answer|body|string| yes | 答案|只能是A,B,C,D|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "answer": "D",
    "analysis": "扎染在染色前需要折叠或绑缚织物，以创造特殊图案效果。这是与普通织物染色的主要不同之处，后者通常是将整个织物浸泡在染料中。"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» answer|string|true|none|正确答案|none|
|»» analysis|string|true|none|解析|none|

# 商品模块

## GET 获取推荐列表

GET /commodity/base/list/recommend

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|timestamp|query|string| no ||限制时间戳，默认为当前时间戳|
|pageSize|query|integer| no ||限制查询个数，默认为5，最小为1，最大为10|
|Authorization|header|string| yes ||none|

#### Description

**timestamp**: 限制时间戳，默认为当前时间戳
第一次请求无须带上该参数，后续请求需要带上 上次响应结果中的timestamp

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "resultList": [
      {
        "spuId": "1722531137928171520",
        "categoryId": 1,
        "title": "扎染吊带连衣裙",
        "image": " ",
        "basePrice": 172,
        "sales": 213,
        "productionPlace": "广东东莞"
      },
      {
        "spuId": "1722531130835603456",
        "categoryId": 2,
        "title": "云南大理扎染礼盒",
        "image": " ",
        "basePrice": 142,
        "sales": 45,
        "productionPlace": "云南大理"
      },
      {
        "spuId": "1722531126494498816",
        "categoryId": 2,
        "title": "手工蓝染扎染布艺手链",
        "image": " ",
        "basePrice": 32,
        "sales": 34,
        "productionPlace": "浙江金华"
      }
    ],
    "timestamp": "1699518503000"
  }
}
```

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "resultList": [],
    "timestamp": null
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» resultList|[object]|true|none|查询结果|none|
|»»» spuId|string|true|none|产品id|none|
|»»» categoryId|integer|true|none|分类id|none|
|»»» title|string|true|none|产品名|none|
|»»» image|string|true|none|产品图片|none|
|»»» basePrice|integer|true|none|起售价|none|
|»»» sales|integer|true|none|销量|none|
|»»» productionPlace|string|true|none|产地|none|
|»» timestamp|string|true|none|限制时间戳|下次请求需要带上此时间戳|

## GET 根据分类获取对应列表

GET /commodity/base/list/{categoryId}

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|categoryId|path|string| yes ||1为衣服，2为其他|
|timestamp|query|string| no ||限制时间戳，默认为当前时间戳|
|pageSize|query|integer| no ||限制查询个数，默认为5，最小为1，最大为10|
|Authorization|header|string| yes ||none|

#### Description

**timestamp**: 限制时间戳，默认为当前时间戳
第一次请求无须带上该参数，后续请求需要带上 上次响应结果中的timestamp

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "resultList": [
      {
        "spuId": "1722531130835603456",
        "categoryId": 2,
        "title": "云南大理扎染礼盒",
        "image": " ",
        "basePrice": 142,
        "sales": 45,
        "productionPlace": "云南大理"
      },
      {
        "spuId": "1722531126494498816",
        "categoryId": 2,
        "title": "手工蓝染扎染布艺手链",
        "image": " ",
        "basePrice": 32,
        "sales": 34,
        "productionPlace": "浙江金华"
      }
    ],
    "timestamp": "1699518503000"
  }
}
```

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "resultList": [],
    "timestamp": null
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» resultList|[object]|true|none|查询结果|none|
|»»» spuId|string|true|none|产品id|none|
|»»» categoryId|integer|true|none|分类id|none|
|»»» title|string|true|none|产品名|none|
|»»» image|string|true|none|产品图片|none|
|»»» basePrice|integer|true|none|起售价|none|
|»»» sales|integer|true|none|销量|none|
|»»» productionPlace|string|true|none|产地|none|
|»» timestamp|string¦null|true|none|限制时间戳|none|

## GET 获取商品详情

GET /commodity/detail/{spuId}

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|spuId|path|string| yes ||产品id|
|Authorization|header|string| yes ||none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "base": {
      "spuId": "1722531137928171520",
      "categoryId": 1,
      "title": "扎染吊带连衣裙",
      "image": " ",
      "basePrice": 172,
      "sales": 213,
      "productionPlace": "广东东莞",
      "useSpec": 1,
      "spec": [
        {
          "name": "尺码",
          "value": [
            "150/76A/XS",
            "155/80A/S",
            "160/84A/M",
            "165/88A/L",
            "170/92A/XL",
            "175/96A/XXL"
          ]
        }
      ]
    },
    "details": [
      {
        "skuId": "1722534310415831040",
        "spec": "{\"尺码\": \"160/84A/M\"}",
        "price": 172,
        "stock": 40
      },
      {
        "skuId": "1722534315180560384",
        "spec": "{\"尺码\": \"150/76A/XS\"}",
        "price": 172,
        "stock": 30
      },
      {
        "skuId": "1722534317550342144",
        "spec": "{\"尺码\": \"155/80A/S\"}",
        "price": 172,
        "stock": 10
      },
      {
        "skuId": "1722535921825484800",
        "spec": "{\"尺码\": \"165/88A/L\"}",
        "price": 172,
        "stock": 30
      },
      {
        "skuId": "1722535928930635776",
        "spec": "{\"尺码\": \"170/92A/XL\"}",
        "price": 172,
        "stock": 50
      },
      {
        "skuId": "1722535940649521152",
        "spec": "{\"尺码\": \"175/96A/XXL\"}",
        "price": 172,
        "stock": 34
      }
    ]
  }
}
```

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "base": {
      "spuId": "1722531130835603456",
      "categoryId": 2,
      "title": "云南大理扎染礼盒",
      "image": " ",
      "basePrice": 142,
      "sales": 45,
      "productionPlace": "云南大理",
      "useSpec": 0,
      "spec": null
    },
    "details": [
      {
        "skuId": "1722534303222599680",
        "spec": null,
        "price": 142,
        "stock": 66
      }
    ]
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» base|object|true|none|基本信息|也就是产品信息|
|»»» spuId|string|true|none|产品id|none|
|»»» categoryId|integer|true|none|分类id|none|
|»»» title|string|true|none|产品名|none|
|»»» image|string|true|none|产品图片|none|
|»»» basePrice|integer|true|none|起售价|none|
|»»» sales|integer|true|none|销量 |none|
|»»» productionPlace|string|true|none|产地|none|
|»»» useSpec|integer|true|none|是否有规格|0为单一规格，1为多规格|
|»»» spec|[object]¦null|true|none|规格数组|如果useSpec为0,则本字段为null|
|»»»» name|string|false|none|规格名|none|
|»»»» value|[string]|false|none|可选的规格|none|
|»» details|[object]|true|none|具体信息数组|也就是商品信息数组|
|»»» skuId|string|true|none|商品id|none|
|»»» spec|string¦null|true|none|规格信息|Json字符串|
|»»» price|integer|true|none|商品售价|none|
|»»» stock|integer|true|none|库存|none|

## GET 商品搜索

GET /commodity/base/list/search

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|key|query|string| yes ||检索关键词|
|timestamp|query|string| no ||限制时间戳，默认为当前时间戳|
|pageSize|query|string| no ||限制查询个数，默认为5，最小为1，最大为10|
|Authorization|header|string| yes ||none|

#### Description

**timestamp**: 限制时间戳，默认为当前时间戳
第一次请求无须带上该参数，后续请求需要带上 上次响应结果中的timestamp

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "resultList": [
      {
        "spuId": "1722531126494498816",
        "categoryId": 2,
        "title": "手工蓝染扎染布艺手链",
        "image": " ",
        "basePrice": 32,
        "sales": 34,
        "productionPlace": "浙江金华"
      },
      {
        "spuId": "1722531130835603456",
        "categoryId": 2,
        "title": "云南大理扎染礼盒",
        "image": " ",
        "basePrice": 142,
        "sales": 45,
        "productionPlace": "云南大理"
      },
      {
        "spuId": "1722531137928171520",
        "categoryId": 1,
        "title": "扎染吊带连衣裙",
        "image": " ",
        "basePrice": 172,
        "sales": 213,
        "productionPlace": "广东东莞"
      }
    ],
    "timestamp": "1699518814000"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» resultList|[object]|true|none|查询结果|none|
|»»» spuId|string|true|none|产品id|none|
|»»» categoryId|integer|true|none|分类id|none|
|»»» title|string|true|none|产品名|none|
|»»» image|string|true|none|产品图片|none|
|»»» basePrice|integer|true|none|起售价|none|
|»»» sales|integer|true|none|销量|none|
|»»» productionPlace|string|true|none|产地|none|
|»» timestamp|string|true|none|限制时间戳|下次请求需要带上此时间戳|

# 购物车

## GET 获取购物车信息

GET /shopping/cart/list

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": [
    {
      "base": {
        "spuId": "1722531126494498816",
        "categoryId": 1722531128730062800,
        "title": "手工蓝染扎染布艺手链",
        "image": " ",
        "basePrice": 32,
        "sales": 34,
        "productionPlace": "浙江金华",
        "useSpec": 0,
        "spec": null
      },
      "detail": {
        "skuId": "1722534298487230464",
        "spec": null,
        "price": 32,
        "stock": 49
      },
      "num": 3
    },
    {
      "base": {
        "spuId": "1722531137928171520",
        "categoryId": 1722531135570972700,
        "title": "扎染吊带连衣裙",
        "image": " ",
        "basePrice": 172,
        "sales": 213,
        "productionPlace": "广东东莞",
        "useSpec": 1,
        "spec": [
          {
            "name": "尺码",
            "value": [
              "150/76A/XS",
              "155/80A/S",
              "160/84A/M",
              "165/88A/L",
              "170/92A/XL",
              "175/96A/XXL"
            ]
          }
        ]
      },
      "detail": {
        "skuId": "1722534310415831040",
        "spec": "{\"尺码\": \"160/84A/M\"}",
        "price": 172,
        "stock": 40
      },
      "num": 2
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|[object]|true|none|数据|none|
|»» base|object|true|none|基本信息|也就是产品信息|
|»»» spuId|string|true|none|产品id|none|
|»»» categoryId|integer|true|none|分类id|none|
|»»» title|string|true|none|标题|none|
|»»» image|string|true|none|图片|none|
|»»» basePrice|integer|true|none|起售价|none|
|»»» sales|integer|true|none|销量|none|
|»»» productionPlace|string|true|none|产地|none|
|»»» useSpec|integer|true|none|是否有规格|0为单一规格，1为多规格|
|»»» spec|[object]|true|none|规格数组|如果useSpec为0,则本字段为null|
|»»»» name|string|false|none|规格名|none|
|»»»» value|[string]|false|none|规格值|可能包含的规格选项|
|»» detail|object|true|none|具体信息|也就是商品信息|
|»»» skuId|string|true|none|商品id|none|
|»»» spec|string¦null|true|none|规格|Json字符串|
|»»» price|integer|true|none|具体售价|none|
|»»» stock|integer|true|none|库存|none|
|»» num|integer|true|none|预选的商品数量|none|

## POST 将商品保存进购物车

POST /shopping/cart/add/commodity

适用于将商品详情页中将商品添加进购物车，本接口num值会和购物车的同款商品数量**进行累加**操作(如果原先存在)

> Body Parameters

```json
{
  "skuId": "1722534303222599680",
  "num": 1
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|
|body|body|object| no ||none|
|» skuId|body|string| yes | 商品Id|none|
|» num|body|integer| yes | 预选的商品数量|注意，该数量是会和原先存在的商品数量进行累加|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": null
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|null|true|none|数据|none|

## PUT 更新预选商品的的数量

PUT /shopping/cart/commodity/num

适用于将购物车页面更新商品的数量，该数量将直接替换原先存在的商品数量，**不会进行累加**
注意：
1.如果更新的商品之前未添加进购物车，则直接报错
2.num必须大于等于1，如果要进行删除操作，请调用删除接口

> Body Parameters

```json
{
  "skuId": "1722534303222599680",
  "num": 3
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| no ||none|
|body|body|object| no ||none|
|» skuId|body|string| yes | 商品id|none|
|» num|body|integer| yes | 预选商品数量|注意，该数量将直接替换原先存在的商品数量，不会进行累加|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": null
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|null|true|none||none|

## DELETE 删除购物车中的商品

DELETE /shopping/cart/commodity

> Body Parameters

```json
{
  "skuIds": [
    "1722534303222599680",
    "1722534298487230464"
  ]
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|
|body|body|object| no ||none|
|» skuIds|body|[string]| yes | 商品id列表|none|

> Response Examples

> 200 Response

```json
{
  "status": "string",
  "message": "string",
  "data": null
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|null|true|none||none|

## POST 购物车创建订单

POST /shopping/cart/create/order

> Body Parameters

```json
{
  "skuIds": [
    "1722534303222599680"
  ],
  "addressId": "1722235239041110018"
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|
|body|body|object| no ||none|
|» skuIds|body|[string]| yes | 商品id集合|none|
|» addressId|body|string| yes | 收货地址id|none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "orderId": [
      "1731255550999662592",
      "1731255551129686016"
    ],
    "amount": 408
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» orderId|[string]|true|none|订单编号集合|none|
|»» amount|integer|true|none|总支付金额|none|

# 订单模块

## POST 创建单个订单

POST /order/create

> Body Parameters

```json
{
  "skuId": "1722534298487230464",
  "num": 3,
  "addressId": "1722235239041110018"
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|
|body|body|object| no ||none|
|» skuId|body|string| yes | 商品id|none|
|» num|body|integer| yes | 购买的商品数量|none|
|» addressId|body|string| yes | 用户收货地址id|none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "orderId": [
      "1731248040284655616"
    ],
    "amount": 96
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» orderId|[string]|true|none|订单编号集合|none|
|»» amount|integer|true|none|总金额|none|

## GET 获取用户订单列表

GET /order/list/{status}

该接口每次响应最多返回5条订单信息，不可自定义。

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|status|path|string| yes ||unpaid-->获取用户未支付列表，unfinished-->获取用户待收货列表|
|timestamp|query|string| no ||限制时间戳，除第一次请求不需要携带外，其余请求都需要携带响应返回的时间戳|
|Authorization|header|string| yes ||none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "resultList": [
      {
        "orderId": "1726931908115435520",
        "skuId": "1722534310415831040",
        "title": "扎染吊带连衣裙",
        "image": " ",
        "spec": "{\"尺码\": \"160/84A/M\"}",
        "actualPrice": 172,
        "num": 3,
        "amount": 516,
        "paymentType": 2,
        "status": 2
      },
      {
        "orderId": "1726927452669874176",
        "skuId": "1722534298487230464",
        "title": "手工蓝染扎染布艺手链",
        "image": " ",
        "spec": null,
        "actualPrice": 32,
        "num": 3,
        "amount": 96,
        "paymentType": 1,
        "status": 2
      }
    ],
    "timestamp": "1700566564000"
  }
}
```

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "resultList": [],
    "timestamp": null
  }
}
```

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "resultList": [
      {
        "orderId": "1726931908115435529",
        "skuId": "1722534310415831040",
        "title": "扎染吊带连衣裙",
        "image": " ",
        "spec": "{\"尺码\": \"160/84A/M\"}",
        "actualPrice": 172,
        "num": 3,
        "amount": 516,
        "status": 1
      },
      {
        "orderId": "1726931908115435539",
        "skuId": "1722534298487230464",
        "title": "手工蓝染扎染布艺手链",
        "image": " ",
        "spec": null,
        "actualPrice": 32,
        "num": 3,
        "amount": 96,
        "status": 1
      }
    ],
    "timestamp": "1700566564000"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» resultList|[object]|true|none|结果集合|该列表有可能为空|
|»»» orderId|string|true|none|订单id|none|
|»»» skuId|string|true|none|商品id|none|
|»»» title|string|true|none|标题|none|
|»»» image|string|true|none|图片路径|none|
|»»» spec|string¦null|true|none|商品规格|none|
|»»» actualPrice|integer|true|none|真实成交价|none|
|»»» num|integer|true|none|购买的商品数量|none|
|»»» amount|integer|true|none|订单总价|none|
|»»» paymentType|integer|false|none|支付类型|当查询未支付列表时，该字段不会出现。<br />支付方式：1微信、2支付宝、3银行卡|
|»»» status|integer|true|none|订单状态|0取消，1未付款、2已付款、3已发货、4已签收。<br />该接口只能查询到1未付款、2已付款、3已发货 三种状态|
|»» timestamp|string|true|none|限制时间戳|限制时间戳，下次请求时需带上此时间戳|

## POST 订单取消接口

POST /order/cancel/{orderId}

只有处于未支付状态的订单才可取消，否则，将报401错误

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|orderId|path|string| yes ||订单id|
|Authorization|header|string| yes ||none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": null
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|null|true|none||none|

# 徽章模块

## GET 获取用户的徽章兑换情况

GET /badge/list

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|Authorization|header|string| yes ||none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "result": [
      {
        "badgeId": "1728328052745834496",
        "name": "LanHua",
        "image": " ",
        "points": 30,
        "redeemed": true,
        "redeemTime": "2023/11/25"
      },
      {
        "badgeId": "1728327775724638208",
        "name": "MaLan",
        "image": " ",
        "points": 30,
        "redeemed": true,
        "redeemTime": "2023/11/25"
      },
      {
        "badgeId": "1728328048530558976",
        "name": "GuiHua",
        "image": " ",
        "points": 30,
        "redeemed": false
      }
    ],
    "redeemedNum": 2
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» result|[object]|true|none|徽章列表|包含已经兑换的徽章和没有兑换的徽章，返回结果已根据以下规则进行排序：1. 兑换的>未兑换的 2. 新兑换的>旧兑换的|
|»»» badgeId|string|true|none|徽章id|none|
|»»» name|string|true|none|徽章名|none|
|»»» image|string|true|none|徽章照片|未被兑换时为灰色图片<br />已被兑换则为彩色图片|
|»»» points|integer|true|none|徽章积分|兑换该徽章需要的积分|
|»»» redeemed|boolean|true|none|是否已经被用户所兑换|none|
|»»» redeemTime|string|false|none|徽章被兑换的时间|徽章被兑换的时间，当redeemed为true时，才会出现该字段|
|»» redeemedNum|integer|true|none|已经兑换的徽章数量|none|

## POST 兑换徽章

POST /badge/redeem/{badgeId}

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|badgeId|path|string| yes ||徽章id|
|Authorization|header|string| yes ||none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": "兑换成功"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|string|true|none|数据|none|

# 支付模块

## POST 支付订单

POST /pay/order/{paymentType}

> Body Parameters

```json
{
  "orderIds": [
    "1731569837718769664",
    "1731572125036318720"
  ]
}
```

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|paymentType|path|string| yes ||支付方式 1微信、2支付宝、3银行卡|
|Authorization|header|string| yes ||none|
|body|body|object| no ||none|
|» orderIds|body|[string]| yes | 订单编号集合|none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": "支付成功"
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|string|true|none||none|

# 物流模块

## GET 物流查询

GET /logistics/info/{orderId}

物流状态有三种：0 未发货， 1 已发货，2 已签收
如果为0 未发货状态，物流公司，物流编号，寄件人信息 都为null

### Params

|Name|Location|Type|Required|Title|Description|
|---|---|---|---|---|---|
|orderId|path|string| yes ||订单id|
|Authorization|header|string| yes ||none|

> Response Examples

> 成功

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "id": "1731569837731450882",
    "orderId": "1731569837718769664",
    "logisticsCompanies": null,
    "logisticsCode": null,
    "recipientName": "张三",
    "recipientPhone": "18--secret--789",
    "recipientArea": "福建省,福州市,闽侯县",
    "recipientAddress": "福州大学",
    "senderName": null,
    "senderPhone": null,
    "senderArea": null,
    "senderAddress": null,
    "status": 0,
    "createTime": "2023-12-04 15:03:15",
    "updateTime": "2023-12-04 15:03:15"
  }
}
```

```json
{
  "status": "200",
  "message": "success",
  "data": {
    "id": "1731572125099253762",
    "orderId": "1731572125036318720",
    "logisticsCompanies": "顺丰快递",
    "logisticsCode": "SF1629851402437",
    "recipientName": "张三",
    "recipientPhone": "18--secret--789",
    "recipientArea": "福建省,福州市,闽侯县",
    "recipientAddress": "福州大学",
    "senderName": "王五",
    "senderPhone": "18023456789",
    "senderArea": "广东省,广州市,越秀区",
    "senderAddress": "海珠广场",
    "status": 1,
    "createTime": "2023-12-04 15:12:20",
    "updateTime": "2023-12-04 15:12:20"
  }
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» status|string|true|none|状态码|none|
|» message|string|true|none|状态信息|none|
|» data|object|true|none|数据|none|
|»» id|string|true|none|id|none|
|»» orderId|string|true|none|订单id|none|
|»» logisticsCompanies|string¦null|true|none|物流公司|none|
|»» logisticsCode|string¦null|true|none|物流编号|none|
|»» recipientName|string|true|none|收件人姓名|none|
|»» recipientPhone|string|true|none|收件人电话|none|
|»» recipientArea|string|true|none|收件人所处地区|none|
|»» recipientAddress|string|true|none|收件人具体地址|none|
|»» senderName|string¦null|true|none|寄件人姓名|none|
|»» senderPhone|string¦null|true|none|寄件人电话|none|
|»» senderArea|string¦null|true|none|寄件人所处地区|none|
|»» senderAddress|string¦null|true|none|寄件人具体地址|none|
|»» status|integer|true|none|物流状态|0 未发货， 1 已发货，2 已签收|
|»» createTime|string|true|none|创建时间|none|
|»» updateTime|string|true|none|更新时间|none|

