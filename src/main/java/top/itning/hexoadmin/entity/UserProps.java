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
    /**
     * hexo.cmd 文件所在位置<br/>
     * G:\\Library_WEB\\hexo.cmd
     */
    private String hexoCmdPath;
    /**
     * 工作目录<br/>
     * G:\\ProjectData\\WebstormProjects\\itningblog\\
     */
    private String workingDir;

    public String getMarkDownPath() {
        return markDownPath;
    }

    /**
     * Markdown 文件路径
     */
    public void setMarkDownPath(String markDownPath) {
        this.markDownPath = markDownPath;
    }

    public String getDbFilePath() {
        return dbFilePath;
    }

    /**
     * 数据库存放路径
     */
    public void setDbFilePath(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }

    public String getHexoCmdPath() {
        return hexoCmdPath;
    }

    /**
     * hexo.cmd 文件所在位置<br/>
     * G:\\Library_WEB\\hexo.cmd
     */
    public void setHexoCmdPath(String hexoCmdPath) {
        this.hexoCmdPath = hexoCmdPath;
    }

    public String getWorkingDir() {
        return workingDir;
    }

    /**
     * 工作目录<br/>
     * G:\\ProjectData\\WebstormProjects\\itningblog\\
     */
    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }
}
