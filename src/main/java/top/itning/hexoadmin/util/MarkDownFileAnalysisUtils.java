package top.itning.hexoadmin.util;

import top.itning.hexoadmin.entity.MarkDownFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * MarkDown文件解析工具类
 *
 * @author wangn
 */
public class MarkDownFileAnalysisUtils {
    /**
     * 扩展名
     */
    public static final String EXTENSION_NAME = ".md";

    private MarkDownFileAnalysisUtils() {

    }

    /**
     * 从给定的文件中解析MarkDown
     *
     * @param markDownFile File
     * @return MarkDownFile实体
     */
    public static MarkDownFile analysisMarkDownFileFromFile(File markDownFile) {
        if (!markDownFile.getPath().endsWith(EXTENSION_NAME)) {
            throw new IllegalArgumentException("this file name not end with .md");
        }
        MarkDownFile newMarkDownFile = new MarkDownFile();
        newMarkDownFile.setLocation(markDownFile.getName());
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(markDownFile));
            String newLine;
            while ((newLine = bufferedReader.readLine()) != null) {
                if (newLine.startsWith("title")) {
                    newMarkDownFile.setTitle(getLineStringValue(newLine).orElse(""));
                }
                if (newLine.startsWith("date")) {
                    newMarkDownFile.setDate(getLineDateValue(newLine).orElse(null));
                }
                if (newLine.startsWith("updated")) {
                    newMarkDownFile.setUpdated(getLineDateValue(newLine).orElse(null));
                }
                if (newLine.startsWith("categories")) {
                    newMarkDownFile.setCategories(getLineStringValue(newLine).orElse(""));
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newMarkDownFile;
    }

    /**
     * 取给定的字符串中第一个冒号后面的字符<br/>
     * 例 <code>title: C++ 构造函数 笔记<code/><br/>
     * 返回 <code>C++ 构造函数 笔记<code/>
     *
     * @param line 给定的字符串
     * @return 可能为空的字符串
     */
    private static Optional<String> getLineStringValue(String line) {
        int index = line.indexOf(":");
        if (index == -1 || index + 1 == line.length()) {
            return Optional.empty();
        }
        return Optional.of(line.substring(index + 2));
    }

    /**
     * 取给定的字符串中第一个冒号后面的字符并转化成Date对象<br/>
     *
     * @param line 给定的字符串
     * @return 可能为空的Date对象
     */
    private static Optional<Date> getLineDateValue(String line) {
        Optional<String> stringOptional = getLineStringValue(line);
        if (stringOptional.isPresent()) {
            String dateStr = stringOptional.get();
            //2018-06-19 20:57:34
            try {
                return Optional.ofNullable(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
            } catch (ParseException e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
