import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class KalkulatorApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Label dan input
        Label titleLabel = new Label("Kalkulator Modern");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setStyle("-fx-font-weight: bold;");

        TextField input1 = new TextField();
        input1.setPromptText("Angka pertama");

        TextField input2 = new TextField();
        input2.setPromptText("Angka kedua");

        ComboBox<String> operationBox = new ComboBox<>();
        operationBox.getItems().addAll("Tambah", "Kurang", "Kali", "Bagi", "Pangkat");
        operationBox.setPromptText("Pilih operasi");

        Button hitungBtn = new Button("Hitung");
        Button clearBtn = new Button("Clear");

        Label resultLabel = new Label("Hasil akan muncul di sini.");
        resultLabel.setStyle("-fx-text-fill: #333;");
        resultLabel.setFont(Font.font(14));

        // Grid Layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(input1, 0, 1, 2, 1);
        grid.add(input2, 0, 2, 2, 1);
        grid.add(operationBox, 0, 3, 2, 1);
        grid.add(hitungBtn, 0, 4);
        grid.add(clearBtn, 1, 4);
        grid.add(resultLabel, 0, 5, 2, 1);

        // Tombol Hitung
        hitungBtn.setOnAction(e -> {
            try {
                double a = Double.parseDouble(input1.getText());
                double b = Double.parseDouble(input2.getText());
                String op = operationBox.getValue();

                if (op == null) {
                    resultLabel.setText("Pilih operasi terlebih dahulu.");
                    return;
                }

                double hasil = 0;
                switch (op) {
                    case "Tambah":
                        hasil = tambah(a, b);
                        break;
                    case "Kurang":
                        hasil = kurang(a, b);
                        break;
                    case "Kali":
                        hasil = kali(a, b);
                        break;
                    case "Bagi":
                        if (b == 0) {
                            resultLabel.setText("Tidak bisa dibagi nol.");
                            return;
                        }
                        hasil = bagi(a, b);
                        break;
                    case "Pangkat":
                        hasil = pangkat((int)a, (int)b);
                        break;
                }

                resultLabel.setText("Hasil: " + hasil);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Masukkan angka yang valid.");
            }
        });

        // Tombol Clear
        clearBtn.setOnAction(e -> {
            input1.clear();
            input2.clear();
            operationBox.setValue(null);
            resultLabel.setText("Hasil akan muncul di sini.");
        });

        // Scene dan styling
        Scene scene = new Scene(grid, 350, 350);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Kalkulator Modern");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method dasar
    private double tambah(double a, double b) {
        return a + b;
    }

    private double kurang(double a, double b) {
        return a - b;
    }

    private double kali(double a, double b) {
        return a * b;
    }

    private double bagi(double a, double b) {
        return a / b;
    }

    // Rekursi untuk pangkat
    private int pangkat(int base, int exponent) {
        if (exponent == 0) return 1;
        return base * pangkat(base, exponent - 1);
    }
}
