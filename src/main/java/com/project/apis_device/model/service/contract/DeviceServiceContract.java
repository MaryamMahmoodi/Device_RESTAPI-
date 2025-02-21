package com.project.apis_device.model.service.contract;

import com.project.apis_device.model.entity.Device;

import java.util.HashMap;
import java.util.List;

public interface DeviceServiceContract
{

    HashMap<String, Object> insert(Device device);


    HashMap<String, Device> getOneById(int deviceId);


    List<Device> getAll();


    HashMap<String, Device> update(int deviceId, Device updatedDevice);


    HashMap<String, Device> updatePartial(int deviceId, Device partialDevice);

    HashMap<String, Device> deleteById(int deviceId);

    List<Device> searchDevicesByBrand(String brand);

    HashMap<String, Device> restore(int id);

}
