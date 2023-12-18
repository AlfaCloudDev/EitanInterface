package com.example.demo.logic;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.demo.boundries.OutgoingDeliveryProposal;

public interface OutgoingDeliveryProposalInterface {
    
    public ResponseEntity<Map<String, Object>> CreateAndUploadDeliveryProposal(OutgoingDeliveryProposal outgoingDeliveryProposal);
}