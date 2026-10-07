package com.patrones.u2;

public final class HtmlHeaderFooter implements ReportHeaderFooter {
    @Override
    public String renderHeader(String institutionName) {
        return "[HTML:encabezado] <header>" + escape(institutionName)
                + " - Portal de estudiantes</header>";
    }

    @Override
    public String renderFooter(int pageNumber) {
        return "[HTML:pie] <footer>Vista " + pageNumber + " - generada dinamicamente</footer>";
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
