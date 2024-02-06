package com.rany.cake.toolkit.mock.faker;

/**
 * 数据类型
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/8/13 18:08
 */
public enum FakerType {

    /**
     * 名称
     */
    NAME,

    /**
     * 英文名
     */
    EN_NAME,

    /**
     * 手机号
     */
    MOBILE,

    /**
     * 邮箱
     */
    EMAIL,

    /**
     * 住址
     */
    ADDRESS,

    /**
     * 身份证信息
     */
    ID_CARD,

    /**
     * 储蓄卡
     */
    DEBIT_CARD,

    /**
     * 信用卡
     */
    CREDIT_CARD,

    /**
     * 学历
     */
    EDUCATION,

    /**
     * 学校名称
     */
    UNIVERSITY,

    /**
     * 行业
     */
    INDUSTRY,

    /**
     * 车牌号
     */
    LICENSE_PLATE,

    /**
     * 社会统一信用代码
     */
    COMPANY_CREDIT_CODE,

    /**
     * 公司名称
     */
    COMPANY_NAME,

    /**
     * 常用ip
     */
    IP,

    ;

    public static final FakerType[] BASE = {NAME, MOBILE, EMAIL, ADDRESS, ID_CARD};

    public static final FakerType[] ALL = values();

}
