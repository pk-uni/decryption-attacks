import rotor96Crypto.Rotor96Crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class COAv1 {


    static List<CipherText> mostLikely = new ArrayList<>();

    public static void main(String[] args) {

        String ciphertext = "ojf#=1A8eduHhV#g-5u-G\"uDP-Jxv/1^3YF7zqb\u007F^N^.0l%`V+,R6Jhh8(9lWlrD$+>=E$kgK[<TC:f, H[XSZt?7&f|~Y?#7(hCu-}zfauoi+#g^,B`ZzOp)*Ok$f,Xs\u007F#0t9B2XP-{[O7CG+k|B~\"[SzMM>~!o_nfX.$VJks(0HY vCeH~z&x)\"Mf(2UQ3uHX4=1A8p;l=R>tetl+-H^!p>{MJvTg?TyoxW1|ch\\>U'pI>!TkKcP0h,iCZ&4kU:C]Os$3ceYRvuvf=+e\\_ It[MFxo,w6S7=O3eJHh+*mHi`DecpD`<\"-aNDwER?du,&HpE<|0N";
        String correctKey = findKey(ciphertext);

        List<Integer> isCorrectKeyArr = new ArrayList<>();
        System.out.println("Progress:");
        for (int i = 0; i < ciphertext.length(); i++) {
            String currentKey = findKey(ciphertext.substring(0, 1 + i));
            isCorrectKeyArr.add((Objects.equals(currentKey, correctKey)) ? 1 : 0);
            System.out.printf("%.2f%%\r", ((double) i / ciphertext.length()) * 100);
        }
        int unambiguousDecodingMinLength = isCorrectKeyArr.lastIndexOf(0) + 1;
        System.out.println("100%");
        System.out.println("---------------");
        System.out.printf("Ciphertext letters required before unambiguous decoding is possible: %d %n", unambiguousDecodingMinLength);
        System.out.printf("Correct key is: %s %n", correctKey);
    }

    public static String findKey(String ct) {
        mostLikely.clear();
        File f = new File("C:\\Uni\\CSD-Coursework\\decryption-attacks\\src\\passwords");
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String key;
            String foundKey = "";
            while ((key = br.readLine()) != null) {
                String decrypted_ct = Rotor96Crypto.encdec(2, key, ct);
                CipherText temp_ct = new CipherText(decrypted_ct);
                int keyIndex = updateMostLikely(temp_ct);
                if (keyIndex == 0) foundKey = key;
            }
            return foundKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int updateMostLikely(CipherText o) {
        if (mostLikely.size() == 0) {
            mostLikely.add(o);
            return 0;
        }
        for (int i = 0; i < mostLikely.size(); i++) {
            if (o.compareTo(mostLikely.get(i)) > 0) {
                mostLikely.add(i, o);
                if (mostLikely.size() > 10) {
                    mostLikely = mostLikely.subList(0, 10);
                }
                return i;
            }
        }
        return -1;
    }
}
