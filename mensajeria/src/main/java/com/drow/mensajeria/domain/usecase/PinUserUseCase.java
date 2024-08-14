package com.drow.mensajeria.domain.usecase;

import com.drow.mensajeria.domain.api.IPinUserServicePort;
import com.drow.mensajeria.domain.exception.PinAlreadyAssigned;
import com.drow.mensajeria.domain.exception.PinDoesntExist;
import com.drow.mensajeria.domain.model.PinUserModel;
import com.drow.mensajeria.domain.spi.IPinUserPersistencePort;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

@RequiredArgsConstructor
public class PinUserUseCase implements IPinUserServicePort {

    private final IPinUserPersistencePort pinUserPersistencePort;
    private final Random random;
    @Value("${twilioAccountSid}")
    private String twilioAccountSid;
    @Value("${twilioPassword}")
    private String password;
    @Value("${twilioPhoneNumber}")
    private String twilioPhoneNumber;
    @Value("${twilioMyPhoneNumber}")
    private String twilioMyPhoneNumber;

    @Override
    public void savePinUser(Integer userId, Integer orderId) {
        if (pinUserPersistencePort.findByUserIdAndOrderId(userId, orderId) != null) {
            Integer pin = pinUserPersistencePort.findByUserIdAndOrderId(userId, orderId).getPin();
            throw new PinAlreadyAssigned("Ya se ha generado un pin para esta orden " + pin);
        }
        PinUserModel pinUserModel = new PinUserModel();
        pinUserModel.setUserId(userId);
        pinUserModel.setOrderId(orderId);
        int randomNumber = random.nextInt((99999 - 10000) + 1) + 10000;
        pinUserModel.setPin(randomNumber);
        Twilio.init(twilioAccountSid, password);
        Message.creator(
                new com.twilio.type.PhoneNumber(twilioMyPhoneNumber),
                new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                "Su pin de seguridad es " + randomNumber).create();
        pinUserPersistencePort.savePinUser(pinUserModel);
    }

    @Override
    public PinUserModel findPinUser(Integer pin) {
        PinUserModel pinUserModel = pinUserPersistencePort.findByPin(pin);
        if (pinUserModel == null) {
            throw new PinDoesntExist("El pin no existe");
        }
        PinUserModel response = new PinUserModel();
        response.setUserId(pinUserModel.getUserId());
        response.setOrderId(pinUserModel.getOrderId());
        response.setPin(pinUserModel.getPin());
        return pinUserModel;
    }
}
