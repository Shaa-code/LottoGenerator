module com.lotto.lottogenerator {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.lotto.lottogenerator to javafx.fxml;
    exports com.lotto.lottogenerator;
}