package top.itning.hexoadmin.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用户自定义配置
 *
 * @author wangn
 */

@Component
@ConfigurationProperties(prefix = "user-props")
public class UserProps {
    /**
     * Markdown 文件路径
     */
    private String markDownPath;
    /**
     * 数据库存放路径
     */
    private String dbFilePath;

    public String getMarkDownPath() {
        return markDownPath;
    }

    public void setMarkDownPath(String markDownPath) {
        this.markDownPath = markDownPath;
    }

    public String getDbFilePath() {
        return dbFilePath;
    }

    public void setDbFilePath(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }
}
