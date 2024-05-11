package com.t3t.bookstoreapi.book.util;

import com.t3t.bookstoreapi.property.ObjectStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookServiceUtils {
    private static ObjectStorageProperties objectStorageProperties;
    private static final String CONTAINER_PREFIX = "/t3team/";
    private static final String THUMBNAIL_PREFIX = "book_thumbnails/";
    private static final String BOOK_IMAGE_PREFIX = "book_images/";

    @Autowired
    private BookServiceUtils(ObjectStorageProperties objectStorageProperties) {
        this.objectStorageProperties = objectStorageProperties;
    }

    /**
     * 썸네일 이미지 URL의 prefix를 설정
     * @param imageUrl 원본 이미지 URL
     * @return prefix가 추가된 수정된 이미지 URL
     * @author Yujin-nKim(김유진)
     */
    public static String setThumbnailImagePrefix(String imageUrl) {
        return objectStorageProperties.getStorageUrl() + CONTAINER_PREFIX + THUMBNAIL_PREFIX + imageUrl;
    }

    /**
     * 미리보기 이미지 URL 목록의 prefix를 설정합니다.
     * @param imageUrlList 원본 이미지 URL 목록
     * @return prefix가 추가된 수정된 이미지 URL 목록
     * @author Yujin-nKim(김유진)
     */
    public static List<String> setBookImagePrefix(List<String> imageUrlList) {
        List<String> modifiedUrls = new ArrayList<>();
        for(String imageUrl : imageUrlList) {
            modifiedUrls.add(objectStorageProperties.getStorageUrl() + CONTAINER_PREFIX + BOOK_IMAGE_PREFIX + imageUrl);
        }
        return modifiedUrls;
    }
}