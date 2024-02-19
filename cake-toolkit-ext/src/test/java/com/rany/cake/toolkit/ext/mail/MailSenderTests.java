package com.rany.cake.toolkit.ext.mail;


import com.rany.cake.toolkit.lang.io.Files1;
import org.junit.Test;

import java.io.File;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2024/02/18 18:16
 */
public class MailSenderTests {

    @Test
    public void sendQQ() {
        MailSender sender = new MailSender(MailServerType.QQ).debug();
        sender.auth("18668485565@163.com", "");
        MailMessage msg = new MailMessage();
        msg.from("18668485565@163.com")
                .to("")
                .cc("")
                .bcc("")
                .title("标题")
                .html()
                .content("<hr/><br/><p>text</p><br/><hr/>")
                .addLine("-------------------")
                .addLine("--------end--------")
                .addLine("-------------------")
                .attachment(new MailAttachment("/Users/zhongshengwang/Desktop/tmp.txt"))
                .attachment(new MailAttachment(new File("/Users/zhongshengwang/Desktop/tmp2.txt")))
                .attachment(new MailAttachment(Files1.openInputStreamSafe(new File("/Users/zhongshengwang/Desktop/tmp3.txt")), "sql.md"))
                .attachment(new MailAttachment("fq".getBytes(), "1.file"));
        System.out.println(msg);
        sender.send(msg);
    }
}
