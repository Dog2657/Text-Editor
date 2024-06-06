package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dog2657.richtext.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestModelDataStructure {
    private void simulateKeyPresses(String word){
        Model instance = Model.getInstance();

        for(int i=0; i<word.length(); i++) {
            String character = word.substring(i, i + 1);
            instance.add_text(character);
            instance.moveCursor(1);
        }
    }

    @BeforeEach
    void setUp() {
        Model.getInstance().clear_data();
        Model.getInstance().load_file("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
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


    @Test
    void add_start_simulated() {
        Model.getInstance().setCursor(0);
        simulateKeyPresses("Cat ");

        String exp = "Cat Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        assertEquals(exp, Model.getInstance().get_text_output());
        assertEquals(2, Model.getInstance().get_data_pieces().size());
    }

    @Test
    void add_middle_single_simulated() {
        Model.getInstance().setCursor(28);
        simulateKeyPresses("Cat ");

        String exp = "Lorem ipsum dolor sit amet, Cat consectetur adipiscing elit.";

        assertEquals(exp, Model.getInstance().get_text_output());
        assertEquals(3, Model.getInstance().get_data_pieces().size());
    }

    @Test
    void add_middle_simulated() {
        Model.getInstance().setCursor(28);
        simulateKeyPresses("Cat ");

        Model.getInstance().setCursor(32);
        simulateKeyPresses("Dog ");

        String exp = "Lorem ipsum dolor sit amet, Cat Dog consectetur adipiscing elit.";

        assertEquals(exp, Model.getInstance().get_text_output());
        assertEquals(3, Model.getInstance().get_data_pieces().size());


    }

    @Test
    void add_middle_several_simulated() {
        Model.getInstance().setCursor(12);
        simulateKeyPresses("Cat ");

        Model.getInstance().setCursor(32);
        simulateKeyPresses("Dog are the best ");

        Model m = Model.getInstance();

        String exp = "Lorem ipsum Cat dolor sit amet, Dog are the best consectetur adipiscing elit.";

        assertEquals(exp, Model.getInstance().get_text_output());
        assertEquals(5, Model.getInstance().get_data_pieces().size());
    }

    @Test
    void delete_start_backwards(){
        Model.getInstance().setCursor(6);
        Model.getInstance().delete_text(false);

        String exp = "Loremipsum dolor sit amet, consectetur adipiscing elit.";

        assertEquals(exp, Model.getInstance().get_text_output());
    }


    @Disabled("Disabled until adding forward delete feature")
    @Test
    void delete_start_forwards(){
        Model.getInstance().setCursor(0);
        Model.getInstance().delete_text(true);

        String exp = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        assertEquals(exp, Model.getInstance().get_text_output());
        assertEquals(1, Model.getInstance().get_data_pieces().size());
    }
}
