package com.dreamteam.datavisualizator.common.selectors.impl;

import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.impl.SelectorInstanceInfo;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.util.List;

public class SelectorInstanceInfoCreator extends AbstactSelectorCreator implements SelectorCreator{
    private static final Logger LOGGER = Logger.getLogger(SelectorInstanceInfoCreator.class);

    @Override
    public void addSelector(List<Selector> arraySelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql) {
        try {
            SelectorInstanceInfo selector = new SelectorInstanceInfo();
            SimpleJdbcCall simpleCallTemplate = new SimpleJdbcCall(generalTemplate);
            simpleCallTemplate.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_INSTANCE_INFO);
            String sql_query = simpleCallTemplate.executeFunction(String.class).toString();
            ResultSetWrappingSqlRowSet results = (ResultSetWrappingSqlRowSet) templateHM.queryForRowSet(sql_query);
            selector.setName("Instance information");
            selector.setValue(HtmlSerializer.createHtmlTable(results, "selectorInstanceInfo"));
            arraySelectors.add(selector);
        }catch (DataAccessException e) {
            LOGGER.error("Selector 'Instance information' can not be selected from HM DataBase", e);
        }catch (Exception e) {
            LOGGER.error("Selector 'Instance information' can not be created", e);
        }
    }

    private static final String QUERY_FOR_INSTANCE_INFO = "instance_info";
}
