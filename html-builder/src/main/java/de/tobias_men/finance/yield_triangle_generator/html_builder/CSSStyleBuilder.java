package de.tobias_men.finance.yield_triangle_generator.html_builder;

class CSSStyleBuilder {
    private StringBuilder builder;

    CSSStyleBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    public void appendStyle() {
        builder.append("<style>");
        appendGeneralStyle();

        appendYieldStyle("neutral", "#FFFFFF");

        appendYieldStyle("pos-0", "#EDF0CD");
        appendYieldStyle("pos-1", "#D1DA7C");
        appendYieldStyle("pos-2", "#BDCA00");
        appendYieldStyle("pos-3", "#A2C22B");
        appendYieldStyle("pos-4", "#74B33A");
        appendYieldStyle("pos-5", "#51A927");
        appendYieldStyle("pos-6", "#128A2C");
        appendYieldStyle("pos-7", "#0C792D", true);

        appendYieldStyle("neg-0", "#FDEDE4");
        appendYieldStyle("neg-1", "#FCCEBA");
        appendYieldStyle("neg-2", "#FAAD90");
        appendYieldStyle("neg-3", "#F58C68");
        appendYieldStyle("neg-4", "#F16943");
        appendYieldStyle("neg-5", "#EC4519");
        appendYieldStyle("neg-6", "#E71B00");
        appendYieldStyle("neg-7", "#E71B00", true);

        builder.append("</style>");
    }

    private void appendGeneralStyle() {
        builder.append("body {");
        builder.append("font-family: Verdana, Arial, sans-serif;");
        builder.append("}");

        builder.append("table {");
        builder.append("border-spacing: 0px;");
        builder.append("}");

        builder.append("td {");
        builder.append("text-align: right;");
        builder.append("width: 25px;");
        builder.append("padding: 4px;");
        builder.append("}");

    }

    private void appendYieldStyle(String cssClassName, String backgroundColor) {
        appendYieldStyle(cssClassName, backgroundColor, false);
    }

    private void appendYieldStyle(String cssClassName, String backgroundColor, boolean highlight) {
        builder.append(".").append(cssClassName).append(" {");
        builder.append("background-color: ").append(backgroundColor).append(";");
        if (highlight) {
            builder.append("color: #ffffff;");
            builder.append("font-weight: bold;");
        }
        builder.append("}");
    }
}
