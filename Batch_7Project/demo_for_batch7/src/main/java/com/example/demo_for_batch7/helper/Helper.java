package com.example.demo_for_batch7.helper;

import com.example.demo_for_batch7.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

public class Helper {


    public static  <U, V>PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {

        List<U> entity = page.getContent();
        LOGGER.info("Initiating  request to  getAllUser");
        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());
        LOGGER.info("Completed request to getAllUser");

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber()+1);
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;

    }


    }
