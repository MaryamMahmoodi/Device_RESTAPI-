package com.project.apis_device.model.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceMapper implements RowMapper<Device>
{

    @Override
    public Device mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Device device = new Device();
        device.setId(rs.getInt("id"));
        device.setName(rs.getString("name"));
        device.setBrand(rs.getString("brand"));
        device.setCreated_at(rs.getTimestamp("created_at"));
        device.setCreated_at(rs.getTimestamp("deleted_at"));
        device.setCreated_at(rs.getTimestamp("updated_at"));

        return device;
    }
}
