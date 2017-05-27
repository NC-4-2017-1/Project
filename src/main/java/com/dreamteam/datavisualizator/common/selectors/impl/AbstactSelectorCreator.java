package com.dreamteam.datavisualizator.common.selectors.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


public abstract class AbstactSelectorCreator {
    protected static final String SELECTOR_QUERIES_PACKAGE = "pkg_selectors";
}
