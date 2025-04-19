import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class textFile extends Application {
    private static final String NAMA_FILE = "src/AdrianZidan_24552011224.txt";

    @Override
    public void start(Stage stage) {
        TextField inputNama = new TextField();
        inputNama.setPromptText("Masukkan nama");

        TextField inputPesan = new TextField();
        inputPesan.setPromptText("Masukkan pesan");

        Button tombolSimpan = new Button("Simpan ke File");
        Button tombolBaca = new Button("Baca dari File");
        Button tombolUbah = new Button("Ubah Data");

        TextArea areaTeks = new TextArea();
        areaTeks.setEditable(false);

        // Tombol Simpan
        tombolSimpan.setOnAction(e -> {
            try (FileWriter writer = new FileWriter(NAMA_FILE, true)) {
                String data = "Nama: " + inputNama.getText() + ", Pesan: " + inputPesan.getText() + "\n";
                writer.write(data);
                inputNama.clear();
                inputPesan.clear();
                areaTeks.setText("Berhasil menyimpan data.");
            } catch (IOException ex) {
                areaTeks.setText("Gagal menyimpan data.");
            }
        });

        // Tombol Baca
        tombolBaca.setOnAction(e -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
                StringBuilder isi = new StringBuilder();
                String baris;
                while ((baris = reader.readLine()) != null) {
                    isi.append(baris).append("\n");
                }
                areaTeks.setText(isi.toString());
            } catch (IOException ex) {
                areaTeks.setText("Gagal membaca file.");
            }
        });

        // Tombol Ubah
        tombolUbah.setOnAction(e -> {
            String namaInput = inputNama.getText();
            String pesanBaru = inputPesan.getText();

            List<String> isiBaru = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
                String baris;
                boolean ditemukan = false;
                while ((baris = reader.readLine()) != null) {
                    if (baris.contains("Nama: " + namaInput + ",")) {
                        // Ubah pesan untuk nama tersebut
                        baris = "Nama: " + namaInput + ", Pesan: " + pesanBaru;
                        ditemukan = true;
                    }
                    isiBaru.add(baris);
                }

                if (!ditemukan) {
                    areaTeks.setText("Data dengan nama tersebut tidak ditemukan.");
                    return;
                }

            } catch (IOException ex) {
                areaTeks.setText("Gagal membaca file.");
                return;
            }

            // Tulis ulang file dengan data yang sudah diperbarui
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE, false))) {
                for (String baris : isiBaru) {
                    writer.write(baris);
                    writer.newLine();
                }
                areaTeks.setText("Data berhasil diubah.");
                inputNama.clear();
                inputPesan.clear();
            } catch (IOException ex) {
                areaTeks.setText("Gagal menyimpan perubahan.");
            }
        });

        VBox layout = new VBox(10, inputNama, inputPesan, tombolSimpan, tombolUbah, tombolBaca, areaTeks);
        layout.setStyle("-fx-padding: 20px");

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Text File dengan JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
