import rotor96Crypto.Rotor96Crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class COAv1 {
    public static void main(String[] args) {
        File f = new File("C:\\Uni\\CSD-Coursework\\decryption-attacks\\src\\passwords");

        String ct = "g5P\u007FWC9WwvV2./gvu\\5fV%D}]^RQ^Cc>u$zoj2l`AIhZD.Q1Gl?K,BF'U8rw_^(mVzJk:LmLmx(|P qFjMi\"_gQfm6#84r;1,u;I";

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String key;
            while ((key = br.readLine()) != null) {
                String decrypted_ct = Rotor96Crypto.encdec(2, key, ct);
                System.out.println(decrypted_ct.substring(0, 10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}