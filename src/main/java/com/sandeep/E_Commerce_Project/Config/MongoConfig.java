package com.sandeep.E_Commerce_Project.Config;


import org.bson.types.Decimal128;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new BigDecimalToDecimal128Converter(),
                new Decimal128ToBigDecimalConverter()
        ));
    }

    static class BigDecimalToDecimal128Converter
            implements org.springframework.core.convert.converter.Converter<BigDecimal, Decimal128> {

        @Override
        public Decimal128 convert(BigDecimal source) {
            return new Decimal128(source);
        }
    }

    static class Decimal128ToBigDecimalConverter
            implements org.springframework.core.convert.converter.Converter<Decimal128, BigDecimal> {

        @Override
        public BigDecimal convert(Decimal128 source) {
            return source.bigDecimalValue();
        }
    }
}


//this is to convert BigDecimal into Decimal form because BigDecimal is not supported in mongoDb but support in SQL databases thats why we need this conversion config