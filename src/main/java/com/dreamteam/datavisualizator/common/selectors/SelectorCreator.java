package com.dreamteam.datavisualizator.common.selectors;

import com.dreamteam.datavisualizator.models.Selector;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface SelectorCreator {
    public void addSelector(Map<BigInteger, Selector> mapSelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql);
    public String getAttrValue(Selector selector);
}
