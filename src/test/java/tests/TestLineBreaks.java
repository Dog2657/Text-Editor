package tests;

import com.dog2657.richtext.DataStructure.LineBreaks;
import com.dog2657.richtext.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLineBreaks {
    @BeforeEach
    void setup(){
        LineBreaks.getInstance().parse("Aperture Science.\n" +
                "We do what we must\n" +
                        "Because we can.\n" +
                        "For the good of all of us.\n" +
                        "Except the ones who are dead.");
    }

    @Test
    void parse_text_long(){
        ArrayList<Integer> exp = new ArrayList<>();
        exp.add(17);
        exp.add(36);
        exp.add(52);
        exp.add(79);

        assertEquals(exp, LineBreaks.getInstance().getBreaks());
    }

    @Test
    void get_cursor_line(){
        assertEquals(1, LineBreaks.getInstance().getLine(26));
    }

    @Test
    void shift_points(){
        int cursor = 36;
        LineBreaks instance = LineBreaks.getInstance();

        instance.shiftPoints(cursor, 1);

        ArrayList<Integer> exp = new ArrayList<>();
        exp.add(17);
        exp.add(37);
        exp.add(53);
        exp.add(80);

        assertEquals(exp, instance.getBreaks());
    }
}
