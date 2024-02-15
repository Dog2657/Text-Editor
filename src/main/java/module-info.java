module com.dog2657.richtext {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dog2657.richtext to javafx.fxml;
    exports com.dog2657.richtext;
}