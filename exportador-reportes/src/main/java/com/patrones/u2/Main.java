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

        try {
            ReportFactoryRegistry.resolve("json");
            throw new IllegalStateException("json debio ser rechazado como formato");
        } catch (IllegalArgumentException expected) {
            System.out.println("Rechazado correctamente: " + expected.getMessage());
        }
    }

    private static void print(ReportExportService service, String heading, String format,
                              List<GradeRecord> records) {
        System.out.println("=== " + heading + " ===");
        System.out.println(service.export(format, records, "UFPS"));
    }
}