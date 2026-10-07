package com.patrones.u2;

import java.util.Locale;
import java.util.Objects;

/** Configuracion inmutable de una exportacion. */
public final class ExportConfig {
    private final String format;
    private final String outputPath;
    private final String pageSize;
    private final String orientation;
    private final String locale;
    private final String watermarkText;
    private final boolean includeLogo;
    private final boolean compress;
    private final int maxRowsPerPage;

    private ExportConfig(Builder builder) {
        this.format = builder.format;
        this.outputPath = builder.outputPath;
        this.pageSize = builder.pageSize;
        this.orientation = builder.orientation;
        this.locale = builder.locale;
        this.watermarkText = builder.watermarkText;
        this.includeLogo = builder.includeLogo;
        this.compress = builder.compress;
        this.maxRowsPerPage = builder.maxRowsPerPage;
    }

    public String getFormat() { return format; }
    public String getOutputPath() { return outputPath; }
    public String getPageSize() { return pageSize; }
    public String getOrientation() { return orientation; }
    public String getLocale() { return locale; }
    public String getWatermarkText() { return watermarkText; }
    public boolean isIncludeLogo() { return includeLogo; }
    public boolean isCompress() { return compress; }
    public int getMaxRowsPerPage() { return maxRowsPerPage; }

    public static final class Builder {
        private final String format;
        private String outputPath;
        private String pageSize = "A4";
        private String orientation = "PORTRAIT";
        private String locale = "es-CO";
        private String watermarkText;
        private boolean includeLogo = true;
        private boolean compress;
        private int maxRowsPerPage = 40;

        public Builder(String format) {
            Objects.requireNonNull(format, "format no puede ser null");
            String normalized = format.trim().toLowerCase(Locale.ROOT);
            if (normalized.isEmpty()) {
                throw new IllegalArgumentException("format es obligatorio");
            }
            this.format = normalized;
        }

        public Builder outputPath(String value) {
            this.outputPath = blankToNull(value);
            return this;
        }

        public Builder pageSize(String value) {
            this.pageSize = requireText(value, "pageSize");
            return this;
        }

        public Builder orientation(String value) {
            String normalized = requireText(value, "orientation").toUpperCase(Locale.ROOT);
            if (!normalized.equals("PORTRAIT") && !normalized.equals("LANDSCAPE")) {
                throw new IllegalArgumentException("orientation debe ser PORTRAIT o LANDSCAPE");
            }
            this.orientation = normalized;
            return this;
        }

        public Builder locale(String value) {
            this.locale = requireText(value, "locale");
            return this;
        }

        public Builder watermarkText(String value) {
            this.watermarkText = blankToNull(value);
            return this;
        }

        public Builder includeLogo(boolean value) {
            this.includeLogo = value;
            return this;
        }

        public Builder compress(boolean value) {
            this.compress = value;
            return this;
        }

        public Builder maxRowsPerPage(int value) {
            this.maxRowsPerPage = value;
            return this;
        }

        public ExportConfig build() {
            if (compress && outputPath == null) {
                throw new IllegalStateException(
                        "compress=true requiere outputPath; no se puede comprimir un resultado en memoria");
            }
            if (maxRowsPerPage <= 0) {
                throw new IllegalStateException("maxRowsPerPage debe ser mayor que cero");
            }
            return new ExportConfig(this);
        }

        private static String requireText(String value, String name) {
            Objects.requireNonNull(value, name + " no puede ser null");
            if (value.isBlank()) {
                throw new IllegalArgumentException(name + " no puede estar vacio");
            }
            return value.trim();
        }

        private static String blankToNull(String value) {
            return value == null || value.isBlank() ? null : value.trim();
        }
    }
}
