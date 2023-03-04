import rotor96Crypto.Rotor96Crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class KPA {

    public static void main(String[] args) {

        File f = new File("C:\\Uni\\CSD-Coursework\\decryption-attacks\\src\\passwords");

        String ct = "g5P\u007FWC9WwvV2./gvu\\5fV%D}]^RQ^Cc>u$zoj2l`AIhZD.Q1Gl?K,BF'U8rw_^(mVzJk:LmLmx(|P qFjMi\"_gQfm6#84r;1,u;I";
        String known_pt = "Th";

        ArrayList<String> good_keys = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String key;
            while ((key = br.readLine()) != null) {
                String decrypted_ct = Rotor96Crypto.encdec(2, key, ct);
                if (known_pt.equals(decrypted_ct.substring(0, 2))) {
                    good_keys.add(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int bestKeyIndex = 0;
        double highestScore = new CipherText(Rotor96Crypto.encdec(2, good_keys.get(0), ct)).getScore();
        for (int i = 1; i < good_keys.size(); i++) {
            CipherText temp_ct = new CipherText(Rotor96Crypto.encdec(2, good_keys.get(i), ct));
            if (temp_ct.getScore() > highestScore) {
                bestKeyIndex = i;
            }
        }

        String correctKey = good_keys.get(bestKeyIndex);
        System.out.printf("Correct key is: %s %n", correctKey);
        System.out.printf("The decoded message is: %n%s %n", Rotor96Crypto.encdec(2, correctKey, ct));
    }
}
