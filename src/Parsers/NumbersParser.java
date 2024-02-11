package src.Parsers;

public class NumbersParser {
    private String source;
    private Long integerNumber;
    private Double floatNumber;
    private boolean isNumber;

    public NumbersParser() {}

    public NumbersParser isInteger() {
        try {
            this.setIntegerNumber(Long.parseLong(source));
            this.setIsNumber(true);
            return this;
        } catch (NumberFormatException | NullPointerException e) {
            this.setIsNumber(false);
            return this;
        }
    }


    public NumbersParser isFloat() {
        try {
            this.setFloatNumber(Double.parseDouble(source));
            this.setIsNumber(true);
            return this;
        } catch (NumberFormatException | NullPointerException e) {
            this.setIsNumber(false);
            return this;
        }
    }

    public String getSource() {
        return source;
    }

    public Long getIntegerNumber() {
        return integerNumber;
    }

    public Double getFloatNumber() {
        return floatNumber;
    }

    public boolean getIsNumber() {
        return isNumber;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private void setIntegerNumber(Long integerNumber) {
        this.integerNumber = integerNumber;
    }

    private void setFloatNumber(Double floatNumber) {
        this.floatNumber = floatNumber;
    }

    private void setIsNumber(boolean number) {
        isNumber = number;
    }
}
