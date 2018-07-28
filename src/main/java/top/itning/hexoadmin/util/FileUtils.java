package top.itning.hexoadmin.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 文件工具类
 *
 * @author wangn
 */
public class FileUtils {
    private FileUtils() {

    }

    /**
     * 根据传入File目录对象递归获取文件对象
     *
     * @param rootFolder 项目根路径
     * @param fileList   用于存储File对象的集合
     * @param filterName 过滤文件扩展名
     */
    public static void getFiles(File rootFolder, List<File> fileList, String filterName) {
        assert rootFolder != null;
        assert fileList != null;
        if (rootFolder.isDirectory()) {
            Arrays.stream(Objects.requireNonNull(rootFolder.list())).forEach(str -> getFiles(new File(rootFolder.getPath() + "/" + str), fileList, filterName));
        } else if (rootFolder.isFile() && rootFolder.getPath().endsWith(filterName) && rootFolder.length() > 0) {
            fileList.add(rootFolder);
        }
    }
}
