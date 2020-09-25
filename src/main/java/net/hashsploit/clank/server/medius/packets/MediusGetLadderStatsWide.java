package net.hashsploit.clank.server.medius.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import net.hashsploit.clank.server.MediusClient;
import net.hashsploit.clank.server.DataPacket;
import net.hashsploit.clank.server.RTPacketId;
import net.hashsploit.clank.server.medius.MediusCallbackStatus;
import net.hashsploit.clank.server.medius.MediusConstants;
import net.hashsploit.clank.server.medius.MediusPacket;
import net.hashsploit.clank.server.medius.MediusPacketType;
import net.hashsploit.clank.server.medius.objects.MediusMessage;
import net.hashsploit.clank.utils.Utils;

public class MediusGetLadderStatsWide extends MediusPacket {

	private byte[] messageID = new byte[MediusConstants.MESSAGEID_MAXLEN.getValue()];
	private byte[] accountOrClanID = new byte[4]; // int
	
	public MediusGetLadderStatsWide() {
		super(MediusPacketType.GetLadderStatsWide,MediusPacketType.GetLadderStatsWideResponse);
	}

	@Override
	public void read(MediusMessage mm) {
		// Process the packet
		ByteBuffer buf = ByteBuffer.wrap(mm.getPayload());
		buf.get(messageID);
		buf.get(accountOrClanID);
		logger.fine("Message ID : " + Utils.bytesToHex(messageID));
		logger.fine("Account or Clan ID: " + Utils.bytesToHex(accountOrClanID));
	}
	
	@Override
	public MediusMessage write(MediusClient client) {

		byte[] stats = Utils.buildByteArrayFromString("0", MediusConstants.LADDERSTATSWIDE_MAXLEN.getValue());

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(messageID);
			outputStream.write(Utils.intToBytes(MediusCallbackStatus.MediusSuccess.getValue()));
			outputStream.write(accountOrClanID);
			outputStream.write(stats);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new MediusMessage(responseType, outputStream.toByteArray());
	}

}