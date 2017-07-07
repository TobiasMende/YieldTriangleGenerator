package de.tobias_men.finance.yield_triangle_generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Tobi on 27.03.16.
 */
class FileWriter implements StringRepresentationWriter {
    private String filePath;

    FileWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void writeRepresentation(String string) {
        try {
            PrintWriter writer = new PrintWriter(filePath, "UTF-8");
            writer.write(string);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
