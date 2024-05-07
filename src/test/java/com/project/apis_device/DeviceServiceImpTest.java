package com.project.apis_device;

import com.project.apis_device.model.da.imp.DeviceDaImp;
import com.project.apis_device.model.entity.Device;
import com.project.apis_device.model.enums.AppResponseType;
import com.project.apis_device.model.exception.DuplicationException;
import com.project.apis_device.model.exception.OperationFailedException;
import com.project.apis_device.model.service.imp.DeviceServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class DeviceServiceImpTest
{
    @Mock
    private DeviceDaImp deviceDaImp;

    @InjectMocks
    private DeviceServiceImp deviceServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsert_Success() {
        // Prepare device object
        Device device = new Device();
        device.setName("Test Device");
        device.setBrand("Test Brand");

        // Prepare expected result
        HashMap<String, Object> expectedResult = new HashMap<>();
        expectedResult.put(AppResponseType.SUCCESS.name(), device);

        // Mock behavior of deviceDaImp.insert
        when(deviceDaImp.insert(any(Device.class))).thenReturn(expectedResult);

        // Call the method to be tested
        HashMap<String, Object> result = deviceServiceImp.insert(device);

        // Verify result
        assertEquals(expectedResult, result);
    }

    @Test
    void testInsert_Exception() {
        // Prepare device object
        Device device = new Device();
        device.setName("Test Device");
        device.setBrand("Test Brand");

        // Prepare expected result
        HashMap<String, Object> expectedResult = new HashMap<>();
        expectedResult.put(AppResponseType.EXCEPTION.name(), "Insertion failed");

        // Mock behavior of deviceDaImp.insert to throw an exception
        when(deviceDaImp.insert(any(Device.class))).thenThrow(new RuntimeException("Some error"));

        // Verify that an OperationFailedException is thrown
        assertThrows(RuntimeException.class, () -> {
            deviceServiceImp.insert(device);
        });
    }

    @Test
    void testInsert_DuplicationException() {
        // Prepare device object
        Device device = new Device();
        device.setName("Test Device");
        device.setBrand("Test Brand");

        // Prepare expected result
        HashMap<String, Object> expectedResult = new HashMap<>();
        expectedResult.put(AppResponseType.EXIST.name(), true);

        // Mock behavior of deviceDaImp.insert to return duplication result
        when(deviceDaImp.insert(any(Device.class))).thenReturn(expectedResult);

        // Verify that a DuplicationException is thrown
        assertThrows(DuplicationException.class, () -> {
            deviceServiceImp.insert(device);
        });
    }
}
