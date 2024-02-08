package com.rany.cake.toolkit.mock;

import com.rany.cake.toolkit.mock.plate.LicensePlateGenerator;
import org.junit.Test;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/8/13 18:02
 */
public class LicensePlateGeneratorTest {

    @Test
    public void gen1() {
        for (int i = 0; i < 50; i++) {
            System.out.println(LicensePlateGenerator.generator());
        }
    }

    @Test
    public void gen2() {
        for (int i = 0; i < 50; i++) {
            System.out.println(LicensePlateGenerator.generator(11));
        }
    }

}
