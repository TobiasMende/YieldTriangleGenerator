package de.tobias_men.finance.yield_triangle_generator;

import de.tobias_men.finance.yield_triangle_generator.html_builder.HtmlRepresentationBuilder;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Tobi on 25.03.16.
 */
public class ConsolePrinterMain {
    public static void main(String[] args) {
        UserArguments arguments = parseArguments(args);
        YieldTriangleBuilder builder = new YieldTriangleBuilder();
        StringRepresentationBuilder representationBuilder = new HtmlRepresentationBuilder();
        StringRepresentationWriter representationWriter = new FileWriter("./Renditedreieck.html");
        try {
            log("Building triangle");
            YieldTriangle triangle = builder.generateTriangleForSymbol(arguments.symbol, arguments.fromYear, arguments.toYear);
            printTriangle(triangle, representationBuilder, representationWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static UserArguments parseArguments(String[] args) {
        UserArguments arguments = new UserArguments();
        arguments.symbol = args.length > 0 ? args[0] : "^GDAXI";
        arguments.fromYear = args.length > 1 ? Integer.parseInt(args[1]) : 1900;
        arguments.toYear = args.length > 2 ? Integer.parseInt(args[2]) : Calendar.getInstance().get(Calendar.YEAR) - 1;
        return arguments;
    }

    private static void log(String s) {
        System.out.println(s);
    }

    private static void printTriangle(YieldTriangle triangle, StringRepresentationBuilder representationBuilder, StringRepresentationWriter representationWriter) {
        log("Build string representation");
        String representation = representationBuilder.buildRepresentation(triangle);
        log("Write string representation");
        representationWriter.writeRepresentation(representation);
    }


    private static class UserArguments {
        String symbol;
        int fromYear;
        int toYear;
    }
}
