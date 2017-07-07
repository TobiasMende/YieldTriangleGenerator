package de.tobias_men.finance.yield_triangle_generator;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Stream;


public class YieldTriangleBuilder {

    public YieldTriangle generateTriangleForSymbol(String symbol, int fromYear, int toYear) throws IOException {
        YieldTriangle triangle = new YieldTriangle(symbol);
        Calendar from = new GregorianCalendar(fromYear, Calendar.JANUARY, 0);
        Calendar to = new GregorianCalendar(toYear, Calendar.DECEMBER, 31);
        Stock stock = YahooFinance.get(symbol, from, Interval.DAILY);
        List<HistoricalQuote> history = stock.getHistory(from, to, Interval.DAILY);

        // Filter first day of JAN and last day of DEC
        Stream<HistoricalQuote> yearBeginEnd = history.stream().filter(quote -> (quote.getDate().get(Calendar.MONTH) == Calendar.JANUARY) || (quote.getDate().get(Calendar.MONTH) == Calendar.DECEMBER));
        yearBeginEnd.forEach(historicalQuote -> {
            Calendar date = historicalQuote.getDate();
            BigDecimal value = historicalQuote.getAdjClose();
            triangle.setCloseValueForYear(date, value);
        });

        return triangle;
    }
}
