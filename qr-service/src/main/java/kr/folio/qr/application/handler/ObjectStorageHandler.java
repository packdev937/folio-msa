package kr.folio.qr.application.handler;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class ObjectStorageHandler {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public Mono<String> uploadFile(byte[] fileByte) {
        String keyName = "/" + UUID.randomUUID();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileByte.length);
        objectMetadata.setContentType("image/jpeg");

        return Mono.fromCallable(() -> {
            try (InputStream inputStream = new ByteArrayInputStream(fileByte)) {
	amazonS3Client.putObject(
	    new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
	        .withCannedAcl(CannedAccessControlList.PublicRead));

	return "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;
            } catch (Exception e) {
	throw new RuntimeException(e);
            }
        });
    }
}
