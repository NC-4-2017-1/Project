package com.dreamteam.datavisualizator.common.selectors.impl;

import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.impl.SelectorLastErrors;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.dreamteam.datavisualizator.common.IdList.S_LAST_ERRORS_OBJTYPE_ID;

public class SelectorLastErrorsCreator extends AbstactSelectorCreator implements SelectorCreator {
    private static final Logger LOGGER = Logger.getLogger(SelectorLastErrorsCreator.class);

    @Override
    public void addSelector(Map<BigInteger, Selector> mapSelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql) {
        try {
            SelectorLastErrors selector = new SelectorLastErrors();
            SimpleJdbcCall simpleCallTemplate = new SimpleJdbcCall(generalTemplate);
            simpleCallTemplate.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_LAST_ERRORS);
            String sql_query = simpleCallTemplate.executeFunction(String.class).toString();
            ResultSetWrappingSqlRowSet results = (ResultSetWrappingSqlRowSet) templateHM.queryForRowSet(sql_query);
            selector.setName("Last DB errors");
            selector.setValue(HtmlSerializer.createHtmlTable(results, "selectorLastErrors"));
            mapSelectors.put(S_LAST_ERRORS_OBJTYPE_ID, selector);
        }catch (DataAccessException e) {
            LOGGER.error("Selector 'Last DB errors' can not be selected from HM DataBase", e);
        }catch (Exception e) {
            LOGGER.error("Selector 'Last DB errors' can not be created", e);
        }
    }

    @Override
    public String getAttrValue(Selector selector) {
        return null;
    }

    private static final String QUERY_FOR_LAST_ERRORS = "last_errors";
}
