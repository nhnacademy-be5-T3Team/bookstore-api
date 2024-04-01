package com.t3t.bookstoreapi.model.enums;

public enum TableStatus {
    TRUE(1, true),
    FALSE(0, false);

    private Integer value;
    private boolean status;

    TableStatus(Integer value, boolean status) {
        this.value = value;
        this.status = status;
    }

    public static boolean getStatusFromValue(Integer value) {
        for (TableStatus tableStatus : TableStatus.values()) {
            if (tableStatus.value.equals(value)) {
                return tableStatus.status;
            }
        }
        return FALSE.status;
    }
}