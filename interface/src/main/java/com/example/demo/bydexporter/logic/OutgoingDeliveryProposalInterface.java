package com.example.demo.bydexporter.logic;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.demo.bydexporter.boundries.OutgoingDeliveryProposal;

public interface OutgoingDeliveryProposalInterface {
    
    public ResponseEntity<Map<String, Object>> CreateAndUploadDeliveryProposal(OutgoingDeliveryProposal outgoingDeliveryProposal);
}