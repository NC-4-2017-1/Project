package com.dreamteam.datavisualizator.common.selectors.impl;

import com.dreamteam.datavisualizator.common.exceptions.SelectorCreateException;
import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.impl.SelectorActiveSessions;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.dreamteam.datavisualizator.common.IdList.S_ACTIVE_SESSIONS_OBJTYPE_ID;

public class SelectorActiveSessionsCreator extends AbstactSelectorCreator implements SelectorCreator {
    private static final Logger LOGGER = Logger.getLogger(SelectorActiveSessionsCreator.class);

    @Override
    public void addSelector(Map<BigInteger, Selector> mapSelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql) {
        try {
            SelectorActiveSessions selector = new SelectorActiveSessions();
            SimpleJdbcCall simpleCallTemplate = new SimpleJdbcCall(generalTemplate);
            int top = Integer.parseInt(whereSql);
            selector.setTop(top);
            simpleCallTemplate.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_ACTIVE_SESSIONS);
            SqlParameterSource in = new MapSqlParameterSource().addValue("top", top);
            String sql_query = simpleCallTemplate.executeFunction(String.class, in).toString();
            ResultSetWrappingSqlRowSet results = (ResultSetWrappingSqlRowSet) templateHM.queryForRowSet(sql_query);
            selector.setName("Active sessions");
            selector.setValue(HtmlSerializer.createHtmlTable(results, "selectorActiveSessions", 0));
            mapSelectors.put(S_ACTIVE_SESSIONS_OBJTYPE_ID,selector);
        }catch (BadSqlGrammarException e) {
            LOGGER.error("Selector 'Active sessions' can not be selected from HM DataBase", e);
            throw  new SelectorCreateException("Selector 'Active sessions' can not be selected from HM DataBase. " + e.getSQLException().getLocalizedMessage());
        }catch (DataAccessException e) {
            LOGGER.error("Selector 'Active sessions' can not be selected from HM DataBase", e);
            throw  new SelectorCreateException("Selector 'Active sessions' can not be selected from HM DataBase");
        }catch (Exception e) {
            LOGGER.error("Selector 'Active sessions' can not be created", e);
            throw  new SelectorCreateException("Selector 'Active sessions' can not be created");
        }
    }

    @Override
    public String getAttrValue(Selector selector) {
        int top = ((SelectorActiveSessions)selector).getTop();
        return Integer.toString(top);
    }

    @Override
    public String getValueForShow(Selector selector) {
        String top = getAttrValue(selector);
        return "<pre class=\"text-primary selector-info-mess\">Selector 'Active sessions' have attribute Top = " + top + ".</pre>";
    }

    private static final String QUERY_FOR_ACTIVE_SESSIONS = "active_sessions";
}
