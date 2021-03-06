package com.wordanalysis.jiebaservice.util;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回
 * @author Chen 2018/1/10
 */
@Data
@ApiModel("返回结果集")
public class Res extends HashMap<String,Object> {
    private static final long serialVersionUID = 1L;

    public static Res error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }
    public static Res error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public Res() {
        put("code",0);
    }

    public static Res error(int code, String msg) {
        Res r = new Res();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static Res ok(String msg) {
        Res r = new Res();
        r.put("msg", msg);
        return r;
    }

    public static Res ok(Map<String, Object> map) {
        Res r = new Res();
        r.putAll(map);
        return r;
    }

    //创建Res对象
    public static Res ok() {
        return new Res();
    }

    //put结果集
    public Res put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /*
    public Object get(String key){
        Object o = super.get(key);
        if (o == null)return null;
        return  o;
    }
    */
}