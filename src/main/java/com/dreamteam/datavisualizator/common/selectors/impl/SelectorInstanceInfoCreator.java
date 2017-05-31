package com.dreamteam.datavisualizator.common.selectors.impl;

import com.dreamteam.datavisualizator.common.exceptions.SelectorCreateException;
import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.dao.impl.HealthMonitorProjectDAOImpl;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.impl.SelectorInstanceInfo;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.dreamteam.datavisualizator.common.IdList.S_INSTANCE_INFO_OBJTYPE_ID;

public class SelectorInstanceInfoCreator extends AbstactSelectorCreator implements SelectorCreator{
    private static final Logger LOGGER = Logger.getLogger(SelectorInstanceInfoCreator.class);

    @Override
    public void addSelector(Map<BigInteger, Selector> mapSelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql) {
        try {
            SelectorInstanceInfo selector = new SelectorInstanceInfo();
            SimpleJdbcCall simpleCallTemplate = new SimpleJdbcCall(generalTemplate);
            simpleCallTemplate.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_INSTANCE_INFO);
            String sql_query = simpleCallTemplate.executeFunction(String.class).toString();
            ResultSetWrappingSqlRowSet results = (ResultSetWrappingSqlRowSet) templateHM.queryForRowSet(sql_query);
            selector.setName("Instance information");
            selector.setValue(HtmlSerializer.createHtmlTable(results, "selectorInstanceInfo"));
            mapSelectors.put(S_INSTANCE_INFO_OBJTYPE_ID, selector);
        }catch (BadSqlGrammarException e) {
            LOGGER.error("Selector 'Instance information' can not be selected from HM DataBase", e);
            throw  new SelectorCreateException("Selector 'Instance information' can not be selected from HM DataBase. " + e.getSQLException().getLocalizedMessage());
        }catch (DataAccessException e) {
            LOGGER.error("Selector 'Instance information' can not be selected from HM DataBase", e);
            throw  new SelectorCreateException("Selector 'Instance information' can not be selected from HM DataBase");
        }catch (Exception e) {
            LOGGER.error("Selector 'Instance information' can not be created", e);
            throw  new SelectorCreateException("Selector 'Instance information' can not be created");
        }
    }

    @Override
    public String getAttrValue(Selector selector) {
        return null;
    }

    private static final String QUERY_FOR_INSTANCE_INFO = "instance_info";
}
