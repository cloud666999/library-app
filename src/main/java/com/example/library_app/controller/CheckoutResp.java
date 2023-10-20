package com.example.library_app.controller;


import com.example.library_app.model.Book;
import com.example.library_app.model.Checkout_Details;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;


public record CheckoutResp(

        Set<Checkout_DetailsResp> checkoutDetails,
        OffsetDateTime checkoutDate,

        OffsetDateTime returnDate
) {

}
