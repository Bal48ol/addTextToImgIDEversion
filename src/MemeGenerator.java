import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemeGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nВведите команду (help/meme/exit): ");
            String command = scanner.nextLine();

            switch (command) {
                case "help":
                    displayHelpManual();
                    break;
                case "meme":
                    System.out.print("\nВведите путь файла: ");
                    String imagePath = scanner.nextLine();
                    System.out.print("\nВведите текст мема: ");
                    String text = scanner.nextLine();
                    System.out.print("\nВведите номер позиции текста: ");
                    String position = scanner.nextLine();
                    System.out.print("\nВведите номер шрифта текста: ");
                    String font = scanner.nextLine();
                    addTextToImage(imagePath, text, position, font);
                    break;
                case "exit":
                    System.out.println("\nВыход из программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("\nНеизвестная команда: " + command);
            }
        }
    }

    private static void displayHelpManual() {
        System.out.println("\nКоманды:");
        System.out.println("\thelp — Мануал по использованию программы");
        System.out.println("\tmeme — Добавить текст к картинке");
        System.out.println("\texit — Выход из программы");
        System.out.println("\nНомер места в котором будет картинка:");
        System.out.println("\t1 - Вверху");
        System.out.println("\t2 - В центре");
        System.out.println("\t3 - Внизу");
        System.out.println("\nНомер шрифта текста:");
        System.out.println("\t1 - Impact");
        System.out.println("\t2 - Comic Sans MS");
        System.out.println("\t3 - Times New Roman");
        System.out.println("\t4 - Tahoma");
    }

    private static void addTextToImage(String imagePath, String text, String position, String textFont) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            Graphics2D graphics = image.createGraphics();

            var fontsError = new ArrayList<>(){{
                add("1");
                add("2");
                add("3");
                add("4");
            }};
            var posError = new ArrayList<>(){{
                add("1");
                add("2");
                add("3");
            }};

            if (!fontsError.contains(textFont) && !posError.contains(position)){
                System.out.println("\nНеверное значение позиции текста и шрифта. Прочитайте мануал (help)");
                return;
            }

            // Установка шрифта и других свойств текста
            List<Font> fonts = new ArrayList<>();
            switch (textFont){
                case "1":
                    Font font1 = new Font("Impact", Font.BOLD, 36);
                    fonts.add(font1);
                    break;
                case "2":
                    Font font2 = new Font("Comic Sans MS", Font.BOLD, 36);
                    fonts.add(font2);
                    break;
                case "3":
                    Font font3 = new Font("Times New Roman", Font.BOLD, 36);
                    fonts.add(font3);
                    break;
                case "4":
                    Font font4 = new Font("Tahoma", Font.BOLD, 36);
                    fonts.add(font4);
                    break;
                default:
                    System.out.println("\nНеверный номер шрифта. Прочитайте мануал (help)");
                    return;
            }
            for (Font font : fonts){
                graphics.setFont(font);
                graphics.setColor(Color.WHITE);
            }

            // Расчет позиции для размещения текста
            FontMetrics fontMetrics = graphics.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(text);
            int textHeight = fontMetrics.getHeight();
            int x;
            int y;

            if (position.equalsIgnoreCase("1")) {
                x = (image.getWidth() - textWidth) / 2;
                y = textHeight;
            }
            else if (position.equalsIgnoreCase("2")) {
                x = (image.getWidth() - textWidth) / 2;
                y = (image.getHeight() - textHeight) / 2;
            }
            else if (position.equalsIgnoreCase("3")) {
                x = (image.getWidth() - textWidth) / 2;
                y = image.getHeight() - textHeight;
            }
            else {
                System.out.println("\nНеверное значение позиции текста. Прочитайте мануал (help)");
                return;
            }

            // Добавление текста на изображение
            graphics.drawString(text, x, y);
            graphics.dispose();

            // Сохранение измененного изображения
            String outputImagePath = getOutputImagePath(imagePath);
            ImageIO.write(image, "png", new File(outputImagePath));

            System.out.println("\nТекст добавлен к картинке. Путь мем-картинки: " + outputImagePath);
        }
        catch (IOException e) {
            System.out.println("\nОшибка: " + e.getMessage());
        }
    }

    private static String getOutputImagePath(String imagePath) {
        String directory = new File(imagePath).getParent();
        String fileName = new File(imagePath).getName();
        return directory + File.separator + "meme_" + fileName;
    }
}
