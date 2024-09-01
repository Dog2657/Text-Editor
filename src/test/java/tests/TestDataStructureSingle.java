package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dog2657.richtext.DataStructure.DataStructure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDataStructureSingle {
    private class Singleton{
        private static DataStructure instance;

        public static void set(String content){
            instance = new DataStructure(content);
        }

        public static DataStructure getInstance(){
            return instance;
        }
    }


    private void simulateKeyPresses(int cursor, String word){
        DataStructure instance = Singleton.getInstance();

        for(int i=0; i<word.length(); i++) {
            String character = word.substring(i, i + 1);
            instance.add_text(cursor + i, character);
        }
    }

    @BeforeEach
    void setUp() {
        Singleton.set("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    }

    @Test
    void add_start() {
        Singleton.getInstance().add_text(0, "Cat ");

        String exp = "Cat Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void add_start_and_end() {
        Singleton.getInstance().add_text(0, "Cat ");
        Singleton.getInstance().add_text(60, " Dog");

        String exp = "Cat Lorem ipsum dolor sit amet, consectetur adipiscing elit. Dog";

        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void add_middle_single() {
        Singleton.getInstance().add_text(28, "Cat ");

        String exp = "Lorem ipsum dolor sit amet, Cat consectetur adipiscing elit.";

        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void add_middle_several() {
        Singleton.getInstance().add_text(28, "Cat ");
        Singleton.getInstance().add_text(32, "Dog ");

        String exp = "Lorem ipsum dolor sit amet, Cat Dog consectetur adipiscing elit.";

        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void add_end() {
        Singleton.getInstance().add_text(56, " Cat");

        String exp = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cat";

        assertEquals(exp, Singleton.getInstance().getOutput());
    }


    @Test
    void add_start_simulated() {
        simulateKeyPresses(0, "Cat ");

        String exp = "Cat Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

        assertEquals(exp, Singleton.getInstance().getOutput());
        assertEquals(2, Singleton.getInstance().getPiecesSize());
    }

    @Test
    void add_middle_single_simulated() {
        simulateKeyPresses(28, "Cat ");

        String exp = "Lorem ipsum dolor sit amet, Cat consectetur adipiscing elit.";

        assertEquals(exp, Singleton.getInstance().getOutput());
        assertEquals(3, Singleton.getInstance().getPiecesSize());
    }

    @Test
    void add_middle_simulated() {
        simulateKeyPresses(28, "Cat ");
        simulateKeyPresses(32, "Dog ");

        String exp = "Lorem ipsum dolor sit amet, Cat Dog consectetur adipiscing elit.";

        assertEquals(exp, Singleton.getInstance().getOutput());
        assertEquals(3, Singleton.getInstance().getPiecesSize());
    }

    @Test
    void add_middle_several_simulated() {
        simulateKeyPresses(12, "Cat ");
        simulateKeyPresses(32, "Dog are the best ");

        String exp = "Lorem ipsum Cat dolor sit amet, Dog are the best consectetur adipiscing elit.";

        assertEquals(exp, Singleton.getInstance().getOutput());
        assertEquals(5, Singleton.getInstance().getPiecesSize());
    }

    @Test
    void delete_single(){
        Singleton.getInstance().delete_text_new(5, 6);

        String exp = "Loremipsum dolor sit amet, consectetur adipiscing elit.";

        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void delete_range_start(){
        Singleton.getInstance().delete_text_new(0, 6);
        String exp = "ipsum dolor sit amet, consectetur adipiscing elit.";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void delete_range_end(){
        Singleton.getInstance().delete_text_new(50, 56);
        String exp = "Lorem ipsum dolor sit amet, consectetur adipiscing";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void delete_range_full_piece(){
        Singleton.getInstance().delete_text_new(0, 56);
        String exp = "";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }
}
