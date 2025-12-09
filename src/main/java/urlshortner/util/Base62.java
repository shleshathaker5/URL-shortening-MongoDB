package urlshortner.util;

import java.math.BigInteger;

public final class Base62 {
    private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int BASE = ALPHABET.length;

    private Base62() {}

    // Encode unsigned byte[] to Base62 string
    public static String encode(byte[] bytes) {
        BigInteger value = new BigInteger(1, bytes); // 1 -> positive
        if (value.equals(BigInteger.ZERO)) return "0";

        StringBuilder sb = new StringBuilder();
        BigInteger base = BigInteger.valueOf(BASE);
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divRem = value.divideAndRemainder(base);
            value = divRem[0];
            int rem = divRem[1].intValue();
            sb.append(ALPHABET[rem]);
        }
        return sb.reverse().toString();
    }

    // Decode Base62 string into unsigned byte[] of expected length (pad/truncate as needed)
    public static byte[] decodeToBytes(String str, int expectedLength) {
        BigInteger value = BigInteger.ZERO;
        BigInteger base = BigInteger.valueOf(BASE);
        for (char c : str.toCharArray()) {
            int digit = charToIndex(c);
            value = value.multiply(base).add(BigInteger.valueOf(digit));
        }
        byte[] full = value.toByteArray(); // may contain a leading 0 byte for sign
        // Convert to unsigned byte[] of length expectedLength
        byte[] result = new byte[expectedLength];
        int srcPos = Math.max(0, full.length - expectedLength);
        int length = Math.min(full.length, expectedLength);
        System.arraycopy(full, srcPos, result, expectedLength - length, length);
        return result;
    }

    private static int charToIndex(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'A' && c <= 'Z') return 10 + (c - 'A');
        if (c >= 'a' && c <= 'z') return 36 + (c - 'a');
        throw new IllegalArgumentException("Invalid Base62 character: " + c);
    }
}