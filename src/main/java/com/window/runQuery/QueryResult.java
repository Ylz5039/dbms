package com.window.runQuery;

public class QueryResult {
    private String selectedText;
    private String displayText;

    public QueryResult(String selectedText, String displayText) {
        this.selectedText = selectedText;
        this.displayText = displayText;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public String getDisplayText() {
        return displayText;
    }
}
