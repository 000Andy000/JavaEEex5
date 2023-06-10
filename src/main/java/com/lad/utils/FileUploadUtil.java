package com.lad.utils;

/**
 * @author Andy
 * @date 2023/6/6 10:55
 */
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtil {

    //设置基础路径
    private static final String BASE_FOLDER = "D:/pic";

    //主方法：传入文件和用户Id，返回图片路径名，并将图片存入对应位置
    public static String uploadFile(MultipartFile file, String userId) throws IOException {
        //获取原始名
        String originalFilename = file.getOriginalFilename();
        //获取唯一名
        String uniqueFileName = generateUniqueFileName(originalFilename);
        //获取文件夹路径（若不存在则会自动创建）
        String userFolderPath = getTargetFolderPath(userId);
        // 构建目标文件路径：用户文件夹路径 + 文件分隔符 + 唯一文件名
        String targetFilePath = userFolderPath + File.separator + uniqueFileName;
        // 创建目标文件对象
        File targetFile = new File(targetFilePath);
        // 将上传文件保存到目标路径
        file.transferTo(targetFile);
        // 返回保存后的唯一文件名
        return targetFilePath;

    }

    //传入原始名，生成唯一名
    public static String generateUniqueFileName(String originalFilename) {
        //获取后缀
        String extension = getFileExtension(originalFilename);
        return UUID.randomUUID().toString() + extension;
    }

    //获取文件后缀
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex);
        }
        return "";
    }

    private static String getTargetFolderPath(String userId) {
        // 构建用户文件夹路径：基础文件夹路径 + 文件分隔符 + 用户ID
        String userFolderPath = BASE_FOLDER + File.separator + userId;

        // 创建文件夹对象
        File folder = new File(userFolderPath);

        // 检查文件夹是否存在，如果不存在则创建
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 返回用户文件夹路径
        return userFolderPath;
    }

}

