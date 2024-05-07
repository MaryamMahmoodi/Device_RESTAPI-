package com.project.oneglobale_device.model.da.contract;

import com.project.oneglobale_device.model.entity.Device;

import java.util.HashMap;
import java.util.List;

public interface DeviceDaContract
{

    HashMap<String, Object> insert(Device device);


    HashMap<String, Device> getOneById(int id);


    List<Device> getAll();


    HashMap<String, Device> update(int deviceId, Device updatedDevice);


    HashMap<String, Device> updatePartial(int deviceId, Device partialDevice);


    boolean deleteDevice(int deviceId);


    List<Device> searchDevicesByBrand(String brand);


}
