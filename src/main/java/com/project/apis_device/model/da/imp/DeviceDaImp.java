package com.project.apis_device.model.da.imp;

import com.project.apis_device.model.da.contract.DeviceDaContract;
import com.project.apis_device.model.entity.Device;
import com.project.apis_device.model.entity.DeviceMapper;
import com.project.apis_device.model.enums.AppResponseType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;


@Repository
public class DeviceDaImp implements DeviceDaContract
{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
    @Override
    public HashMap<String, Object> insert(Device device)
    {
        HashMap<String, Object> result = new HashMap<>();

        if (!checkExistByName(0, device.getName()))
        {
            try
            {
                entityManager.joinTransaction();
                entityManager.persist(device);

                result.put(AppResponseType.SUCCESS.name(), device);
            }
            catch (Exception e)
            {
                result.put(AppResponseType.EXCEPTION.name(), e.getCause().getMessage());
            }
        }
        else
        {
            result.put(AppResponseType.EXIST.name(), true);
        }
        return result;
    }


    @Override
    public HashMap<String, Device> getOneById(int id)
    {
        HashMap<String, Device> result = new HashMap<>();
        try
        {
            if (checkExistById(id))
            {
                result.put(AppResponseType.SUCCESS.name(), this.entityManager.find(Device.class, id));
            }
            else
            {
                result.put(AppResponseType.NOT_FOUND.name(), null);
            }

        }
        catch (Exception e)
        {
            result.put(AppResponseType.EXCEPTION.name(), null);
        }
        return result;
    }


    @Override
    public List<Device> getAll()
    {
        try
        {
            List<Device> devices = entityManager.createQuery("SELECT d FROM Device d", Device.class).getResultList();

            if (!devices.isEmpty())
            {
                return devices;
            }
            return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }


    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
    public HashMap<String, Device> update(int id, Device updatedDevice)
    {
        HashMap<String, Device> result = new HashMap<>();
        if (checkExistById(id))
        {
            if (!checkExistByName(id, updatedDevice.getName()))
            {
                try
                {
                    entityManager.joinTransaction();

                    updatedDevice.setUpdated_at(new Timestamp(System.currentTimeMillis()));
                    updatedDevice.setId(id);

                    entityManager.merge(updatedDevice);
                    result.put(AppResponseType.SUCCESS.name(), updatedDevice);

                }
                catch (Exception e)
                {
                    result.put(AppResponseType.EXCEPTION.name(), null);
                }
            }
            else
            {
                result.put(AppResponseType.DUPLICATE.name(), null);
            }
        }
        else
        {
            result.put(AppResponseType.NOT_FOUND.name(), null);
        }

        return result;
    }


    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
    public HashMap<String, Device> updatePartial(int id, Device partialDevice)
    {
        HashMap<String, Device> result = new HashMap<>();

        if (checkExistById(id))
        {
            try
            {
                Device existingDevice = entityManager.find(Device.class, id);

                if (partialDevice.getName() != null)
                {
                    existingDevice.setName(partialDevice.getName());
                }

                if (partialDevice.getBrand() != null)
                {
                    existingDevice.setBrand(partialDevice.getBrand());
                }

                existingDevice.setUpdated_at(new Timestamp(System.currentTimeMillis()));
                entityManager.merge(existingDevice);

                result.put(AppResponseType.SUCCESS.name(), existingDevice);
            }
            catch (Exception e)
            {
                result.put(AppResponseType.EXCEPTION.name(), null);
            }
        }
        else
        {
            result.put(AppResponseType.NOT_FOUND.name(), null);
        }

        return result;
    }


    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
    @Override
    public HashMap<String, Device> deleteById(int id)
    {
        // Soft delete: Set the 'deleted_at' field instead of removing from the database
        // This allows us to restore the device if needed

        HashMap<String, Device> result = new HashMap<>();

        try
        {
            // Check if the device with the given ID exists
            if (checkExistById(id))
            {
                // Begin a transaction
                entityManager.joinTransaction();
                Device device = entityManager.find(Device.class, id);

                if (device != null)
                {
                    // Set the 'deleted_at' field to the current timestamp
                    device.setDeleted_at(new Timestamp(System.currentTimeMillis()));
                    entityManager.merge(device);

                    // Soft delete successful
                    result.put(AppResponseType.SUCCESS.name(), null);
                }
                else
                {
                    result.put(AppResponseType.FAILED.name(), null);
                }
            }
            else
            {
                result.put(AppResponseType.NOT_FOUND.name(), null);
            }
        }
        catch (Exception e)
        {
            // Exception occurred during soft delete operation
            result.put(AppResponseType.EXCEPTION.name(), null);
        }

        return result;
    }




    @Override
    public List<Device> searchDevicesByBrand(String brand) {
       try
       {
           return entityManager.createQuery("SELECT d FROM Device d WHERE d.brand = :brand AND d.deleted_at IS NULL", Device.class)
                   .setParameter("brand", brand)
                   .getResultList();
       }
       catch (Exception e)
       {
           throw e;
       }

    }


    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
    @Override
    public HashMap<String, Device> restore(int id)
    {
        // Restoration: Restore a soft-deleted device by setting 'deleted_at' to null
        HashMap<String, Device> result = new HashMap<>();

            try
            {
                entityManager.joinTransaction();
                Device device = entityManager.find(Device.class, id);

                // Check if the device was previously soft-deleted
                if (device.getDeleted_at() != null)
                {
                    // Restore the device by setting 'deleted_at' to null
                    device.setDeleted_at(null);
                    entityManager.merge(device);

                    // Restoration successful
                    result.put(AppResponseType.SUCCESS.name(), device);
                }
                else
                {
                    result.put(AppResponseType.FAILED.name(), null);
                }
            }
            catch (Exception e)
            {
                result.put(AppResponseType.EXCEPTION.name(), null);
            }

        return result;
    }


    // Check if a device with the given ID exists and is not soft-deleted
    // Returns true if the device exists and is not soft-deleted, false otherwise
    private boolean checkExistById(int id)
    {
        try
        {
            // The query returns true if such a device exists, false otherwise
            return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM devices WHERE id=:id AND deleted_at IS NULL)",
                    new MapSqlParameterSource("id", id), Boolean.class));
        }
        catch (Exception e)
        {
            return false;
        }
    }


    // Check if a device with the given name exists (excluding the device with the given ID if provided)
    // Returns true if a device with the given name exists, false otherwise
    private boolean checkExistByName(int id, String name)
    {
        try
        {
            if (id != 0)
            {
                if (checkExistById(id))
                {
                    // If the device with the given ID exists, check if another device with the same name exists (excluding the device with the given ID)
                    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("id", id)
                            .addValue("name", name);
                    boolean exists = !(namedParameterJdbcTemplate.query("select * from devices where id<>:id and name=:name",
                            mapSqlParameterSource, new DeviceMapper()).isEmpty());
                    if (exists)
                    {
                        //log.info("Device with name '{}' exists with ID '{}'", name, id);
                    }
                    else
                    {
                        //log.info("Device with name '{}' does not exist with ID '{}'", name, id);
                    }
                    return exists;
                }

                return false;
            }
            else
            {
                // Check if any device with the given name exists (without considering the device ID)
                boolean exists = !(namedParameterJdbcTemplate.query("select * from devices where name=:name",
                        new MapSqlParameterSource().addValue("name", name), new DeviceMapper()).isEmpty());
                if (exists)
                {
                    //log.info("Device with name '{}' exists", name);
                }
                else
                {
                    //log.info("Device with name '{}' does not exist", name);
                }
                return exists;
            }
        }
        catch (Exception e)
        {
            return false;
        }
    }


}
