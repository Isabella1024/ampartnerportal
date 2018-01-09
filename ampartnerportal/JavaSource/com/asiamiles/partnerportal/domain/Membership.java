package com.asiamiles.partnerportal.domain;

public class Membership {

    private String memberId;
    private String lastName;
    private String firstName;
    private String checkingMethod;
    private String lastNameChar;
    private String firstNameChar;
    private String  isSwapName;
    
    
    /**
     * @return the checkingMethod
     */
    public String getCheckingMethod() {
        return checkingMethod;
    }
    /**
     * @param checkingMethod the checkingMethod to set
     */
    public void setCheckingMethod(String checkingMethod) {
        this.checkingMethod = checkingMethod;
    }
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the firstNameChar
     */
    public String getFirstNameChar() {
        return firstNameChar;
    }
    /**
     * @param firstNameChar the firstNameChar to set
     */
    public void setFirstNameChar(String firstNameChar) {
        this.firstNameChar = firstNameChar;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the lastNameChar
     */
    public String getLastNameChar() {
        return lastNameChar;
    }
    /**
     * @param lastNameChar the lastNameChar to set
     */
    public void setLastNameChar(String lastNameChar) {
        this.lastNameChar = lastNameChar;
    }
    /**
     * @return the memberId
     */
    public String getMemberId() {
        return memberId;
    }
    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    /**
     * @return the isSwapName
     */
//    public boolean isSwapName() {
//        return isSwapName;
//    }
//    /**
//     * @param isSwapName the isSwapName to set
//     */
//    public void setSwapName(boolean isSwapName) {
//        this.isSwapName = isSwapName;
//    }
    /**
     * @return the isSwapName
     */
    public String getIsSwapName() {
        return isSwapName;
    }
    /**
     * @param isSwapName the isSwapName to set
     */
    public void setIsSwapName(String isSwapName) {
        this.isSwapName = isSwapName;
    }

}
