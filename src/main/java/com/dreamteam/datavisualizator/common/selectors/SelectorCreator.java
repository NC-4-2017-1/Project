package com.dreamteam.datavisualizator.common.selectors;

import com.dreamteam.datavisualizator.models.Selector;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface SelectorCreator {
    public void addSelector(List<Selector> arraySelectors, JdbcTemplate generalTemplate, JdbcTemplate templateHM, String whereSql);
}
