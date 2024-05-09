package tests;

import com.dog2657.richtext.DataStructure.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPiece {
    @Test
    void split() {
        Piece act1 = new Piece(0, 20, "original");
        Piece act2 = act1.split(10);


        assertEquals(act1.getLength(), 10);
        assertEquals(act1.getStart(), 0);
        assertEquals(act1.getSource(), "original");

        assertEquals(act2.getLength(), 10);
        assertEquals(act2.getStart(), 11);
        assertEquals(act2.getSource(), "original");
    }

    @Test
    void split_out_of_bounds_positive() {
        Piece act1 = new Piece(0, 20, "original");

        AssertionError exception = assertThrows(AssertionError.class, () -> {
            act1.split(21);
        });

        assertEquals(exception.getMessage(), "Point is outside of this piece");
    }

    @Test
    void split_out_of_bounds_negative() {
        Piece act1 = new Piece(0, 20, "original");

        AssertionError exception = assertThrows(AssertionError.class, () -> {
            act1.split(-1);
        });

        assertEquals(exception.getMessage(), "Point is outside of this piece");
    }
}