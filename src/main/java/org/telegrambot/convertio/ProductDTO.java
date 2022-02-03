package org.telegrambot.convertio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

public enum ProductDTO {
    ;

    public enum Request {
        ;

        @Value
        @Builder
        public static class Create implements Name, Price, Cost {
            String name;
            Double price;
            Double cost;
        }
    }

    public enum Response {
        ;

        @Value
        @Builder
        public static class Public implements Id, Name, Price {
            Long id;
            String name;
            Double price;
        }

        @Value
        @Builder
        public static class Private implements Id, Name, Price, Cost {
            Long id;
            String name;
            Double price;
            Double cost;
        }
    }

    private interface Id {
        @Positive Long getId();
    }

    private interface Name {
        @NotBlank String getName();
    }

    private interface Price {
        @Positive Double getPrice();
    }

    private interface Cost {
        @Positive Double getCost();
    }
}
