package com.example.library_app.controller;

import com.example.library_app.model.Checkout;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {Checkout_DetailsMapper.class})
public interface CheckoutMapper {

    CheckoutResp toCheckoutResp(Checkout checkout);

    Checkout toCheckout(CheckoutReq checkoutReq);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCheckout(CheckoutUpdateReq checkoutUpdateReq, @MappingTarget Checkout checkout);
}
