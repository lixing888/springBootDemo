package com.springboot.demo.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.demo.enumbean.ResultState;
import io.swagger.annotations.ApiModel;
import java.util.Date;

@ApiModel
public class BaseResult {
    private ResultState state;
    private String message;
    private String code;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date timestamp;

    public BaseResult() {
        this.state = ResultState.SUCCESS;
        this.message = "执行成功";
        this.code = "000000";
        this.timestamp = new Date();
    }

    public BaseResult errorResult() {
        this.state = ResultState.FAIL;
        this.message = "执行失败";
        this.code = "400";
        return this;
    }

    public ResultState getState() {
        return this.state;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setState(ResultState state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseResult)) {
            return false;
        } else {
            BaseResult other = (BaseResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$state = this.getState();
                    Object other$state = other.getState();
                    if (this$state == null) {
                        if (other$state == null) {
                            break label59;
                        }
                    } else if (this$state.equals(other$state)) {
                        break label59;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                Object this$timestamp = this.getTimestamp();
                Object other$timestamp = other.getTimestamp();
                if (this$timestamp == null) {
                    if (other$timestamp != null) {
                        return false;
                    }
                } else if (!this$timestamp.equals(other$timestamp)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseResult;
    }

    @Override
    public String toString() {
        return "BaseResult(state=" + this.getState() + ", message=" + this.getMessage() + ", code=" + this.getCode() + ", timestamp=" + this.getTimestamp() + ")";
    }
}
