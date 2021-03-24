package com.springboot.demo.vo;

import lombok.Data;

import java.util.List;

/**
 * 区块结构
 *
 * @author lixing
 */
@Data
public class Block {
    /**
     * 区块索引号
     */
    private int index;
    /**
     * 当前区块的hash值,区块唯一标识
     */
    private String hash;
    /**
     * 生成区块的时间戳
     */
    private long timestamp;
    /**
     * 当前区块的交易集合
     */
    private List<TransactionBitCoin> transactions;
    /**
     * 工作量证明，计算正确hash值的次数
     */
    private int nonce;
    /**
     * 前一个区块的hash值
     */
    private String previousHash;

    public Block() {
        super();
    }

    public Block(int index, long timestamp, List<TransactionBitCoin> transactions, int nonce, String previousHash, String hash) {
        super();
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.nonce = nonce;
        this.previousHash = previousHash;
        this.hash = hash;
    }
}


