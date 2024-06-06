package tests;


import com.dog2657.richtext.Model;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestModelLineBreaks {
    private ArrayList<String> get_lines(){
        ArrayList<String> lines = new ArrayList<String>();

        Model.getInstance().process_each_line_output(new Model.processLineCallback(){
            @Override
            public void process(int line, String content) {
                lines.add(content);
            }
        });

        return lines;
    }

    @Test
    void parse_text_long(){
        Model.getInstance().load_file("Aperture Science.\nWe do what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        ArrayList<Integer> exp = new ArrayList<>();
        exp.add(17);
        exp.add(36);
        exp.add(52);
        exp.add(79);

        assertEquals(exp, Model.getInstance().getBreaks());
    }

    @Test
    void outputting() {
        Model.getInstance().load_file("Aperture Science. \nWe do what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        ArrayList<String> act = this.get_lines();

        ArrayList<String> exp = new ArrayList<>();
        exp.add("Aperture Science.");
        exp.add("We do what we must");
        exp.add("Because we can.");
        exp.add("For the good of all of us.");
        exp.add("Except the ones who are dead.");

        assertEquals(exp, act);
    }

    @Test
    void subtract_shift_firts_line() {
        Model.getInstance().load_file("Aperture Science.\nWe do what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        Model.getInstance().setCursor(17);


        Model.getInstance().delete_text(false);

        ArrayList<Integer> actPoints = Model.getInstance().getBreaks();
        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(16);
        expPoints.add(35);
        expPoints.add(51);
        expPoints.add(78);

        assertEquals(expPoints, actPoints);


        ArrayList<String> actText = this.get_lines();
        ArrayList<String> expText = new ArrayList<>();
        expText.add("Aperture Science");
        expText.add("We do what we must");
        expText.add("Because we can.");
        expText.add("For the good of all of us.");
        expText.add("Except the ones who are dead.");

        assertEquals(expText, actText);
    }

    @Test
    void addition_shift_firts_line() {
        Model.getInstance().load_file("Aperture Science.\nWe do what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        Model.getInstance().setCursor(17);


        Model.getInstance().add_text("a");

        ArrayList<Integer> actPoints = Model.getInstance().getBreaks();
        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(18);
        expPoints.add(37);
        expPoints.add(53);
        expPoints.add(80);

        assertEquals(expPoints, actPoints);


        ArrayList<String> actText = this.get_lines();
        ArrayList<String> expText = new ArrayList<>();
        expText.add("Aperture Science.a");
        expText.add("We do what we must");
        expText.add("Because we can.");
        expText.add("For the good of all of us.");
        expText.add("Except the ones who are dead.");

        assertEquals(expText, actText);
    }

    @Test
    void add_new_line() {
        Model.getInstance().load_file("Aperture Science.\nWedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        Model.getInstance().setCursor(19);

        Model.getInstance().newLine();

        ArrayList<Integer> actPoints = Model.getInstance().getBreaks();
        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(17);
        expPoints.add(19);
        expPoints.add(36);
        expPoints.add(52);
        expPoints.add(79);

        assertEquals(expPoints, actPoints);


        ArrayList<String> actText = this.get_lines();
        ArrayList<String> expText = new ArrayList<>();
        expText.add("Aperture Science.");
        expText.add("W");
        expText.add("edo what we must");
        expText.add("Because we can.");
        expText.add("For the good of all of us.");
        expText.add("Except the ones who are dead.");

        assertEquals(expText, actText);
    }

    @Test
    void delete_line() {
        Model.getInstance().load_file("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        Model.getInstance().setCursor(20);

        Model.getInstance().deleteLine();

        ArrayList<Integer> actPoints = Model.getInstance().getBreaks();
        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(17);
        expPoints.add(35);
        expPoints.add(51);
        expPoints.add(78);

        assertEquals(expPoints, actPoints);


        ArrayList<String> actText = this.get_lines();
        ArrayList<String> expText = new ArrayList<>();
        expText.add("Aperture Science.");
        expText.add("Wedo what we must");
        expText.add("Because we can.");
        expText.add("For the good of all of us.");
        expText.add("Except the ones who are dead.");

        assertEquals(expText, actText);
    }
}
