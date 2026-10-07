package com.patrones.u2;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Registro de fabricas concretas. Es una clase utilitaria, no un Singleton:
 * sus metodos estaticos no requieren identidad de objeto.
 */
public final class ReportFactoryRegistry {
    private static final Map<String, Supplier<ReportFormatFactory>> REGISTRY =
            new ConcurrentHashMap<>();

    static {
        register("pdf", PdfReportFactory::new);
        register("excel", ExcelReportFactory::new);
        register("html", HtmlReportFactory::new);
    }

    private ReportFactoryRegistry() {
        throw new AssertionError("No se debe instanciar una clase utilitaria");
    }

    public static void register(String format, Supplier<ReportFormatFactory> supplier) {
        String key = normalize(format);
        Objects.requireNonNull(supplier, "supplier no puede ser null");
        REGISTRY.put(key, supplier);
    }

    public static ReportFormatFactory resolve(String format) {
        String key = normalize(format);
        Supplier<ReportFormatFactory> supplier = REGISTRY.get(key);
        if (supplier == null) {
            throw new IllegalArgumentException("Formato no registrado: " + key
                    + ". Disponibles: " + REGISTRY.keySet());
        }
        return Objects.requireNonNull(supplier.get(),
                "La fabrica registrada para " + key + " devolvio null");
    }

    private static String normalize(String format) {
        Objects.requireNonNull(format, "format no puede ser null");
        String key = format.trim().toLowerCase(Locale.ROOT);
        if (key.isEmpty()) {
            throw new IllegalArgumentException("format no puede estar vacio");
        }
        return key;
    }
}
