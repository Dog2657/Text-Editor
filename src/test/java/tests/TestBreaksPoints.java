package tests;


import com.dog2657.richtext.DataClasses.BreakPoints;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBreaksPoints {


    @Test
    void parse_text_long(){
        BreakPoints instance = new BreakPoints("Aperture Science.\nWe do what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        ArrayList<Integer> exp = new ArrayList<>();
        exp.add(17);
        exp.add(36);
        exp.add(52);
        exp.add(79);

        assertEquals(exp, instance.getPoints());
    }

    @Test
    void parse_text_short(){
        BreakPoints instance = new BreakPoints("Aperture Science.");

        ArrayList<Integer> exp = new ArrayList<>();

        assertEquals(exp, instance.getPoints());
    }


    @Test
    void subtract_shift_line() {
        BreakPoints instance = new BreakPoints("Aperture Science.\nWe do what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        instance.shiftPoints(17, -1);

        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(16);
        expPoints.add(35);
        expPoints.add(51);
        expPoints.add(78);

        assertEquals(expPoints, instance.getPoints());
    }

    @Test
    void addition_shift_line() {
        BreakPoints instance = new BreakPoints("Aperture Science.\nWe do what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        instance.shiftPoints(17, 1);

        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(18);
        expPoints.add(37);
        expPoints.add(53);
        expPoints.add(80);

        assertEquals(expPoints, instance.getPoints());
    }

    @Test
    void add_new_line() {
        BreakPoints instance = new BreakPoints("Aperture Science.\nWedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        instance.newPoint(1, 19);

        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(17);
        expPoints.add(19);
        expPoints.add(35);
        expPoints.add(51);
        expPoints.add(78);

        assertEquals(expPoints, instance.getPoints());
    }

    @Test
    void add_new_line_shift() {
        BreakPoints instance = new BreakPoints("Aperture Science.\nWedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        instance.shiftPoints(19, 1);
        instance.newPoint(1, 19);

        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(17);
        expPoints.add(19);
        expPoints.add(36);
        expPoints.add(52);
        expPoints.add(79);

        assertEquals(expPoints, instance.getPoints());
    }

    @Test
    void delete_line() {
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        instance.deletePoint(1);

        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(17);
        expPoints.add(36);
        expPoints.add(52);
        expPoints.add(79);

        assertEquals(expPoints, instance.getPoints());
    }

    @Test
    void delete_shift_line() {
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        instance.deletePoint(1);
        instance.shiftPoints(20, -1);

        ArrayList<Integer> expPoints = new ArrayList<>();
        expPoints.add(17);
        expPoints.add(35);
        expPoints.add(51);
        expPoints.add(78);

        assertEquals(expPoints, instance.getPoints());
    }

    @Test
    void get_line_length_start(){
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        assertEquals(17, instance.getLineLength(0, 109));
    }

    @Test
    void get_line_length_end(){
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        assertEquals(29, instance.getLineLength(5, 109));
    }

    @Test
    void get_line_length_middle_one(){
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        assertEquals(1, instance.getLineLength(1, 109));
    }

    @Test
    void get_line_length_middle_Two(){
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        assertEquals(16, instance.getLineLength(2, 109));
    }

    @Test
    void get_line_length_middle_Three(){
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        assertEquals(15, instance.getLineLength(3, 109));
    }

    @Test
    void get_line_length_middle_Four(){
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        assertEquals(26, instance.getLineLength(4, 109));
    }

    @Test
    void covert_position_to_line_start(){
         BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
         assertEquals(0, instance.getPositionLine("Aperture Science".length()));
    }

    @Test
    void covert_position_to_line_end(){
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        assertEquals(5, instance.getPositionLine("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the".length()));
    }

    @Test
    void covert_position_to_line_middle(){
        BreakPoints instance = new BreakPoints("Aperture Science.\nW\nedo what we must\nBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");
        assertEquals(3, instance.getPositionLine("Aperture Science.\nW\nedo what we must\nBecause we".length()));
    }



    @Test
    void convert_absolute_position_into_relative(){
        BreakPoints instance = new BreakPoints("Aperture Science.Wedo what we mustBecause we can.\nAperture Science.\nWe do what we mustBecause we can.\nFor the good of all of us.\nExcept the ones who are dead.");

        int abs = 55;
        int line = 1;

        assertEquals(5, instance.getRelativeLineLocation(abs, line));
    }

}
