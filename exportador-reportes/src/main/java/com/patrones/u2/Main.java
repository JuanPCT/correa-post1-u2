package com.patrones.u2;

import java.util.List;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        List<GradeRecord> records = List.of(
                new GradeRecord("20231001", "Ana Torres", "IS-301", 4.2),
                new GradeRecord("20231002", "Luis Rey", "IS-301", 3.8),
                new GradeRecord("20231003", "Marta Diaz", "IS-301", 4.7));

        ReportExportService service = new ReportExportService();
        print(service, "Exportacion PDF", "pdf", records);
        print(service, "Exportacion Excel", "excel", records);
        print(service, "Exportacion HTML", "html", records);

        ExportConfig defaults = new ExportConfig.Builder("pdf").build();
        System.out.println("=== Configuracion por defecto ===");
        System.out.println(service.export(defaults, records, "UFPS"));

        ExportConfig custom = new ExportConfig.Builder("excel")
                .outputPath("build/acta.csv")
                .pageSize("LETTER")
                .orientation("LANDSCAPE")
                .locale("en-US")
                .watermarkText("BORRADOR")
                .includeLogo(false)
                .compress(true)
                .maxRowsPerPage(25)
                .build();
        System.out.println("=== Configuracion personalizada ===");
        System.out.println(service.export(custom, records, "UFPS"));

        System.out.println("=== Validacion de configuracion inconsistente ===");
        boolean invalidConfigRejected = false;
        try {
            new ExportConfig.Builder("html").compress(true).build();
        } catch (IllegalStateException expected) {
            invalidConfigRejected = true;
            System.out.println("Rechazado correctamente: " + expected.getMessage());
        }
        if (!invalidConfigRejected) {
            throw new IllegalStateException("compress sin outputPath debio ser rechazado");
        }

        System.out.println("=== Validacion de formato desconocido ===");
        boolean unknownFormatRejected = false;
        try {
            ReportFactoryRegistry.resolve("json");
        } catch (IllegalArgumentException expected) {
            unknownFormatRejected = true;
            System.out.println("Rechazado correctamente: " + expected.getMessage());
        }
        if (!unknownFormatRejected) {
            throw new IllegalStateException("json debio ser rechazado como formato");
        }
    }

    private static void print(ReportExportService service, String heading, String format,
                              List<GradeRecord> records) {
        System.out.println("=== " + heading + " ===");
        System.out.println(service.export(format, records, "UFPS"));
    }
}