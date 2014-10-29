package de;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * Main Class of LEGO Mindstorms client getting data from Google Glass
 * 
 * @author wombat
 *
 */
public class MindGlass extends WebSocketServer {
 
	private static RegulatedMotor leftMotor;
	private static RegulatedMotor rightMotor;

	public MindGlass() throws UnknownHostException {
		super(new InetSocketAddress(8888));
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	}

	public static void main(String... args) throws Exception {
		System.out.println("### Starting up ###");

		WebSocketImpl.DEBUG = true;
		MindGlass mindGlass = new MindGlass();
		mindGlass.start();

		Button.ENTER.waitForPressAndRelease();

		mindGlass.stop();
		System.out.println("### Shutting DOWN ###");
	}

	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		System.out.println( "Connection closed ");
	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		arg1.printStackTrace();
	}

	@Override
	public void onMessage(WebSocket arg0, String arg1) {
		System.out.println( "received: " + arg1 );

//		float a = 4.5f * Float.valueOf(arg1);
		
		int i = Integer.valueOf(arg1);

		leftMotor.rotate(i, true);
		rightMotor.rotate(i * -1, true);
	}

	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		System.out.println( "opened connection" );		
	}
}