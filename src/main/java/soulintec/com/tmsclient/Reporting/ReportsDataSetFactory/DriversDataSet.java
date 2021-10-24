package soulintec.com.tmsclient.Reporting.ReportsDataSetFactory;


import soulintec.com.tmsclient.Reporting.ReportsDTO.DTO;

import java.util.List;
import java.util.Map;


public class DriversDataSet implements ReportDataSet{

    private final List<DTO> dataList;

    public DriversDataSet(List<DTO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public Map getParameters() {
        return parameters;
    }

    @Override
    public List<DTO> getDataSet() {
        DataSet.clear();

        DataSet.addAll(dataList);

        return DataSet;
    }
}
