package com.project.oneglobale_device.model.da.imp;

import com.project.oneglobale_device.model.da.contract.DeviceDaContract;
import com.project.oneglobale_device.model.entity.Device;
import com.project.oneglobale_device.model.entity.DeviceMapper;
import com.project.oneglobale_device.model.enums.AppResponseType;
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
        HashMap<String, Device> result = new HashMap<>();

        try
        {
            if (checkExistById(id))
            {
                entityManager.joinTransaction();
                Device device = entityManager.find(Device.class, id);

                if (device != null)
                {
                    device.setDeleted_at(new Timestamp(System.currentTimeMillis()));
                    entityManager.merge(device);

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
            result.put(AppResponseType.EXCEPTION.name(), null);
        }

        return result;
    }



    @Override
    public List<Device> searchDevicesByBrand(String brand)
    {
        return null;
    }


    private boolean checkExistById(int id)
    {
        try
        {
            return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM devices WHERE id=:id AND deleted_at IS NULL)",
                    new MapSqlParameterSource("id", id), Boolean.class));
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private boolean checkExistByName(int id, String name)
    {
        try
        {
            if (id != 0)
            {
                if (checkExistById(id))
                {
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
