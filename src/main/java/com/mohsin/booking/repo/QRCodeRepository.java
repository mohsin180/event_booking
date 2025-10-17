package com.mohsin.booking.repo;

import com.mohsin.booking.domain.entity.QRCode;
import com.mohsin.booking.domain.entity.QRCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, UUID> {
    Optional<QRCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID userId);

    Optional<QRCode> findByIdAndStatus(UUID qrCodeId, QRCodeStatusEnum status);
}
