package de.tobias_men.finance.yield_triangle_generator.html_builder;

import de.tobias_men.finance.yield_triangle_generator.StringRepresentationBuilder;
import de.tobias_men.finance.yield_triangle_generator.YieldTriangle;

/**
 * Created by Tobi on 27.03.16.
 */
public class HtmlRepresentationBuilder implements StringRepresentationBuilder {
    @Override
    public String buildRepresentation(YieldTriangle triangle) {
        StringBuilder builder = new StringBuilder();
        appendHeader(builder, triangle);
        new TriangleTableBuilder(builder).appendTriangle(triangle);
        appendFooter(builder);
        return builder.toString();
    }

    private void appendHeader(StringBuilder builder, YieldTriangle triangle) {
        builder.append("<html>");
        builder.append("<head>");
        appendTitle(builder, triangle);
        new CSSStyleBuilder(builder).appendStyle();
        builder.append("</head>");
        builder.append("<body>");

    }

    private void appendTitle(StringBuilder builder, YieldTriangle triangle) {
        builder.append("<title>");
        builder.append("Yield Triangle for Symbol '" + triangle.getSymbol() +"' from " + triangle.getMinYear() + " to " + triangle.getMaxYear());
        builder.append("</title>");
    }

    private void appendFooter(StringBuilder builder) {
        builder.append("</body>");
        builder.append("</html>");
    }

}
