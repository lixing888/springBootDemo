package com.springboot.demo.store.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户稿费收费配置
 * </p>
 *
 * @author zhaojingbo
 * @since 2019-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JcUserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    /**
     * 微信账号名
     */
    private String accountWeixin;

    /**
     * 微信账号openid
     */
    @TableField("account_weixin_openId")
    private String accountWeixinOpenid;

    /**
     * 支付宝账号
     */
    private String accountAlipy;

    /**
     * 提现账户(0微信 1支付宝)
     */
    private Integer drawAccount;

    /**
     * 稿费总金额
     */
    private Double contentTotalAmount;

    /**
     * 待提现稿费金额
     */
    private Double contentNoPayAmount;

    /**
     * 稿费年金额
     */
    private Double contentYearAmount;

    /**
     * 稿费本月金额
     */
    private Double contentMonthAmount;

    /**
     * 稿费本日金额
     */
    private Double contentDayAmount;

    /**
     * 被用户购买次数
     */
    private Integer contentBuyCount;

    /**
     * 累计提现次数
     */
    private Integer drawCount;

    /**
     * 上次提现时间
     */
    private Date lastDrawTime;

    /**
     * 上次用户购买时间
     */
    private Date lastBuyTime;


}
