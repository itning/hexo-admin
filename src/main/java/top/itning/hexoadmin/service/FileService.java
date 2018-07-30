package top.itning.hexoadmin.service;

import top.itning.hexoadmin.entity.MarkDownFile;

import java.util.List;

/**
 * 文件服务
 *
 * @author wangn
 */
public interface FileService {
    /**
     * 获取所有文件
     *
     * @return 文件集合
     */
    List<MarkDownFile> getAllFileList();

    /**
     * 根据文件名获取文件
     *
     * @param fileName 文件名
     * @return MarkDownFile
     */
    MarkDownFile getMarkDownFileByFileName(String fileName);

    /**
     * 备份文件
     */
    void backupMarkDownFiles();

    /**
     * 保存文件或更新文件
     *
     * @param markDownFile MarkDownFile
     */
    void saveMarkDownFile(MarkDownFile markDownFile);

    /**
     * 删除文件
     *
     * @param location 路径
     */
    void delete(String location);
}
