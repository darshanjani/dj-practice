package com.dj;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AmazonS3Test
{
    private static final String BUCKET_NAME = "jogetupload";
    private static AmazonS3 s3Client;

    @BeforeClass
    public static void beforeClass() {
        java.security.Security.setProperty("networkaddress.cache.ttl" , "60");
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }

    @Test
    public void listBuckets() {
        List<Bucket> buckets = s3Client.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket);
        }
    }

    @Test
    @Ignore
    public void uploadFile() {
        s3Client.putObject(BUCKET_NAME, "receipt1.jpg", new File("C:\\receipts\\receipt1.jpg"));
        s3Client.putObject(BUCKET_NAME, "receipt2.jpg", new File("C:\\receipts\\receipt2.jpg"));
    }

    @Test
    @Ignore
    public void listObjects() {
        ObjectListing objectListing = s3Client.listObjects(BUCKET_NAME);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (S3ObjectSummary objectSummary : objectSummaries) {
            System.out.println("* " + objectSummary.getKey());
        }
    }

    @Test
    @Ignore
    public void downloadObject() throws Exception {
        S3Object o = s3Client.getObject(BUCKET_NAME, "receipt1.jpg");
        S3ObjectInputStream s3is = o.getObjectContent();
        FileOutputStream fos = new FileOutputStream(new File("C:\\temp\\receipt1.jpg"));
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = s3is.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        s3is.close();
        fos.close();
    }

    @Test
    @Ignore
    public void deleteObject() {
        s3Client.deleteObject(BUCKET_NAME, "receipt2.jpg");
    }

    @Test
    public void generateSignedUrl() throws Exception {
        java.util.Date expiration = new java.util.Date();
        long msec = expiration.getTime();
        msec += 1000 * 60; // 1 hour.
        expiration.setTime(msec);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(BUCKET_NAME, "receipt1.jpg");
        generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
        generatePresignedUrlRequest.setExpiration(expiration);

        URL s = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        System.out.println(s.toString());
    }
}
