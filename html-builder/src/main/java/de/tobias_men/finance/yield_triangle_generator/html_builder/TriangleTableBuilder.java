package de.tobias_men.finance.yield_triangle_generator.html_builder;

import de.tobias_men.finance.yield_triangle_generator.YieldTriangle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.NoSuchElementException;

class TriangleTableBuilder {
    private StringBuilder builder;

    TriangleTableBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    public void appendTriangle(YieldTriangle triangle) {
        builder.append("<table>");
        appendTableBody(triangle);
        appendSellingYearsHeader(triangle);
        builder.append("</table>");
    }

    private void appendTableBody(YieldTriangle triangle) {
        final int numberOfColumns = numberOfYearsBetween(triangle.getMinYear(), triangle.getMaxYear()) + 1;
        builder.append("<tr>");
        appendEmptyCells(numberOfColumns);
        appendTitleCell("Kaufen");
        builder.append("</tr>");


        appendEmptyCells(numberOfColumns);
        for (Year buyYear = triangle.getMaxYear().minusYears(1); !buyYear.isBefore(triangle.getMinYear()); buyYear = buyYear.minusYears(1)) {
            builder.append("<tr>");
            final int numberOfEntries = numberOfYearsBetween(buyYear, triangle.getMaxYear());
            final int numberOfEmptyColumns = numberOfColumns - numberOfEntries;
            appendEmptyCells(numberOfEmptyColumns);
            appendYieldsForBuyYear(buyYear, triangle);
            builder.append("</tr>");
        }
    }

    private void appendSellingYearsHeader(YieldTriangle triangle) {
        builder.append("<tr>");
        appendTitleCell("Verkauf");
        for (Year year = triangle.getMinYear().plusYears(1); !year.isAfter(triangle.getMaxYear()); year = year.plusYears(1)) {
            appendTitleCell(year);
        }
        builder.append("</tr>");
    }

    private void appendYieldsForBuyYear(Year buyYear, YieldTriangle triangle) {
        for (Year sellYear = buyYear.plusYears(1); !sellYear.isAfter(triangle.getMaxYear()); sellYear = sellYear.plusYears(1)) {
            try {
                BigDecimal yield = triangle.getYield(buyYear, sellYear).setScale(1, RoundingMode.HALF_UP);
                String cssClass = calculateCssClassForYield(yield);
                builder.append("<td class=\"" + cssClass + " yield-field\">");
                builder.append(yield);
                builder.append("</td>");
            } catch (IllegalArgumentException e) {
                builder.append("<td class=\"yield-field\">");
                builder.append("NaN");
                builder.append("</td>");
            }
        }
        appendTitleCell(buyYear);
    }

    private void appendTitleCell(Object content) {
        builder.append("<th>");
        builder.append(content);
        builder.append("</th>");
    }

    private String calculateCssClassForYield(BigDecimal yield) {
        int sign = yield.signum();

        String cssClass;

        if (sign == -1) {
            cssClass = "neg-" + cssLevelForAbsoluteYield(yield.abs());
        } else if (sign == 1) {
            cssClass = "pos-" + cssLevelForAbsoluteYield(yield);
        } else {
            cssClass = "neutral";
        }

        return cssClass;
    }

    private String cssLevelForAbsoluteYield(BigDecimal yield) {
        double value = yield.doubleValue();
        if (value > 0 && value <= 1) {
            return "0";
        }
        if (value <= 2.5) {
            return "1";
        }
        if (value <= 5) {
            return "2";
        }
        if (value <= 7.5) {
            return "3";
        }
        if (value <= 10) {
            return "4";
        }
        if (value <= 15) {
            return "5";
        }
        if (value <= 25) {
            return "6";
        }
        return "7";
    }

    private void appendEmptyCells(int numberOfColumns) {
        for (int i = 0; i < numberOfColumns; i++) {
            builder.append("<td></td>");
        }
    }

    private int numberOfYearsBetween(Year first, Year second) {
        return second.getValue() - first.getValue();
    }
}
