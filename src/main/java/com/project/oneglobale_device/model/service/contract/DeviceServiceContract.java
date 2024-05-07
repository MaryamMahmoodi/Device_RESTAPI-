package com.project.oneglobale_device.model.service.contract;

import com.project.oneglobale_device.model.entity.Device;

import java.util.HashMap;
import java.util.List;

public interface DeviceServiceContract
{

    HashMap<String, Object> insert(Device device);


    HashMap<String, Device> getOneById(int deviceId);


    List<Device> getAll();


    HashMap<String, Device> update(int deviceId, Device updatedDevice);


    HashMap<String, Device> updatePartial(int deviceId, Device partialDevice);


    boolean deleteDevice(int deviceId);


    List<Device> searchDevicesByBrand(String brand);
}
