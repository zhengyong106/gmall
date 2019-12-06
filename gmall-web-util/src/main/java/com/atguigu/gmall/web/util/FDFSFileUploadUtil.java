package com.atguigu.gmall.web.util;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FDFSFileUploadUtil {
    private static final Logger logger = LoggerFactory.getLogger(FDFSFileUploadUtil.class);

    private static final String HTTP_SERVICE_URL = "http://192.168.10.10/";

    static {
        try {
            ClientGlobal.init(new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.error("FastDFS Client Init Fail!",e);
        }
    }

    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        return new StorageClient(trackerServer, null);
    }
    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getConnection();
    }

    public static String upload(MultipartFile file) {
        String[] uploadResults = null;
        StorageClient storageClient=null;

        try {
            storageClient = getStorageClient();
            String fileName = file.getOriginalFilename();
            String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
            uploadResults = storageClient.upload_file(file.getBytes(), fileExtName, null);
        } catch (IOException e) {
            logger.error("IO Exception when uploading the file:" + file.getName(), e);
        } catch (Exception e) {
            logger.error("Non IO Exception when uploading the file:" + file.getName(), e);
        }

        if (null != uploadResults) {
            return HTTP_SERVICE_URL + "/" + uploadResults[0]+ "/" + uploadResults[1];
        } else if (null != storageClient) {
            logger.error("upload file fail, error code:" + storageClient.getErrorCode());
            return null;
        }
        return null;
    }

    public static String upload(File file) {
        String[] uploadResults = null;
        StorageClient storageClient=null;

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] fileData = new byte[inputStream.available()];
            inputStream.read(fileData);

            storageClient = getStorageClient();
            String fileName = file.getAbsolutePath();
            String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
            uploadResults = storageClient.upload_file(fileData, fileExtName, null);
        } catch (Exception e) {
            logger.error("Exception when uploading the file:" + file.getName(), e);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (null != uploadResults) {
            return HTTP_SERVICE_URL + "/" + uploadResults[0]+ "/" + uploadResults[1];
        } else if (null != storageClient) {
            logger.error("upload file fail, error code:" + storageClient.getErrorCode());
            return null;
        } else {
            logger.error("upload file fail, error code:" + storageClient.getErrorCode());
        }
        return null;
    }

}
