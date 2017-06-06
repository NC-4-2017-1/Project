package com.dreamteam.datavisualizator.common.selectors.impl;


import com.dreamteam.datavisualizator.common.selectors.SelectorCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


public abstract class AbstactSelectorCreator implements SelectorCreator{
    protected static final String SELECTOR_QUERIES_PACKAGE = "pkg_selectors";
}
