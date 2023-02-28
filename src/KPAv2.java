import rotor96Crypto.Rotor96Crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class KPAv2 {

    public static void main(String[] args) {

        File f = new File("C:\\Uni\\CSD-Coursework\\decryption-attacks\\src\\passwords");

        String ct = "g5P\u007FWC9WwvV2./gvu\\5fV%D}]^RQ^Cc>u$zoj2l`AIhZD.Q1Gl?K,BF'U8rw_^(mVzJk:LmLmx(|P qFjMi\"_gQfm6#84r;1,u;I";
        String known_pt = "Th";


        ArrayList<String> good_keys = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String key;
            while ((key = br.readLine()) != null) {
                String encrypted_known_pt = Rotor96Crypto.encdec(1, key, known_pt);
                if (encrypted_known_pt.equals(ct.substring(0, 2))) {
                    good_keys.add(key);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println(ct);
        System.out.println(good_keys);
        for (String key : good_keys) {
            System.out.println(Rotor96Crypto.encdec(2, key, ct));
        }
    }
}

