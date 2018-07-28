package top.itning.hexoadmin.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.itning.hexoadmin.entity.UserProps;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Sql lite helper
 *
 * @author itning
 */
@SuppressWarnings("unused")
@Component
public class SqliteHelper {
    private final static Logger logger = LoggerFactory.getLogger(SqliteHelper.class);

    private Connection connection;
    private ResultSet resultSet;
    private String dbFilePath;

    @Autowired
    public SqliteHelper(UserProps userProps) {
        if (userProps.getDbFilePath() == null) {
            logger.error("属性 user-props.db-file-path 没有配置,请在配置文件中配置它们");
            throw new RuntimeException("属性 user-props.db-file-path 没有配置,请在配置文件中配置它们");
        } else {
            logger.info("属性 user-props.db-file-path 值为 {}", userProps.getDbFilePath());
            this.dbFilePath = userProps.getDbFilePath();
            try {
                connection = getConnection(dbFilePath);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        init();
    }

    private void init() {
        try {
            int i = executeUpdate("create table if not exists markdown (file_name text , content text)");
            logger.info("execute init table sql return {}", i);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("error init table ", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @param dbFilePath db文件路径
     * @return 数据库连接
     * @throws ClassNotFoundException if the class cannot be located
     * @throws SQLException           if a database access error occurs or the url is
     *                                {@code null}
     */
    private Connection getConnection(String dbFilePath) throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
        return conn;
    }

    /**
     * 执行sql查询
     *
     * @param sql sql select 语句
     * @param rse 结果集处理类对象
     * @return 查询结果
     * @throws SQLException           see {@link #getConnection(String)} method
     * @throws ClassNotFoundException see {@link #getConnection(String)} method
     */
    public <T> T executeQuery(String sql, ResultSetExtractor<T> rse) throws SQLException, ClassNotFoundException {
        resultSet = getConnection().createStatement().executeQuery(sql);
        return rse.extractData(resultSet);
    }

    /**
     * 执行select查询，返回结果列表
     *
     * @param sql sql select 语句
     * @param rm  结果集的行数据处理类对象
     * @return 结果列表
     * @throws SQLException           see {@link #getConnection(String)} method
     * @throws ClassNotFoundException see {@link #getConnection(String)} method
     */
    public <T> List<T> executeQuery(String sql, RowMapper<T> rm) throws SQLException, ClassNotFoundException {
        List<T> rsList = new ArrayList<>();
        resultSet = getConnection().createStatement().executeQuery(sql);
        while (resultSet.next()) {
            rsList.add(rm.mapRow(resultSet, resultSet.getRow()));
        }
        return rsList;
    }

    /**
     * 执行数据库更新sql语句
     *
     * @param sql sql语句
     * @return 更新行数
     * @throws SQLException           see {@link #getConnection(String)} method
     * @throws ClassNotFoundException see {@link #getConnection(String)} method
     */
    public int executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        return getConnection().createStatement().executeUpdate(sql);
    }

    /**
     * 执行数据库更新sql语句
     *
     * @param sql sql语句
     * @return 更新行数
     * @throws SQLException           see {@link #getConnection(String)} method
     * @throws ClassNotFoundException see {@link #getConnection(String)} method
     */
    public int executeUpdate(String sql, byte[] bytes) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setBytes(1, bytes);
        return ps.executeUpdate();
    }

    /**
     * 执行数据库更新sql语句
     *
     * @param sql 新sql语句
     * @return 更新行数
     * @throws SQLException           see {@link #getConnection(String)} method
     * @throws ClassNotFoundException see {@link #getConnection(String)} method
     */
    public int executeUpdate(String sql, String fileName) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ps.setBytes(1, readFile(fileName));
        return ps.executeUpdate();
    }

    /**
     * 执行数据库更新sql语句
     *
     * @param sql 新sql语句
     * @return 更新行数
     * @throws SQLException           see {@link #getConnection(String)} method
     * @throws ClassNotFoundException see {@link #getConnection(String)} method
     */
    public int executeUpdate(String sql, File file, String... args) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        if (args != null) {
            ps.setBytes(args.length + 1, readFile(file));
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, args[i]);
            }
        } else {
            ps.setBytes(1, readFile(file));
        }
        return ps.executeUpdate();
    }

    /**
     * 执行多个sql更新语句
     *
     * @param sqls 多个sql更新语句
     * @throws SQLException           see {@link #getConnection(String)} method
     * @throws ClassNotFoundException see {@link #getConnection(String)} method
     */
    public void executeUpdate(String... sqls) throws SQLException, ClassNotFoundException {
        for (String sql : sqls) {
            getConnection().createStatement().executeUpdate(sql);
        }
    }

    /**
     * 执行数据库更新 sql List
     *
     * @param sqls sql列表
     * @throws SQLException           see {@link #getConnection(String)} method
     * @throws ClassNotFoundException see {@link #getConnection(String)} method
     */
    public void executeUpdate(List<String> sqls) throws SQLException, ClassNotFoundException {
        for (String sql : sqls) {
            getConnection().createStatement().executeUpdate(sql);
        }
    }

    /**
     * 获得连接
     *
     * @return Connection
     * @throws ClassNotFoundException see {@link #getConnection(String)} method
     * @throws SQLException           see {@link #getConnection(String)} method
     */
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        if (null == connection) {
            connection = getConnection(dbFilePath);
        }
        return connection;
    }

    /**
     * 数据库资源关闭和释放
     */
    public void destroyed() {
        try {
            if (null != connection) {
                connection.close();
                connection = null;
            }

            if (null != resultSet) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {
            logger.error("Sqlite数据库关闭时异常", e);
        }
    }

    /**
     * 读文件返回字节数组
     *
     * @param file 文件路径
     * @return 字节数组
     */
    private byte[] readFile(String file) {
        File f = new File(file);
        return readFile(f);
    }

    /**
     * 读文件返回字节数组
     *
     * @param file 文件
     * @return 字节数组
     */
    private byte[] readFile(File file) {
        ByteArrayOutputStream bos = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1; ) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }
}