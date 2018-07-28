package top.itning.hexoadmin.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件信息实体
 *
 * @author wangn
 */
public class MarkDownFile implements Serializable {
    /**
     * 文件路径
     */
    private String location;
    /**
     * 标题
     */
    private String title;
    /**
     * 分类
     */
    private String categories;
    /**
     * 创建时间
     */
    private Date date;
    /**
     * 更新时间
     */
    private Date updated;
    /**
     * 内容
     */
    private String content;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MarkDownFile{" +
                "location='" + location + '\'' +
                ", title='" + title + '\'' +
                ", categories='" + categories + '\'' +
                ", date=" + date +
                ", updated=" + updated +
                ", content='" + content + '\'' +
                '}';
    }
}
