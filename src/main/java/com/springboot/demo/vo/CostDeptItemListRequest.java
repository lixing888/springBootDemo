package com.springboot.demo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CostDeptItemListRequest {

    private List<CostDeptItemListRequest.CostDeptItem> costDeptItemList;

    @Data
    public static class CostDeptItem {
        /**
         * 预算科目
         */
        private String budgetItemCode;

        /**
         * 费用部门
         */
        private String costDeptId;

        /**
         * 金额
         */
        private BigDecimal amount;

        /**
         * 币种  String, 必须是CNY
         */
        private String currency;

    }
}

