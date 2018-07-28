package top.itning.hexoadmin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果集执行器
 *
 * @author itning
 */
public interface ResultSetExtractor<T> {
    /**
     * 执行数据返回
     *
     * @param rs {@link ResultSet}
     * @return T
     * @throws SQLException SQLException
     */
    T extractData(ResultSet rs) throws SQLException;
}