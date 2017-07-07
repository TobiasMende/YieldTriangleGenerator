package de.tobias_men.finance.yield_triangle_generator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class YieldTriangle {
    private String symbol;
    private Map<Year, ValueDate> closeValues;
    private Year minYear;
    private Year maxYear;

    public Year getMinYear() {
        return minYear;
    }

    public Year getMaxYear() {
        return maxYear;
    }


    public YieldTriangle(String symbol) {
        this.symbol = symbol;
        closeValues = new HashMap<>();
    }

    public void setCloseValueForYear(Calendar date, BigDecimal value) {
        Year year = Year.of(date.get(Calendar.YEAR));
        ValueDate valueDate = closeValues.get(year);
        if (valueDate == null) {
            valueDate = new ValueDate(date, value);
        }

        if (valueDate.date.compareTo(date) < 0) {
            valueDate.date = date;
            valueDate.value = value;
        }
        closeValues.put(year, valueDate);
        updateMinYear(year);
        updateMaxYear(year);
    }


    private void updateMinYear(Year year) {
        if (minYear == null || minYear.isAfter(year)) {
            minYear = year;
        }
    }

    private void updateMaxYear(Year year) {
        if (maxYear == null || maxYear.isBefore(year)) {
            maxYear = year;
        }
    }

    public BigDecimal getYield(Year buyYear, Year sellYear) throws IllegalArgumentException {

        throwIfArgumentAreInvalid(buyYear, sellYear);

        return calculateGeometricAverageYield(buyYear, sellYear);
    }

    private BigDecimal calculateGeometricAverageYield(Year buyYear, Year sellYear) {
        int period = sellYear.getValue() - buyYear.getValue();
        BigDecimal start = closeValues.get(buyYear).value;
        BigDecimal end = closeValues.get(sellYear).value;
        BigDecimal rational = end.divide(start, RoundingMode.HALF_UP);
        BigDecimal yieldPerYear = root(rational, period);
        return yieldPerYear.subtract(BigDecimal.ONE).multiply(new BigDecimal(100));
    }

    private void throwIfArgumentAreInvalid(Year buyYear, Year sellYear) {
        if (!sellYear.isAfter(buyYear)) {
            throw new IllegalArgumentException("The end year " + sellYear + " is before the start year " + buyYear);
        }

        if (!closeValues.containsKey(buyYear)) {
            throw new IllegalArgumentException("No data available for year " + buyYear);
        }

        if (!closeValues.containsKey(sellYear)) {
            throw new IllegalArgumentException("No data available for year " + sellYear);
        }
    }

    public String getSymbol() {
        return symbol;
    }

    private class ValueDate {
        Calendar date;
        BigDecimal value;

        public ValueDate(Calendar date, BigDecimal value) {
            this.date = date;
            this.value = value;
        }
    }

    protected static BigDecimal root(BigDecimal number, double root) {
        return new BigDecimal(Math.pow(number.doubleValue(), 1.0 / root));
    }

}
