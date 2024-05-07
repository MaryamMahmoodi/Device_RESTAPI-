package com.project.apis_device;


import com.project.apis_device.model.da.imp.DeviceDaImp;
import com.project.apis_device.model.entity.Device;
import com.project.apis_device.model.enums.AppResponseType;
import com.project.apis_device.model.service.imp.DeviceServiceImp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest
public class DeviceIntegrationTest
{
    @Autowired
    private DeviceDaImp deviceDaImp;

    @MockBean
    private DeviceServiceImp deviceServiceImp;


    @MockBean
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void testInsertDevice_Success() {

        Device device = new Device();
        device.setName("Test Device");
        device.setBrand("Test Brand");

        // Insert the device
        HashMap<String, Object> result = deviceDaImp.insert(device);

        // Verify the result
        assertEquals(AppResponseType.SUCCESS.name(), result.keySet().iterator().next());
    }


    @Test
    public void testGetOneById_NotFound() {
        // Mock the behavior of namedParameterJdbcTemplate to return null
        when(namedParameterJdbcTemplate.queryForObject(anyString(), anyMap(), (RowMapper<Object>) any())).thenReturn(null);

        // Call the method to be tested
        HashMap<String, Device> result = deviceDaImp.getOneById(1);

        // Verify the result
        assertEquals(1, result.size());
        assertEquals(AppResponseType.NOT_FOUND.name(), result.keySet().iterator().next());
        assertEquals(null, result.get(AppResponseType.NOT_FOUND.name()));
    }


}
