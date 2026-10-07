package com.patrones.u2;

import java.util.Objects;

/** Una calificacion individual dentro de un acta academica. */
public final class GradeRecord {
    private final String studentId;
    private final String studentName;
    private final String courseCode;
    private final double grade;

    public GradeRecord(String studentId, String studentName, String courseCode, double grade) {
        this.studentId = requireText(studentId, "studentId");
        this.studentName = requireText(studentName, "studentName");
        this.courseCode = requireText(courseCode, "courseCode");
        if (!Double.isFinite(grade) || grade < 0.0 || grade > 5.0) {
            throw new IllegalArgumentException("grade debe estar entre 0.0 y 5.0");
        }
        this.grade = grade;
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name + " no puede ser null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " no puede estar vacio");
        }
        return value;
    }

    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getCourseCode() { return courseCode; }
    public double getGrade() { return grade; }
}
