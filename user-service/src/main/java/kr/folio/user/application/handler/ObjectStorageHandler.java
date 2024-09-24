package kr.folio.user.application.handler;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class ObjectStorageHandler {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final String S3_BASE_URL = "https://folio-bucket.s3.ap-northeast-2.amazonaws.com/";

    public String uploadFile(MultipartFile file) throws IOException {
        log.info("Upload File to S3 at {}", this.getClass().getSimpleName());

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명
        String s3FileName =
            UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        InputStream inputStream = file.getInputStream();
        byte[] fileBytes = IOUtils.toByteArray(inputStream); //이미지를 byte[]로 변환

        // Metadata를 생성한다
        ObjectMetadata objectMetadata = new ObjectMetadata(); //objectMetadata 생성
        objectMetadata.setContentType("image/" + extension); // Image/jpeg
        objectMetadata.setContentLength(fileBytes.length);
        objectMetadata.setContentDisposition("inline");

        //S3에 요청할 때 사용할 byteInputStream 생성
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(
	bucketName,
	s3FileName,
	byteArrayInputStream,
	objectMetadata
            );

            amazonS3Client.putObject(putObjectRequest);
        } catch (Exception exception) {
            log.error("Failed to upload image to S3", exception);
            throw new RuntimeException(exception);
        } finally {
            byteArrayInputStream.close();
            inputStream.close();
        }

        return amazonS3Client.getUrl(bucketName, s3FileName).toString();
    }
}
