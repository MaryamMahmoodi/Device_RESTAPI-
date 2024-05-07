package com.project.oneglobale_device.controller;

import com.project.oneglobale_device.model.entity.Device;
import com.project.oneglobale_device.model.enums.AppResponseType;
import com.project.oneglobale_device.model.exception.GetDataRetrievalException;
import com.project.oneglobale_device.model.service.imp.DeviceServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/device")
public class DeviceController
{

    @Autowired
    private DeviceServiceImp deviceServiceImp;


    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insert(@Valid @RequestBody Device device)
    {
        HashMap<String, Object> resultMap = deviceServiceImp.insert(device);

        if (resultMap.containsKey(AppResponseType.SUCCESS.name()))
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap.get(AppResponseType.SUCCESS.name()));
        }
        else if (resultMap.containsKey(AppResponseType.FAILED.name()) || resultMap.containsKey(AppResponseType.EXCEPTION.name()))
        {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resultMap.get(AppResponseType.EXCEPTION.name()));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(AppResponseType.EXIST.name());
        }
    }


    @GetMapping(value = "/getOne/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") int id)
    {
        HashMap<String, Device> result = deviceServiceImp.getOneById(id);

        if (result.containsKey(AppResponseType.SUCCESS.name()))
        {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else if (result.containsKey(AppResponseType.NOT_FOUND.name()))
        {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/getAll")
    public List<Device> getAll() throws GetDataRetrievalException
    {
        try
        {
            return deviceServiceImp.getAll();
        }
        catch (Exception e)
        {
            return Collections.emptyList();
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable int id, @RequestBody Device updatedDevice)
    {
        try
        {
            HashMap<String, Device> resultMap = deviceServiceImp.update(id, updatedDevice);

            if (resultMap.containsKey(AppResponseType.SUCCESS.name()))
            {
                return ResponseEntity.ok(resultMap.get(AppResponseType.SUCCESS.name()));
            }
            else if (resultMap.containsKey(AppResponseType.NOT_FOUND.name()))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
            }
            else if (resultMap.containsKey(AppResponseType.DUPLICATE.name()))
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Device already exists");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update operation failed");
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PutMapping("/updatePartial/{id}")
    public ResponseEntity<?> updatePartialDevice(@PathVariable int id, @RequestBody Device partialDevice)
    {
        try
        {
            HashMap<String, Device> resultMap = deviceServiceImp.updatePartial(id, partialDevice);

            if (resultMap.containsKey(AppResponseType.SUCCESS.name()))
            {
                return ResponseEntity.ok(resultMap.get(AppResponseType.SUCCESS.name()));
            }
            else if (resultMap.containsKey(AppResponseType.NOT_FOUND.name()))
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
            }
            else if (resultMap.containsKey(AppResponseType.DUPLICATE.name()))
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Device already exists");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update operation failed");
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

}
