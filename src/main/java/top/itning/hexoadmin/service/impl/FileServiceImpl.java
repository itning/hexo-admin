package top.itning.hexoadmin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.hexoadmin.dao.SqliteHelper;
import top.itning.hexoadmin.entity.MarkDownFile;
import top.itning.hexoadmin.entity.UserProps;
import top.itning.hexoadmin.service.FileService;
import top.itning.hexoadmin.util.MarkDownFileAnalysisUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static top.itning.hexoadmin.util.FileUtils.getFiles;
import static top.itning.hexoadmin.util.MarkDownFileAnalysisUtils.EXTENSION_NAME;

/**
 * 文件服务实现
 *
 * @author wangn
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private static final List<File> FILE_LIST = new ArrayList<>();

    private final SqliteHelper sqliteHelper;

    @Autowired
    public FileServiceImpl(UserProps userProps, SqliteHelper sqliteHelper) {
        if (userProps.getMarkDownPath() == null) {
            logger.error("属性 user-props.mark-down-path 没有配置,请在配置文件中配置它们");
            throw new RuntimeException("属性 user-props.mark-down-path 没有配置,请在配置文件中配置它们");
        } else {
            logger.info("属性 user-props.mark-down-path 值为 {}", userProps.getMarkDownPath());
            getFiles(new File(userProps.getMarkDownPath()), FILE_LIST, EXTENSION_NAME);
        }
        this.sqliteHelper = sqliteHelper;
    }

    @Override
    public List<MarkDownFile> getAllFileList() {
        return FILE_LIST.stream()
                .map(MarkDownFileAnalysisUtils::analysisMarkDownFileFromFile)
                .sorted(Comparator.comparing(MarkDownFile::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public MarkDownFile getMarkDownFileByFileName(String fileName) {
        MarkDownFile markDownFile = new MarkDownFile();
        List<MarkDownFile> markDownFileList = FILE_LIST.stream()
                .filter(file -> file.getName().equals(fileName))
                .map(file -> {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                        markDownFile.setContent(bufferedReader.lines().reduce((a, b) -> {
                            a += "\n";
                            return a + b;
                        }).orElse(""));
                        bufferedReader.close();
                    } catch (IOException e) {
                        logger.error("error ", e);
                    }
                    return markDownFile;
                })
                .collect(Collectors.toList());
        if (markDownFileList.isEmpty()) {
            return markDownFile;
        }
        return markDownFileList.get(0);
    }

    @Override
    public void backupMarkDownFiles() {
        try {
            sqliteHelper.executeUpdate("delete from markdown");
            FILE_LIST.parallelStream().forEach(file -> {
                try {
                    sqliteHelper.executeUpdate("insert into markdown values(?,?)", file, file.getName());
                } catch (SQLException | ClassNotFoundException e) {
                    logger.error("error backup file ", e);
                }
            });
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("error backup file ", e);
        }
    }
}
