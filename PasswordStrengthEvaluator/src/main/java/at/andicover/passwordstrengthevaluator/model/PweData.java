package at.andicover.passwordstrengthevaluator.model;

import java.util.Objects;

public class PweData {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PweData pweData = (PweData) o;
        return Objects.equals(password, pweData.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}