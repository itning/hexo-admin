package top.itning.hexoadmin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 行映射
 *
 * @author itning
 */
public interface RowMapper<T> {
    /**
     * 行映射
     *
     * @param rs    {@link ResultSet}
     * @param index 下角标
     * @return T
     * @throws SQLException SQLException
     */
    T mapRow(ResultSet rs, int index) throws SQLException;
}