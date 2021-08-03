package com.mycompany.plugin;

import org.osgi.service.event.Event;

import com.katalon.platform.api.controller.ReportCollectionController;
import com.katalon.platform.api.controller.ReportController;
import com.katalon.platform.api.event.EventListener;
import com.katalon.platform.api.event.ExecutionEvent;
import com.katalon.platform.api.exception.ResourceException;
import com.katalon.platform.api.execution.TestSuiteCollectionExecutionContext;
import com.katalon.platform.api.execution.TestSuiteExecutionContext;
import com.katalon.platform.api.extension.EventListenerInitializer;
import com.katalon.platform.api.model.ReportCollectionEntity;
import com.katalon.platform.api.model.ReportEntity;
import com.katalon.platform.api.service.ApplicationManager;

public class MyExecutionEventListener implements EventListenerInitializer {

    @Override
    public void registerListener(EventListener listener) {
        listener.on(Event.class, event -> {
            if (ExecutionEvent.TEST_SUITE_FINISHED_EVENT.equals(event.getTopic())) {
                ExecutionEvent eventObject = (ExecutionEvent) event.getProperty("org.eclipse.e4.data");

                TestSuiteExecutionContext testSuiteContext = (TestSuiteExecutionContext) eventObject
                        .getExecutionContext();

                String reportId = testSuiteContext.getReportId();

                ReportController ctl = ApplicationManager.getInstance()
                        .getControllerManager()
                        .getController(ReportController.class);

                try {
                    ReportEntity report = ctl.getReport(null, reportId);
                    System.out.println(
                            "Test suite execution completed - Junit file path: " + report.getJunitReportLocation());

                } catch (ResourceException e) {
                    System.out.println(e.getDetailMessage());
                }

            } else if (ExecutionEvent.TEST_SUITE_COLLECTION_FINISHED_EVENT.equals(event.getTopic())) {
                ExecutionEvent eventObject = (ExecutionEvent) event.getProperty("org.eclipse.e4.data");

                TestSuiteCollectionExecutionContext testSuiteCollectionContext = (TestSuiteCollectionExecutionContext) eventObject
                        .getExecutionContext();

                String path = testSuiteCollectionContext.getReportFolderLocation();

                ReportCollectionController ctl = ApplicationManager.getInstance()
                        .getControllerManager()
                        .getController(ReportCollectionController.class);

                try {
                    ReportCollectionEntity report = ctl.getReport(path);
                    System.out.println("Test suite collection execution completed - Junit file path: "
                            + report.getJunitReportLocation());

                } catch (ResourceException e) {
                    System.out.println(e.getDetailMessage());
                }
            }
        });
    }
}
