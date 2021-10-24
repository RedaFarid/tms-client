package soulintec.com.tmsclient.Reporting.ReportsDetails;

import com.google.common.io.Resources;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import soulintec.com.tmsclient.Reporting.ReportsDTO.DTO;
import soulintec.com.tmsclient.Reporting.ReportsDataSetFactory.ReportDataSet;
import soulintec.com.tmsclient.Reporting.ReportsDataSetFactory.ReportsDataSetFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportDetailsFactory {
    private static ReportDetailsFactory singleton;
    private JasperReport jasperReport;
    private Window ownerWindow;
    
    private final Map<String, String> reportsFiles = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    private ReportsDataSetFactory reportsDataSetFactory;


    public ReportDetailsFactory() {
        //filling the locations regarding every report jrxml file
        reportsFiles.put("Tanks", "/ReportsDesign/Tanks.jrxml");
        reportsFiles.put("Materials", "/ReportsDesign/Materials.jrxml");
        reportsFiles.put("Clients", "/ReportsDesign/Clients.jrxml");
        reportsFiles.put("Drivers", "/ReportsDesign/Drivers.jrxml");
    }

    @Async("GPExecutor")
    public CompletableFuture<Pane> getReportDetailsPaneFor(String reportName) {
        try {
            ReportDataSet dataset = reportsDataSetFactory.createDataSetAndParametersFor(reportName, null);

            JasperDesign jasperdesign = JRXmlLoader.load(Resources.getResource(reportsFiles.get(reportName)).getFile());
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperdesign);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, dataset.getParameters(), new JRBeanCollectionDataSource(dataset.getDataSet()));

            return CompletableFuture.completedFuture(new JRPrintPreview(jasperprint));
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.failedFuture(new RuntimeException("Error loading report"));
        }
    }

    public Pane getReportDetailsPaneFor(String reportName, List<DTO> dataList) throws Exception {
        try (InputStream reportInputStream = getClass().getResourceAsStream((reportsFiles.get(reportName)))) {
            ReportDataSet dataset = reportsDataSetFactory.createDataSetAndParametersFor(reportName, dataList);
            JasperDesign jasperdesign = JRXmlLoader.load(reportInputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperdesign);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, dataset.getParameters(), new JRBeanCollectionDataSource(dataset.getDataSet()));
            return new JRPrintPreview(jasperprint);
        }
    }

    public void export(String fileChooserTitle, String fileName, Map<String, Object> paramMap, JRBeanCollectionDataSource dataSource) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooserTitle);
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF files", "*.pdf"),
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx"),
                new FileChooser.ExtensionFilter("Word files", "*.docx"));
        File selectedFile = fileChooser.showSaveDialog(ownerWindow);
//        System.err.println(selectedFile.getAbsolutePath());
        if (selectedFile != null) {
            ownerWindow.getScene().setCursor(Cursor.WAIT);

            String extension = "";
            int i = selectedFile.getName().lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }

            try {
                Exporter exporter;

                switch (extension.toLowerCase()) {
                    case "pdf":
                        exporter = new JRPdfExporter();
                        SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
                        config.setMetadataCreator("App name");
                        exporter.setConfiguration(config);
                        break;
                    case "xlsx":
                        exporter = new JRXlsxExporter();
                        System.err.println("xlsx.........");
                        break;
                    case "docx":
                        exporter = new JRDocxExporter();
                        break;
                    default:
                        return;
                }

                exporter.setExporterInput(new SimpleExporterInput(JasperFillManager.fillReport(jasperReport, paramMap, dataSource)));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(selectedFile));
                exporter.exportReport();
            } catch (JRException ex) {
                ex.printStackTrace();
            } catch (JRRuntimeException ex) { // File is being used by another process and cannot be overwritten
                System.out.println(ex.getMessage());
            } finally {
                ownerWindow.getScene().setCursor(Cursor.DEFAULT);
            }
        }
    }
}
