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
    void getContent_start(){
        double x = Model.getInstance().getFont().getCharacterWidth() * 5;
        int y = Model.getInstance().getFont().getFontHeight() * 3;

        Selection selection = new Selection(0);
        selection.setEnd("This was a triumph.".length());

        Model.getInstance().getCursor().setSelection(selection);

        try {
            assertEquals(Model.getInstance().getCursor().getSelection().getContent(), "This was a triumph.");
        } catch (SelectionEmptyException e) {
            fail();
        }
    }

    @Test
    void getContent_middle(){
        Selection selection = new Selection("This was a triumph.I'm making a note here:huge success.".length());
        selection.setEnd("This was a triumph.I'm making a note here:huge success.It's hard to overstateMy satisfaction.".length());

        Model.getInstance().getCursor().setSelection(selection);

        try {
            assertEquals("It's hard to overstate\nMy satisfaction.", Model.getInstance().getCursor().getSelection().getContent());
        } catch (SelectionEmptyException e) {
            fail();
        }
    }

    @Test
    void getContent_end(){
        Selection selection = new Selection("This was a triumph.I'm making a note here:huge success.It's hard to overstateMy satisfaction.Aperture Science.We do what we mustBecause we can.For the good of all of us.Except the ones who are dead.But there's no sense cryingOver every mistake.".length());
        selection.setEnd("This was a triumph.I'm making a note here:huge success.It's hard to overstateMy satisfaction.Aperture Science.We do what we mustBecause we can.For the good of all of us.Except the ones who are dead.But there's no sense cryingOver every mistake.You just keep on tryingTill you run out of cake.And the Science gets done.And you make a neat gun.For the people who areStill alive.".length());

        Model.getInstance().getCursor().setSelection(selection);

        try {
            assertEquals("You just keep on trying\nTill you run out of cake.\nAnd the Science gets done.\nAnd you make a neat gun.\nFor the people who are\nStill alive.", Model.getInstance().getCursor().getSelection().getContent());
        } catch (SelectionEmptyException e) {
            fail();
        }
    }

    @Test
    void getEmptyContent(){
        SelectionEmptyException exception = assertThrows(SelectionEmptyException.class, () -> {
            Model.getInstance().getCursor().getSelection();
        });

        assertEquals(exception.getMessage(), "Unable to get selection due to it being empty");
    }
}
