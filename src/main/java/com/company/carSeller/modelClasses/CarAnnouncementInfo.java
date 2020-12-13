package com.company.carSeller.modelClasses;

import com.company.carSeller.entities.ModificationEntity;
import lombok.Data;

import java.util.List;

@Data
public class CarAnnouncementInfo {
    private String title;
    private int price;
    private String description;
    private int marka_id;
    private int color_id;
    private int engine_id;
    private int user_info_id;
    private String firstModification;
    private String secondModification;
    private String thirdModification;
    private String fourthModification;
}
