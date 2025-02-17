package br.com.contacts.api.auth.enums;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private final String role;

    UserRole(String value) {
        this.role = value;
    }

    public String getValue() {
        return role;
    }
}


