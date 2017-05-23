package com.dreamteam.datavisualizator.common.utils;

import java.util.ArrayList;
import java.util.List;

public class XmlTable {
    public List<XmlRow> rows = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XmlTable)) return false;

        XmlTable xmlTable = (XmlTable) o;

        if(rows==null||xmlTable.rows==null) return false;
        if(rows.size()!=xmlTable.rows.size()) return false;

        for (int i=0; i<rows.size(); i++){
            if (!rows.get(i).equals(xmlTable.rows.get(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return rows != null ? rows.hashCode() : 0;
    }
}
