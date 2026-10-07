package com.patrones.u2;

import java.util.List;
import java.util.Locale;

public final class PdfReportBody implements ReportBody {
    @Override
    public String render(List<GradeRecord> records) {
        StringBuilder result = new StringBuilder("[PDF:cuerpo] estudiante | curso | nota\n");
        for (GradeRecord record : records) {
            result.append(String.format(Locale.ROOT, "%s | %s | %.1f%n",
                    record.getStudentName(), record.getCourseCode(), record.getGrade()));
        }
        return result.toString();
    }
}
