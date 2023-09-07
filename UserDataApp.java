import java.io.*;
import java.text.*;
import java.util.*;

public class UserDataApp {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия, Имя, Отчество, дата рождения(dd.MM.yyyy), номер телефона, пол(m или f)):");
        String input = scanner.nextLine();

        try {
            UserData data = parseData(input);
            saveData(data);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static UserData parseData(String input) throws ParseException {
        String[] parts = input.split(" ");
        if (parts.length != 6) {
            throw new CountMismatchException(6, parts.length);
        }

        try {
            UserData data = new UserData();
            data.lastName = parts[0];
            data.firstName = parts[1];
            data.middleName = parts[2];
            data.birthDate = DATE_FORMAT.parse(parts[3]); // здесь может возникнуть java.text.ParseException
            data.phoneNumber = Long.parseLong(parts[4]);
            if (!parts[5].matches("[mf]")) {
                throw new DataFormatException("неверный пол");
            }
            data.gender = parts[5].charAt(0);
            return data;
        } catch (NumberFormatException e) {
            throw new DataFormatException("номер телефона неверного формата");
        } catch (java.text.ParseException e) {  // здесь я использую полное имя класса
            throw new DataFormatException("дата рождения неверного формата");
        }
    }


    private static void saveData(UserData data) throws IOException {
        try (FileWriter writer = new FileWriter(data.lastName + ".txt", true)) {
            writer.write(data.toString());
            writer.write(System.lineSeparator());
        }
    }

    static class UserData {
        String lastName;
        String firstName;
        String middleName;
        Date birthDate;
        long phoneNumber;
        char gender;

        @Override
        public String toString() {
            return lastName + " " + firstName + " " + middleName + " " +
                    DATE_FORMAT.format(birthDate) + " " + phoneNumber + " " + gender;
        }
    }
}
