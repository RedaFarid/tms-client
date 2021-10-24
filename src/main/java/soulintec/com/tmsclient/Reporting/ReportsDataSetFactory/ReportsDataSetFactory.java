package soulintec.com.tmsclient.Reporting.ReportsDataSetFactory;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soulintec.com.tmsclient.Reporting.ReportsDTO.DTO;
import soulintec.com.tmsclient.Services.TanksService;

import java.util.List;

@Service
@Log4j2
public class ReportsDataSetFactory {

    @Autowired
    private TanksService tanksService;

    public ReportDataSet createDataSetAndParametersFor(String report, List<DTO> dataList) {
        return switch (report) {
            //Adding all cases of reports
            case "Tanks" ->  new TanksDataSet(dataList);
            case "Materials" ->  new MaterialsDataSet(dataList);
            default -> throw new IllegalStateException("Unexpected value: " + report);
        };
    }
}
