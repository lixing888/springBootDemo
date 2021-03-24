package com.springboot.demo.util;

import com.alibaba.fastjson.JSON;
import com.springboot.demo.vo.Block;
import com.springboot.demo.vo.TransactionBitCoin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 比特币工具类
 *
 * @author lixing
 */
public class BitCoinUtil {

    /**
     * 挖矿
     *
     * @param blockchain 整个区块链
     * @param txs        需记账交易记录
     * @param address    矿工钱包地址
     * @return
     */
    private static void mineBlock(List<Block> blockchain, List<TransactionBitCoin> txs, String address) {
        //加入系统奖励的交易，默认挖矿奖励10个比特币
        TransactionBitCoin sysTx = new TransactionBitCoin(UUID.randomUUID().toString(), "", address, 10);
        txs.add(sysTx);
        //获取当前区块链里的最后一个区块
        Block latestBlock = blockchain.get(blockchain.size() - 1);
        //随机数
        int nonce = 1;
        String hash = "";
        while (true) {
            hash = Sha256.getSHA256(latestBlock.getHash() + JSON.toJSONString(txs) + nonce);
            if (hash.startsWith("8521")) {
                System.out.println("=====计算结果正确，计算次数为：" + nonce + ",hash:" + hash);
                break;
            }
            nonce++;
            System.out.println("计算错误，hash:" + hash);
        }
        //解出难题，可以构造新区块并加入进区块链里
        Block newBlock = new Block(latestBlock.getIndex() + 1, System.currentTimeMillis(), txs, nonce, latestBlock.getHash(), hash);
        blockchain.add(newBlock);
        System.out.println("挖矿后的区块链：" + JSON.toJSONString(blockchain));
    }

    /**
     * 查询余额
     *
     * @param blockchain
     * @param address
     * @return
     */
    public static int getWalletBalance(List<Block> blockchain, String address) {
        int balance = 0;
        for (Block block : blockchain) {
            List<TransactionBitCoin> transactions = block.getTransactions();
            for (TransactionBitCoin transaction : transactions) {
                if (address.equals(transaction.getRecipient())) {
                    balance += transaction.getAmount();
                }
                if (address.equals(transaction.getSender())) {
                    balance -= transaction.getAmount();
                }
            }
        }
        return balance;
    }

    public static void main(String[] args) {
        //创建一个空的区块链
        List<Block> blockchain = new ArrayList<>();
        //生成创世区块
        Block block = new Block(1, System.currentTimeMillis(), new ArrayList<TransactionBitCoin>(), 1, "1", "1");
        //加入创世区块到区块链里
        blockchain.add(block);
        System.out.println(JSON.toJSONString(blockchain));
        // 发送方钱包地址
        String sender = "sender_wallet";
        //接收方钱包地址
        String recipient = "recipient_wallet";
        //创建一个空的交易集合
        List<TransactionBitCoin> txs = new ArrayList<>();
        //挖矿
        mineBlock(blockchain, txs, sender);
        System.out.println(sender + "钱包的余额为：" + getWalletBalance(blockchain, sender));
        //创建一个空的交易集合
        List<TransactionBitCoin> txs1 = new ArrayList<>();
        //已发生但未记账的交易记录，发送者给接收者转账3个比特币
        TransactionBitCoin tx1 = new TransactionBitCoin(UUID.randomUUID().toString(), sender, recipient, 3);
        //已发生但未记账的交易记录，发送者给接收者转账1个比特币
        TransactionBitCoin tx2 = new TransactionBitCoin(UUID.randomUUID().toString(), sender, recipient, 1);
        txs1.add(tx1);
        txs1.add(tx2);
        //挖矿
        mineBlock(blockchain, txs1, sender);
        System.out.println(sender + "钱包的余额为：" + getWalletBalance(blockchain, sender));
        System.out.println(recipient + "钱包的余额为：" + getWalletBalance(blockchain, recipient));
    }

}
