package com.rany.cake.toolkit.lang.unit;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2022/5/17 10:55
 */
public class DiskStorageUnitTest {

    public static void main(String[] args) {
        System.out.println(DiskStorageUnit.BIT.toBit(8192));
        System.out.println(DiskStorageUnit.BIT.toByte(8192));
        System.out.println(DiskStorageUnit.BIT.toKilobyte(8192));
        System.out.println(DiskStorageUnit.BIT.toMegabyte(8388608));
        System.out.println(DiskStorageUnit.BIT.toGigabyte(8589934592L));
        System.out.println(DiskStorageUnit.BIT.toTerabyte(8796093022208L));
        System.out.println("------");
        System.out.println(DiskStorageUnit.B.toBit(1024));
        System.out.println(DiskStorageUnit.B.toByte(1024));
        System.out.println(DiskStorageUnit.B.toKilobyte(1024));
        System.out.println(DiskStorageUnit.B.toMegabyte(1048576));
        System.out.println(DiskStorageUnit.B.toGigabyte(1073741824L));
        System.out.println(DiskStorageUnit.B.toTerabyte(1099511627776L));
        System.out.println("------");
        System.out.println(DiskStorageUnit.KB.toBit(1));
        System.out.println(DiskStorageUnit.KB.toByte(1));
        System.out.println(DiskStorageUnit.KB.toKilobyte(1024));
        System.out.println(DiskStorageUnit.KB.toMegabyte(1048576));
        System.out.println(DiskStorageUnit.KB.toGigabyte(1073741824L));
        System.out.println(DiskStorageUnit.KB.toTerabyte(1099511627776L));
        System.out.println("------");
        System.out.println(DiskStorageUnit.MB.toBit(1));
        System.out.println(DiskStorageUnit.MB.toByte(1));
        System.out.println(DiskStorageUnit.MB.toKilobyte(1));
        System.out.println(DiskStorageUnit.MB.toMegabyte(1));
        System.out.println(DiskStorageUnit.MB.toGigabyte(1024));
        System.out.println(DiskStorageUnit.MB.toTerabyte(1048576));
        System.out.println("------");
        System.out.println(DiskStorageUnit.GB.toBit(1));
        System.out.println(DiskStorageUnit.GB.toByte(1));
        System.out.println(DiskStorageUnit.GB.toKilobyte(1));
        System.out.println(DiskStorageUnit.GB.toMegabyte(1));
        System.out.println(DiskStorageUnit.GB.toGigabyte(1024));
        System.out.println(DiskStorageUnit.GB.toTerabyte(1048576));
        System.out.println("------");
        System.out.println(DiskStorageUnit.TB.toBit(1));
        System.out.println(DiskStorageUnit.TB.toByte(1));
        System.out.println(DiskStorageUnit.TB.toKilobyte(1));
        System.out.println(DiskStorageUnit.TB.toMegabyte(1));
        System.out.println(DiskStorageUnit.TB.toGigabyte(1));
        System.out.println(DiskStorageUnit.TB.toTerabyte(1));
    }

}
