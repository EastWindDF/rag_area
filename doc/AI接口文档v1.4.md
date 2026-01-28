# AI 问答接口

---

## 本次版修改说明（v1.4）
### 1. 接口-3
- 增加"查询反馈"接口，通过时间范围查询问题，并按模型提供商返回该模型的回答及反馈。


## 1. 对话接口（流式）

该接口负责接收用户输入并向单个模型生成回复。

* **URL**: `/v1/chat/completions`
* **Method**: `POST`
* **Header**:
  * `Content-Type: application/json`
  * `Accept: text/event-stream`
* **请求体（常用字段）**：

```json
{
  "messages": [
    {"role": "user", "content": "像给五岁孩子解释一样解释相对论"}
  ],
  "session_id": "chat_xxxxxxxxx",
  "start_time": "2025-12-22 21:45:00", // 话单（知识）开始时间
  "end_time": "2025-12-23 21:45:00", // 话单（知识）截止时间
}
```

**字段说明（核心）**

| 字段名 | 类型 | 必填 | 描述 | 示例 |
| --- | --- | --- | --- | --- |
| `messages` | Array | 是 | 对话消息列表，role ∈ `user` | 见上 |
| `session_id` | String | 是 | 会话ID，用于追问及跟踪 | `chat_xxxxxxxxx` |
| `start_time` | String | 否 | 话单（知识）开始时间，格式yyyy-MM-dd HH:mm:ss | `2026-01-01 00:00:00` |
| `end_time` | String | 否 | 话单（知识）截止时间，格式yyyy-MM-dd HH:mm:ss | `2026-01-02 23:59:59` |

---

### 1.1 流式响应（SSE）示例

服务器以 `data:` 前缀推送 JSON 块。每个块含 `choices[].delta` 表示增量内容。

```
data: {
  "session_id": "chatcmpl-abc123",
  "object": "chat.completion.chunk",
  "created": 1735392001,
  "choices": [
    {"index": 0, "delta": {"content": "<think>作为一个"}, "finish_reason": null}
  ]
}
data: {
  "session_id": "chatcmpl-abc123",
  "object": "chat.completion.chunk",
  "created": 1735392001,
  "choices": [
    {"index": 0, "delta": {"content": "大模型</think>"}, "finish_reason": null}
  ]
}
data: {
  "session_id": "chatcmpl-abc123",
  "object": "chat.completion.chunk",
  "created": 1735392001,
  "choices": [
    {"index": 0, "delta": {"content": "想象"}, "finish_reason": null}
  ]
}

data: {
  "session_id": "chatcmpl-abc123",
  "object": "chat.completion.chunk",
  "created": 1735392002,
  "choices": [
    {"index": 0, "delta": {"content": "你在一个巨大的"}, "finish_reason": null}
  ]
}

data: {
  "session_id": "chatcmpl-abc123",
  "object": "chat.completion.chunk",
  "created": 1735392003,
  "choices": [
    {"index": 0, "delta": {}, "finish_reason": "stop"}
  ]
}

data: {
  "session_id": "chatcmpl-abc123",
  "object": "chat.completion.chunk",
  "created": 1735392003,
  "citations": [
    {"id": "ref_1", "summary": "空间物理学基础", "start_time": "2026-01-06 15:23:23", "duration": 120, "callnumber": "123", "callednumber": "456", "relevance": 98, "labels": "科学|数学|机器"},
    {"id": "ref_2", "summary": "空间物理学基础2", "start_time": "2026-01-06 15:23:23", "duration": 120, "callnumber": "123", "callednumber": "456", "relevance": 98, "labels": "科学|数学|机器"}
  ]
}

data: {
  "session_id": "chatcmpl-abc123",
  "object": "chat.completion.chunk",
  "created": 1735392003,
  "citations": [
    {"id": "ref_3", "summary": "空间物理学基础", "start_time": "2026-01-06 15:23:23", "duration": 120, "callnumber": "123", "callednumber": "456", "relevance": 98, "labels": "科学|数学|机器"}
  ]
}

data: [DONE]
```

* `finish_reason` 在最后一个块为 `"stop"`，其他块为 `null`。
* SSE 结束标识为 `data: [DONE]`。


在流式模式下，后端会不断推送小的 JSON 块（Chunks）。需根据 `session_id` 将内容分发到对应的卡片。

**字段说明（核心）**

| 字段名 | 类型 | 描述 | 示例值 |
| --- | --- | --- | --- |
| `session_id` | String | 用于追踪同一场对话，支持多轮状态维持。 | `"sess_123456"` |
| `choices` | Array | 推送的增量文本内容。 | `{"choices": [{"index": 0, "delta": {"content": "你在一个巨大的"}, "finish_reason": null}]}` |
| `choices[].delta` | String | 包含本次推送的增量文本内容，首次content的首文本以<think>开头代表思考模式，未以<think>开头代表非思考。 | `见上案例` |
| `choices[].finish_reason` | String | 生成结束标识。`stop` 代表正常结束，`null` 代表收信中。 | `"stop"` |
| `citations` | Array | **（仅在结束帧[DONE]前返回，可以多次返回，但每次返回都需要保持单个引用的完整性）** 对应截图中的“引用”来源列表。 | `见上` |
| `citations[].id ` | String | 引用的标识ID。 | `“ref_1”` |
| `citations[].summary ` | String | 引用的摘要内容。 | `“空间物理学基础”` |
| `citations[].start_time ` | String | 通话开始时间，格式yyyy-MM-dd HH:mm:ss | `2026-01-06 15:23:23` |
| `citations[].duration ` | String | 通话时长，单位：秒 | `120` |
| `citations[].callnumber ` | String | 主号码 | `` |
| `citations[].callednumber ` | String | 被号码 | `` |
| `citations[].relevance ` | String | 相关度，0～100（没有则不展示） | `` |
| `citations[].labels ` | String | 相关标签，用“\|”分割（没有则不展示） | `科学|数学|机器人` |

---

## 2. 引用详情接口

当用户点击卡片底部的引用来源时，获取详细原始内容。

* **URL**: `/api/v1/reference/detail/{ref_id}`
* **Method**: `GET`
* **Response Body**:

```json
{
  "ref_id": "ref_1",
  "content": "这里是书中与回答相关的原始段落文字...",
  "begin_time": 42, // 可选，引用语音文件中开始时间
  "end_time": 1042, // 可选，引用语音文件中结束时间
  "file": "http://example.com/book/brief-history-of-time" // 语音文件
}

```

**字段说明（核心）**

| 字段名 | 类型 | 描述 | 示例值 |
| --- | --- | --- | --- |
| `ref_id ` | String | 引用的标识ID（根据ID可找到播放音频）。 | `"ref_1"` |
| `content ` | String | 转写内容json数组的字符串化。 | `"[{}]"` |
| `trans ` | String | 翻译内容数组的字符串化。 | `"[{}]"` |
| `time_point ` | Number | 时间点，单位：秒。 | `` |
| `key_elements ` | Object | 关键要素。 |  |
| `key_elements.persons ` | String[] | 人物数组 | `["Tom","Jerry"]` |
| `key_elements.organizations ` | String[] | 组织数组 | `["Club","Bar"]` |
| `key_elements.events ` | String[] | 事件数组 | `["New Year","event1"]` |
| `key_elements.others ` | String[] | 其余元素数组 | `["other1","other2"]` |
---

## 3. 查询反馈接口

通过时间范围查询所有问题，并按模型提供商返回该模型的回答及反馈。
**注意：仅返回指定模型提供商的回答与反馈。**

* **URL**: `/api/v1/feedback/query`
* **Method**: `POST`
* **Header**:
  * `Content-Type: application/json`
* **请求体（常用字段）**：

```json
{
  "provider": "model-1",
  "start_time": "2026-01-01 00:00:00",
  "end_time": "2026-01-02 23:59:59"
}
```

**字段说明（核心）**

| 字段名 | 类型 | 必填 | 描述 | 示例 |
| --- | --- | --- | --- | --- |
| `provider` | String | 是 | 模型提供商标识（仅返回该提供商的回答与反馈） | `model-1` |
| `start_time` | String | 是 | 查询开始时间，格式yyyy-MM-dd HH:mm:ss | `2026-01-01 00:00:00` |
| `end_time` | String | 是 | 查询截止时间，格式yyyy-MM-dd HH:mm:ss | `2026-01-02 23:59:59` |

* **Response Body**:

```json
{
  "provider": "model-1",
  "items": [
    {
      "session_id": "q_10001",
      "question": "请解释量子纠缠。",
      "gmt_begin": "2026-01-01 09:00:12",
      "gmt_end": "2026-01-01 09:01:12",
      "answer": "量子纠缠是指...",
      "citations": [],
      "feedback": {
        "liked": true,
        "comments": ["存在编造内容", "相应速度太慢"]
      }
    }
  ]
}
```

**字段说明（核心）**

| 字段名 | 类型 | 描述 | 示例值 |
| --- | --- | --- | --- |
| `provider` | String | 模型提供商标识（仅此提供商结果） | `openai` |
| `items` | Array | 结果列表 | `[]` |
| `items[].question_id` | String | 问题ID | `q_10001` |
| `items[].question` | String | 问题文本 | `请解释量子纠缠。` |
| `items[].ask_time` | String | 提问时间，格式yyyy-MM-dd HH:mm:ss | `2026-01-01 09:00:12` |
| `items[].answer` | String | **该模型提供商**的回答 | `量子纠缠是指...` |
| `items[].citations` | Array | **该模型提供商**的引用 | `[]` |
| `items[].feedback` | Object | **该模型提供商**的反馈 |  |
| `items[].feedback.liked` | Boolean | 点赞/踩 | `true` |
| `items[].feedback.comments` | Array | 反馈内容数组 | ` ["存在编造内容", "相应速度太慢"]` |

> provider 通过各个厂家给定的公钥来进行加密；加密过程如下；provider只可使用一次，加密算法如下;

```java
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

public class RSAPublicEncrypt {
    
    private static final String ALGORITHM_RSA = "RSA";
    public static String encrypt(String plainText, String publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
        plainText += "@" + UUID.randomUUID();
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }
}
```

