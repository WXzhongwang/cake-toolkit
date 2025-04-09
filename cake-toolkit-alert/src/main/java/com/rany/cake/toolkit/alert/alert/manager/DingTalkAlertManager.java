package com.rany.cake.toolkit.alert.alert.manager;

import com.alibaba.fastjson.JSONObject;
import com.rany.cake.toolkit.lang.utils.Strings;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DingTalkAlertManager
 *
 * @author zhongshengwang
 */
public class DingTalkAlertManager {

    private final static Logger logger = LoggerFactory.getLogger(DingTalkAlertManager.class);

    private static final int CPU_NUM = Runtime.getRuntime().availableProcessors();

    private static final AtomicInteger INCREMENT = new AtomicInteger();

    private static final BlockingQueue<DingMsgRequest> QUEUE = new ArrayBlockingQueue<>(200);

    private static volatile ThreadPoolExecutor EXECUTOR = null;

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(1);


    private static ThreadPoolExecutor getThreadPool() {
        if (EXECUTOR == null) {
            synchronized (DingTalkAlertManager.class) {
                if (EXECUTOR == null) {
                    int coreNum = CPU_NUM <= 0 ? 8 : CPU_NUM;
                    EXECUTOR = new ThreadPoolExecutor(coreNum, coreNum, 2000L, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<>(5000), r -> new Thread(Thread.currentThread().getThreadGroup(), r,
                            "Alert-ThreadPool-" + INCREMENT.getAndIncrement()), new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }
        return EXECUTOR;
    }

    static {
        // 启动定时任务，每隔1分钟处理一次队列中的消息
        SCHEDULED_EXECUTOR.scheduleAtFixedRate(() -> {
            processMessages();
            int remainingCapacity = QUEUE.remainingCapacity();
            int threshold = QUEUE.size() / 3;
            int maxRetries = 5;
            // 设置最大重试次数
            int retries = 0;
            while (remainingCapacity < threshold && retries < maxRetries) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("Thread sleep interrupted", e);
                }

                logger.info("Queue is nearly full, processing messages immediately.");
                processMessages();
                remainingCapacity = QUEUE.remainingCapacity();
                retries++;
            }
            logger.info("Queue is not full, waiting for next scheduled processing.");

        }, 0, 1, TimeUnit.MINUTES);
    }

    private static void processMessages() {
        List<DingMsgRequest> messages = getAlertEntitiesFromQueue();
        if (!messages.isEmpty()) {
            getThreadPool().execute(new AlertWorker(messages));
        }
    }

    /**
     * 异步发送
     * 重复实体短期内会被合并处理
     *
     * @param webHookUrl  发送消息地址
     * @param alertEntity 消息实体
     * @return 发送结果
     */
    public static boolean asyncAlert(String webHookUrl, JSONObject alertEntity) {
        if (Strings.isNotEmpty(webHookUrl) && Objects.nonNull(alertEntity)) {
            DingMsgRequest request = new DingMsgRequest(webHookUrl, alertEntity);
            if (!QUEUE.offer(request)) {
                logger.warn("alert queue is full, ignore it");
                return false;
            }
        } else {
            logger.warn("alert entity is null or url is empty, ignore it");
            return false;
        }
        return true;
    }

    /**
     * 立即发送
     *
     * @param webHookUrl  发送消息地址
     * @param alertEntity 消息实体
     * @return 发送结果
     */
    public static boolean alert(String webHookUrl, JSONObject alertEntity) {
        if (Strings.isEmpty(webHookUrl) || Objects.isNull(alertEntity)) {
            logger.warn("alert entity is null or url is empty, ignore it");
            return false;
        }
        DingMsgRequest request = new DingMsgRequest(webHookUrl, alertEntity);
        return DingUtils.send(request);
    }

    private static List<DingMsgRequest> getAlertEntitiesFromQueue() {
        List<DingMsgRequest> alertEntities = new ArrayList<>();
        do {
            DingMsgRequest alertEntity = null;
            try {
                alertEntity = QUEUE.poll(3L, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                logger.warn(ex.getMessage(), ex);
                break;
            }
            if (alertEntity == null) {
                break;
            }
            alertEntities.add(alertEntity);
        } while (alertEntities.size() < 20);
        return alertEntities;
    }

    @Data
    public static class AlertWorker implements Runnable {
        public AlertWorker(List<DingMsgRequest> messages) {
            this.messages = messages;
        }

        private List<DingMsgRequest> messages;

        private Map<String, JSONObject> mergeMessages(List<DingMsgRequest> messages) {
            Map<String, JSONObject> mergedMap = new HashMap<>();
            for (DingMsgRequest message : messages) {
                String key = message.getUrl() + message.getBody().toString();
                if (!mergedMap.containsKey(key)) {
                    // 消息合并处理
                    mergedMap.put(key, message.getBody());
                }
            }
            return mergedMap;
        }

        @Override
        public void run() {
            Map<String, JSONObject> mergedMessages = mergeMessages(messages);
            for (Map.Entry<String, JSONObject> entry : mergedMessages.entrySet()) {
                DingMsgRequest request = new DingMsgRequest(entry.getKey(), entry.getValue());
                try {
                    DingUtils.send(request);
                } catch (Exception e) {
                    logger.error("Failed to send alert message to DingTalk", e);
                }
            }
        }
    }

}
