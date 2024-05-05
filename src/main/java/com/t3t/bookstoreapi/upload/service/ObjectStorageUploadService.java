package com.t3t.bookstoreapi.upload.service;

import com.t3t.bookstoreapi.property.ObjectStorageProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Object Storage에 파일을 업로드하는 서비스 클래스 <br>
 * 파일을 업로드할 때는 인증 토큰을 가져와서 요청 헤더에 추가하고, 파일을 요청 본문에 추가하여 업로드함 <br>
 * @author Yujin-nKim(김유진)
 */
@Component
@RequiredArgsConstructor
public class ObjectStorageUploadService {
    private final ObjectStorageAuthService authService;
    private final ObjectStorageProperties objectStorageProperties;
    private final RestTemplate restTemplate;

    /**
     * 지정된 컨테이너, 폴더 및 객체에 대한 업로드 URL을 생성
     * @param containerName 컨테이너의 이름
     * @param folderName 폴더의 이름
     * @param objectName 객체의 이름
     * @return 생성된 업로드 URL
     * @author Yujin-nKim(김유진)
     */
    private String getUrl(@NonNull String containerName, @NonNull String folderName, @NonNull String objectName) {
        return objectStorageProperties.getAuthUrl() + "/" + containerName + "/"  + folderName  + "/" + objectName;
    }

    /**
     * 지정된 컨테이너/폴더에 object 파일을 업로드
     * @param containerName 업로드할 컨테이너의 이름
     * @param folderName 업로드할 폴더의 이름
     * @param objectName 업로드할 객체의 이름
     * @param file 업로드할 파일
     * @author Yujin-nKim(김유진)
     */
    public void uploadObject(String containerName, String folderName, String objectName, final MultipartFile file) {
        String url = this.getUrl(containerName, folderName, objectName);
        String tokenId = authService.getToken();

        // InputStream을 요청 본문에 추가할 수 있도록 RequestCallback 오버라이드
        final RequestCallback requestCallback = new RequestCallback() {
            public void doWithRequest(final ClientHttpRequest request) throws IOException {
                request.getHeaders().add("X-Auth-Token", tokenId);
                IOUtils.copy(file.getInputStream(), request.getBody());
            }
        };

        // 오버라이드한 RequestCallback을 사용할 수 있도록 설정
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);

        HttpMessageConverterExtractor<String> responseExtractor
                = new HttpMessageConverterExtractor<String>(String.class, restTemplate.getMessageConverters());

        // API 호출
        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
    }
}