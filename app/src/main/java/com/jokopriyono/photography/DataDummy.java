package com.jokopriyono.photography;

import java.util.ArrayList;
import java.util.List;

public class DataDummy {
    private List<String> listDataDummy = new ArrayList<>();

    public DataDummy() {
        listDataDummy.add("Dummy satu");
        listDataDummy.add("Dummy dua");
        listDataDummy.add("Dummy tiga");
        listDataDummy.add("Dummy empat");
        listDataDummy.add("Dummy lima");
        listDataDummy.add("Dummy enam");
        listDataDummy.add("Dummy tujuh");
        listDataDummy.add("Dummy delapan");
        listDataDummy.add("Dummy sembilan");
        listDataDummy.add("Dummy sepuluh");
    }

    public List<String> getListDataDummy() {
        return listDataDummy;
    }
}
