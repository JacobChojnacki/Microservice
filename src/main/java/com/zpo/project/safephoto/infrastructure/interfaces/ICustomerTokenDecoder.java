package com.zpo.project.safephoto.infrastructure.interfaces;
import com.zpo.project.safephoto.infrastructure.models.CustomerModel;

public interface ICustomerTokenDecoder {
    CustomerModel getCustomerFromToken(String token) throws Exception;
}
