package com.patrones.u2;

/** Producto abstracto: encabezado y pie en el mismo formato del cuerpo. */
public interface ReportHeaderFooter {
    String renderHeader(String institutionName);
    String renderFooter(int pageNumber);
}
