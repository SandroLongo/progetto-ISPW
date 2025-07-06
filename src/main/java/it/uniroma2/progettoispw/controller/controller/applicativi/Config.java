package it.uniroma2.progettoispw.controller.controller.applicativi;

public class Config {
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_SURNAME_LENGTH = 50;
    public static final int MIN_SURNAME_LENGTH = 1;
    public static final int MAX_EMAIL_LENGTH = 255;
    public static final int MIN_EMAIL_LENGTH = 1;
    public static final int MAX_PASSWORD_LENGTH = 100;
    public static final int MIN_PASSWORD_LENGTH = 1;
    public static final int MAX_CF_LENGTH = 16;
    public static final int MIN_CF_LENGTH = 16;
    public static final int MIN_AGE = 18;
    public static final int MAX_PHONE_LENGTH = 15;
    public static final int MIN_PHONE_LENGTH = 1;
    public static final int MAX_ADDRESS_LENGTH = 255;
    public static final int MIN_ADDRESS_LENGTH = 1;
    public static final int MIN_DOCTOR_CODE_LENGTH = 1;
    public static final int MAX_DOCTOR_CODE_LENGTH = 5;
    public static final int MAX_DESCRIPTION_LENGTH = 1500;
    public static final int MAX_ACTIVE_INGRIDIENT_ID_LENGHT = 9;
    public static final int MAX_MODICINAL_PRODUCT_ID_LENGHT = 9;
    public static final int MAX_QUANTITY_ALLOWED = 100000;
    public static final int MAX_REPETITION_ALLOWED = 100;
    public static final int MAX_RATE_ALLOWED = 30;
    public static final int MAX_MEAUSUREMENT_UNIT_LENGHT = 100;


    private Config() {
        //in modo da non poter creare la classe
    }
}
