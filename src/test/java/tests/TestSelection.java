package tests;

import com.dog2657.richtext.DataClasses.Selection;
import com.dog2657.richtext.Model;
import com.dog2657.richtext.exceptions.SelectionEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSelection {
    @BeforeEach
    void initTests(){
        Model.getInstance().load_file("This was a triumph.\n" +
            "I'm making a note here:\n" +
            "huge success.\n" +
            "\n" +
            "It's hard to overstate\n" +
            "My satisfaction.\n" +
            "\n" +
            "Aperture Science.\n" +
            "We do what we must\n" +
            "Because we can.\n" +
            "For the good of all of us.\n" +
            "Except the ones who are dead.\n" +
            "\n" +
            "But there's no sense crying\n" +
            "Over every mistake.\n" +
            "You just keep on trying\n" +
            "Till you run out of cake.\n" +
            "And the Science gets done.\n" +
            "And you make a neat gun.\n" +
            "For the people who are\n" +
            "Still alive."
        );
    }

    @Test
    void translateXYFirstLine(){
        double x = Model.getInstance().getFont().getCharacterWidth() * 5;
        int y = Model.getInstance().getFont().getFontHeight() * 0;

        int position = Model.getInstance().getCursor().translateXYLocation(x, y);

        assertEquals(position, 5);
    }

    @Test
    void translateXYThirdLine(){
        double x = Model.getInstance().getFont().getCharacterWidth() * 5;
        int y = Model.getInstance().getFont().getFontHeight() * 3;

        int position = Model.getInstance().getCursor().translateXYLocation(x, y);

        assertEquals(position, "This was a triumph.\nI'm making a note here:\nhuge ".length());
    }

    @Test
    void getContent(){
        double x = Model.getInstance().getFont().getCharacterWidth() * 5;
        int y = Model.getInstance().getFont().getFontHeight() * 3;

        Selection selection = new Selection(0);
        selection.setEnd("This was a triumph.".length());

        Model.getInstance().getCursor().setSelection(selection);

        try {
            assertEquals(Model.getInstance().getCursor().getSelection(), "This was a triumph.");
        } catch (SelectionEmptyException e) {
            fail();
        }
    }

    @Test
    void getEmptyContent(){
        AssertionError exception = assertThrows(AssertionError.class, () -> {
            Model.getInstance().getCursor().getSelection();
        });

        assertEquals(exception.getMessage(), "Unable to get selection due to it being empty");
    }
}
