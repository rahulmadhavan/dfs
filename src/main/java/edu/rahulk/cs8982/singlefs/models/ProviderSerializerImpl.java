package edu.rahulk.cs8982.singlefs.models;

import edu.rahulk.cs8982.singlefs.db.utils.serializer.Serializer;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Created by rahulk on 2/11/16.
 */
public class ProviderSerializerImpl implements Serializer<Provider>{

    Logger logger = LoggerFactory.getLogger(ProviderSerializerImpl.class);
    private static final String DELIMITER = ", ";

    @Override
    public String serialize(Provider provider) {

        return new StringBuilder()
                .append(StringEscapeUtils.escapeCsv(provider.getProviderId()))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv(provider.getName()))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv(provider.getZipCode()))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv((provider.getProcedureCode())))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv(provider.getNosOfProcedures()))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv(provider.getStreet1()))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv(provider.getStreet2()))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv(provider.getCity()))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv(provider.getRow()))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv(provider.getGender()))
                .append(DELIMITER)
                .append(StringEscapeUtils.escapeCsv(provider.getState()))
                .toString();
    }

    @Override
    public Provider deserialize(String stringObject) {
        String[] stringFields = stringObject.split(DELIMITER);
        int length = stringFields.length;
        Provider provider = null;

        String state = length >= 10 ? stringFields[10] : null;
        String gender = length >= 9 ? stringFields[9] : null;
        String row = length >= 8 ? stringFields[8] : null;
        String providerId = length >= 0 ? stringFields[0] : null;
        String zipCode = length >= 2 ? stringFields[2] : null;
        String procedureCode = length >= 3 ? stringFields[3] : null;
        String nosOfProcedures = length >= 4 ? stringFields[4] : null;
        String name = length >= 1 ? stringFields[1] : null;
        String street1= length >= 5 ? stringFields[5] : null;
        String street2= length >= 6 ? stringFields[6] : null;
        String city = length >= 7 ? stringFields[7] : null;

        try {


            provider = new Provider(StringEscapeUtils.unescapeCsv(providerId),
                    StringEscapeUtils.unescapeCsv(zipCode),
                    StringEscapeUtils.unescapeCsv(procedureCode));
            provider.setStreet1(StringEscapeUtils.unescapeCsv(street1));
            provider.setStreet2(StringEscapeUtils.unescapeCsv(street2));
            provider.setNosOfProcedures(StringEscapeUtils.unescapeCsv(nosOfProcedures));
            provider.setRow(StringEscapeUtils.unescapeCsv(row));
            provider.setCity(StringEscapeUtils.unescapeCsv(city));
            provider.setName(StringEscapeUtils.unescapeCsv(name));
            provider.setGender(StringEscapeUtils.unescapeCsv(gender));
            provider.setState(StringEscapeUtils.unescapeCsv(state));


        } catch (Exception e) {
            logger.error(String.format("failed deserializing %s",providerId), e);
        }
        return provider;
    }

}
