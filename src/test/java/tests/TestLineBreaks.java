package tests;

import com.dog2657.richtext.DataStructure.LineBreaks;
import com.dog2657.richtext.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLineBreaks {
    @Test
    void parse_text_long(){
        LineBreaks.getInstance().parse("Aperture Science.\n" +
                "We do what we must\n" +
                "Because we can.\n" +
                "For the good of all of us.\n" +
                "Except the ones who are dead.");

        ArrayList<Integer> exp = new ArrayList<>();
        exp.add(17);
        exp.add(36);
        exp.add(52);
        exp.add(79);

        assertEquals(exp, LineBreaks.getInstance().getBreaks());
    }

    @Test
    void get_cursor_line(){
        LineBreaks.getInstance().parse("Aperture Science.\n" +
                "We do what we must\n" +
                "Because we can.\n" +
                "For the good of all of us.\n" +
                "Except the ones who are dead.");

        assertEquals(1, LineBreaks.getInstance().getLine(26));
    }
}
