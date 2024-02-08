package com.rany.cake.toolkit.mock;

import com.rany.cake.toolkit.mock.education.EducationGenerator;
import com.rany.cake.toolkit.mock.education.UniversityGenerator;
import org.junit.Test;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/8/13 15:35
 */
public class UniversityGeneratorTest {

    @Test
    public void gen2() {
        for (int i = 0; i < 20; i++) {
            System.out.println(UniversityGenerator.generatorUniversity());
        }
    }

    @Test
    public void gen3() {
        for (int i = 0; i < 20; i++) {
            System.out.println(UniversityGenerator.generatorUniversityHigh());
        }
    }

    @Test
    public void gen4() {
        for (int i = 0; i < 20; i++) {
            System.out.println(UniversityGenerator.generatorUniversityLow());
        }
    }

    @Test
    public void gen5() {
        for (int i = 0; i < 30; i++) {
            String edu = EducationGenerator.generatorEducation();
            System.out.println(edu + " " + UniversityGenerator.generatorUniversity(edu));
        }
    }

}
