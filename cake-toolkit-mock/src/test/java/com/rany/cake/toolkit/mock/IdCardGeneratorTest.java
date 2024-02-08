package com.rany.cake.toolkit.mock;

import com.rany.cake.toolkit.lang.identity.IdCards;
import com.rany.cake.toolkit.lang.utils.Valid;
import com.rany.cake.toolkit.mock.idcard.IdCardGenerator;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/8/12 10:22
 */
public class IdCardGeneratorTest {

    @Test
    public void generator() {
        for (int i = 0; i < 20; i++) {
            String c = IdCardGenerator.generator(18);
            System.out.print(c + " ");
            System.out.print(IdCards.getAge(c) + " ");
            System.out.print(IdCards.getGender(c) ? "男 " : "女 ");
            System.out.print(IdCardGenerator.getIssueOrg(c) + " ");
            System.out.print(IdCardGenerator.getPeriodString(c) + " ");
            System.out.print(IdCardGenerator.getAddress(c) + " ");
            System.out.println();
            Valid.isTrue(IdCards.isValidCard(c));
            Valid.isTrue(IdCards.getAge(c) == 18);
        }
    }

    @Test
    public void generator1() {
        for (int i = 0; i < 20; i++) {
            String c = IdCardGenerator.generator(5, 50);
            System.out.print(c + " ");
            System.out.print(IdCards.getAge(c) + " ");
            System.out.print(IdCards.getGender(c) ? "男 " : "女 ");
            System.out.print(IdCardGenerator.getIssueOrg(c) + " ");
            System.out.print(IdCardGenerator.getPeriodString(c) + " ");
            System.out.print(IdCardGenerator.getFullAddress(c) + " ");
            System.out.print(IdCardGenerator.getAddressCode(c) + " ");
            System.out.print(Arrays.toString(IdCardGenerator.getAddressCodeExt(c)) + " ");
            System.out.print(Arrays.toString(IdCardGenerator.getAddressExt(c)) + " ");
            System.out.println();
            Valid.isTrue(IdCards.isValidCard(c));
        }
    }

}
