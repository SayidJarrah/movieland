package com.dkorniichuk.movieland.dao.util;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PaginationHelper<E> {
    private static final int PAGE_SIZE = 5;

    public Page<E> getPage(JdbcTemplate jdbcTemplate,
                           String sqlCountRows,
                           String sqlFetchRows,
                           Object args[],
                           Integer pageNumber,
                           ResultSetExtractor extractor) {
        int rowCount = jdbcTemplate.queryForObject(sqlCountRows, args, Integer.class);
        int pageCount = rowCount / PAGE_SIZE;
        if (rowCount > pageCount * PAGE_SIZE) {
            pageCount++;
        }
        Page<E> page = new Page<>();
        page.setPageAvailable(pageCount);
        //TODO : rewrite, use Optional
        pageNumber = pageNumber != null ? pageNumber : 1;
        page.setPageNumber(pageNumber);
        page.setTotalItems(rowCount);

        int startRow = (pageNumber - 1) * PAGE_SIZE;

        jdbcTemplate.query(sqlFetchRows,
                args,
                new ResultSetExtractor<Object>() {
                    @Override
                    public Object extractData(ResultSet resultSet) throws DataAccessException, SQLException {
                        List pageItems = page.getPageItems();
                        Object allItems = extractor.extractData(resultSet);
                        int endRow = startRow + PAGE_SIZE < rowCount ? startRow + PAGE_SIZE : rowCount;
                        pageItems.add(((List) allItems).subList(startRow, endRow));
                        return page;
                    }
                });
        return page;
    }
}
