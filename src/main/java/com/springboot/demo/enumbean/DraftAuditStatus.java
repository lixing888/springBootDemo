package com.springboot.demo.enumbean;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.Map;

/**
 * @author lixing
 */
public enum  DraftAuditStatus {
    //0草稿状态，1待审核，2通过状态，3拒绝状态,4不需要审批

    draft(0,"待提交"),un_audit(1,"待审核"),pass(2,"通过"),reject(3,"拒绝"),non_audit(4,"无需审批"),canel(5,"已撤销");

    @Getter
    private Integer status;
    /**
     * 页面展示操作名称
     */
    @Getter
    private String message;

    private static Map<Integer, DraftAuditStatus> map;
    static {
        DraftAuditStatus[] enumConstants = DraftAuditStatus.class.getEnumConstants();
        ImmutableMap.Builder<Integer, DraftAuditStatus> builder = ImmutableMap.builder();
        for (DraftAuditStatus enumConstant : enumConstants) {
            builder.put(enumConstant.status, enumConstant);
        }
        map = builder.build();
    }

    public static DraftAuditStatus getDraftAuditEnum(Integer id) {
        DraftAuditStatus versionEnum = map.get(id);
        return versionEnum;
    }

    DraftAuditStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }


    public static void main(String[] args) {
        DraftAuditStatus versionEnum = getDraftAuditEnum(1);
        System.out.println(versionEnum.getMessage());
    }


}
