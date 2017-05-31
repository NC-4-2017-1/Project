package com.dreamteam.datavisualizator.common.selectors.impl;

import com.dreamteam.datavisualizator.common.exceptions.SelectorCreateException;
import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.impl.SelectorDBLocks;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.dreamteam.datavisualizator.common.IdList.S_DB_LOCKS_OBJTYPE_ID;

public class SelectorDBLocksCreator extends AbstactSelectorCreator implements SelectorCreator {
    private static final Logger LOGGER = Logger.getLogger(SelectorDBLocksCreator.class);

    @Override
    public void addSelector(Map<BigInteger, Selector> mapSelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql) {
        try {
            SelectorDBLocks selector = new SelectorDBLocks();
            SimpleJdbcCall simpleCallTemplate = new SimpleJdbcCall(generalTemplate);
            simpleCallTemplate.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_DB_LOCKS);
            String sql_query = simpleCallTemplate.executeFunction(String.class).toString();
            ResultSetWrappingSqlRowSet results = (ResultSetWrappingSqlRowSet) templateHM.queryForRowSet(sql_query);
            selector.setName("DB locks");
            selector.setValue(HtmlSerializer.createHtmlTable(results, "selectorDbLocks"));
            mapSelectors.put(S_DB_LOCKS_OBJTYPE_ID, selector);
        }catch (BadSqlGrammarException e) {
            LOGGER.error("Selector 'DB locks' can not be selected from HM DataBase", e);
            throw  new SelectorCreateException("Selector 'DB locks' can not be selected from HM DataBase. " + e.getSQLException().getLocalizedMessage());
        }catch (DataAccessException e) {
            LOGGER.error("Selector 'DB locks' can not be selected from HM DataBase", e);
            throw  new SelectorCreateException("Selector 'DB locks' can not be selected from HM DataBase");
        }catch (Exception e) {
            LOGGER.error("Selector 'DB locks' can not be created", e);
            throw  new SelectorCreateException("Selector 'DB locks' can not be created");
        }
    }

    @Override
    public String getAttrValue(Selector selector) {
        return null;
    }

    private static final String QUERY_FOR_DB_LOCKS = "db_locks";
}
