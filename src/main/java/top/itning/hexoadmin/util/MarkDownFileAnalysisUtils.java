package top.itning.hexoadmin.util;

import com.google.common.base.CharMatcher;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.itning.hexoadmin.entity.MarkDownFile;

import java.io.*;
import java.nio.charset.Charset;
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
    private static final Logger logger = LoggerFactory.getLogger(MarkDownFileAnalysisUtils.class);
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
        try {
            newMarkDownFile.setLocation(markDownFile.getName());
            BufferedReader bufferedReader = new BufferedReader(new FileReader(markDownFile));
            analysisMarkDown(newMarkDownFile, bufferedReader);
        } catch (FileNotFoundException e) {
            logger.error("file not found error ", e);
        }
        return newMarkDownFile;
    }

    /**
     * 解析MarkDown
     *
     * @param newMarkDownFile 实体
     * @param bufferedReader  BufferedReader
     */
    private static void analysisMarkDown(MarkDownFile newMarkDownFile, BufferedReader bufferedReader) {
        try {
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
            logger.error("error ", e);
        }
    }

    /**
     * 从给定的MarkDownFile实体中Content属性解析
     *
     * @param markDownFile 实体
     * @return 解析完的MarkDownFile
     */
    public static MarkDownFile analysisMarkDownFromEntity(MarkDownFile markDownFile) {
        if (StringUtils.isBlank(markDownFile.getContent())) {
            return markDownFile;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(markDownFile.getContent().getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        analysisMarkDown(markDownFile, br);
        return markDownFile;
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
        return Optional.of(StringUtils.trim(CharMatcher.is('\'').trimFrom(line.substring(index + 2))));
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
