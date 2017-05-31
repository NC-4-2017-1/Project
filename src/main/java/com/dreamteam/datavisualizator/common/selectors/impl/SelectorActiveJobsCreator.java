package com.dreamteam.datavisualizator.common.selectors.impl;

import com.dreamteam.datavisualizator.common.exceptions.SelectorCreateException;
import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.impl.SelectorActiveJobs;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.dreamteam.datavisualizator.common.IdList.S_ACTIVE_JOBS_OBJTYPE_ID;

public class SelectorActiveJobsCreator extends AbstactSelectorCreator implements SelectorCreator {
    private static final Logger LOGGER = Logger.getLogger(SelectorActiveJobsCreator.class);

    @Override
    public void addSelector(Map<BigInteger, Selector> mapSelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql) {
        try {
            SelectorActiveJobs selector = new SelectorActiveJobs();
            SimpleJdbcCall simpleCallTemplate = new SimpleJdbcCall(generalTemplate);
            int hourCount = Integer.parseInt(whereSql);
            selector.setHourCount(hourCount);
            simpleCallTemplate.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_ACTIVE_JOBS);
            SqlParameterSource in = new MapSqlParameterSource().addValue("hour_count", hourCount);
            String sql_query = simpleCallTemplate.executeFunction(String.class, in).toString();
            ResultSetWrappingSqlRowSet results = (ResultSetWrappingSqlRowSet) templateHM.queryForRowSet(sql_query);
            selector.setName("Active jobs");
            selector.setValue(HtmlSerializer.createHtmlTable(results, "selectorActiveJobs"));
            mapSelectors.put(S_ACTIVE_JOBS_OBJTYPE_ID, selector);
        }catch (BadSqlGrammarException e) {
            LOGGER.error("Selector 'Active jobs' can not be selected from HM DataBase", e);
            throw  new SelectorCreateException("Selector 'Active jobs' can not be selected from HM DataBase. " + e.getSQLException().getLocalizedMessage());
        }catch (DataAccessException e) {
            LOGGER.error("Selector 'Active jobs' can not be selected from HM DataBase", e);
            throw  new SelectorCreateException("Selector 'Active jobs' can not be selected from HM DataBase");
        }catch (Exception e) {
            LOGGER.error("Selector 'Active jobs' can not be created", e);
            throw  new SelectorCreateException("Selector 'Active jobs' can not be created");
        }
    }

    @Override
    public String getAttrValue(Selector selector){
        int hourCount = ((SelectorActiveJobs)selector).getHourCount();
        return Integer.toString(hourCount);
    }

    private static final String QUERY_FOR_ACTIVE_JOBS = "active_jobs";
}
