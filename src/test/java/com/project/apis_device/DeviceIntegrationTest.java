package com.project.apis_device;

import com.project.apis_device.controller.DeviceController;
import com.project.apis_device.model.da.imp.DeviceDaImp;
import com.project.apis_device.model.entity.Device;
import com.project.apis_device.model.enums.AppResponseType;
import com.project.apis_device.model.service.imp.DeviceServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;



@SpringBootTest
public class DeviceIntegrationTest
{
    @Autowired
    private DeviceDaImp deviceDaImp;

    @MockBean
    private DeviceServiceImp deviceServiceImp;


    @InjectMocks
    private DeviceController deviceController;

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


}
