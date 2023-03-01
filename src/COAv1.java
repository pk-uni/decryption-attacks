import rotor96Crypto.Rotor96Crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class COAv1 {

    static List<CipherText> mostLikely = new ArrayList<>();

    public static void main(String[] args) {
        File f = new File("C:\\Uni\\CSD-Coursework\\decryption-attacks\\src\\passwords");

        String ct = "ojf#=1A8eduHhV#g-5u-G\"uDP-Jxv/1^3YF7zqb\u007F^N^.0l%`V+,R6Jhh8(9lWlrD$+>=E$kgK[<TC:f, H[XSZt?7&f|~Y?#7(hCu-}zfauoi+#g^,B`ZzOp)*Ok$f,Xs\u007F#0t9B2XP-{[O7CG+k|B~\"[SzMM>~!o_nfX.$VJks(0HY vCeH~z&x)\"Mf(2UQ3uHX4=1A8p;l=R>tetl+-H^!p>{MJvTg?TyoxW1|ch\\>U'pI>!TkKcP0h,iCZ&4kU:C]Os$3ceYRvuvf=+e\\_ It[MFxo,w6S7=O3eJHh+*mHi`DecpD`<\"-aNDwER?du,&HpE<|0N";

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String key;
            while ((key = br.readLine()) != null) {
                String decrypted_ct = Rotor96Crypto.encdec(2, key, ct);
                CipherText temp_ct = new CipherText(decrypted_ct);
                updateMostLikely(temp_ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            CipherText o = mostLikely.get(i);
            System.out.printf("%s - %s%n", i, o.getScore());
        }
    }

    public static void updateMostLikely(CipherText o) {
        if (mostLikely.size() == 0) {
            mostLikely.add(o);
            return;
        }
        for (int i = 0; i < mostLikely.size(); i++) {
            if (o.compareTo(mostLikely.get(i)) > 0) {
                mostLikely.add(i, o);
                if (mostLikely.size() > 10) {
                    mostLikely = mostLikely.subList(0, 10);
                }
                return;
            }
        }
    }
}
