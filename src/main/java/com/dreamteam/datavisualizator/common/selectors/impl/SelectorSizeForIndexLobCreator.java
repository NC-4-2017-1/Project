package com.dreamteam.datavisualizator.common.selectors.impl;

import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import com.dreamteam.datavisualizator.models.Selector;
import com.dreamteam.datavisualizator.models.impl.SelectorSizeForIndexLob;
import com.dreamteam.datavisualizator.services.HtmlSerializer;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

import java.util.List;

public class SelectorSizeForIndexLobCreator extends AbstactSelectorCreator implements SelectorCreator {
    private static final Logger LOGGER = Logger.getLogger(SelectorSizeForIndexLobCreator.class);

    @Override
    public void addSelector(List<Selector> arraySelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql) {
        try {
            SelectorSizeForIndexLob selector = new SelectorSizeForIndexLob();
            SimpleJdbcCall simpleCallTemplate = new SimpleJdbcCall(generalTemplate);
            selector.setSegment(whereSql);
            simpleCallTemplate.withCatalogName(SELECTOR_QUERIES_PACKAGE).withFunctionName(QUERY_FOR_SIZE_INDEX_LOB);
            SqlParameterSource in = new MapSqlParameterSource().addValue("segment", whereSql);
            String sql_query = simpleCallTemplate.executeFunction(String.class, in).toString();
            ResultSetWrappingSqlRowSet results = (ResultSetWrappingSqlRowSet) templateHM.queryForRowSet(sql_query);
            selector.setName("Size for table-index-lob");
            selector.setValue(HtmlSerializer.createHtmlTable(results, "selectorSizeForIndexLob"));
            arraySelectors.add(selector);
        }catch (DataAccessException e) {
            LOGGER.error("Selector 'Size for table-index-lob' can not be selected from HM DataBase", e);
        }catch (Exception e) {
            LOGGER.error("Selector 'Size for table-index-lob' can not be created", e);
        }
    }

    private static final String QUERY_FOR_SIZE_INDEX_LOB = "size_for_index_lob";
}
