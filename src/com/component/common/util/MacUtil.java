package com.component.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

/**
 * ��ȡĳIP��ַ��Mac��ַ
 * 
 * @author LiuKun
 * @version 1.0
 * @created 2012-8-20 ����7:10:23
 */
public class MacUtil {
	/* === window �� cmd��ʽ ============= */
	/**
	 * @param ip
	 *            IP��ַ
	 * @return mac��ַ
	 */
	public static String getMac(String ip) {
		String mac = "";
		BufferedReader in = null;
		try {
			Process process = Runtime.getRuntime().exec("nbtstat -A " + ip);
			in = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.indexOf("MAC Address") != -1) {
					mac = line.substring(line.indexOf("=") + 1).trim();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Exception e) {
				}
		}
		return mac;
	}

	/* === UDP��ʽ ============= */

	public static String getMac(HttpServletRequest request) {
		String smac = "";
		String sip = request.getHeader("x-forwarded-for");
		if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) {
			sip = request.getHeader("Proxy-Client-IP");
		}
		if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) {
			sip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (sip == null || sip.length() == 0 || "unknown".equalsIgnoreCase(sip)) {
			sip = request.getRemoteAddr();
		}
		MacUtil umac = null;
		try {
			umac = new MacUtil(sip);
		} catch (Exception e) {
		}
		try {
			if (umac != null)
				smac = umac.GetRemoteMacAddr();
		} catch (Exception e) {
		}
		return smac;
	}

	private String sRemoteAddr;

	private int iRemotePort = 137;

	private byte[] buffer = new byte[1024];

	private DatagramSocket ds = null;

	public MacUtil(String strAddr) throws Exception {
		sRemoteAddr = strAddr;
		ds = new DatagramSocket();
	}

	protected final DatagramPacket send(final byte[] bytes) throws IOException {
		DatagramPacket dp = new DatagramPacket(bytes, bytes.length,
				InetAddress.getByName(sRemoteAddr), iRemotePort);
		ds.send(dp);
		return dp;
	}

	protected final DatagramPacket receive() throws Exception {
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		ds.receive(dp);
		return dp;
	}

	protected byte[] GetQueryCmd() throws Exception {
		byte[] t_ns = new byte[50];
		t_ns[0] = 0x00;
		t_ns[1] = 0x00;
		t_ns[2] = 0x00;
		t_ns[3] = 0x10;
		t_ns[4] = 0x00;
		t_ns[5] = 0x01;
		t_ns[6] = 0x00;
		t_ns[7] = 0x00;
		t_ns[8] = 0x00;
		t_ns[9] = 0x00;
		t_ns[10] = 0x00;
		t_ns[11] = 0x00;
		t_ns[12] = 0x20;
		t_ns[13] = 0x43;
		t_ns[14] = 0x4B;

		for (int i = 15; i < 45; i++) {
			t_ns[i] = 0x41;
		}

		t_ns[45] = 0x00;
		t_ns[46] = 0x00;
		t_ns[47] = 0x21;
		t_ns[48] = 0x00;
		t_ns[49] = 0x01;
		return t_ns;
	}

	protected final String GetMacAddr(byte[] brevdata) throws Exception {

		int i = brevdata[56] * 18 + 56;
		String sAddr = "";
		StringBuffer sb = new StringBuffer(17);

		for (int j = 1; j < 7; j++) {
			sAddr = Integer.toHexString(0xFF & brevdata[i + j]);
			if (sAddr.length() < 2) {
				sb.append(0);
			}
			sb.append(sAddr.toUpperCase());
			if (j < 6)
				sb.append('-');
		}
		return sb.toString();
	}

	public final void close() {
		try {
			ds.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public final String GetRemoteMacAddr() throws Exception {
		byte[] bqcmd = GetQueryCmd();
		send(bqcmd);
		DatagramPacket dp = receive();
		String smac = GetMacAddr(dp.getData());
		close();
		return smac;
	}
}