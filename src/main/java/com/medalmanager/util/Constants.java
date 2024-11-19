package com.medalmanager.util;

public final class Constants {
    private Constants() {}

    public static final int REQUIRED_COUNTRIES = 16;
    public static final String APP_TITLE = "Gerenciador de Medalhas";
    public static final String ERROR_DIALOG_TITLE = "Erro";
    public static final String SUCCESS_DIALOG_TITLE = "Sucesso";

    public static final class Database {
        private Database() {}

        public static final String COUNTRIES_TABLE = "paises";
        public static final String MODALITIES_TABLE = "modalidades";
    }

    public static final class Messages {
        private Messages() {}

        public static final String COUNTRY_COUNT_ERROR = "Exatamente 16 países precisam ser selecionados";
        public static final String SAVE_SUCCESS = "Dados salvos com sucesso!";
        public static final String DATABASE_ERROR = "Erro com o banco de dados!";
    }
}