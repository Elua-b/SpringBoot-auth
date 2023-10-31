package live.learnlynx.api.v1.enums;

public enum ECurrency {
    USD("US Dollar"),
    EUR("Euro"),
    GBP("British Pound"),
    JPY("Japanese Yen"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CHF("Swiss Franc"),
    CNY("Chinese Yuan"),
    HKD("Hong Kong Dollar"),
    NZD("New Zealand Dollar"),
    INR("Indian Rupee"),
    SGD("Singapore Dollar"),
    MYR("Malaysian Ringgit"),
    KRW("South Korean Won"),
    THB("Thai Baht"),
    IDR("Indonesian Rupiah"),
    PHP("Philippine Peso"),
    MXN("Mexican Peso"),
    BRL("Brazilian Real"),
    RUB("Russian Ruble"),
    ZAR("South African Rand"),
    SAR("Saudi Arabian Riyal"),
    AED("United Arab Emirates Dirham"),
    SEK("Swedish Krona"),
    NOK("Norwegian Krone"),
    DKK("Danish Krone"),
    RWF("Rwandan Franc");

    private final String displayName;

    ECurrency(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

