package com.rany.cake.toolkit.lang.utils;

import com.rany.cake.toolkit.lang.constant.Const;

import java.nio.charset.Charset;

/**
 * 编码工具类
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email zhongshengwang.zsw@alibaba-inc.com
 */
public class CharsetUtils {

    public static final Charset UTF_8 = of(Const.UTF_8);

    public static final Charset GBK = of(Const.GBK);

    public static final Charset GB_2312 = of(Const.GB_2312);

    public static final Charset ISO_8859_1 = of(Const.ISO_8859_1);

    private CharsetUtils() {
    }

    public static Charset of(String charset) {
        return Charset.forName(charset);
    }

    /**
     * 是否是支持的编码集
     *
     * @param charset charset
     * @return support
     */
    public static boolean isSupported(String charset) {
        if (Strings.isBlank(charset)) {
            return false;
        }
        try {
            return Charset.isSupported(charset);
        } catch (Exception e) {
            return false;
        }
    }

}
