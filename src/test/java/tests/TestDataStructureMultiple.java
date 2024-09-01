package tests;

import com.dog2657.richtext.DataStructure.DataStructure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDataStructureMultiple {
    private class Singleton{
        private static DataStructure instance;

        public static void set(String[] content){
            int length = content[0].length();

            instance = new DataStructure(content[0]);

            for (int i=1; i<content.length; i++) {
                String text = content[i];

                instance.add_text(length, text);
                length += text.length();
            }
        }

        public static DataStructure getInstance(){
            return instance;
        }
    }

    @BeforeEach
    void setUp() {
        String[] s = {"Lorem ", "ipsum", " dol","or ", "sit ame","t, ", "consectetur", " adipiscin", "g elit."};
        Singleton.set(s);
    }

    @Test
    void delete_range_full_pieces(){
        Singleton.getInstance().delete_text_new(0, 56);
        String exp = "";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void delete_range_middle_pieces_split_start(){
        //"Lorem ipsum dolor sit amet, consectetur adipiscing elit."

        Singleton.getInstance().delete_text_new( 14, 39);
        String exp = "Lorem ipsum do adipiscing elit.";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void delete_range_middle_pieces_split_start_end(){//
        Singleton.getInstance().delete_text_new( 8, 50);
        String exp = "Lorem ip elit.";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void delete_range_middle_pieces_split_end(){
        Singleton.getInstance().delete_text_new( 11, 34);
        String exp = "Lorem ipsumtetur adipiscing elit.";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void delete_range_middle_pieces_no_split(){
        Singleton.getInstance().delete_text_new( 11, 39);
        String exp = "Lorem ipsum adipiscing elit.";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void delete_range_half_pieces_start_no_split(){
        Singleton.getInstance().delete_text_new( 15, 56);
        String exp = "Lorem ipsum dol";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }

    @Test
    void inside_single_piece_delete(){
        Singleton.getInstance().delete_text_new( 30, 38);
        String exp = "Lorem ipsum dolor sit amet, cor adipiscing elit.";
        assertEquals(exp, Singleton.getInstance().getOutput());
    }
}
