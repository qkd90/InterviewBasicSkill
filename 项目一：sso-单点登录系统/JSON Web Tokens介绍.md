# JSON Web Tokens介绍

## 什么是 JSON Web 令牌？

JSON Web Token (JWT) 是一个开放标准 ( [RFC 7519](https://tools.ietf.org/html/rfc7519) )，它定义了一种紧凑且自包含的方式，用于在各方之间以 JSON 对象的形式安全传输信息。此信息可以验证和信任，因为它是数字签名的。JWT 可以使用密钥（使用**HMAC算法）或使用****RSA**或**ECDSA**的公钥/私钥对进行签名。

虽然 JWT 可以加密以在各方之间提供保密性，但我们将专注于*签名*令牌。签名的令牌可以验证其中包含的声明的*完整性*，而加密的令牌会向其他方*隐藏*这些声明。当使用公钥/私钥对对令牌进行签名时，签名还证明只有持有私钥的一方才是签署它的一方。

## 什么时候应该使用 JSON Web Tokens？

以下是 JSON Web 令牌有用的一些场景：

- **授权**：这是使用 JWT 最常见的场景。用户登录后，每个后续请求都将包含 JWT，从而允许用户访问该令牌允许的路由、服务和资源。单点登录是当今广泛使用 JWT 的一项功能，因为它的开销很小并且能够在不同的域中轻松使用。
- **信息交换**：JSON Web 令牌是在各方之间安全传输信息的好方法。因为可以对 JWT 进行签名（例如，使用公钥/私钥对），所以您可以确定发件人就是他们所说的那个人。此外，由于使用标头和有效负载计算签名，您还可以验证内容没有被篡改。

## JSON Web Token 结构是什么？

在其紧凑的形式中，JSON Web Tokens 由以点 (.) 分隔的三部分组成，它们是：

- 标题
- 有效载荷
- 签名

因此，JWT 通常如下所示。

```
xxxxx.yyyyy.zzzzz
```

让我们分解不同的部分。

### 标题

标头*通常*由两部分组成：令牌的类型，即 JWT，以及正在使用的签名算法，例如 HMAC SHA256 或 RSA。

例如：

```
{
  "alg": "HS256",
  "typ": "JWT"
}
```

然后，这个 JSON 被**Base64Url**编码以形成 JWT 的第一部分。

### 有效载荷

令牌的第二部分是有效负载，其中包含声明。声明是关于实体（通常是用户）和附加数据的陈述。索赔分为三种类型：*注册*索赔、*公开*索赔和*私人*索赔。

- [**注册声明**](https://tools.ietf.org/html/rfc7519#section-4.1)：这些是一组预定义的声明，它们不是强制性的，但建议使用，以提供一组有用的、可互操作的声明。其中一些是： **iss**（发行人）、 **exp**（到期时间）、 **sub**（主题）、 **aud**（受众）[等](https://tools.ietf.org/html/rfc7519#section-4.1)。

  请注意，声明名称只有三个字符，只要 JWT 是紧凑的。

- [**公共声明**](https://tools.ietf.org/html/rfc7519#section-4.2)：这些可以由使用 JWT 的人随意定义。但是为了避免冲突，它们应该在[IANA JSON Web Token Registry](https://www.iana.org/assignments/jwt/jwt.xhtml)中定义，或者定义为包含抗冲突命名空间的 URI。

- [**私人声明**](https://tools.ietf.org/html/rfc7519#section-4.3)：这些是为在同意使用它们的各方之间共享信息而创建的自定义声明，既不是*注册*声明也不*是公共*声明。

一个示例有效载荷可能是：

```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```

然后对有效负载进行**Base64Url**编码以形成 JSON Web 令牌的第二部分。

请注意，对于已签名的令牌，此信息虽然受到保护以防篡改，但任何人都可以读取。除非已加密，否则请勿将机密信息放入 JWT 的有效负载或标头元素中。

### 签名

要创建签名部分，您必须获取编码的标头、编码的有效负载、秘密、标头中指定的算法，并对其进行签名。

例如，如果您想使用 HMAC SHA256 算法，签名将通过以下方式创建：

```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```

签名用于验证消息在此过程中没有被更改，并且在使用私钥签名的令牌的情况下，它还可以验证 JWT 的发送者就是它所说的那个人。

### 把所有的放在一起

输出是三个用点分隔的 Base64-URL 字符串，可以在 HTML 和 HTTP 环境中轻松传递，同时与基于 XML 的标准（如 SAML）相比更紧凑。

下面显示了一个 JWT，该 JWT 具有先前的标头和有效负载编码，并使用秘密签名。![编码的 JWT](https://cdn.auth0.com/content/jwt/encoded-jwt3.png)

如果您想玩 JWT 并将这些概念付诸实践，您可以使用[jwt.io Debugger](https://jwt.io/#debugger-io)来解码、验证和生成 JWT。

![JWT.io 调试器](https://cdn.auth0.com/blog/legacy-app-auth/legacy-app-auth-5.png)

## JSON Web 令牌如何工作？

在身份验证中，当用户使用其凭据成功登录时，将返回一个 JSON Web Token。由于令牌是凭据，因此必须非常小心以防止出现安全问题。通常，您不应将令牌保留超过所需的时间。

[由于缺乏安全性，](https://cheatsheetseries.owasp.org/cheatsheets/HTML5_Security_Cheat_Sheet.html#local-storage)您也不应该在浏览器存储中存储敏感的会话数据。

每当用户想要访问受保护的路由或资源时，用户代理应该发送 JWT，通常在**Authorization**标头中使用**Bearer**模式。标头的内容应如下所示：

```
Authorization: Bearer <token>
```

在某些情况下，这可以是一种无状态授权机制。服务器的受保护路由将检查`Authorization`标头中是否存在有效的 JWT，如果存在，则允许用户访问受保护的资源。如果 JWT 包含必要的数据，则可能会减少查询数据库以进行某些操作的需要，尽管情况并非总是如此。

请注意，如果您通过 HTTP 标头发送 JWT 令牌，则应尽量防止它们变得太大。某些服务器不接受超过 8 KB 的标头。如果您试图在 JWT 令牌中嵌入太多信息，例如通过包含所有用户的权限，您可能需要替代解决方案，例如[Auth0 Fine-Grained Authorization](https://fga.dev/)。

如果令牌在`Authorization`标头中发送，则跨域资源共享 (CORS) 不会成为问题，因为它不使用 cookie。

下图显示了如何获取 JWT 并将其用于访问 API 或资源：

![JSON Web 令牌如何工作](https://cdn2.auth0.com/docs/media/articles/api-auth/client-credentials-grant.png)

1. 应用程序或客户端向授权服务器请求授权。这是通过不同的授权流程之一执行的。例如，一个典型的符合[OpenID Connect](http://openid.net/connect/)的Web 应用程序将使用[授权代码流](http://openid.net/specs/openid-connect-core-1_0.html#CodeFlowAuth)`/oauth/authorize`通过端点。
2. 当授权被授予时，授权服务器向应用程序返回一个访问令牌。
3. 应用程序使用访问令牌访问受保护的资源（如 API）。

请注意，使用签名令牌，令牌中包含的所有信息都会向用户或其他方公开，即使他们无法更改。这意味着您不应将秘密信息放入令牌中。

## 为什么我们应该使用 JSON Web Tokens？

让我们谈谈**JSON Web Tokens (JWT)**与**Simple Web Tokens (SWT)**和**Security Assertion Markup Language Tokens (SAML)**相比的优势。

由于 JSON 不像 XML 那样冗长，因此在对其进行编码时，它的大小也更小，这使得 JWT 比 SAML 更紧凑。这使得 JWT 成为在 HTML 和 HTTP 环境中传递的不错选择。

安全方面，SWT 只能通过使用 HMAC 算法的共享密钥进行对称签名。但是，JWT 和 SAML 令牌可以使用 X.509 证书形式的公钥/私钥对进行签名。与签署 JSON 的简单性相比，使用 XML 数字签名签署 XML 而不引入隐蔽的安全漏洞是非常困难的。

JSON 解析器在大多数编程语言中都很常见，因为它们直接映射到对象。相反，XML 没有自然的文档到对象映射。这使得使用 JWT 比使用 SAML 断言更容易。

关于使用，JWT 用于 Internet 规模。这突出了 JSON Web 令牌在多个平台（尤其是移动平台）上客户端处理的便利性。

![比较编码的 JWT 和编码的 SAML 的长度](https://cdn.auth0.com/content/jwt/comparing-jwt-vs-saml2.png)*编码的 JWT 和编码的 SAML 的长度比较*