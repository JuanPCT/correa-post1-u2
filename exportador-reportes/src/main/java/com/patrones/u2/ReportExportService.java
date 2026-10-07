package com.patrones.u2;

import java.util.List;
import java.util.Objects;

/** Orquesta la exportacion mediante una familia de productos compatible. */
public final class ReportExportService {
    public String export(String format, List<GradeRecord> records, String institutionName) {
        Objects.requireNonNull(records, "records no puede ser null");
        Objects.requireNonNull(institutionName, "institutionName no puede ser null");
        ReportFormatFactory factory = ReportFactoryRegistry.resolve(format);
        ReportBody body = factory.createBody();
        ReportHeaderFooter headerFooter = factory.createHeaderFooter();

        StringBuilder output = new StringBuilder();
        output.append(headerFooter.renderHeader(institutionName)).append(System.lineSeparator());
        output.append(body.render(List.copyOf(records)));
        output.append(headerFooter.renderFooter(1));
        return output.toString();
    }
}