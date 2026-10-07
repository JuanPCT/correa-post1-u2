package com.patrones.u2;

import java.util.List;
import java.util.Objects;

/** Orquesta la exportacion mediante una familia de productos compatible. */
public final class ReportExportService {
    public String export(String format, List<GradeRecord> records, String institutionName) {
        ExportConfig config = new ExportConfig.Builder(format).build();
        return export(config, records, institutionName);
    }

    public String export(ExportConfig config, List<GradeRecord> records, String institutionName) {
        Objects.requireNonNull(config, "config no puede ser null");
        Objects.requireNonNull(records, "records no puede ser null");
        Objects.requireNonNull(institutionName, "institutionName no puede ser null");

        ReportFormatFactory factory = ReportFactoryRegistry.resolve(config.getFormat());
        ReportBody body = factory.createBody();
        ReportHeaderFooter headerFooter = factory.createHeaderFooter();

        StringBuilder output = new StringBuilder();
        output.append("[config] format=").append(config.getFormat())
                .append(" pageSize=").append(config.getPageSize())
                .append(" orientation=").append(config.getOrientation())
                .append(" locale=").append(config.getLocale())
                .append(" watermark=").append(config.getWatermarkText() == null
                        ? "none" : config.getWatermarkText())
                .append(" includeLogo=").append(config.isIncludeLogo())
                .append(" compress=").append(config.isCompress())
                .append(" maxRowsPerPage=").append(config.getMaxRowsPerPage())
                .append(System.lineSeparator());
        output.append(headerFooter.renderHeader(institutionName)).append(System.lineSeparator());
        output.append(body.render(List.copyOf(records)));
        output.append(headerFooter.renderFooter(1));
        return output.toString();
    }
}
