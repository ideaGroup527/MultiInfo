package org.jmu.multiinfo.base.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.jmu.multiinfo.exception.DAOSysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceUtils;


public class BaseJdbcDao {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    /**
     * jdbcTemplate
     *
     * @return
     */
    public final JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    /**
     * simpleJdbcTemplate
     *
     * @return
     */
    public final NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return this.namedParameterJdbcTemplate;
    }

    /**
     * simpleJdbcInsert
     *
     * @return
     */
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    /**
     * jdbcTemplate
     *
     * @param dataSource
     * @return
     */
    public final JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        if (dataSource == null) {
            return this.jdbcTemplate;
        }
        return new JdbcTemplate(dataSource);
    }

    /**
     * simpleJdbcTemplate
     *
     * @param dataSource
     * @return
     */
    public final NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(DataSource dataSource) {
        if (dataSource == null) {
            return this.namedParameterJdbcTemplate;
        }
        return new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * simpleJdbcInsert
     *
     * @param dataSource
     * @return
     */
    public SimpleJdbcInsert getSimpleJdbcInsert(DataSource dataSource) {
        if (dataSource == null) {
            return this.simpleJdbcInsert;
        }
        return new SimpleJdbcInsert(dataSource);
    }

    /**
     * ��ѯ���������ֵ������ֱ�ӵ���spring��queryForObject�������¼�����ڵĻ������׳��쳣��Ϊ�˱����쳣���֣�����ڻ�����˸÷���
     *
     * @param <T>
     * @param sql
     * @param rm
     * @param args
     * @return
     */
    public <T> T queryForObject(String sql, RowMapper<T> rm) {

        List<T> queryList = getNamedParameterJdbcTemplate().query(sql, rm);

        if (queryList == null || queryList.size() < 1)
            return null;

        return queryList.get(0);
    }

    /**
     * �ж������Ƿ����
     *
     * @param rs
     * @param columnName
     * @return
     */
    public boolean isExsitedColumn(ResultSet rs, String columnName) {

        if (rs == null || StringUtils.isEmpty(columnName))
            return false;

        try {
            int columnCount = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                if (columnName.equalsIgnoreCase(rs.getMetaData().getColumnName(i)))
                    return true;
            }
        } catch (SQLException e) {
            throw new DataAccessResourceFailureException(e.getMessage(), e);
        }
        return false;
    }

    /**
     * �õ����ݿ�����
     *
     * @return Connection
     * @throws SQLException
     */
    protected Connection getDataSourceUtilsConnection() throws SQLException {
        return DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
    }

    /**
     * �ر����ݿ�������Դ
     *
     * @param conn ���ݿ�����
     * @param st   ִ�е�statement
     * @param rs   �α�����
     */
    protected void closeConn(Connection conn, Statement st, ResultSet rs) {
        cleanUp(conn, null, st, rs);
    }

    /**
     * �ر����ݿ�������Դ
     *
     * @param conn ���ݿ�����
     * @param cs   ִ�еĴ洢����statement
     * @param ps   ִ�е�statement
     * @param rs   �α�����
     */
    protected void cleanUp(Connection conn, CallableStatement cs, Statement ps, ResultSet rs) {
        try {
            try {
                if (rs != null)
                    rs.close();
            } finally {
                try {
                    if (ps != null)
                        ps.close();
                } finally {
                    try {
                        if (cs != null)
                            cs.close();
                    } finally {
                        if (conn != null) {
                            DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
                            conn = null;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DAOSysException("clean database resource error:" + ex.getMessage());
        }
    }

}
