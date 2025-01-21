package com.rany.cake.toolkit.cms.util;



import com.rany.cake.toolkit.cms.exception.CmsException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtil {

    /**
     * 写入本地磁盘
     *
     * @param parentPath 父级路径
     * @param fileName 文件名称
     * @param data 数据
     * @return 文件路径
     */
    public static String writeToFile(Path parentPath, String fileName, String data) {
        Path filePath = Paths.get(parentPath.toString(), fileName);
        if (!Files.exists(filePath.getParent())) {
            try {
                Files.createDirectories(filePath.getParent());
            } catch (IOException e) {
                throw new CmsException("创建文件父级目录出错", e);
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(filePath,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE)) {

            writer.write(data);
            return filePath.toString();
        } catch (IOException e) {
            throw new CmsException("关闭io流出错", e);
        }
    }

    /**
     * 从本地磁盘读取文件
     *
     * @param parentPath 父级路径
     * @param fileName 文件名称
     * @return 文件内容
     */
    public static String readFromFile(String parentPath, String fileName) {
        Path filePath = Paths.get(parentPath, fileName);
        Path pathParent = filePath.getParent();
        if (Files.exists(pathParent)) {
            StringBuffer sb = new StringBuffer();
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }
                return sb.toString().trim().isEmpty() ? null : sb.toString();
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void deleteCacheFiles(Path cmsRootPath) throws IOException {
        deleteFile(cmsRootPath);
    }

    private static void deleteFile(Path filePath) throws IOException {
        if (filePath == null) {
            return;
        }
        File file = filePath.toFile();
        if (!file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            String[] childFiles = file.list();
            if (childFiles != null) {
                for (String childFile : childFiles) {
                    if ("".equalsIgnoreCase(childFile)) {
                        return;
                    } else {
                        deleteFile(Paths.get(filePath.toString(), childFile));
                    }
                }
            }
        }

        if (file.isFile()) {
            Files.deleteIfExists(filePath);
        }
    }

}
