# Post-contenido - Unidad 2: Patrones creacionales

**Estudiante:** Juan Pablo Correa Tarazona  
**Código:** no publicado  
**Curso:** Fundamentos de Patrones de Diseño de Software

## Descripción
Proyecto Maven para exportar actas académicas en formatos PDF, Excel y HTML, con familias compatibles de productos.

## Cómo ejecutar
Desde exportador-reportes/:
    mvn compile
    mvn exec:java -Dexec.mainClass="com.patrones.u2.Main"

## Decisiones de diseño

### Decisión 1 - Abstract Factory frente a Factory Method
Se elige Abstract Factory porque cada exportación contiene dos productos relacionados: ReportBody y ReportHeaderFooter. Ambos deben pertenecer al mismo formato, por lo que el requisito consiste en preservar una familia consistente, no escoger una clase de producto única. ReportFormatFactory crea ambos productos y cada implementación concreta crea un par PDF, Excel o HTML. CSV requerirá otra fábrica y sus dos productos; el cliente continuará usando las abstracciones. Factory Method se descarta porque el problema no presenta un único producto indivisible y no expresa directamente la compatibilidad entre el cuerpo y el encabezado/pie.

### Decisión 2 - Registro dinámico frente a switch
ReportFactoryRegistry usa Map<String, Supplier<ReportFormatFactory>>. Para registrar CSV se agrega un proveedor nuevo, sin modificar un switch ni el flujo cliente. Un selector condicional es más corto para tres formatos, pero cada extensión requiere editarlo y revisar sus ramas. El registro valida formatos no encontrados y mantiene la variación en un solo punto de composición.

## Conclusiones
Abstract Factory resuelve la restricción de compatibilidad entre dos productos y el registro permite extender las familias de salida sin alterar el cliente.