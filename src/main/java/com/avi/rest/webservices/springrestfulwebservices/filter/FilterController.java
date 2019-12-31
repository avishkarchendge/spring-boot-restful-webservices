package com.avi.rest.webservices.springrestfulwebservices.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilterController {

    @GetMapping("/filtering")
    public Filter getSpecificField() {
        return new Filter("value1", "value2", "value3");
    }

    @GetMapping("/filtering-list")
    public List<Filter> getSpecificFieldList() {
        return Arrays.asList(new Filter("value1", "value2", "value3"),
                new Filter("value11", "value12", "value13"));
    }

    @GetMapping("/dynamic-filtering")
    public MappingJacksonValue getSpecificDynamicField() {
        DynamicFilter dynamicFilter = new DynamicFilter("value1", "value2", "value3");
        return getMappingValue(Arrays.asList(dynamicFilter));
    }

    @GetMapping("/dynamic-filtering-list")
    public MappingJacksonValue getSpecificDynamicFieldList() {
        List<DynamicFilter> filterList = Arrays.asList(new DynamicFilter("value1", "value2", "value3"),
                new DynamicFilter("value11", "value12", "value13"));
        return getMappingValue(filterList);
    }

    private MappingJacksonValue getMappingValue(List<DynamicFilter> dynamicFilter) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("field1", "field2");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("dynamicFilter", filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dynamicFilter);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }
}
