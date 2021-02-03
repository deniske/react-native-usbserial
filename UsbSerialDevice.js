
class UsbSerialDevice {

    constructor(UsbSerialModule, UsbSerialDevNativeObject) {
        this.UsbSerialModule = UsbSerialModule;
        this.id = UsbSerialDevNativeObject.id;
    }

    writeAsync(value = "") {
        console.log('writeAsync')
        return this.UsbSerialModule.writeInDeviceAsync(this.id, value);
    }

    readAsync() {
        console.log('readAsync')
        return this.UsbSerialModule.readFromDeviceAsync(this.id);
    }
}

module.exports = UsbSerialDevice;
