package com.dreamteam.datavisualizator.common.utils;

import java.util.ArrayList;
import java.util.List;

public class XmlRow {
    public List<Object> cells = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XmlRow)) return false;

        XmlRow row = (XmlRow) o;

        if(cells==null||row.cells==null) return false;
        if(cells.size()!=row.cells.size()) return false;

        for (int i=0; i<cells.size(); i++){
            if (!cells.get(i).equals(row.cells.get(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return cells != null ? cells.hashCode() : 0;
    }

    @Override
    public String toString() {
        String string = "XmlRow{ ";
        for (int i=0; i<cells.size(); i++){
            string+="'"+cells.get(i)+"'"+" ";
        }
        string+=" }";

        return string;
    }
}
