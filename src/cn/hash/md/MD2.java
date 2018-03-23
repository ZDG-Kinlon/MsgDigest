package cn.hash.md;

import cn.hash.HASH;

public class MD2 extends HASH {

    private static final int[] S = new int[]{
            41, 46, 67, 201, 162, 216, 124, 1, 61, 54, 84, 161, 236, 240, 6,
            19, 98, 167, 5, 243, 192, 199, 115, 140, 152, 147, 43, 217, 188,
            76, 130, 202, 30, 155, 87, 60, 253, 212, 224, 22, 103, 66, 111, 24,
            138, 23, 229, 18, 190, 78, 196, 214, 218, 158, 222, 73, 160, 251,
            245, 142, 187, 47, 238, 122, 169, 104, 121, 145, 21, 178, 7, 63,
            148, 194, 16, 137, 11, 34, 95, 33, 128, 127, 93, 154, 90, 144, 50,
            39, 53, 62, 204, 231, 191, 247, 151, 3, 255, 25, 48, 179, 72, 165,
            181, 209, 215, 94, 146, 42, 172, 86, 170, 198, 79, 184, 56, 210,
            150, 164, 125, 182, 118, 252, 107, 226, 156, 116, 4, 241, 69, 157,
            112, 89, 100, 113, 135, 32, 134, 91, 207, 101, 230, 45, 168, 2, 27,
            96, 37, 173, 174, 176, 185, 246, 28, 70, 97, 105, 52, 64, 126, 15,
            85, 71, 163, 35, 221, 81, 175, 58, 195, 92, 249, 206, 186, 197,
            234, 38, 44, 83, 13, 110, 133, 40, 132, 9, 211, 223, 205, 244, 65,
            129, 77, 82, 106, 220, 55, 200, 108, 193, 171, 250, 36, 225, 123,
            8, 12, 189, 177, 74, 120, 136, 149, 139, 227, 99, 232, 109, 233,
            203, 213, 254, 59, 0, 29, 57, 242, 239, 183, 14, 102, 88, 208, 228,
            166, 119, 114, 248, 235, 117, 75, 10, 49, 68, 80, 180, 143, 237,
            31, 26, 219, 153, 141, 51, 159, 17, 131, 20,
    };

    private static final byte[][] PADDING;

    static {
        PADDING = new byte[17][];
        for (int i = 1; i < 17; i++) {
            byte[] b = new byte[i];
            for (int n = 0, len = b.length; n < len; n++)
                b[n] = (byte) i;
            PADDING[i] = b;
        }
    }

    private int[] X;

    private int[] C;

    private byte[] cBytes;

    public MD2() {
        super("MD2", 16, 16);
        X = new int[48];
        C = new int[16];
        cBytes = new byte[16];
    }

    public void implReset() {
        fill(X, 0);
        fill(C, 0);
    }

    public void implDigest(byte[] out, int ofs) {
        int padValue = 16 - ((int) bytesProcessed & 15);
        update(PADDING[padValue], 0, padValue);
        for (int i = 0; i < 16; i++) {
            cBytes[i] = (byte) C[i];
        }
        implCompress(cBytes, 0);
        for (int i = 0; i < 16; i++) {
            out[ofs + i] = (byte) X[i];
        }
    }

    public void implCompress(byte[] b, int ofs) {
        for (int i = 0; i < 16; i++) {
            int k = b[ofs + i] & 0xff;
            X[16 + i] = k;
            X[32 + i] = k ^ X[i];
        }

        int t = C[15];
        for (int i = 0; i < 16; i++) {
            t = (C[i] ^= S[X[16 + i] ^ t]);
        }

        t = 0;
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 48; j++) {
                t = (X[j] ^= S[t]);
            }
            t = (t + i) & 0xff;
        }
    }
}
