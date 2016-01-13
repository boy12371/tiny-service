# TinyService
====

> Golang风格的Java微型API框架

* 支持Servlet规范或使用内嵌Jetty server
* 用于构建简单REST API
* 借鉴了[SparkJava](http://sparkjava.com) , 相比原来精简了代码并省去了个人来讲不必要的JSP支持 , 并添加了Java 7支持

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

* launch with jetty


```
Tiny.server("localhost", 8080);
```
or

```
Tiny.server("localhost", 8080, 1000, 20, 2000);
```


* launch with servlet

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

* return Json

```
	RenderFactory.setDefaultRender(new JsonRender());
```

* use Gson

```
     RenderFactory.setDefaultRender(new Render() {
                private Gson gson = new Gson();
                @Override
                public String rend(Object o) throws Exception {
                    return gson.toJson(o);
                }
            });
```


