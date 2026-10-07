package com.patrones.u2;

import java.util.List;
import java.util.Locale;

public final class HtmlReportBody implements ReportBody {
    @Override
    public String render(List<GradeRecord> records) {
        StringBuilder result = new StringBuilder("[HTML:cuerpo] <table><thead><tr><th>Estudiante</th><th>Curso</th><th>Nota</th></tr></thead><tbody>\n");
        for (GradeRecord record : records) {
            result.append("<tr><td>").append(escape(record.getStudentName()))
                    .append("</td><td>").append(escape(record.getCourseCode()))
                    .append("</td><td>").append(String.format(Locale.ROOT, "%.1f", record.getGrade()))
                    .append("</td></tr>\n");
        }
        return result.append("</tbody></table>").toString();
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
