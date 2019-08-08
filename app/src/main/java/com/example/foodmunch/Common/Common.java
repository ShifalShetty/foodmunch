package com.example.foodmunch.Common;

import com.example.foodmunch.Model.User;

public class Common {

    public static User currrentUser;

    public static String convertCodeTOStatus(String status) {
        if(status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "On The Way";
        else
            return  "Shipped";
    }

}
