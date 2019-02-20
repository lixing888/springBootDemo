package com.springboot.demo.store.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户简历
 * </p>
 *
 * @author lixing
 * @since 2019-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JcUserResume implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    /**
     * 简历名称
     */
    private String resumeName;

    /**
     * 期望工作性质
     */
    private String targetWorknature;

    /**
     * 期望工作地点
     */
    private String targetWorkplace;

    /**
     * 期望职位类别
     */
    private String targetCategory;

    /**
     * 期望月薪
     */
    private String targetSalary;

    /**
     * 毕业学校
     */
    private String eduSchool;

    /**
     * 毕业时间
     */
    private Date eduGraduation;

    /**
     * 学历
     */
    private String eduBack;

    /**
     * 专业
     */
    private String eduDiscipline;

    /**
     * 最近工作公司名称
     */
    private String recentCompany;

    /**
     * 最近公司所属行业
     */
    private String companyIndustry;

    /**
     * 公司规模
     */
    private String companyScale;

    /**
     * 职位名称
     */
    private String jobName;

    /**
     * 职位类别
     */
    private String jobCategory;

    /**
     * 工作起始时间
     */
    private Date jobStart;

    /**
     * 下属人数
     */
    private String subordinates;

    /**
     * 工作描述
     */
    private String jobDescription;

    /**
     * 自我评价
     */
    private String selfEvaluation;


}
