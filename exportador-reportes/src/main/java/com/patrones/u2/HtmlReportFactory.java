package com.patrones.u2;

public final class HtmlReportFactory implements ReportFormatFactory {
    @Override
    public ReportBody createBody() { return new HtmlReportBody(); }

    @Override
    public ReportHeaderFooter createHeaderFooter() { return new HtmlHeaderFooter(); }
}
