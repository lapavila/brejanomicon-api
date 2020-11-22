package org.gujavasc.brejanomicon.api.read.repository;

import org.gujavasc.brejanomicon.api.read.filter.CityFilter;
import org.gujavasc.brejanomicon.api.read.mapper.CityMapper;
import org.gujavasc.brejanomicon.api.model.City;
import org.gujavasc.brejanomicon.core.jdbc.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityReaderRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<City> findAll() {
        return SqlQuery.create(jdbcTemplate)
                .sql("SELECT city.id,")
                .sql("       city.name,")
                .sql("       city.capital,")
                .sql("       state.id,")
                .sql("       state.abbreviation,")
                .sql("       state.name")
                .sql("  FROM city")
                .sql("       LEFT OUTER JOIN state on state.id = city.state_id")
                .query(CityMapper.createCityRowMapper());
    }

    public List<City> filter(CityFilter filter) {
        return SqlQuery.create(jdbcTemplate)
                .sql("SELECT city.id,")
                .sql("       city.name,")
                .sql("       city.capital,")
                .sql("       state.id,")
                .sql("       state.abbreviation,")
                .sql("       state.name")
                .sql("  FROM city")
                .sql("       LEFT OUTER JOIN state on state.id = city.state_id")
                .where().like("city.name", filter.getName())
                .and().eq("city.capital", filter.getCapital())
                .and().eq("state.abbreviation", filter.getStateAbbreviation())
                .query(CityMapper.createCityRowMapper());
    }

}