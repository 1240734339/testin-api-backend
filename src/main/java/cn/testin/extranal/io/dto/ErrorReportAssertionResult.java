package cn.testin.extranal.io.dto;

import lombok.Data;

@Data
public class ErrorReportAssertionResult  extends ResponseAssertionResult {
    private String errorReportMessage;

    public ErrorReportAssertionResult(String errorReportMessage){
        this.errorReportMessage = errorReportMessage;
        this.setMessage(errorReportMessage);
    }
}