package com.github.daveb8772.ocpi.ocpirestservice.controller.ChargeModels;

public class ConnectorType {


    private final ConnectorTypeValue connectorTypeValue;
    public enum ConnectorTypeValue {
        TYPE1,
        TYPE2,
        CCS_COMBO_2,
        CHAdeMO,
        TESLA
    }


    public ConnectorType(ConnectorTypeValue connectorTypeValue) {
        this.connectorTypeValue = connectorTypeValue;
    }

    public ConnectorTypeValue getValue() {
        return connectorTypeValue;
    }

    public static ConnectorTypeValue fromValue(ConnectorTypeValue connectorTypeValue) {
        if (connectorTypeValue == null) {
            return null;
        }

        return switch (connectorTypeValue) {
            case TYPE1 -> ConnectorTypeValue.TYPE1;
            case TYPE2 -> ConnectorTypeValue.TYPE2;
            case CCS_COMBO_2 -> ConnectorTypeValue.CCS_COMBO_2;
            case CHAdeMO -> ConnectorTypeValue.CHAdeMO;
            case TESLA -> ConnectorTypeValue.TESLA;
        };
    }

    @Override
    public String toString() {
        return connectorTypeValue.name();
    }
}
