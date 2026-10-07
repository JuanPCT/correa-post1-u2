# Post-contenido - Unidad 2: Patrones creacionales

**Estudiante:** Juan Pablo Correa Tarazona  
**Código:** no publicado  
**Curso:** Fundamentos de Patrones de Diseño de Software

## Descripción

Un único proyecto Maven (exportador-reportes/) modela la exportación de reportes académicos en PDF, Excel y HTML. La Parte 1 crea una familia coherente de productos por formato y permite registrar formatos sin un switch. La Parte 2 incorpora una configuración inmutable construida con Builder y valida combinaciones inconsistentes.

## Cómo ejecutar

Desde exportador-reportes/:

    mvn compile
    mvn exec:java -Dexec.mainClass="com.patrones.u2.Main"

La demostración imprime salidas identificables por formato; representa el contrato y el flujo de exportación, no genera archivos PDF o Excel binarios.

## Decisiones de diseño

### Decisión 1 - Abstract Factory frente a Factory Method

**Elección: Abstract Factory.** Cada exportación contiene dos productos relacionados: ReportBody y ReportHeaderFooter. No basta con escoger una sola clase; ambos componentes deben ser de la misma familia. Por eso ReportFormatFactory ofrece createBody() y createHeaderFooter(), y cada fábrica concreta crea el par PDF, Excel o HTML. Si el problema se modelara como un documento indivisible, Factory Method sería suficiente; el enunciado separa explícitamente dos productos y plantea el riesgo real de mezclar formatos. Al agregar CSV se necesitan una implementación de cuerpo y otra de encabezado/pie, junto con su fábrica. El cliente conserva los contratos abstractos y una fábrica concreta mantiene la compatibilidad del par. Factory Method, con un único producto por creador, no expresaría directamente esa restricción entre dos productos.

### Decisión 2 - Registro dinámico frente a switch

**Elección: Map<String, Supplier<ReportFormatFactory>>.** ReportFactoryRegistry normaliza y valida la clave y delega la construcción al proveedor registrado. Agregar CSV requiere registrar CsvReportFactory::new, no editar un switch existente. Un switch sería sencillo al inicio, pero cada nuevo formato exigiría modificar el selector y volver a revisar ramas previas. El registro mantiene esa variación en la composición y produce un error claro para formatos desconocidos.

### Decisión 3 - Builder frente a constructor telescópico o setters

**Elección: Builder fluido para ExportConfig.** El formato es obligatorio y los otros ocho campos son opcionales. Un constructor de nueve argumentos obliga a memorizar el orden y permite intercambiar valores del mismo tipo; varios constructores sobrecargados crecerían con las combinaciones de opciones. Setters mutables dejarían una configuración parcialmente armada sin una validación central. El Builder aplica valores por defecto, encadena las opciones y revisa compress con outputPath y maxRowsPerPage en build(), antes de crear un objeto válido e inmutable.

### Decisión 4 - ¿El registro debe ser Singleton?

**Conclusión: no conviene convertirlo en Singleton clásico.** ReportFactoryRegistry es una clase utilitaria final con estado estático compartido; no se necesita pasar su identidad, implementarle una interfaz ni inyectar una instancia sustituible. Su mapa pequeño no implica inicialización costosa y el campo estático ya proporciona una fuente de verdad dentro de la JVM. Agregar getInstance() y sincronización introduciría ceremonia sin resolver una necesidad presente. Si una versión multi-institución necesitara registros independientes, el Singleton sería una restricción; en ese caso convendría inyectar registros por institución.

## Estructura

    exportador-reportes/
      pom.xml
      src/main/java/com/patrones/u2/
        GradeRecord.java
        ReportBody.java
        ReportHeaderFooter.java
        ReportFormatFactory.java
        PdfReportBody.java
        PdfHeaderFooter.java
        PdfReportFactory.java
        ExcelReportBody.java
        ExcelHeaderFooter.java
        ExcelReportFactory.java
        HtmlReportBody.java
        HtmlHeaderFooter.java
        HtmlReportFactory.java
        ReportFactoryRegistry.java
        ReportExportService.java
        ExportConfig.java
        Main.java

## Conclusiones

El análisis del problema determinó que el formato comprende una familia de dos productos que debe permanecer consistente, por lo que Abstract Factory expresa una restricción que una fábrica de producto único no cubre por sí sola. El registro de proveedores hace extensible la selección sin ramificaciones. Builder permite configurar parámetros opcionales y validar el estado antes de construir. Singleton se descartó para el registro porque la clase utilitaria ya comparte el mapa y no requiere identidad de objeto.

## Herramientas utilizadas

Java 17, Apache Maven, Git y GitHub.
