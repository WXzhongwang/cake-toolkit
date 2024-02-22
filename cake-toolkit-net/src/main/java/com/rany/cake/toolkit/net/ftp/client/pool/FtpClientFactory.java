package com.rany.cake.toolkit.net.ftp.client.pool;

import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Valid;
import com.rany.cake.toolkit.net.ftp.client.instance.FtpInstance;
import com.rany.cake.toolkit.net.ftp.client.instance.IFtpInstance;
import com.rany.cake.toolkit.net.ftp.config.FtpConfig;
import com.rany.cake.toolkit.net.ftp.config.FtpsConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

/**
 * FTPClient 工厂
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/3/18 11:25
 */
public class FtpClientFactory {

    /**
     * FTP配置
     */
    private final FtpConfig config;

    public FtpClientFactory(FtpConfig config) {
        Valid.notNull(config, "config is null");
        this.config = config;
    }

    /**
     * 创建一个连接
     *
     * @return 连接
     */
    public FTPClient createClient() {
        FTPClient client;
        // ssl
        if (config instanceof FtpsConfig) {
            client = new FTPSClient(((FtpsConfig) config).getProtocol(), ((FtpsConfig) config).isImplicit());
        } else {
            client = new FTPClient();
        }
        int reply;
        try {
            client.setDataTimeout(config.getDateTimeout());
            client.setConnectTimeout(config.getConnTimeout());
            client.setListHiddenFiles(config.isShowHidden());
            client.setControlEncoding(config.getRemoteContentCharset());
            client.setBufferSize(config.getBuffSize());
            client.connect(config.getHost(), config.getPort());
            if (config instanceof FtpsConfig && client instanceof FTPSClient) {
                ((FTPSClient) client).execPROT(((FtpsConfig) config).getProtect());
                SSLSocketFactory socketFactory = ((FtpsConfig) config).getSocketFactory();
                if (socketFactory != null) {
                    client.setSocketFactory(socketFactory);
                }
            }

            client.login(config.getUsername(), config.getPassword());
            reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                throw Exceptions.ftp("connection FTP client error because not ready");
            } else {
                client.setFileType(FTPClient.BINARY_FILE_TYPE);
                if (config.isPassiveMode()) {
                    client.enterLocalPassiveMode();
                    client.enterRemotePassiveMode();
                }
                client.cwd(config.getRemoteRootDir());
            }
            return client;
        } catch (IOException e) {
            throw Exceptions.ftp("connection FTP client error", e);
        }
    }

    /**
     * 创建一个实例
     *
     * @return this
     */
    public IFtpInstance createInstance() {
        return new FtpInstance(this.createClient(), config);
    }

    /**
     * 获取配置项
     */
    public FtpConfig getConfig() {
        return config;
    }

}
