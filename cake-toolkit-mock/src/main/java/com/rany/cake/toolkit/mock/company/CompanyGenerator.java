package com.rany.cake.toolkit.mock.company;


import com.rany.cake.toolkit.lang.random.Randoms;
import com.rany.cake.toolkit.lang.utils.Arrays1;
import com.rany.cake.toolkit.lang.utils.Strings;
import com.rany.cake.toolkit.mock.addres.AddressSupport;
import com.rany.cake.toolkit.mock.industry.IndustryGenerator;

/**
 * 公司名称生成器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/8/19 16:23
 */
public class CompanyGenerator {

    private static final String[] SUFFIX;

    private CompanyGenerator() {
    }

    static {
        SUFFIX = new String[]{
                "责任有限公司", "股份有限公司",
                "发展有限公司", "有限公司", "公司"
        };
    }

    public static String generatorCompanyName() {
        return generatorCompanyName(AddressSupport.randomProvinceCode(), IndustryGenerator.generatorManagementType());
    }

    public static String generatorCompanyName(int provinceCode) {
        return generatorCompanyName(provinceCode, IndustryGenerator.generatorManagementType());
    }

    public static String generatorCompanyName(String managementType) {
        return generatorCompanyName(AddressSupport.randomProvinceCode(), managementType);
    }

    /**
     * 随机生成公司名称
     *
     * @param provinceCode   省编码
     * @param managementType 经营类型
     * @return 公司名称
     */
    public static String generatorCompanyName(int provinceCode, String managementType) {
        if (Strings.isBlank(managementType)) {
            return Strings.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        // 省名称
        String provinceName = AddressSupport.getProvinceName(provinceCode);
        if (provinceName.startsWith("黑龙江") || provinceName.startsWith("内蒙古")) {
            provinceName = provinceName.substring(0, 3);
        } else {
            provinceName = provinceName.substring(0, 2);
        }
        sb.append(provinceName);
        // 字号
        if (Randoms.randomBoolean(2)) {
            sb.append(Strings.randomChars(Randoms.randomInt(2, 4)));
        }
        // 经营类型
        sb.append(managementType);
        // 组织形式
        sb.append(Arrays1.random(SUFFIX));
        return sb.toString();
    }

}
