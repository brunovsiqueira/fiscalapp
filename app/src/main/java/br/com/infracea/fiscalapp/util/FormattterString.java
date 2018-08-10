package br.com.infracea.fiscalapp.util;

public class FormattterString {

    public static String formatCpf(String cpf) {
        String str = "a12.334tyz.78x";
        return str.replaceAll("[^\\d.]", "");
    }

}
