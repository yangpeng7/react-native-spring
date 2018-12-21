package com.host.response;

import java.util.List;

public class HomeResponse {

    public List<Bundle> data;

    public class Bundle {
        public String name;
        public String desc;
    }
}

