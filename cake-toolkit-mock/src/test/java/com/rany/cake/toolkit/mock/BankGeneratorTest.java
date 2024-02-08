package com.rany.cake.toolkit.mock;

import com.rany.cake.toolkit.lang.wrapper.Pair;
import com.rany.cake.toolkit.mock.bank.BankCardGenerator;
import com.rany.cake.toolkit.mock.bank.BankCardSupport;
import com.rany.cake.toolkit.mock.bank.BankCardType;
import com.rany.cake.toolkit.mock.bank.BankNameType;
import org.junit.Test;

/**
 * http://www.jsons.cn/bankcheck/
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/8/11 11:43
 */
public class BankGeneratorTest {

    @Test
    public void gen1() {
        for (int i = 0; i < 100; i++) {
            Pair<BankNameType, String> p = BankCardGenerator.generatorCard();
            System.out.println(p);
            System.out.println(BankCardGenerator.generatorOpeningBank(p.getKey()));
            assert BankCardSupport.valid(p.getValue());
        }
    }

    @Test
    public void gen2() {
        for (int i = 0; i < 100; i++) {
            Pair<BankNameType, String> p = BankCardGenerator.generatorCard(BankCardType.CREDIT);
            System.out.println(p);
            assert BankCardSupport.valid(p.getValue());
        }
    }

    @Test
    public void gen3() {
        for (int i = 0; i < 100; i++) {
            String p = BankCardGenerator.generatorCard(BankNameType.ICBC, BankCardType.DEBIT);
            System.out.println(p);
            assert BankCardSupport.valid(p);
        }
    }

    @Test
    public void gen4() {
        for (int i = 0; i < 100; i++) {
            String p = BankCardGenerator.generatorCard(BankNameType.ICBC, BankCardType.CREDIT);
            System.out.println(p);
            assert BankCardSupport.valid(p);
        }
    }

}
