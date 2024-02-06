package com.rany.cake.toolkit.lang.mutable;


/**
 * 可变 double
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/1/8 18:19
 */
public class MutableDouble extends Number implements Comparable<MutableDouble>, Mutable<Number> {

    private static final long serialVersionUID = 8237498752382937491L;

    private double value;

    public MutableDouble() {
    }

    public MutableDouble(double value) {
        this.value = value;
    }

    public MutableDouble(Number value) {
        this.value = value.doubleValue();
    }

    public MutableDouble(String value) {
        this.value = Double.parseDouble(value);
    }

    @Override
    public Double get() {
        return this.value;
    }

    @Override
    public void set(Number value) {
        this.value = value.doubleValue();
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isNaN() {
        return Double.isNaN(this.value);
    }

    public boolean isInfinite() {
        return Double.isInfinite(this.value);
    }

    public void increment() {
        ++this.value;
    }

    public double getAndIncrement() {
        return this.value++;
    }

    public double incrementAndGet() {
        return ++this.value;
    }

    public void decrement() {
        --this.value;
    }

    public double getAndDecrement() {
        return this.value--;
    }

    public double decrementAndGet() {
        return --this.value;
    }

    public void add(double operand) {
        this.value += operand;
    }

    public void add(Number operand) {
        this.value += operand.doubleValue();
    }

    public void subtract(double operand) {
        this.value -= operand;
    }

    public void subtract(Number operand) {
        this.value -= operand.doubleValue();
    }

    public double addAndGet(double operand) {
        this.value += operand;
        return this.value;
    }

    public double addAndGet(Number operand) {
        this.value += operand.doubleValue();
        return this.value;
    }

    public double getAndAdd(double operand) {
        double last = this.value;
        this.value += operand;
        return last;
    }

    public double getAndAdd(Number operand) {
        double last = this.value;
        this.value += operand.doubleValue();
        return last;
    }

    public Double toDouble() {
        return this.doubleValue();
    }

    @Override
    public byte byteValue() {
        return (byte) this.value;
    }

    @Override
    public short shortValue() {
        return (short) this.value;
    }

    @Override
    public int intValue() {
        return (int) this.value;
    }

    @Override
    public long longValue() {
        return (long) this.value;
    }

    @Override
    public float floatValue() {
        return (float) this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MutableDouble && Double.doubleToLongBits(((MutableDouble) obj).value) == Double.doubleToLongBits(this.value);
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(this.value);
        return (int) (bits ^ bits >>> 32);
    }

    @Override
    public int compareTo(MutableDouble other) {
        return Double.compare(this.value, other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

}
