package com.rany.cake.toolkit.mock;

import com.alibaba.fastjson.JSON;
import com.rany.cake.toolkit.mock.faker.Faker;
import com.rany.cake.toolkit.mock.faker.FakerInfo;
import com.rany.cake.toolkit.mock.faker.FakerType;
import org.junit.Test;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/8/14 1:22
 */
public class FakerTest {

    @Test
    public void faker1() {
        for (int i = 0; i < 20; i++) {
            FakerInfo faker = Faker.generator(FakerType.BASE);
            System.out.println(JSON.toJSONString(faker, true));
            System.out.println();
        }
    }

    @Test
    public void faker2() {
        for (int i = 0; i < 20; i++) {
            FakerInfo faker = Faker.generator(FakerType.ALL);
            System.out.println(JSON.toJSONString(faker, true));
            System.out.println();
        }
    }

    @Test
    public void faker3() {
        for (int i = 0; i < 20; i++) {
            FakerInfo faker = Faker.generator(FakerType.ID_CARD, FakerType.DEBIT_CARD);
            System.out.println(JSON.toJSONString(faker, true));
            System.out.println();
        }
    }

}
