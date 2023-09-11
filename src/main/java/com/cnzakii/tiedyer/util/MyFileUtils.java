package com.cnzakii.tiedyer.util;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.cnzakii.tiedyer.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.cnzakii.tiedyer.common.constant.SystemConstants.FILE_ROOT_PATH;
import static com.cnzakii.tiedyer.common.http.ResponseStatus.SERVER_ERROR;

/**
 * 用于处理文件上传和删除的工具类。
 * 该类提供了上传文件和删除文件的常用方法。
 *
 * @author Zaki
 * @since 2023-09-04
 **/
@Slf4j
public class MyFileUtils {

    /**
     * 上传文件到指定子路径下。
     *
     * @param subPath 目标子路径
     * @param file    要上传的文件
     * @return 上传后的文件路径（包含子路径和文件名）
     */
    public static String uploadFile(String subPath, MultipartFile file) {
        String oldFileName = file.getOriginalFilename();

        if (StrUtil.isEmpty(oldFileName)) {
            log.warn("文件名为空");
            return null;
        }

        String newFileName = IdUtil.simpleUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));

        File dest = new File(FILE_ROOT_PATH + subPath + newFileName);

        // 判断文件目录是否存在
        if (!dest.getParentFile().exists()) {
            log.info("创建目录{}", dest.getParentFile());
            boolean b = dest.getParentFile().mkdirs();
            if (!b) {
                log.error("目录({})创建失败", dest.getParentFile());
                return null;
            }
        }

        try {
            // 保存文件
            file.transferTo(dest);
        } catch (IOException exception) {
            log.error("文件保存失败=====》{}", exception.getLocalizedMessage());
            return null;
        }

        return subPath + newFileName;
    }


    /**
     * 删除指定文件。
     *
     * @param filePath 文件路径（包含文件名，不包含根路径）
     */
    public static void deleteFile(String filePath) {
        String path = FILE_ROOT_PATH + filePath;
        File file = new File(path);

        if (!file.exists()) {
            log.warn("文件{}不存在", file);
            return;
        }

        boolean deleted = file.delete();
        if (BooleanUtil.isFalse(deleted)) {
            log.error("文件删除失败，filePath===》{}", path);
            throw new BusinessException(SERVER_ERROR, "文件删除失败");
        }
    }
}
