package com.pongo.taf.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePetRequest {

        private String name;
        private String id;
        private String status;
}
