package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.dreamteam.datavisualizator.common.IdList.*;

public class BeforeTemplate {


    private DataSource dataSource;
    private DataSourceTransactionManager transactionManager;
    private JdbcTemplate generalTemplate;
    private SimpleJdbcCall simpleCallTemplate;

    void raiseContext() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(com.dreamteam.datavisualizator.common.configurations.ApplicationContext.class);
        dataSource = (DataSource) applicationContext.getBean("dataSource");
        transactionManager = (DataSourceTransactionManager) applicationContext.getBean("transactionManager");
        generalTemplate = (JdbcTemplate) applicationContext.getBean("generalTemplate");
        simpleCallTemplate = (SimpleJdbcCall) applicationContext.getBean("simpleCallTemplate");
    }

    public static void main(String[] args) {
        BeforeTemplate bt = new BeforeTemplate();
        bt.raiseContext();
        bt.codeGoesHere();
    }

    public List<DataVisualizationProject> getProjectsByAuthor(Integer id) {
        return generalTemplate.query(SELECT_DV_PROJECT_BY_AUTHOR, new Object[]{id}, new DataVisualizationProjectRowMapper());
    }

    void codeGoesHere() {

        List<DataVisualizationProject> pr = getProjectsByAuthor(1);

       /* Project healthMonitorProject = healthMonitorProjectDAO.getProjectById(BigInteger.valueOf(87L));
        System.out.println(healthMonitorProject.getCreationDate());
        System.out.println(healthMonitorProject.getName());
        System.out.println(healthMonitorProject.getId());*/
        //System.out.println(healthMonitorProject.getCreationDate());
    }

    private class DataVisualizationProjectRowMapper implements RowMapper<DataVisualizationProject> {
        public DataVisualizationProject mapRow(ResultSet rs, int rownum) throws SQLException {
            DataVisualizationProject.Builder builder = new DataVisualizationProject.Builder(
                    rs.getString(DVProjectColumnName.name.toString()),
                    rs.getDate(DVProjectColumnName.createDate.toString()),
                    BigInteger.valueOf(rs.getLong(DVProjectColumnName.author.toString()))
            );
            builder.id(BigInteger.valueOf(rs.getLong(DVProjectColumnName.id.toString())));
            builder.description(rs.getString(DVProjectColumnName.description.toString()));
//            builder.usersProjectAccessible();
//            builder.graphics();
            return builder.build();
        }
    }

    private enum DVProjectColumnName {
        id("id"),
        name("name"),
        createDate("create_date"),
        author("author"),
        description("description");
        private final String columnName;

        private DVProjectColumnName(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private static String SELECT_DV_PROJECT_BY_AUTHOR="select objects.object_id id, objects.name name, creation_date.date_value creation_date,author.object_id author, description.value description" +
            " from OBJECTS, ATTRIBUTES creation_date,Objects author, ATTRIBUTES description, OBJREFERENCE ref" +
            " WHERE OBJECTS.OBJECT_ID=creation_date.object_id" +
            " AND creation_date.ATTR_id=" + PROJECT_DATE_ATTR_ID +
            " and OBJECTS.OBJECT_ID=description.object_id" +
            " and description.ATTR_ID=" + PROJECT_DESCRIPTION_ATTR_ID +
            " and ref.ATTR_ID=" + PROJECT_AUTHOR_RELATION_ATTR_ID +
            " and ref.OBJECT_ID=OBJECTS.OBJECT_ID" +
            " and ref.REFERENCE=author.OBJECT_ID" +
            " and author.OBJECT_ID=?";


}
