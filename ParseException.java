class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}

class DataFormatException extends ParseException {
    public DataFormatException(String detail) {
        super("Неверный формат данных: " + detail);
    }
}

class CountMismatchException extends ParseException {
    public CountMismatchException(int expected, int actual) {
        super("Ожидалось " + expected + " элемента, но было предоставлено " + actual);
    }
}
