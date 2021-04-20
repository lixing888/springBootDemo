package com.springboot.demo.util;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;

import java.io.IOException;

public class BucketDelete {
    private static ObsClient obsClient;

    public static void main(String[] args) throws InterruptedException {
        try {
            String endPoint = "https://obs.cn-north-4.myhwclouds.com";
            String ak = "ORNXZ2IJECAKLP3EA88S";
            String sk = "Fj6unAj1cjBhHTKkbehxOQZwpLKMius9Z4c1RUx0";
            String bucketName = "bj00000test";
            /*String endPoint = "XXX";        // e.g "https://obs.cn-north-1.myhuaweicloud.com"
            String ak = "XXX";                              // 对象所属的帐户ak
            String sk = "XXX";                              // 对象所属的帐户sk
            String bucketName = "obs-chongchong-beijing";               // 需要填写为实际的桶名*/
            obsClient = new ObsClient(ak, sk, endPoint);
            //列举桶中对象并删除
            ListVersionsResult result = obsClient.listVersions(bucketName);
            if (result.getVersions().length != 0) {
                ListVersionsRequest request = new ListVersionsRequest(bucketName);
                // 设置每次批量删除500个对象
                request.setMaxKeys(500);
                ListVersionsResult result1;
                do {
                    result1 = obsClient.listVersions(request);
                    DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucketName);
                    for (VersionOrDeleteMarker v : result1.getVersions()) {
                        deleteRequest.addKeyAndVersion(v.getKey(), v.getVersionId());
                    }
                    DeleteObjectsResult deleteResult = obsClient.deleteObjects(deleteRequest);
                    // 获取删除成功的对象
                    System.out.println(deleteResult.getDeletedObjectResults());
                    request.setKeyMarker(result1.getNextKeyMarker());
                    request.setVersionIdMarker(result1.getNextVersionIdMarker());
                } while (result.isTruncated());
            }
            //删除桶
//            obsClient.deleteBucket(bucketName);
//            System.out.println("\n------------------\nbucket already delete.");
        } catch (ObsException e) {
            e.printStackTrace();
            System.out.println("Response Code: " + e.getResponseCode());
            System.out.println("Error Message: " + e.getErrorMessage());
            System.out.println("Error Code:       " + e.getErrorCode());
            System.out.println("Request ID:      " + e.getErrorRequestId());
            System.out.println("Host ID:           " + e.getErrorHostId());
        } finally {
            if (obsClient != null) {
                try {
                    obsClient.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
