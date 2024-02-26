package com.rany.cake.toolkit.net.base.file.sftp;

import com.jcraft.jsch.SftpATTRS;
import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.time.Dates;

import java.util.Date;

/**
 * sftp 文件包装
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2022/5/18 16:16
 */
public class SftpFileWrapper extends SftpFile {

    /**
     * 文件属性
     */
    private final SftpATTRS attrs;

    public SftpFileWrapper(String path, SftpATTRS attrs) {
        this.path = path;
        this.attrs = attrs;
        this.accessTime = Dates.date(attrs.getATime());
        this.modifyTime = Dates.date(attrs.getMTime());
        this.size = attrs.getSize();
        this.uid = attrs.getUId();
        this.gid = attrs.getGId();
        this.permission = Files1.permission8to10(attrs.getPermissions() & 0xFFF);
        this.permissionString = attrs.getPermissionsString();
    }

    public SftpATTRS getAttrs() {
        return attrs;
    }

    public void setSize(long size) {
        this.size = size;
        this.attrs.setSIZE(size);
    }

    public void setUid(int uid) {
        this.uid = uid;
        this.attrs.setUIDGID(uid, gid);
    }

    public void setGid(int gid) {
        this.gid = gid;
        this.attrs.setUIDGID(uid, gid);
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        this.attrs.setACMODTIME((int) (accessTime.getTime() / Const.MS_S_1), (int) (modifyTime.getTime() / Const.MS_S_1));
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
        this.attrs.setACMODTIME((int) (accessTime.getTime() / Const.MS_S_1), (int) (modifyTime.getTime() / Const.MS_S_1));
    }

    public void setPermission(int permission) {
        this.permission = permission;
        this.attrs.setPERMISSIONS(Files1.permission10to8(permission));
    }

}
