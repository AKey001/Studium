package de.antonkiessling.studium.commons;

public enum PDFDocumentType {
    MODULES("modules.pdf"),
    WEIGHTING("weighting.pdf"),
    PLAN("buildings.pdf");

    private String fileName;

    PDFDocumentType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
