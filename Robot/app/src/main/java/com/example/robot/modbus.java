package com.example.robot;

import android.os.AsyncTask;
import android.widget.TextView;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class modbus {

    TCPMasterConnection conn;

    private class connect_slave extends AsyncTask<String, Void, TCPMasterConnection> {
        protected TCPMasterConnection doInBackground(String... conf) {
            InetAddress adr = null;
            int port = Integer.parseInt(conf[1]);
            try {
                adr = InetAddress.getByName(conf[0]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            //           if (port == 0) {
            port = Modbus.DEFAULT_PORT;
            //           }
//            conn = new TCPMasterConnection(adr);
            conn.setPort(port);
            try {
                conn.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;
        }

        protected void onPostExecute(Long result) {

        }
    }

    private class read_regs extends AsyncTask<(TCPMasterConnection, Integer, Integer> {

        @Override
        protected Integer doInBackground(TCPMasterConnection cn, int raddr, int rcount) {
            int tmp;

            ReadMultipleRegistersRequest req = new ReadMultipleRegistersRequest(raddr, rcount);
            ModbusTCPTransaction trans = new ModbusTCPTransaction(cn);
            trans.setConnection(cn);
            trans.setRequest(req);
            try {
                trans.execute();
            } catch (ModbusException e) {
                e.printStackTrace();
            }

            ReadMultipleRegistersResponse res = (ReadMultipleRegistersResponse) trans.getResponse();
/*
        for (int i = 0; i < 20; i++) {
            regs[i] = res.getRegisterValue(i);
        }
*/
            tmp = res.getRegisterValue(1);
            return tmp;
        }

        protected void onPostExecute(Long result) {

        }
    }
}