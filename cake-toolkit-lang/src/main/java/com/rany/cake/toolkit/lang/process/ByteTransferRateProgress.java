package com.rany.cake.toolkit.lang.process;

import com.rany.cake.toolkit.lang.Threads;
import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.utils.Objects1;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * 数据传输进度条 (实时速率计算)
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/11/6 23:07
 */
public class ByteTransferRateProgress extends ByteTransferProgress {

    /**
     * 是否计算实时速率
     */
    protected boolean computeRate;

    /**
     * 计算间隔 ms
     */
    protected int interval;

    /**
     * 当前速度 byte
     */
    protected volatile long nowRate;

    /**
     * 进度调度器
     */
    private ExecutorService rateExecutor;

    /**
     * 进度处理器
     */
    private Consumer<? super ByteTransferRateProgress> rateAcceptor;

    public ByteTransferRateProgress(long end) {
        this(0, end);
    }

    public ByteTransferRateProgress(long start, long end) {
        super(start, end);
    }

    public ByteTransferRateProgress computeRate() {
        return this.computeRate(Const.MS_S_1);
    }

    /**
     * 开启计算实时速率
     *
     * @param interval 间隔
     */
    public ByteTransferRateProgress computeRate(int interval) {
        this.computeRate = true;
        this.interval = interval;
        return this;
    }

    /**
     * 进度调度器
     *
     * @param rateExecutor 线程池
     * @return this
     */
    public ByteTransferRateProgress rateExecutor(ExecutorService rateExecutor) {
        this.rateExecutor = rateExecutor;
        return this;
    }

    /**
     * 进度回调
     *
     * @param rateAcceptor acceptor
     * @return this
     */
    public ByteTransferRateProgress rateAcceptor(Consumer<? super ByteTransferRateProgress> rateAcceptor) {
        this.rateAcceptor = rateAcceptor;
        return this;
    }

    @Override
    public void start() {
        this.startTime = System.currentTimeMillis();
        if (computeRate) {
            Threads.start(() -> {
                while (!done) {
                    long size = current.get();
                    Threads.sleep(interval);
                    this.nowRate = current.get() - size;
                    if (rateAcceptor != null) {
                        rateAcceptor.accept(this);
                    }
                }
            }, Objects1.def(rateExecutor, Threads.CACHE_EXECUTOR));
        }
    }

    public long getNowRate() {
        return nowRate;
    }

}
