package com.dreamteam.datavisualizator.common.selectors.impl;

import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.impl.SelectorActiveQueries;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.util.List;

public class SelectorActiveQueriesCreator extends AbstactSelectorCreator implements SelectorCreator {
    private static final Logger LOGGER = Logger.getLogger(SelectorActiveQueriesCreator.class);

    @Override
    public void addSelector(List<Selector> arraySelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql) {
        try{
            SelectorActiveQueries selector = new SelectorActiveQueries();
            SimpleJdbcCall simpleCallTemplate = new SimpleJdbcCall(generalTemplate);
            int top = Integer.parseInt(whereSql);
            selector.setTop(top);
            simpleCallTemplate.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_ACTIVE_QUERIES);
            SqlParameterSource in = new MapSqlParameterSource().addValue("top", top);
            String sql_query = simpleCallTemplate.executeFunction(String.class, in).toString();
            ResultSetWrappingSqlRowSet results = (ResultSetWrappingSqlRowSet) templateHM.queryForRowSet(sql_query);
            selector.setName("Active queries");
            selector.setValue(HtmlSerializer.createHtmlTable(results, "selectorActiveQueries"));
            arraySelectors.add(selector);
        }catch (DataAccessException e) {
            LOGGER.error("Selector 'Active queries' can not be selected from HM DataBase", e);
        }catch (Exception e) {
            LOGGER.error("Selector 'Active queries' can not be created", e);
        }
    }

    private static final String QUERY_FOR_ACTIVE_QUERIES = "active_queries";
}
