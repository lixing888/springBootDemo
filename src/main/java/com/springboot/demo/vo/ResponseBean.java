package com.springboot.demo.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.springboot.demo.enumbean.ResultState;
import org.apache.commons.lang3.StringUtils;

/**
 * 用于接口返回值
 * @author 董光耀
 *	样例  success 200 **操作成功
 *	fail 400 **操作失败 402 未登录  400为失败统称  402为具体细节，如果开发不知道该是什么直接400
 */
public class ResponseBean {
    private String message;
    private String code;//返回错误码
    private Object data;

    //默认成功
    public ResponseBean(){
        this.message = "执行成功";
        this.code = "200";
    }

    public ResponseBean errorResult(String message) {
        if (StringUtils.isEmpty(message)) {
            this.message = "执行失败";
        }else{
            this.message=message;
        }
        this.code = "400";
        return this;
    }

    public ResponseBean(ResultState state, String message, String code, Object data,
                        String timestamp) {
        super();
        this.message = message;
        this.code = code;
        this.data = data;
    }
    public ResponseBean(ResultState state, String message, String code) {
        super();
        this.message = message;
        this.code = code;
    }
    public ResponseBean(ResultState state, String message, String code, Object data) {
        super();
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
    /**
     * 返回json字符串
     * @Title: toJsonString
     * @Description: TODO
     * @author : BianShaolei
     * @return: JsonString
     */
    public String toJsonString() {
        return JSON.toJSONString(this);
    }
    /**
     * 如果属性为空 依然返回
     * @author v-liujun.ea
     * @return JsonString
     */
    public String toHasNullValueJsonString(){
        return JSON.toJSONString(this,SerializerFeature.WriteMapNullValue);
    }

}
