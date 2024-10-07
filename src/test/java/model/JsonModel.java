package model;

import java.util.List;

public class JsonModel {

    private String id;
    private String name;
    private List<AccountsModel> accounts;
    private boolean active;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public List<AccountsModel> getAccounts() {
        return accounts;
    }

    public static class AccountsModel {

        private String number;
        private String currency;
        private String bank;

        public String getNumber() {
            return number;
        }

        public String getCurrency() {
            return currency;
        }

        public String getBank() {
            return bank;
        }
    }
}
