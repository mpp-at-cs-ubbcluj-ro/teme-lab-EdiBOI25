package edi.repository;

import edi.model.ComputerRepairRequest;
import edi.model.RequestStatus;

import java.util.List;

public interface ComputerRepairRequestRepository extends Repository<Integer,ComputerRepairRequest>{
    List<ComputerRepairRequest> findByOwnerName(String name);
    List<ComputerRepairRequest> findByModel(String model);
    List<ComputerRepairRequest> filterByStatus(RequestStatus status);
    List<ComputerRepairRequest> filterByModelAndStatus(String model, RequestStatus status);



}
