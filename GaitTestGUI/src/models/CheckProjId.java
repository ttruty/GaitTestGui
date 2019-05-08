package models;

import java.sql.SQLException;

public class CheckProjId {
	/**
     * Creates a projid of specified length by prepending zeros to an input string and appending check digits
     * @param strProjid a prospective projid, which is a string of digits, usually of length 6 or less
     * @param intSize the target projid length, which is usually 8
     * @return the created projid
     * @throws IllegalArgumentException when input string is too long to append check digits and still be target length
     */
    public String createCheckDigits(String strProjid, int intSize)
            throws IllegalArgumentException {
        int intProjidLen;
 
        // For calculating c1
        int intC1Odds = 0;
        int intC1Evens = 0;
        int intC1Total;
 
        // For calculating c2
        int intC2Total = 0;
 
        String strNewProjid;
 
        // Throw an exception if strprojid is
        if (strProjid.length() > (intSize - 2)) {
            throw new IllegalArgumentException(
                    "The length of strProjid must exceeds " + "that of intSize");
        }
 
        // Add ending 00 for check digits to go
 
        intProjidLen = intSize - 2;
 
        strNewProjid = strProjid;
 
        // Add the heading 0's .
        while (strNewProjid.length() < intProjidLen) {
            strNewProjid = "0" + strNewProjid;
        }
 
        char achrProjid[] = strNewProjid.toCharArray();
 
        // Cycle through each 'digit'
        for (int i = 0; i < intProjidLen; i++) {
            Integer intValue = Integer.valueOf(String.valueOf(achrProjid[i]));
 
            // For C1
            if (i % 2 == 0) {
                intC1Evens += intValue.intValue();
            } else {
                intC1Odds += intValue.intValue();
            }
 
            // For C2
            intC2Total += (i + 1) * intValue.intValue();
        }
 
        // Calculate final check digits
        intC1Total = (intC1Evens + (3 * intC1Odds)) % 10;
        intC2Total = intC2Total % 10;
 
        // first check digit is the tens place (xxx0x-xxx9x)
 
        // second check digit is the ones place (xxxx0-xxxx9)
 
        strNewProjid = strNewProjid + intC1Total + intC2Total;
 
        return strNewProjid;
    }
 
    /**
     * Is the Projid in question a legally formated Projid?
     *
     * @param strProjid The Projid to check.
     * @return <code>true</code> - The Projid is legal.<br>
     * <code>false</code> - The Projid is not legal.<br>
     * @throws Exception                A generic database driver error occurred.
     * @throws IllegalArgumentException strProjid is invalid.
     * @throws SQLException             A database error occurred.
     * @author P. Khanton
     * @since 1.0
     */
    public boolean isLegal(String strProjid){
        boolean bolLegal;
 
        int intMinProjidLength = 8;
 
        char[] achrProjid;
 
        // At least intMinProjidLength required.
        if (strProjid.length() == intMinProjidLength) {
            // Verify strProjid is a number
            try {
                Integer.parseInt(strProjid);
 
                bolLegal = true;
            }
 
            // Bad strProjid
            catch (NumberFormatException e) {
            	bolLegal = false;
//                throw new IllegalArgumentException(
//                        "Projid8::isLegal(String strProjid) "
//                                + "-\n strProjid must only contain "
//                                + "numeric characters.\n" + strProjid);
            }
 
            // Validate check digits
            if (bolLegal) {
                String strChoppedProjid;
                String strRepairedProjid;
 
                // Chop off the check digits to verify
                // the Projid.
                achrProjid = strProjid.substring(0, strProjid.length() - 2)
                        .toCharArray();
 
                strChoppedProjid = String.valueOf(achrProjid);
                // Recreate the Projid.
                strRepairedProjid = createCheckDigits(strChoppedProjid,
                        strProjid.length());
 
                // Did they come out the same?
                if (strRepairedProjid.equals(strProjid)) {
                    bolLegal = true;
                } else {
                	bolLegal = false;
//                    throw new IllegalArgumentException(
//                            "Projid8::isLegal(String "
//                                    + "strProjid) -\n strProjid "
//                                    + "has illegal check digits.\n" + strProjid);
                }
            }
 
        } else {
        	bolLegal = false;
//            throw new IllegalArgumentException(
//                    "Projid8::isLegal(String strProjid) -\n"
//                            + "strProjid must be " + intMinProjidLength
//                            + " characters long.\n" + strProjid);
        }
 
        return bolLegal;
    }

	
}
