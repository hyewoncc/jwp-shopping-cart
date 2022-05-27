package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class PhoneNumber {

    private static final int LIMIT_LENGTH = 11;
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[0-9]*$");

    private final String phoneNumber;

    public PhoneNumber(final String phoneNumber) {
        validateLength(phoneNumber);
        validatePattern(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    private void validateLength(final String phoneNumber) {
        if (phoneNumber.length() != LIMIT_LENGTH) {
            throw new IllegalArgumentException(String.format("핸드폰 번호는 %d자여야합니다.", LIMIT_LENGTH));
        }
    }

    private void validatePattern(final String phoneNumber) {
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("핸드폰 번호는 숫자만 가능합니다.");
        }
    }
}