package com.bmateam.reactnativeusbserial;

import com.facebook.react.bridge.Promise;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import java.io.IOException;

public class UsbSerialDevice {
    public UsbSerialPort port;
    private static final int SERIAL_TIMEOUT = 1000;
    private static final int READ_BUFFER_SIZE = 512;


    public UsbSerialDevice(UsbSerialPort port) {
        this.port = port;
    }

    public void writeAsync(String value, Promise promise) {

        if (port != null) {

            try {
                port.write(value.getBytes(), SERIAL_TIMEOUT);

                promise.resolve(null);
            } catch (IOException e) {
                promise.reject(e);
            }

        } else {
            promise.reject(getNoPortErrorMessage());
        }
    }

    public void readAsync(Promise promise) {

        if (port != null) {
            byte buffer[] = new byte[READ_BUFFER_SIZE];
            try {
                int readBytes = port.read(buffer, SERIAL_TIMEOUT);
                String byteString = new String(buffer, 0, readBytes);
                while(readBytes > 0 ) {
                    byte buf[] = new byte[READ_BUFFER_SIZE];
                    readBytes = port.read(buf, SERIAL_TIMEOUT);

                    if (readBytes > 0) {
                        String tmp = new String(buf, 0, readBytes);
                        byteString += tmp;
                    }
                }
                Log.i("serial", "read data: " + byteString);
                promise.resolve(byteString);
            } catch (IOException e) {
                promise.reject(e);
            }

        } else {
            promise.resolve(getNoPortErrorMessage());
        }
    }


    private Exception getNoPortErrorMessage() {
        return new Exception("No port present for the UsbSerialDevice instance");
    }
}
