package com.exchange.stock.matching.orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private Symbol symbol;

    public enum Symbol {
        BAC("BANK OF AMERICA"),
        TCS("TATA CONSULTANCY SERVICES");

        private String companyName;

        Symbol(final String companyName) {
            this.companyName = companyName;
        }

        @Override
        public String toString() {
            return "Symbol{" +
                    "companyName='" + companyName + '\'' +
                    '}';
        }
    }

}
