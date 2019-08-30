package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckFiles {
//	public static boolean checkSum(String srcFile, String dstFile) throws NoSuchAlgorithmException, IOException {
//        System.out.println("Are identical: " + isIdentical("c:\\myfile.txt", "c:\\myfile2.txt"));
//    }

    public static boolean isIdentical(String leftFile, String rightFile) throws IOException, NoSuchAlgorithmException {
        return md5(leftFile).equals(md5(rightFile));
    }

    private static String md5(String file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        File f = new File(file);
        InputStream is = new FileInputStream(f);
        byte[] buffer = new byte[8192];
        int read = 0;
        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);
            return output;
        } finally {
            is.close();
        }
    }

}
