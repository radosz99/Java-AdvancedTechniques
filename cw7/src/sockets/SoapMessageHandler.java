package sockets;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoapMessageHandler {
    public static MessageFactory messageFactory;

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public SOAPMessage getSoapMessage() {
        return soapMessage;
    }

    public String sender, receiver, message, type;
    public SOAPMessage soapMessage;

    public SoapMessageHandler() throws SOAPException {
        messageFactory = MessageFactory.newInstance();
    }

    public void deserializeMessage(String message) throws IOException, SOAPException {
        InputStream is = new ByteArrayInputStream(message.getBytes());
        soapMessage = messageFactory.createMessage(null, is);

        SOAPHeader header = soapMessage.getSOAPPart().getEnvelope().getHeader();
        SOAPBody body = soapMessage.getSOAPPart().getEnvelope().getBody();
        Node receiveInfo = (Node) header.getElementsByTagNameNS("uri", "receiverId").item(0);
        receiver = receiveInfo.getFirstChild().getTextContent();

        Node sendInfo = (Node) header.getElementsByTagNameNS("uri", "senderId").item(0);
        sender = sendInfo.getFirstChild().getTextContent();

        Node typeInfo = (Node) header.getElementsByTagNameNS("uri", "type").item(0);
        type = typeInfo.getFirstChild().getTextContent();

        Node msg = (Node) body.getElementsByTagName("msg").item(0);
        this.message = msg.getFirstChild().getTextContent();
    }

    public static SOAPMessage serializeMessage(String message, String receiver, String sender, String msgType) throws SOAPException {
        messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();

        Name bodyName =  SOAPFactory.newInstance().createName("msg");
        SOAPBodyElement soapBodyElement=soapBody.addBodyElement(bodyName);
        soapBodyElement.addTextNode(message);

        SOAPHeader header = soapEnvelope.getHeader();

        Name receiverHeader = SOAPFactory.newInstance().createName("receiverId", "pre", "uri");
        SOAPElement receiveElement = header.addChildElement(receiverHeader);
        receiveElement.addTextNode(receiver);

        Name senderHeader = SOAPFactory.newInstance().createName("senderId", "pre", "uri");
        SOAPElement sendElement = header.addChildElement(senderHeader);
        sendElement.addTextNode(sender);

        Name typeHeader = SOAPFactory.newInstance().createName("type", "pre", "uri");
        SOAPElement typeElement = header.addChildElement(typeHeader);
        typeElement.addTextNode(msgType);

        soapMessage.saveChanges();
        return soapMessage;
    }


}
