package com.mohsin.booking.services.Impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.mohsin.booking.config.QRCodeConfig;
import com.mohsin.booking.domain.entity.QRCode;
import com.mohsin.booking.domain.entity.QRCodeStatusEnum;
import com.mohsin.booking.domain.entity.Ticket;
import com.mohsin.booking.exceptions.QRCodeGenerationException;
import com.mohsin.booking.exceptions.QRCodeNotFoundException;
import com.mohsin.booking.repo.QRCodeRepository;
import com.mohsin.booking.services.QRCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QRCodeServiceImpl implements QRCodeService {
    private static final int MAX_WIDTH = 300;
    private static final int MAX_HEIGHT = 300;
    private final QRCodeConfig qrCodeConfig;
    private final QRCodeRepository qrCodeRepository;

    @Override
    public QRCode generateQRCode(Ticket ticket) {
        try {
            UUID uniqueId = UUID.randomUUID();
            String qrCodeImage = generateQRCodeImage(uniqueId);
            QRCode qrCode = new QRCode();
            qrCode.setId(uniqueId);
            qrCode.setValue(qrCodeImage);
            qrCode.setStatus(QRCodeStatusEnum.VALID);
            qrCode.setTicket(ticket);
            return qrCodeRepository.saveAndFlush(qrCode);
        } catch (WriterException | IOException exception) {
            throw new QRCodeGenerationException("Failed to generate QRCode" + exception);
        }

    }

    @Override
    public byte[] getQRCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QRCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(() -> new QRCodeNotFoundException("Ticket id " + ticketId + " not found"));
        try {
            return Base64.getDecoder().decode(qrCode.getValue());
        } catch (IllegalArgumentException exception) {
            log.error(exception.getMessage());
            throw new QRCodeNotFoundException("Ticket id " + ticketId + " not found");
        }
    }

    private String generateQRCodeImage(UUID uniqueId) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeConfig.qrCodeWriter().encode(uniqueId.toString(),
                BarcodeFormat.QR_CODE, MAX_WIDTH, MAX_HEIGHT);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "PNG", output);
            byte[] byteArray = output.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        }

    }
}
