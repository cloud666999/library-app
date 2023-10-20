package com.example.library_app.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckOutRq {
        private int userId;
    private List<CheckOutDt> checkOutDtList;
}
