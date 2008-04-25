// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.talend.dataquality.domain.Domain;
import org.talend.dataquality.domain.DomainFactory;
import org.talend.dataquality.domain.LiteralValue;
import org.talend.dataquality.domain.RangeRestriction;
import org.talend.dataquality.domain.RealNumberValue;
import org.talend.dataquality.domain.TextValue;

/**
 * @author scorreia
 * 
 * Helper class for Domain object.
 */
public class DomainHelper {

    private static final DomainFactory DOMAIN = DomainFactory.eINSTANCE;

    /**
     * Method "createDomain" creates a new Domain with the given name.
     * 
     * @param name the name of the domain (could be null)
     * @return the new domain.
     */
    public static Domain createDomain(String name) {
        Domain domain = DOMAIN.createDomain();
        domain.setName(name);

        return domain;
    }

    /**
     * Method "addRangeRestriction" creates a new range restriction and adds it to the given domain.
     * 
     * @param domain the domain to which a new range restriction will be added
     * @return the newly created range restriction
     */
    public static RangeRestriction addRangeRestriction(Domain domain) {
        RangeRestriction rangeRestriction = DOMAIN.createRangeRestriction();
        domain.getRanges().add(rangeRestriction);
        return rangeRestriction;
    }

    /**
     * Method "createContiguousClosedBinsIntoDomain".
     * 
     * @param domainName (can be null)
     * @param nbBins the number of bins to create
     * @param min the minimal value
     * @param max the maximal value
     * @return the domain
     */
    public static Domain createContiguousClosedBinsIntoDomain(String domainName, int nbBins, double min, double max) {
        double[] bins = BinsHandler.generateClosedBins(min, max, nbBins);
        return createContiguousClosedBinsIntoDomain(domainName, bins);
    }

    /**
     * Method "createContiguousClosedBinsIntoDomain".
     * 
     * @param domainName the domain name (can be null)
     * @param bins the "closed" bins (the first value of array is the beginning of the bins and the last value of the
     * array is the end of bins)
     * @return the new Domain
     */
    public static Domain createContiguousClosedBinsIntoDomain(String domainName, double[] bins) {
        Domain domain = createDomain(domainName);
        for (int i = 0; i < bins.length - 1; i++) {
            double min = bins[i];
            double max = bins[i + 1];
            RangeRestriction rangeRestriction = createRealRangeRestriction(min, max);
            domain.getRanges().add(rangeRestriction);
        }
        return domain;
    }

    /**
     * Method "createRangeRestriction".
     * 
     * @param min the min value of the range
     * @param max the max value of the range
     * @return the new Range restriction.
     */
    public static RangeRestriction createRealRangeRestriction(double min, double max) {
        RangeRestriction rangeRestriction = DOMAIN.createRangeRestriction();
        rangeRestriction.setLowerValue(createRealNumberValue(null, min));
        rangeRestriction.setUpperValue(createRealNumberValue(null, max));
        return rangeRestriction;
    }

    public static RangeRestriction createStringRangeRestriction(String min, String max) {
        RangeRestriction rangeRestriction = DOMAIN.createRangeRestriction();
        rangeRestriction.setLowerValue(createStringValue(null, min));
        rangeRestriction.setUpperValue(createStringValue(null, max));
        return rangeRestriction;
    }

    public static TextValue createStringValue(String meaning, String value) {
        TextValue textValue = DomainFactory.eINSTANCE.createTextValue();
        textValue.setEncodeValueMeaning(meaning);
        textValue.setValue(value);
        return textValue;
    }

    /**
     * Method "createRealNumberValue".
     * 
     * @param meaning the meaning of the value (can be null)
     * @param value the value
     * @return a real number value
     */
    public static RealNumberValue createRealNumberValue(String meaning, double value) {
        RealNumberValue realNumberValue = DomainFactory.eINSTANCE.createRealNumberValue();
        realNumberValue.setEncodeValueMeaning(meaning);
        realNumberValue.setValue(value);
        return realNumberValue;
    }

    /**
     * Method "getNumberOfBins".
     * 
     * @param domain the domain containing bins
     * @return the number of bins for this domain
     */
    public static int getNumberOfBins(Domain domain) {
        assert domain != null;
        // TODO scorreia do check that the given domain is really a bins container.
        return domain.getRanges().size();
    }

    /**
     * Method "getMinBinValue".
     * 
     * @param domain
     * @return the lower value of the first bin.
     */
    public static double getMinBinValue(Domain domain) {
        EList<RangeRestriction> ranges = domain.getRanges();
        if (ranges.isEmpty()) {
            throw new IllegalArgumentException("No range contained in given domain (name= " + domain.getName() + ")");
        }
        RangeRestriction r1 = ranges.get(0);
        if (r1 == null) {
            throw new IllegalArgumentException("First range is null in given domain (name= " + domain.getName() + ")");
        }
        return getRealValue(r1.getLowerValue());
    }

    /**
     * Method "getMaxBinValue".
     * 
     * @param domain
     * @return the higher value of the last bin.
     */
    public static double getMaxBinValue(Domain domain) {
        EList<RangeRestriction> ranges = domain.getRanges();
        if (ranges.isEmpty()) {
            throw new IllegalArgumentException("No range contained in given domain (name= " + domain.getName() + ")");
        }
        int lastIdx = ranges.size() - 1;
        RangeRestriction r1 = ranges.get(lastIdx);
        if (r1 == null) {
            throw new IllegalArgumentException("Last range is null in given domain (name= " + domain.getName() + ")");
        }
        return getRealValue(r1.getUpperValue());
    }

    public static String getMinValue(RangeRestriction range) {
        return getTextValue(range.getLowerValue());
    }

    public static String getMaxValue(RangeRestriction range) {
        return getTextValue(range.getUpperValue());
    }

    private static double getRealValue(LiteralValue object) {
        RealNumberValue upperValue = DataqualitySwitchHelper.REAL_NB_VALUE_SWITCH.doSwitch(object);
        if (upperValue == null) {
            throw new IllegalArgumentException(object + " does not contain real value.");
        }
        return upperValue.getValue();
    }

    private static String getTextValue(LiteralValue object) {
        TextValue textValue = DataqualitySwitchHelper.TEXT_VALUE_SWITCH.doSwitch(object);
        if (textValue == null) {
            return null;
        }
        return textValue.getValue();
    }

    /**
     * Method "getDomains".
     * 
     * @param objects some objects
     * @return only the Domain objects
     */
    public static List<Domain> getDomains(Collection<EObject> objects) {
        List<Domain> domains = new ArrayList<Domain>();
        for (EObject object : objects) {
            Domain domain = DataqualitySwitchHelper.DOMAIN_SWITCH.doSwitch(object);
            if (domain != null) {
                domains.add(domain);
            }
        }
        return domains;
    }
}
