package com.t3t.bookstoreapi.book.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BookServiceUtils {

    private BookServiceUtils() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    /**
     * 이미지 리스트에서 빈 파일을 제거
     * @author Yujin-nKim(김유진)
     */
    public static List<MultipartFile> removeEmptyImages(List<MultipartFile> imageList) {
        Iterator<MultipartFile> iterator = imageList.iterator();
        while (iterator.hasNext()) {
            MultipartFile image = iterator.next();
            if (image.getSize() == 0) {
                iterator.remove();
            }
        }
        return imageList;
    }

    /**
     * 업로드할 파일의 이름을 생성
     *
     * @param file 업로드할 파일
     * @return 생성된 파일 이름
     * @author Yujin-nKim(김유진)
     */
    public static String generateUploadFileName(MultipartFile file) {
        UUID uuid = UUID.randomUUID(); // UUID 생성
        String originalFilename = file.getOriginalFilename(); // 파일의 원본 이름 가져오기
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")); // 파일 확장자 가져오기
        return uuid.toString() + fileExtension; // 생성된 UUID와 확장자를 결합하여 파일 이름 반환
    }
}
