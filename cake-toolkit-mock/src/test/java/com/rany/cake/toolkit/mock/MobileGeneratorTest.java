package com.rany.cake.toolkit.mock;

import com.rany.cake.toolkit.mock.mobile.MobileGenerator;
import org.junit.Test;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/8/11 12:33
 */
public class MobileGeneratorTest {

    @Test
    public void getPrefix() {
        for (int i = 0; i < 100; i++) {
            System.out.println(MobileGenerator.getMobilePrefix());
        }
    }

    @Test
    public void gen() {
        for (int i = 0; i < 100; i++) {
            System.out.println(MobileGenerator.generateMobile());
        }
    }

}
