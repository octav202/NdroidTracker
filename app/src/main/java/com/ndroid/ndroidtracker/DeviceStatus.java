package com.ndroid.ndroidtracker;

public class DeviceStatus {

	private int deviceId;
	private Integer lock;
	private Integer wipeData;
	private Integer encryptStorage;
	private Integer reboot;
	private Integer triggered;

	public DeviceStatus() {
		this.deviceId = 0;
		this.lock = 0;
		this.wipeData = 0;
		this.encryptStorage = 0;
		this.reboot = 0;
		this.triggered = 0;
	}

	public DeviceStatus(int deviceId, Integer lock, Integer wipeData, Integer encryptStorage, Integer reboot, Integer triggered) {
		this.deviceId = deviceId;
		this.lock = lock;
		this.wipeData = wipeData;
		this.encryptStorage = encryptStorage;
		this.reboot = reboot;
		this.triggered = triggered;
	}

	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getLock() {
		return lock;
	}
	public void setLock(Integer lock) {
		this.lock = lock;
	}
	public Integer getWipeData() {
		return wipeData;
	}
	public void setWipeData(Integer wipeData) {
		this.wipeData = wipeData;
	}
	public Integer getEncryptStorage() {
		return encryptStorage;
	}
	public void setEncryptStorage(Integer encryptStorage) {
		this.encryptStorage = encryptStorage;
	}
	public Integer getReboot() {
		return reboot;
	}
	public void setReboot(Integer reboot) {
		this.reboot = reboot;
	}
	public Integer getTriggered() {
		return triggered;
	}
	public void setTriggered(Integer triggered) {
		this.triggered = triggered;
	}

    @Override
    public String toString() {
        return "DeviceStatus [deviceId=" + deviceId + ", lock=" + lock + ", wipeData=" + wipeData + ", encryptStorage="
                + encryptStorage + ", reboot=" + reboot + ", triggered=" + triggered + "]";
    }
	

}
