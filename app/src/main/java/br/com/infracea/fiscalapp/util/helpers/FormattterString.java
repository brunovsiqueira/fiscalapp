package br.com.infracea.fiscalapp.util.helpers;

public class FormattterString {

    public static String formatCpf(String cpf) {

        return cpf.replaceAll("[^\\d.]", "");
    }

}
