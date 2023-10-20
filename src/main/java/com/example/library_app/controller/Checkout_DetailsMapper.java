package com.example.library_app.controller;

import com.example.library_app.model.Account;
import com.example.library_app.model.Checkout;
import com.example.library_app.model.Checkout_Details;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Checkout_DetailsMapper {

    Checkout_DetailsResp toCheckoutDetailsResp(Checkout_Details checkoutDetails);

    List<Checkout_DetailsResp> toCheckoutDetailsResp(List<Checkout_Details> checkoutDetails);

    Checkout_Details toCheckoutDetails(Checkout_DetailsReq checkoutReq);

//    Checkout_Details toCheckoutDetails(Checkout_DetailsCheckoutReq checkoutReq);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(Checkout_DetailsUpdateReq req, @MappingTarget Checkout_Details checkoutDetails);
}
