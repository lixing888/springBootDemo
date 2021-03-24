package com.springboot.demo.vo;

import lombok.Data;

/**
 * 比特币交易
 * @author lixing
 */
@Data
public class TransactionBitCoin {

    /**
     * 交易唯一标识
     */
    private String id;
    /**
     * 交易发送方钱包地址
     */
    private String sender;
    /**
     * 交易接收方钱包地址
     */
    private String recipient;
    /**
     * 交易金额
     */
    private int amount;

    public TransactionBitCoin() {
        super();
    }

    public TransactionBitCoin(String id, String sender, String recipient, int amount) {
        super();
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }


}
