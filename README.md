# TinyService
====

>Golang风格的Java RESTful框架，借鉴了[SparkJava](https://github.com/WhiteBlue/spark)

### Getting started

```
public class TestLaunch {
    public static void main(String args[]) {

        Route.get("/",  Route.get("/", (request, response) -> "poi~" );

        Tiny.server("localhost", 8000);

    }
}
```

Then view at: http://localhost:8080

### Examples

```
  Route.get("/users/:name", (request, response) ->"name: " + request.params(":name"));
```

```
  Route.get("/redirect", (request, response) -> {
            response.redirect("/somework");
            return null;
        });
```

```
 Route.get("/protected", (request, response) -> {
            Action.halt(403, "I don't think so!!!");
            return null;
        });
```

### WebServer

* 启动内置服务器


```
Tiny.server("localhost", 8080);
```
or

```
Tiny.server("localhost", 8080, 1000, 20, 2000);
```


* 使用其他WebServer

->web.xml

```
   <filter>
        <filter-name>TinyFilter</filter-name>
        <filter-class>cn.shiroblue.core.TinyFilter</filter-class>
        <init-param>
            <param-name>applicationClass</param-name>
            <param-value>"Your APP Class"</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>TinyFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

->an implementation of TinyApplication

```
public class TestApp implements TinyApplication {
    @Override
    public void init() {
         Route.get("/",  Route.get("/", (request, response) -> "poi~" );
    }
    @Override
    public void destroy() {
    }
}
```

### Render
>TinyService默认无Render(打印str)，可以通过注册Render使用Template Engine或者JSONEncode

* 使用Fastjson

```
   RenderFactory.setDefaultRender(JSON::toJSONString);
   
```

* 使用Gson

```
     RenderFactory.setDefaultRender(new Render() {
                private Gson gson = new Gson();
                @Override
                public String rend(Object o) throws Exception {
                    return gson.toJson(o);
                }
            });
```


