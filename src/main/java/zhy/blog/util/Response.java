package zhy.blog.util;

import java.io.Serializable;

/**
 * To package the response of request.
 */
public class Response implements Serializable {
    private boolean success = true;

    private String msg = "";
    private Object data = null;

    public Response() {
    }

    public Response(Object data) {
        this.data = data;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }

    public Response success(String msg) {
        success = true;
        this.msg = msg;
        return this;
    }

    public Response fail(String msg) {
        success = false;
        this.msg = msg;
        return this;
    }

    public Response success() {
        return success(StringUtil.EMPTY);
    }

    public Response fail() {
        return fail(StringUtil.EMPTY);
    }

    public boolean isSuccess() {
        return success;
    }

    public Response setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Response internalError() {
        return fail("Internal error.Contract the blogger please!");
    }

    public Response notFound() {
        return fail("The target is not found.");
    }
}
