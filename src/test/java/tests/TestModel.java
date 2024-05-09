package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dog2657.richtext.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestModel {
    @BeforeEach
    void setUp() {
        Model.getInstance().clear_data();
        Model.getInstance().set_data_original("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    }

    @Test
    void add_start() {
        Model.getInstance().setCursor(0);
        Model.getInstance().add_text("Cat ");


        String exp = "Cat Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        assertEquals(exp, Model.getInstance().get_text_output());
    }

    @Test
    void add_start_and_end() {
        Model.getInstance().setCursor(0);
        Model.getInstance().add_text("Cat ");

        Model.getInstance().setCursor(60);
        Model.getInstance().add_text(" Dog");

        String exp = "Cat Lorem ipsum dolor sit amet, consectetur adipiscing elit. Dog";

        assertEquals(exp, Model.getInstance().get_text_output());
    }

    @Test
    void add_middle_single() {
        Model.getInstance().setCursor(28);
        Model.getInstance().add_text("Cat ");

        String exp = "Lorem ipsum dolor sit amet, Cat consectetur adipiscing elit.";

        assertEquals(exp, Model.getInstance().get_text_output());
    }

    @Test
    void add_middle_several() {
        Model.getInstance().setCursor(28);
        Model.getInstance().add_text("Cat ");

        Model.getInstance().setCursor(32);
        Model.getInstance().add_text("Dog ");

        String exp = "Lorem ipsum dolor sit amet, Cat Dog consectetur adipiscing elit.";

        assertEquals(exp, Model.getInstance().get_text_output());
    }

    @Test
    void add_end() {
        Model.getInstance().setCursor(56);
        Model.getInstance().add_text(" Cat");

        String exp = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cat";

        assertEquals(exp, Model.getInstance().get_text_output());
    }
}
