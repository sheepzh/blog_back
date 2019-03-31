package zhy.blog.controller;

import zhy.blog.util.BlogException;
import zhy.blog.util.Response;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BaseController {
    Response exceptionWrap(Supplier<Object> supplier) {
        Response response = new Response();
        try {
            Object object = supplier.get();
            if (object == null)
                response.notFound();
            else
                response.success().setData(object);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
            if (e instanceof BlogException) {
                response.setMsg(e.getMessage());
            }
        }
        return response;
    }

    Response exceptionWrap(Consumer<Object> consumer) {
        Response response = new Response();
        try {
            consumer.accept(null);
        } catch (Exception e) {
            e.printStackTrace();
            response.internalError();
            if (e instanceof BlogException) {
                response.setMsg(e.getMessage());
            }
        }
        return response;
    }
}
