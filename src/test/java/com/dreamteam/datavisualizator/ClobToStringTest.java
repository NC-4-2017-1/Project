package com.dreamteam.datavisualizator;

import com.dreamteam.datavisualizator.services.ClobToStringService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class ClobToStringTest {


    @Before
    public void setUp() {

    }

    @Test
    public void clobToString_nullIn_nullOut() {
        String nullToNull = ClobToStringService.clobToString(null);
        assertNull(nullToNull);
    }
}
