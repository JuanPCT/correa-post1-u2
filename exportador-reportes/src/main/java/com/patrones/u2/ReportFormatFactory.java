package com.patrones.u2;

/** Fabrica abstracta que crea productos compatibles de un formato. */
public interface ReportFormatFactory {
    ReportBody createBody();
    ReportHeaderFooter createHeaderFooter();
}
