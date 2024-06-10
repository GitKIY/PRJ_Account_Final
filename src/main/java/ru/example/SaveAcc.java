package ru.example;

import lombok.Getter;

import java.util.Arrays;

public class SaveAcc {
    private final String owner;
    private final CurrAmount[] amtArr;

    public SaveAcc(String owner, CurrAmount[] amtArr) {
        this.owner = owner;
        this.amtArr = amtArr;
    }

    public String getOwner() {
        return owner;
    }

    public CurrAmount[] getAmtArr() {
        return amtArr;
    }

    public String toString() {
        if (this == null) return "Объект не инициализирован.";
        return "Account{" +
                "ownerr='" + owner + '\'' +
                ", amtArr=" + Arrays.toString(amtArr) +
                '}';
    }
}
