package com.springboot.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;

public class ContractData {
    @ApiModelProperty("合同类型")
    private String contractType;
    @ApiModelProperty("等于2的时候是冲销单，不走CEO审批")
    private String hasNoInvoice;
    @ApiModelProperty("合同编号")
    private String bizNumber;
    @ApiModelProperty("合同名称")
    private String contractTypeName;
    @ApiModelProperty("合同金额")
    private BigDecimal contractAmount;
    private Boolean isCeoApprove;
    private Boolean isCeoApproveMonitor;
    @ApiModelProperty("合同审批门限")
    private BigDecimal contractThreshold;
    private List<ContractData.CeoCheckData> costItems;
    private Boolean isOurBody;

    public ContractData() {
    }

    public String getContractType() {
        return this.contractType;
    }

    public String getHasNoInvoice() {
        return this.hasNoInvoice;
    }

    public String getBizNumber() {
        return this.bizNumber;
    }

    public String getContractTypeName() {
        return this.contractTypeName;
    }

    public BigDecimal getContractAmount() {
        return this.contractAmount;
    }

    public Boolean getIsCeoApprove() {
        return this.isCeoApprove;
    }

    public Boolean getIsCeoApproveMonitor() {
        return this.isCeoApproveMonitor;
    }

    public BigDecimal getContractThreshold() {
        return this.contractThreshold;
    }

    public List<ContractData.CeoCheckData> getCostItems() {
        return this.costItems;
    }

    public Boolean getIsOurBody() {
        return this.isOurBody;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public void setHasNoInvoice(String hasNoInvoice) {
        this.hasNoInvoice = hasNoInvoice;
    }

    public void setBizNumber(String bizNumber) {
        this.bizNumber = bizNumber;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public void setIsCeoApprove(Boolean isCeoApprove) {
        this.isCeoApprove = isCeoApprove;
    }

    public void setIsCeoApproveMonitor(Boolean isCeoApproveMonitor) {
        this.isCeoApproveMonitor = isCeoApproveMonitor;
    }

    public void setContractThreshold(BigDecimal contractThreshold) {
        this.contractThreshold = contractThreshold;
    }

    public void setCostItems(List<ContractData.CeoCheckData> costItems) {
        this.costItems = costItems;
    }


    public void setIsOurBody(Boolean isOurBody) {
        this.isOurBody = isOurBody;
    }


    protected boolean canEqual(Object other) {
        return other instanceof ContractData;
    }

    @ApiModel("CEO审核")
    public static class CeoCheckData {
        @ApiModelProperty("编码")
        private String costTypeId;
        @ApiModelProperty("金额")
        private BigDecimal amount;
        @ApiModelProperty("币种")
        private String currency;
        @ApiModelProperty("人民币金额")
        private BigDecimal cnyAmount;
        @ApiModelProperty("审批门限")
        private BigDecimal threshold;
        @ApiModelProperty("预算科目名称")
        private String costItemName;

        public String getCostTypeId() {
            return this.costTypeId;
        }

        public BigDecimal getAmount() {
            return this.amount;
        }

        public String getCurrency() {
            return this.currency;
        }

        public BigDecimal getCnyAmount() {
            return this.cnyAmount;
        }

        public BigDecimal getThreshold() {
            return this.threshold;
        }

        public String getCostItemName() {
            return this.costItemName;
        }

        public void setCostTypeId(String costTypeId) {
            this.costTypeId = costTypeId;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setCnyAmount(BigDecimal cnyAmount) {
            this.cnyAmount = cnyAmount;
        }

        public void setThreshold(BigDecimal threshold) {
            this.threshold = threshold;
        }

        public void setCostItemName(String costItemName) {
            this.costItemName = costItemName;
        }


        public CeoCheckData() {
        }

        public CeoCheckData(String costTypeId, BigDecimal amount, String currency, BigDecimal cnyAmount, BigDecimal threshold, String costItemName) {
            this.costTypeId = costTypeId;
            this.amount = amount;
            this.currency = currency;
            this.cnyAmount = cnyAmount;
            this.threshold = threshold;
            this.costItemName = costItemName;
        }
    }
}
