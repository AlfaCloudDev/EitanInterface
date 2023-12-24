package com.eitanmedical.app.bydexporter.logic;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.eitanmedical.app.bydexporter.boundries.OutgoingDeliveryProposal;

public interface OutgoingDeliveryProposalInterface {
    
    public ResponseEntity<Map<String, Object>> CreateAndUploadDeliveryProposal(OutgoingDeliveryProposal outgoingDeliveryProposal);
}