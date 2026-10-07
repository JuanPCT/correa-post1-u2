package com.patrones.u2;

public final class PdfHeaderFooter implements ReportHeaderFooter {
    @Override
    public String renderHeader(String institutionName) {
        return "[PDF:encabezado] " + institutionName + " - Acta de calificaciones";
    }

    @Override
    public String renderFooter(int pageNumber) {
        return "[PDF:pie] Pagina " + pageNumber + " - apto para firma";
    }
}
