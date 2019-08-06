package com.springboot.demo.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PlainResult<T> extends BaseResult {
    @ApiModelProperty("数据")
    private T data;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PlainResult)) {
            return false;
        } else {
            PlainResult<?> other = (PlainResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PlainResult;
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $data = this.getData();
        int result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "PlainResult(data=" + this.getData() + ")";
    }

    public PlainResult() {
    }

    public PlainResult(T data) {
        this.data = data;
    }
}



