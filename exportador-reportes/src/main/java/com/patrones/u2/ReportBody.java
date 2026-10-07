package com.patrones.u2;

import java.util.List;

/** Producto abstracto: cuerpo de un reporte en un formato de salida. */
public interface ReportBody {
    String render(List<GradeRecord> records);
}
