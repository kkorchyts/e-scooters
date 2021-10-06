package com.kkorchyts.domain.entities;

public class VehicleModelCount {
    private VehicleModel vehicleModel;
    private long count;

    public VehicleModelCount(VehicleModel vehicleModel, long count) {
        this.vehicleModel = vehicleModel;
        this.count = count;
    }

    public VehicleModel getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
