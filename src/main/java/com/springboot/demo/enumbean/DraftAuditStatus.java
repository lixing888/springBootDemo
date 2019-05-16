package com.springboot.demo.enumbean;

import lombok.Getter;

/**
 * @author lixing
 */
public enum  DraftAuditStatus {
    //0草稿状态，1待审核，2通过状态，3拒绝状态

    draft(0,"待提交"),un_audit(1,"待审核"),pass(2,"通过"),reject(3,"拒绝");

    @Getter
    private Integer status;
    @Getter
    private String message;

    DraftAuditStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
