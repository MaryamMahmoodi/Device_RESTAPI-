package com.project.oneglobale_device.model.service.imp;

import com.project.oneglobale_device.model.enums.AppResponseType;
import com.project.oneglobale_device.model.exception.DuplicationException;
import com.project.oneglobale_device.model.exception.GetDataRetrievalException;
import com.project.oneglobale_device.model.exception.OperationFailedException;
import com.project.oneglobale_device.model.da.imp.DeviceDaImp;
import com.project.oneglobale_device.model.entity.Device;
import com.project.oneglobale_device.model.exception.NotFoundException;
import com.project.oneglobale_device.model.service.contract.DeviceServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DeviceServiceImp implements DeviceServiceContract
{
    @Autowired
    private DeviceDaImp deviceDaImp;

    @Override
    public HashMap<String, Object> insert(Device device)
    {

        HashMap<String, Object> resultMap = deviceDaImp.insert(device);

        try
        {
            if (resultMap.containsKey(AppResponseType.EXCEPTION.name()))
            {
                throw new OperationFailedException("insert operation failed");
            }
            else if (resultMap.containsKey(AppResponseType.EXIST.name()))
            {
                throw new DuplicationException();
            }
        }
        catch (Exception e)
        {
            throw e;
        }

        return resultMap;
    }


    @Override
    public HashMap<String, Device> getOneById(int deviceId)
    {

        HashMap<String, Device> resultMap = deviceDaImp.getOneById(deviceId);
        try
        {
            if (resultMap.containsKey(AppResponseType.NOT_FOUND.name()))
            {
                throw new NotFoundException();
            }
            if (resultMap.containsKey(AppResponseType.EXCEPTION.name()))
            {
                throw new GetDataRetrievalException();
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return resultMap;
    }


    @Override
    public List<Device> getAll()
    {
        List<Device> resultMap = deviceDaImp.getAll();
        try
        {
            if (resultMap == null || resultMap.isEmpty())
            {
                throw new GetDataRetrievalException();

            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return resultMap;
    }

    @Override
    public HashMap<String, Device> update(int deviceId, Device updatedDevice)
    {
        HashMap<String, Device> resultMap = deviceDaImp.update(deviceId, updatedDevice);

        try
        {
            if (resultMap.containsKey(AppResponseType.DUPLICATE.name()))
            {
                throw new DuplicationException();
            }
            if (resultMap.containsKey(AppResponseType.EXCEPTION.name()))
            {
                throw new OperationFailedException("update operation failed");
            }
            else if (resultMap.containsKey(AppResponseType.NOT_FOUND.name()))
            {
                throw new NotFoundException();
            }
        }
        catch (Exception e)
        {
            throw e;
        }

        return resultMap;
    }

    @Override
    public HashMap<String, Device> updatePartial(int deviceId, Device partialDevice)
    {
        HashMap<String, Device> resultMap = deviceDaImp.update(deviceId, partialDevice);

        try
        {
            if (resultMap.containsKey(AppResponseType.DUPLICATE.name()))
            {
                throw new DuplicationException();
            }
            if (resultMap.containsKey(AppResponseType.EXCEPTION.name()))
            {
                throw new OperationFailedException("update operation failed");
            }
            else if (resultMap.containsKey(AppResponseType.NOT_FOUND.name()))
            {
                throw new NotFoundException();
            }
        }
        catch (Exception e)
        {
            throw e;
        }

        return resultMap;
    }

    @Override
    public boolean deleteDevice(int deviceId)
    {
        return false;
    }

    @Override
    public List<Device> searchDevicesByBrand(String brand)
    {
        return null;
    }
}
